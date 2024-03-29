package com.tensquare.friend.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 好友表实体类
 */
@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class) //代表可以有联合主键
public class Friend implements Serializable {

    @Id
    private String userid;

    @Id
    private String friendid;

    // 0 单向喜欢 1双向喜欢
    private String islike;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }
}
