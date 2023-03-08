package org.example.config;

public interface ConfigReader {
    <T> T read(String filename, Class<T> tClass);
}
