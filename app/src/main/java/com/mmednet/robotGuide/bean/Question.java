package com.mmednet.robotGuide.bean;

import java.io.Serializable;

/**
 * Created by alpha on 2016/9/2.
 */
public class Question implements Serializable {


    private static final long serialVersionUID = -8318196312442279331L;
    public String title;

    public String icon;

    public String positive;//肯定关键词

    public String negative;//否定关键词

    public Question(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public Question(String title, String icon,String positive,String negative) {
        this.title = title;
        this.icon = icon;
        this.positive = positive;
        this.negative = negative;
    }

    @Override
    public String toString() {
        return "Question{" +
                "title='" + title + '\'' +
                '}';
    }
}
