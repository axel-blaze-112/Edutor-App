package com.example.miniproj2.Models;

public class trendingModel {
    String lessonNo;
    String LangName;
    String coverUrl;
    String key;
    String courseDesc;

    public trendingModel(String lessonNo, String langName, String coverUrl, String key, String courseDesc) {
        this.lessonNo = lessonNo;
        LangName = langName;
        this.coverUrl = coverUrl;
        this.key = key;
        this.courseDesc = courseDesc;
    }

    public String getLessonNo() {
        return lessonNo;
    }

    public void setLessonNo(String lessonNo) {
        this.lessonNo = lessonNo;
    }

    public String getLangName() {
        return LangName;
    }

    public void setLangName(String langName) {
        LangName = langName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }
}
