package com.company;

import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article implements Comparable<Article>{
    private final String title;
    private final String category;
    private final String postTimeString;
    private final Date postTime;
    private final String url;

    public Article(Element el,String baseUrl,String year) throws ParseException{
        SimpleDateFormat d = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        this.title = el.getElementsByClass("thumb-s__tit").text();
        this.url = Article.urlMaker(el.getElementsByClass("thumb-s__tit-link").attr("href").toString(),baseUrl);
        this.category = el.getElementsByClass("thumb-s__category").text();
        this.postTimeString = year+"/"+el.getElementsByClass("thumb-s__date").text();
        this.postTime = d.parse(year+"/"+el.getElementsByClass("thumb-s__date").text());

    }

    @Override
    public String toString() {
        return "\""+this.title+"\",\""+this.category+"\",\""+this.postTimeString+"\",\""+this.url+"\"";
    }

    static String urlMaker(String relativeLocation, String base){
        if(relativeLocation=="" || relativeLocation==null){
            return "";
        }
        if(relativeLocation.contains("http") || relativeLocation.contains(".")){
            return relativeLocation;
        }else {
            return base+relativeLocation;
        }
    }

    public Date getPostTime() {
        return postTime;
    }

    @Override
    public int compareTo(Article other) {
        return this.postTime.compareTo(other.getPostTime());
    }
}
