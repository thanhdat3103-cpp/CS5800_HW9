package org.example.flyweight;

public class StyledCharacter {

    private final char value;
    private final CharacterStyle style;

    public StyledCharacter(char value, CharacterStyle style) {
        this.value = value;
        this.style = style;
    }

    public char getValue() {
        return value;
    }

    public CharacterStyle getStyle() {
        return style;
    }
}