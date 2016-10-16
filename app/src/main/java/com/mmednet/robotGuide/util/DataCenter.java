package com.mmednet.robotGuide.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.mmednet.robotGuide.bean.IntelligenceGuideSymptom;
import com.mmednet.robotGuide.bean.IntelligentGuideDepartment;
import com.mmednet.robotGuide.bean.IntelligentGuideSymptomDepartment;
import com.mmednet.robotGuide.bean.IntelligentGuideSymptoms;
import com.mmednet.robotGuide.bean.Question;
import com.mmednet.robotGuide.db.actual.IntelligentGuideDepartmentDao;
import com.mmednet.robotGuide.db.actual.IntelligentGuideSymptomDepartmentDao;
import com.mmednet.robotGuide.db.actual.IntelligentGuideSymptomsDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alpha on 2016/9/2.
 */
public class DataCenter {

    static Map<Integer, Question> questionMap=new HashMap<Integer, Question>();

    static Map<String, List<Integer>> positionMap=new HashMap<String, List<Integer>>();

    private List<Integer> sortList=new ArrayList<Integer>();

    private static DataCenter dataCenter;

    private static final String TAG=DataCenter.class.getSimpleName();

    static {
        //初始化QuestionMap
        // 全身症状
        Question question=new Question("您有发热吗？", "1", "是;有;偶尔;有时候;发热", "否;没有");
        questionMap.put(1, question);
        question=new Question("您感觉浑身没劲、出虚汗吗？", "3", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(3, question);
        question=new Question("您最近有没有食欲？", "5", "是;有;吃的很多", "否;没有;没有食欲;吃的很少");
        questionMap.put(5, question);
        question=new Question("您最近皮肤或眼睛有明显发黄吗？", "6", "是;有", "否;没有");
        questionMap.put(6, question);
        question=new Question("您最近有失眠吗？", "4", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(4, question);

        // 头部症状
        question=new Question("您头疼吗？", "11", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(11, question);
        question=new Question("您头晕吗？", "12", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(12, question);
        question=new Question("您最近有突然昏倒的情况吗？", "13", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(13, question);
        question=new Question("您的口唇最近有发青发紫的情况吗？", "10", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(10, question);
        question=new Question("您最近有抽搐或短暂昏迷吗？", "2", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(2, question);
        question=new Question("您有头面部水肿的现象吗？", "7", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(7, question);

        // 胸部症状
        question=new Question("您有胸疼吗？", "14", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(14, question);
        question=new Question("您最近感觉心慌吗？", "16", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(16, question);
        question=new Question("您最近感觉呼吸困难吗?", "15", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(15, question);
        question=new Question("您最近咳嗽吗？", "8", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(8, question);
        question=new Question("您咳嗽时有血丝或血块吗？", "9", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(9, question);
        question=new Question("您的皮肤有出血点或瘀斑吗？", "18", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(18, question);
        question=new Question("您胸部有硬结或肿块吗？", "20", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(20, question);

        // 腹部症状
        question=new Question("您肚子疼吗？", "23", "是;有;偶尔;有时候;疼", "否;没有");
        questionMap.put(23, question);
        question=new Question("您拉肚子吗？", "24", "是;有;偶尔;有时候;拉肚子", "否;没有");
        questionMap.put(24, question);
        question=new Question("您最近有感觉恶心或呕吐吗？", "17", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(17, question);
        question=new Question("您便秘吗？", "25", "是;有;偶尔;有时候;便秘", "否;没有");
        questionMap.put(25, question);
        question=new Question("您最近大便时有血吗？或者大便呈黑褐色吗？", "22", "是;有;偶尔;有时候;有血;", "否;没有");
        questionMap.put(22, question);
        question=new Question("您最近有呕血或吐血吗？", "19", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(19, question);
        question=new Question("您腹部有肿块吗？", "26", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(26, question);
        question=new Question("您最近有小便次数增多吗？", "33", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(33, question);
        question=new Question("您最近排尿时有尿急与疼痛现象吗？", "34", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(34, question);
        question=new Question("您最近的尿量多吗？", "36", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(36, question);
        question=new Question("您最近有尿少或无尿吗？", "35", "是;有;偶尔;有时候", "否;没有");
        questionMap.put(35, question);

        //
        question=new Question("您最近腰背疼吗？", "21", "是;有", "否;没有");
        questionMap.put(21, question);
        question=new Question("您有胳膊或腿疼吗？", "30", "是;有", "否;没有");
        questionMap.put(30, question);
        question=new Question("您有关节疼吗？", "27", "是;有", "否;没有");
        questionMap.put(27, question);
        question=new Question("您的胳膊、腿有肿胀现象吗？ ", "29", "是;有", "否;没有");
        questionMap.put(29, question);
        question=new Question("您的胳膊、腿有肿块吗？ ", "31", "是;有", "否;没有");
        questionMap.put(31, question);
        question=new Question("您的皮肤有出血点或紫点瘀斑吗？", "28", "是;有", "否;没有");
        questionMap.put(28, question);
        question=new Question("您的胳膊或腿对冷、热、痛感觉变迟钝吗？", "32", "是;有", "否;没有");
        questionMap.put(32, question);

        //初始化PositionMap
        Integer[] bodyArr=new Integer[]{1, 3, 5, 6, 4};
        positionMap.put("bodySymptom", Arrays.asList(bodyArr));

        Integer[] headArr=new Integer[]{11, 12, 13, 10, 2, 7};
        positionMap.put("headNeckSymptom", Arrays.asList(headArr));

        Integer[] chestArr=new Integer[]{14, 16, 15, 8, 9, 18, 20};
        positionMap.put("chestSymptom", Arrays.asList(chestArr));

        Integer[] abdomenArr=new Integer[]{23, 24, 17, 25, 22, 19, 26, 33, 34, 36, 35};
        positionMap.put("abdomenSymptom", Arrays.asList(abdomenArr));

        Integer[] limbArr=new Integer[]{21, 30, 27, 29, 31, 28, 32};
        positionMap.put("limbSymptom", Arrays.asList(limbArr));
    }

    public static DataCenter getInstance() {
        if (dataCenter == null) {
            synchronized (DataCenter.class) {
                if (dataCenter == null) {
                    dataCenter=new DataCenter();
                }
            }
        }
        return dataCenter;
    }

    /**
     * 根据关键词对症状排序
     *
     * @param key
     */
    public void initData(String key) {
        IntelligenceGuideSymptom intelligenceGuideSymptom=new IntelligenceGuideSymptom();
        intelligenceGuideSymptom.sortSymptom(key);

        for (String k : intelligenceGuideSymptom.getSort().split(",")) {
            intelligenceGuideSymptom.getSortList().addAll(positionMap.get(k));
        }
        setSortList(intelligenceGuideSymptom.getSortList());

        Log.d(TAG, "sortList:" + intelligenceGuideSymptom.getSortList());
    }

    public void setSortList(List<Integer> sortList) {
        this.sortList=sortList;
    }

    public List<Integer> getSortList() {
        return sortList;
    }

    public static Map<Integer, Question> getQuestionMap() {
        return questionMap;
    }

    /**
     * 获得题号为n的题目。从0开始。
     *
     * @param n
     * @return
     */
    public Question getQuestion(int n) {
        //已经答完
        if (n == dataCenter.getSortList().size() - 1) {
            return null;
        }
        int index=dataCenter.getSortList().get(n + 1);
        return questionMap.get(index);
    }

    public List<IntelligentGuideDepartment> guidingDepartment(String sids, Context context) {

        IntelligentGuideSymptomsDao intelligentGuideSymptomsDao=new IntelligentGuideSymptomsDao(context);
        IntelligentGuideSymptomDepartmentDao intelligentGuideSymptomDepartmentDao=new IntelligentGuideSymptomDepartmentDao(context);
        IntelligentGuideDepartmentDao intelligentGuideDepartmentDao=new IntelligentGuideDepartmentDao(context);
        if (!TextUtils.isEmpty(sids)) {
            if (sids.charAt(sids.length() - 1) == ',') {
                sids=sids.substring(0, sids.length() - 1);
            }
            List<String> intList=Arrays.asList(sids.split(","));

            //获得所有症状
            List<IntelligentGuideSymptoms> intelligentGuideSymptoms=intelligentGuideSymptomsDao.query("id", intList);

            //获得症状-科室
            List<IntelligentGuideSymptomDepartment> intelligentGuideSymptomDepartments=new ArrayList<IntelligentGuideSymptomDepartment>();
            for (IntelligentGuideSymptoms symptoms : intelligentGuideSymptoms) {
                IntelligentGuideSymptomDepartment symptomDepartment=intelligentGuideSymptomDepartmentDao.queryObject("symptom_id", symptoms.id + "");
                if (intelligentGuideSymptomDepartments != null)
                    intelligentGuideSymptomDepartments.add(symptomDepartment);
            }
            Map<String, Double> map=new HashMap<String, Double>();
            for (IntelligentGuideSymptomDepartment igsd : intelligentGuideSymptomDepartments) {
                if (map.containsKey(igsd.departmentName)) {
                    map.put(igsd.departmentName,
                            map.get(igsd.departmentName) + Double.valueOf(igsd.weight));
                } else {
                    map.put(igsd.departmentName, Double.valueOf(igsd.weight));
                }
            }

            List<IntelligentGuideSymptomDepartment> temp=new ArrayList<IntelligentGuideSymptomDepartment>();
            for (Map.Entry<String, Double> entry : map.entrySet()) {
                temp.add(new IntelligentGuideSymptomDepartment(entry.getKey(), entry.getValue().toString()));
            }
            Collections.sort(temp);
            if (temp.size() > 3) {
                temp=temp.subList(0, 3);
            }
            List<IntelligentGuideDepartment> departments=new ArrayList<IntelligentGuideDepartment>();
            for (IntelligentGuideSymptomDepartment igsd : temp) {
                IntelligentGuideDepartment igd=intelligentGuideDepartmentDao.queryObject("name", igsd.departmentName);
                if (igd != null) {
                    departments.add(igd);
                }
            }
            return departments;
        }

        return null;

    }

}
