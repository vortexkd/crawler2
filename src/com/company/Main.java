package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ParseException {

        System.out.println("全体処理を開始しました。");

        makeArticleListCsv(2016, 12); // 2016年12月の記事の一覧

        System.out.println("全体処理を終了しました。");

    }

    private static void makeArticleListCsv(int intYear, int intMonth) throws IOException, InterruptedException, ParseException{

        String month = String.format("%02d" , intMonth);
        String year = String.valueOf(intYear);
        int page = 0;
        String urlMain = "http://news.mynavi.jp/list/headline/" + intYear + "/" + month + "/?page=";
        String siteName = "http://" + urlMain.split("/")[2];
        String ELEMENT_CLASS = "thumb-s__item";

        List<Article> articles = new ArrayList<>();

        while (true) {
            page += 1;
            String url = urlMain + page;

            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.getElementsByClass(ELEMENT_CLASS);
            if (elements.size() == 0) {
                break;
            }
            for (Element element : elements) {
                Article newArticle = new Article(element, siteName, year);
                if (!articles.contains(newArticle)) {
                    articles.add(newArticle);
                }
            }
            Thread.sleep(1000);
        }
        //Collections.sort(articles); //List.sort()を利用してくださいと書かれてますが、static
        Comparator<Article> c = (Article a1, Article a2) -> a1.compareTo(a2);
        articles.sort(c);

        writeToCsv(articles);
    }

    public static void writeToCsv(List<Article> articleList) throws IOException{
        String header = "件名,カテゴリ,ニュース日時,URL";
        String filePath = System.getProperty("user.dir")+"/files/mynavi_articles.csv";
        String utf8 = "UTF-8";
        String lineSeparator = "line.separator";


        PrintWriter pw = new PrintWriter(filePath,utf8);
        pw.write(header + System.getProperty(lineSeparator));
        for (Article article : articleList) {
            pw.write(article.toString() + System.getProperty(lineSeparator));
        }
        pw.close();

    }
}
