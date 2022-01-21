package com.example.miniproj2.Models;

public class courseHomeModel
{
    String lessonNo;
    String LangName;
    String coverUrl;
    String key;

    public courseHomeModel(String lessonNo,String LangName , String coverUrl,String key)
    {
        this.lessonNo=lessonNo;
        this.LangName=LangName;
        this.coverUrl=coverUrl;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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
}
