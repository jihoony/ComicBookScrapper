package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.ConfigReader;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;

public class YamlConfigReader implements ConfigReader {

    @Override
    public <T> T read(String filename, Class<T> tClass) {
        final URL resource = getClass().getClassLoader().getResource(filename);
        if (resource == null){
            throw new IllegalArgumentException("file not found " + filename);
        }

        try {

            final FileReader fileReader = new FileReader(new File(resource.toURI()));
            final Object o = new Yaml().load(fileReader);
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(o, tClass);

        } catch (FileNotFoundException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
