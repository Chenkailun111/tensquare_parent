package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部记录
     * @return
     */
    public List<Spit>  findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据ID查询吐槽实体
     * @param id
     * @return
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     *  发布吐槽
     * @param spit
     */
    public void add(Spit spit){
        spit.setId(idWorker.nextId()+"");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);// 分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//评论数
        spit.setState("1");//状态

        /**
         * 逻辑，最开始添加一条记录，没有父节点，
         * 然后下面的回复中，添加一条回复，判断父结点，回复数+1
         */
        //评论处理，如果当前添加的吐槽有父节点，就将父节点的评论数+1
        //比如某个大类下的评论或某个人的回复吐槽
        if(spit.getParentid()!=null &&  !"".equals(spit.getParentid())){
            Query query=new Query();
            //添加条件，为父节点时，评论数+1
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update=new Update();
            //父节点评论数+1
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");

        }

        spitDao.save(spit);
    }

    /**
     * 修改吐槽
     * @param spit
     */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /**
     * 删除吐槽
     * @param id
     */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    /**
     * 根据上级ID查询吐槽内容
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentid(String parentid,int page,int size){
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return  spitDao.findByParentid(parentid,pageRequest);
    }



    /**
     *点赞
     *@param id
     */
    public void updateThumbup(String id){
        Spit spit = spitDao.findById(id).get();
        spit.setThumbup(spit.getThumbup()+1);
        spitDao.save(spit);
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    /*以上方法虽然实现起来比较简单，但是执行效率并不高，
    因为我只需要将点赞数加1就可以了，没必要查询出所有字段修改后再更新所有字段。
    我们可以使用MongoTemplate类来实现对某列的操作。*/
    /**
     * 点赞
     * @param id
     */
    public void updateThumnup(String id){
        //查询条件构建
        Query query=new Query();
        query.addCriteria(Criteria.where( "_id").is(id) );//{_id: x}
        //修改内容
        Update update =new Update();
        update.inc("thumbup",1);  //$inc
        mongoTemplate.updateFirst(query, update,"spit" );
    }

}
