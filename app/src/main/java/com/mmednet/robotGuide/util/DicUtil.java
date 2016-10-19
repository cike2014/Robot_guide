package com.mmednet.robotGuide.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha on 2016/9/19.
 */
public class DicUtil {

    /** 身体症状关键字 **/
    private static final String BODY_WORDS = "全身、浑身、皮肤、淋巴、没劲、烧、体温、汗、冷、吃、梦、睡、胃口、晕、迷糊、昏倒、虚、醒、食欲、饿、厌食、饱、抽、痒、黄";

    /** 头颈部症状关键字 **/
    private static final String HEAD_WORDS = "头、脑、额、脖子、颈、眼、鼻、耳、嗓子、咽、喉、牙、嘴、口、舌、脸、面、腮、扁桃体、肿、咳、咯、痰、晕、血、痒、紫、青、迷糊、转、昏";

    /** 胸部症状关键字 **/
    private static final String CHEST_WORDS = "胸、肋骨、后背、乳房、乳腺、肺、心、腔、气管、憋、闷、慌、跳、岔气、块";

    /** 腹部症状关键字 **/
    private static final String ABDOMEN_WORDS = "腹、胆、脾、腰、膀胱、阴道、输尿管、肚、脐、心口、肠、肝、阑尾、胃、肾、卵巢、子宫、输卵管、胰、恶心、呕吐、尿、块、胀、泻、嗝、顶、扎";

    /** 肢体症状关键字 **/
    private static final String LIMB_WORDS = "手、脚、腿、腰、臂、膝、髋、腕、指、趾、掌、肘、肩、腋、关节、抽、青、紫";


    public static boolean match(String voice,String[] arrs){
        for(String a:arrs){
            if(voice.indexOf(a)>-1){
                return true;
            }
        }
        return false;
    }




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
        catMap.put("头疼",1002);
        catMap.put("头痛",1002);
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
       /* if(catMap.get(voice)==null){
            return "";
        }
        return initCatMap.get(catMap.get(voice));*/

        String keyword="";

        if (match(voice,HEAD_WORDS.split("、"))){
            keyword="头颈";
            return keyword;
        }
        if (match(voice,CHEST_WORDS.split("、"))){
            keyword="胸部";
            return keyword;
        }
        if (match(voice,ABDOMEN_WORDS.split("、"))){
            keyword="腹部";
            return keyword;
        }
        if (match(voice,LIMB_WORDS.split("、"))){
            keyword="四肢";
            return keyword;
        }
        if (match(voice,BODY_WORDS.split("、"))){
            keyword="全身";
            return keyword;
        }

        return keyword;



    }


}
