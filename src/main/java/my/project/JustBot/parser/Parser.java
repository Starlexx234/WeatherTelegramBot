package my.project.JustBot.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Parser {

    private static Document getPage() throws IOException {
        String url = "https://world-weather.ru/pogoda/russia/saratov/";
        Document page;
        page = Jsoup.parse(new URL(url),3000);
        return page;
    }

    public void parser() throws IOException {
        Document page = getPage();
        Element tableWth = page.select("div[id = wrapper]").first();

        Elements currentDate= tableWth.select("ul[id = vertical_tabs]").select("li[class = tab-w], [class = tab-w current], [class = tab-w weekend]");
        Elements currentValues = tableWth.select("div[id = content-left]").select("div[class = pane]");
        for(Element elementOfDate: currentDate){
            System.out.println(elementOfDate.select("div[class = numbers-month]").text() +" "+ elementOfDate.select("div[class = month]").text() + ", " + elementOfDate.select("div[class = day-week]").text());
            System.out.println("Время\tТемпература\tОсадки\tДавление\tВетер\tВлажность");
            for(Element elementOfValues : currentValues){
                System.out.println(elementOfValues.select("tr[class = night]").select("td[class = weather-day]").text() + "\t"
                        + elementOfValues.select("tr[class = night]").select("td[class = weather-temperature]").select("span").text()+"\t\t\t"
                        + elementOfValues.select("tr[class = night]").select("td[class = weather-probability]").text() + "\t\t"
                        + elementOfValues.select("tr[class = night]").select("td[class = weather-pressure]").text() + "\t\t\t"
                        + elementOfValues.select("tr[class = night]").select("td[class = weather-wind]").select("span[class = tooltip]").text() + "м/с\t\t"
                        + elementOfValues.select("tr[class = night]").select("td[class = weather-humidity]").text());
                System.out.println(elementOfValues.select("tr[class = morning]").select("td[class = weather-day]").text() + "\t"
                        + elementOfValues.select("tr[class = morning]").select("td[class = weather-temperature]").select("span").text()+"\t\t\t"
                        + elementOfValues.select("tr[class = morning]").select("td[class = weather-probability]").text() + "\t\t"
                        + elementOfValues.select("tr[class = morning]").select("td[class = weather-pressure]").text() + "\t\t\t"
                        + elementOfValues.select("tr[class = morning]").select("td[class = weather-wind]").select("span[class = tooltip]").text() + "м/с\t\t"
                        + elementOfValues.select("tr[class = morning]").select("td[class = weather-humidity]").text());
                System.out.println(elementOfValues.select("tr[class = day]").select("td[class = weather-day]").text() + "\t"
                        + elementOfValues.select("tr[class = day]").select("td[class = weather-temperature]").select("span").text()+"\t\t\t"
                        + elementOfValues.select("tr[class = day]").select("td[class = weather-probability]").text() + "\t\t"
                        + elementOfValues.select("tr[class = day]").select("td[class = weather-pressure]").text() + "\t\t\t"
                        + elementOfValues.select("tr[class = day]").select("td[class = weather-wind]").select("span[class = tooltip]").text() + "м/с\t\t"
                        + elementOfValues.select("tr[class = day]").select("td[class = weather-humidity]").text());
                System.out.println(elementOfValues.select("tr[class = evening]").select("td[class = weather-day]").text() + "\t"
                        + elementOfValues.select("tr[class = evening]").select("td[class = weather-temperature]").select("span").text()+"\t\t\t"
                        + elementOfValues.select("tr[class = evening]").select("td[class = weather-probability]").text() + "\t\t"
                        + elementOfValues.select("tr[class = evening]").select("td[class = weather-pressure]").text() + "\t\t\t"
                        + elementOfValues.select("tr[class = evening]").select("td[class = weather-wind]").select("span[class = tooltip]").text() + "м/с\t\t"
                        + elementOfValues.select("tr[class = evening]").select("td[class = weather-humidity]").text());
                System.out.println();
                break;
            }
        }


    }
}
