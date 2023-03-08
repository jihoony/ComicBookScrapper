package org.example.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.IntStream;

@Slf4j
public class ComicBookScrapper {
    public static void start(final String startUrl, int bookCount, String dstPath) {

        createNotExistingDirectory(dstPath);

        String bookUrl = startUrl;

        for (int i = 0 ; i < bookCount ; i ++ ) {
            final Connection connect = Jsoup.connect(bookUrl);

            try {
                final Document document = connect.get();

                final String title = document.title().replaceAll("huggiescomics:", "").trim();
                log.debug("title {} bookUrl {}", title, bookUrl);

                final Elements imageTags = document.select("img");

                IntStream.range(0, imageTags.size())
                        .forEach(idx -> {
                            final Element element = imageTags.get(idx);
                            final String src = element.attr("src");

                            try {
                                downloadFile(dstPath, src, title, idx);
                            } catch (IOException | URISyntaxException e) {
                                throw new RuntimeException(e);
                            }

                        });

                final Elements nextLinkElements = document.select(".blog-pager-newer-link");
                final Element nextLinkElement = nextLinkElements.first();

                final String nextUrl = nextLinkElement.attr("href");

                log.debug("nextUrl {}", nextUrl);

                bookUrl = nextUrl;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static void downloadFile(String dstPath, String urlLink, String title, int index) throws IOException, URISyntaxException {

        createNotExistingDirectory(dstPath + "/" + title);

        URL url = new URL(urlLink);
        InputStream in = new BufferedInputStream(url.openStream());

        File file = new File(dstPath + "/" + String.format("%s/%s_%03d.jpg", title, title, index));

        Files.copy(in, Paths.get(file.toURI()), StandardCopyOption.REPLACE_EXISTING);
    }

    private static void createNotExistingDirectory(String path) {
        File dir = new File(path);

        if (!dir.exists()){
            final boolean mkdirs = dir.mkdirs();
        }
    }
}
