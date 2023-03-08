package org.example.payload;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ConfigPayload {
    private String localDirectory;
    private List<Item> items;

    public ConfigPayload() {
    }
}
