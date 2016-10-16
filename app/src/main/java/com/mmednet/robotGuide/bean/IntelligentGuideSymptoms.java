package com.mmednet.robotGuide.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 智能导诊症状
 *
 * @author zdb
 *         created by 2016/9/2 14:52
 */
@DatabaseTable(tableName = "sys_intelligent_guide_symptom")
public class IntelligentGuideSymptoms implements Serializable {
    private static final long serialVersionUID = -7512308894334358822L;

    @DatabaseField(columnName = "id", generatedId = true, canBeNull = true)
    public int id;

    @DatabaseField(columnName = "parts")
    public String parts;

    @DatabaseField(columnName = "symptom")
    public String symptom;

    @Override
    public String toString() {
        return "IntelligentGuideSymptoms{" +
                "id=" + id +
                ", parts='" + parts + '\'' +
                ", symptom='" + symptom + '\'' +
                '}';
    }

    public static String getCreateSql() {
        return "CREATE TABLE sys_intelligent_guide_symptom ( \n" +
                "    id      INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    parts   VARCHAR,\n" +
                "    symptom VARCHAR \n" +
                ");";
    }

    public static String getInsertSql() {
        return "INSERT INTO `sys_intelligent_guide_symptom` VALUES (1, '全身','发热'),\n" +
                " (2, '全身','抽搐惊厥'),\n" +
                " (3, '全身','乏力虚汗'),\n" +
                " (4, '全身','失眠'),\n" +
                " (5, '全身','食欲不振'),\n" +
                " (6, '全身','黄疸'),\n" +
                " (7, '头面部','水肿'),\n" +
                " (8, '头面部','咳嗽咳痰'),\n" +
                " (9, '头面部','咳血'),\n" +
                " (10, '头面部','发绀'),\n" +
                " (11, '头面部','头痛'),\n" +
                " (12, '头面部','眩晕'),\n" +
                " (13, '头面部','晕厥'),\n" +
                " (14, '胸背部','胸痛'),\n" +
                " (15, '胸背部','呼吸困难'),\n" +
                " (16, '胸背部','心悸'),\n" +
                " (17, '胸背部','恶心呕吐'),\n" +
                " (18, '胸背部','皮肤黏膜出血'),\n" +
                " (19, '胸背部','呕血'),\n" +
                " (20, '胸背部','肿块'),\n" +
                " (21, '胸背部','腰背疼'),\n" +
                " (22, '腹部','便血'),\n" +
                " (23, '腹部','腹痛'),\n" +
                " (24, '腹部','腹泻'),\n" +
                " (25, '腹部','便秘'),\n" +
                " (26, '腹部','肿块'),\n" +
                " (27, '四肢关节','关节疼'),\n" +
                " (28, '四肢关节','皮肤黏膜出血'),\n" +
                " (29, '四肢关节','水肿'),\n" +
                " (30, '四肢关节','疼痛'),\n" +
                " (31, '四肢关节','肿块'),\n" +
                " (32, '四肢关节','感觉消退'),\n" +
                " (33, '腹部','血尿'),\n" +
                " (34, '腹部','尿频、尿急与尿痛'),\n" +
                " (35, '腹部','少尿、无尿'),\n" +
                " (36, '腹部','多尿');";
    }
}
