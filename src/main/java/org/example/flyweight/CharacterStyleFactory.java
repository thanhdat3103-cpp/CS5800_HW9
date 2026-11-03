package org.example.flyweight;

import java.util.HashMap;
import java.util.Map;

public class CharacterStyleFactory {

    private final Map<String, CharacterStyle> cache = new HashMap<>();

    public CharacterStyle getStyle(String font, int size, String color) {

        String key = font + "|" + size + "|" + color;

        if (!cache.containsKey(key)) {
            CharacterStyle newStyle =
                    new CharacterStyle(font, size, color);

            cache.put(key, newStyle);
        }

        return cache.get(key);
    }

    public int getStyleCount() {
        return cache.size();
    }
}