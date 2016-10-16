package com.mmednet.main.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 账户实体
 * @author zdb
 * created by 2016/9/22 17:32
 */
@DatabaseTable(tableName="account")
public class Account {

    private static final long serialVersionUID=1L;

    @DatabaseField(generatedId=true, canBeNull=false)
    public long _id;
    @DatabaseField(columnName="id", canBeNull=false)
    public String id;
    @DatabaseField(columnName="name", canBeNull=false)
    public String name;
    @DatabaseField(columnName="sex", canBeNull=false)
    public int sex;
    @DatabaseField(columnName="phone", canBeNull=false)
    public String phone;
    @DatabaseField(columnName="birth", canBeNull=false)
    public String birth;
    @DatabaseField(columnName="weight")
    public double weight;//体重
    @DatabaseField(columnName="bmi")
    public double bmi;//bmi
    @DatabaseField(columnName="diastolic")
    public double diastolic;//舒张压
    @DatabaseField(columnName="systolic")
    public double systolic;//收缩压
    @DatabaseField(columnName="spo")
    public double spo;//血氧
    @DatabaseField(columnName="pr")
    public double pr;//脉率
    @DatabaseField(columnName="kongfu")
    public double kongfu;//空腹血糖
    @DatabaseField(columnName="canqian")
    public double canqian;
    @DatabaseField(columnName="canhou")
    public double canhou;
    @DatabaseField(columnName="shuiqian")
    public double shuiqian;
    @DatabaseField(columnName="head")
    public String head;//头像
    @DatabaseField(columnName = "diseases",defaultValue = "无")
    public String diseases;//已患疾病
    @DatabaseField(columnName = "risk",defaultValue = "无")
    public String risk;//疾病风险

    @Override
    public String toString() {
        return "Account{" +
                "_id=" + _id +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", birth='" + birth + '\'' +
                ", weight=" + weight +
                ", bmi=" + bmi +
                ", diastolic=" + diastolic +
                ", systolic=" + systolic +
                ", spo=" + spo +
                ", pr=" + pr +
                ", kongfu=" + kongfu +
                ", canqian=" + canqian +
                ", canhou=" + canhou +
                ", shuiqian=" + shuiqian +
                ", head='" + head + '\'' +
                ", diseases='" + diseases + '\'' +
                ", risk='" + risk + '\'' +
                '}';
    }
}
