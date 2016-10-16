package com.mmednet.robotGuide.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha on 2016/9/19.
 */
public class DicUtil {

    static Map<String, Integer> catMap=new HashMap<String, Integer>();

    static Map<Integer, String> initCatMap=new HashMap<Integer, String>();

    static {
        initCatMap.put(1001, "全身");
        initCatMap.put(1002, "头颈");
        initCatMap.put(1003, "胸部");
        initCatMap.put(1004, "腹部");
        initCatMap.put(1005, "四肢");


        catMap.put("全身", 1001);
        catMap.put("身体", 1001);
        catMap.put("头", 1002);
        catMap.put("脑袋", 1002);
        catMap.put("头部", 1002);
        catMap.put("脖子", 1002);
        catMap.put("脖子", 1002);
        catMap.put("肚子", 1004);
        catMap.put("腹部", 1004);
    }

    /**
     * 根据语音结果获得关键词
     * @param voice
     * @return
     */
    public static String getKeyWord(String voice){
        if(catMap.get(voice)==null){
            return "";
        }
        return initCatMap.get(catMap.get(voice));
    }


}
