package org.example.payload;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Item {
    private String name;
    private String url;
    private int count;
    private boolean skip;

    public Item() {
    }
}
