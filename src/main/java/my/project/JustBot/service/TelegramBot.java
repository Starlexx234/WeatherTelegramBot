package my.project.JustBot.service;


import my.project.JustBot.config.BotConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.net.URL;

@Component
public class TelegramBot extends TelegramLongPollingBot{

    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (messageText){
                case "/start" :
                    try {
                        startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "/saratov" :
                    try {
                        cityCommandReceived(chatId,"saratov");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "/moscow" :
                    try {
                        cityCommandReceived(chatId,"moscow");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "/peterburg" :
                    try {
                        cityCommandReceived(chatId,"saint_petersburg");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    sendMessage(chatId, "Sorry, command was not recognized");
            }
        }




    }
    private void cityCommandReceived(long chatId, String cityName) throws IOException {
        String answer = parser(cityName);
        sendMessage(chatId,answer);
    }

    private void startCommandReceived(long chatId, String name) throws IOException {

        String answer = "Здравствуй, " + name + ", сейчас ты можешь узнать прогноз погоды на неделю в 3 городах России:\nМосква(/moscow)\nСанкт-Петербург(/peterburg)\nСаратов(/saratov)";
        sendMessage(chatId, answer);

    }
    private void cityWeatherCommandReceived(long chatId, String cityName){

    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try{
            execute(message);
        }
        catch (TelegramApiException e){
            throw new RuntimeException();
        }

    }



    private static Document getPage(String city) throws IOException {
        String url = "https://world-weather.ru/pogoda/russia/" + city;
        Document page;
        page = Jsoup.parse(new URL(url),3000);
        return page;
    }

    public String parser(String city) throws IOException {
        Document page = getPage(city);
        Element tableWth = page.select("div[id = wrapper]").first();
        String res = "";
        Elements currentDate= tableWth.select("ul[id = vertical_tabs]").select("li[class = tab-w], [class = tab-w current], [class = tab-w weekend]");
        Elements currentValues = tableWth.select("div[id = content-left]").select("div[class = pane]");

        for(Element elementOfDate: currentDate){
            res+=(elementOfDate.select("div[class = numbers-month]").text() +" "+ elementOfDate.select("div[class = month]").text() + ", " + elementOfDate.select("div[class = day-week]").text()+"\n");
            res+=("Время   Температура   Осадки   Давление   Ветер   Влажность\n");
            for(Element elementOfValues : currentValues){
                res+=(elementOfValues.select("tr[class = night]").select("td[class = weather-day]").text() + "               "
                        + elementOfValues.select("tr[class = night]").select("td[class = weather-temperature]").select("span").text()+"                "
                        + elementOfValues.select("tr[class = night]").select("td[class = weather-probability]").text() + "            "
                        + elementOfValues.select("tr[class = night]").select("td[class = weather-pressure]").text() + "          "
                        + elementOfValues.select("tr[class = night]").select("td[class = weather-wind]").select("span[class = tooltip]").text() + "м/с          "
                        + elementOfValues.select("tr[class = night]").select("td[class = weather-humidity]").text()+"\n");
                res+=(elementOfValues.select("tr[class = morning]").select("td[class = weather-day]").text() + "                "
                        + elementOfValues.select("tr[class = morning]").select("td[class = weather-temperature]").select("span").text()+"                "
                        + elementOfValues.select("tr[class = morning]").select("td[class = weather-probability]").text() + "            "
                        + elementOfValues.select("tr[class = morning]").select("td[class = weather-pressure]").text() + "          "
                        + elementOfValues.select("tr[class = morning]").select("td[class = weather-wind]").select("span[class = tooltip]").text() + "м/с          "
                        + elementOfValues.select("tr[class = morning]").select("td[class = weather-humidity]").text())+ "\n";
                res+=(elementOfValues.select("tr[class = day]").select("td[class = weather-day]").text() + "               "
                        + elementOfValues.select("tr[class = day]").select("td[class = weather-temperature]").select("span").text()+"                "
                        + elementOfValues.select("tr[class = day]").select("td[class = weather-probability]").text() + "            "
                        + elementOfValues.select("tr[class = day]").select("td[class = weather-pressure]").text() + "          "
                        + elementOfValues.select("tr[class = day]").select("td[class = weather-wind]").select("span[class = tooltip]").text() + "м/с          "
                        + elementOfValues.select("tr[class = day]").select("td[class = weather-humidity]").text())+ "\n";
                res+=(elementOfValues.select("tr[class = evening]").select("td[class = weather-day]").text() + "             "
                        + elementOfValues.select("tr[class = evening]").select("td[class = weather-temperature]").select("span").text()+"                "
                        + elementOfValues.select("tr[class = evening]").select("td[class = weather-probability]").text() + "            "
                        + elementOfValues.select("tr[class = evening]").select("td[class = weather-pressure]").text() + "          "
                        + elementOfValues.select("tr[class = evening]").select("td[class = weather-wind]").select("span[class = tooltip]").text() + "м/с          "
                        + elementOfValues.select("tr[class = evening]").select("td[class = weather-humidity]").text())+"\n";
                res+="\n";
                currentValues.remove(elementOfValues);
                break;
            }
        }
        return res;
    }
}
