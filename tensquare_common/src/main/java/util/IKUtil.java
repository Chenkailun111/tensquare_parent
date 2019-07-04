package util;


import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

/**
 * IK分词器工具类
 */
public class IKUtil {

    /**
     * 分词
     * @param content
     * @param spiltChar
     * @return
     */
    public static String spilt(String content,String spiltChar) throws IOException {
        StringReader stringReader=new StringReader(content);
        IKSegmenter ikSegmenter=new IKSegmenter(stringReader,true);

        Lexeme lexeme = null;
        StringBuilder stringBuilder=new StringBuilder("");
        while( (lexeme = ikSegmenter.next())!=null ){
            stringBuilder.append(lexeme.getLexemeText()+spiltChar);
        }
        return stringBuilder.toString();
    }

}
