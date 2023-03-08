package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.config.ConfigReader;
import org.example.config.YamlConfigReader;
import org.example.parser.ComicBookScrapper;
import org.example.payload.ConfigPayload;

@Slf4j
public class Main {
    public static void main(String[] args) {
        traversal();
//        getFiles();
    }

    public static void traversal(){
        final String url = "http://huggiescomics.blogspot.com/2016/09/1.html";
        boolean forward = true;
        int count = 1000;

        ComicBookScrapper.traversal(url, count, forward);
    }

    public static void getFiles(){

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
}