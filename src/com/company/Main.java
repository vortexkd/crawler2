package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

        System.out.println("全体処理を開始しました。");

        makeArticleListCsv(2016, 12); // 2016年12月の記事の一覧

        System.out.println("全体処理を終了しました。");

    }

    private static void makeArticleListCsv(int intYear, int intMonth) throws IOException, InterruptedException, ParseException{

        String month = String.format("%02d",intMonth);
        String year = ""+intYear;
        int page = 0;
        String url = "http://news.mynavi.jp/list/headline/"+intYear+"/"+month+"/?page="+page;
        String siteName = "http://"+url.split("/")[2];

        ArrayList<Article> articles = new ArrayList<>();

        while (true) {
            page +=1;
            url = "http://news.mynavi.jp/list/headline/"+intYear+"/"+month+"/?page="+page;

            Document doc = Jsoup.connect(url).get();
            Elements els = doc.getElementsByClass("thumb-s__item");
            if (els.size()==0) {
                break;
            }
            /*if (page > 15){ テストのために
                break;
            }*/
            for (Element el : els) {
                Article newArticle = new Article(el, siteName, year);
                if (!articles.contains(newArticle)) {
                    articles.add(newArticle);
                }
            }
            Thread.sleep(1000);
        }
        Collections.sort(articles);
        writeToCsv(articles);
    }

    public static void writeToCsv(ArrayList<Article> articleList) throws IOException{
        String header = "件名,カテゴリ,ニュース日時,URL";
        PrintWriter pw = new PrintWriter(System.getProperty("user.dir")+"/files/mynavi_articles.csv","UTF-8");
        pw.write(header+"\n");
        for (Article article:articleList) {
            pw.write(article.toString()+"\n");
        }
        pw.close();
    }
}
