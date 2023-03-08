package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.config.ConfigReader;
import org.example.config.YamlConfigReader;
import org.example.parser.ComicBookScrapper;
import org.example.payload.ConfigPayload;

@Slf4j
public class Main {
    public static void main(String[] args) {

        ConfigReader configReader = new YamlConfigReader();
        ConfigPayload payload = configReader.read("application.yml", ConfigPayload.class);

        log.debug("payload {}", payload);

        payload.getItems().forEach(item -> {
            if (!item.isSkip()){
                final String name = item.getName();
                final String url = item.getUrl();
                final int count = item.getCount();

                ComicBookScrapper.start(url, count, payload.getLocalDirectory() + "/"+ name);
            }
        });
    }

    public static void test(){

        final String startUrl = "http://huggiescomics.blogspot.com/2016/09/1_34.html";
        final int bookCount = 24;
        final String path = "/Users/jihoon_yang/Desktop/Untitle/슬램덩크/";

        ComicBookScrapper.start(startUrl, bookCount, path);
    }
}