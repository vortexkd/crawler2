package com.company;

import org.jsoup.nodes.Element;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public final class Article implements Comparable<Article>{

    private static final String DATE_PATTERN = "yyyy/MM/dd HH:mm";
    private static final String ELEMENT_TITLE_CLASS = "thumb-s__tit";
    private static final String ELEMENT_URL_CLASS = "thumb-s__tit-link";
    private static final String ELEMENT_CATEGORY_CLASS = "thumb-s__category";
    private static final String ELEMENT_DATE_CLASS = "thumb-s__date";
    private static final String HREF = "href";
    private static final String HTTP = "http";
    private static final String DOT = ".";
    private static final String FORWARD_SLASH =  "/";


    private final String TITLE;
    private final String CATEGORY;
    private final String POST_TIME_STRING;
    private final Date POST_TIME;
    private final String URL;

    static String urlMaker(String relativeLocation, String base){
        if(relativeLocation.equals("") || relativeLocation == null){
            return "";
        }
        if(relativeLocation.contains(HTTP) || relativeLocation.contains(DOT)){
            return relativeLocation;
        }else {
            return base+relativeLocation;
        }
    }

    Article(Element el,String baseUrl,String year) throws ParseException{
        SimpleDateFormat d = new SimpleDateFormat(DATE_PATTERN);
        this.TITLE = el.getElementsByClass(ELEMENT_TITLE_CLASS).text();
        this.URL = Article.urlMaker(el.getElementsByClass(ELEMENT_URL_CLASS).attr(HREF).toString(), baseUrl);
        this.CATEGORY = el.getElementsByClass(ELEMENT_CATEGORY_CLASS).text();
        this.POST_TIME_STRING = year + FORWARD_SLASH + el.getElementsByClass(ELEMENT_DATE_CLASS).text();
        this.POST_TIME = d.parse(year + FORWARD_SLASH + el.getElementsByClass(ELEMENT_DATE_CLASS).text());

    }

    public Date getPOST_TIME() {
        return POST_TIME;
    }

    @Override
    public String toString() {
        //ここもリトラル　を使わないんですか？ \" を変数にしてみたんですけど、もっとわかりづらいと思いました。。。
        //return QUOTE + this.TITLE + QUOTE_COMMA_QUOTE + this.CATEGORY + QUOTE_COMMA_QUOTE ....
        return "\"" +  this.TITLE + "\",\"" + this.CATEGORY + "\",\"" + this.POST_TIME_STRING + "\",\"" + this.URL + "\"";
    }


    @Override
    public int compareTo(Article other) {
        return this.POST_TIME.compareTo(other.getPOST_TIME());
    }
}
