package org.example.flyweight;

import java.util.ArrayList;
import java.util.List;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.DataInputStream;
import java.io.EOFException;

public class Document {

    private final List<StyledCharacter> characters;
    private final CharacterStyleFactory factory;

    // Constructor
    public Document(CharacterStyleFactory factory) {
        this.characters = new ArrayList<>();
        this.factory = factory;
    }

    // Add text to the document
    public void addText(String text, String font, int size, String color) {

        CharacterStyle style = factory.getStyle(font, size, color);

        for (char c : text.toCharArray()) {
            characters.add(new StyledCharacter(c, style));
        }
    }

    // Edit a character and its properties
    public void editCharacter(int index, char newValue,
                              String font, int size, String color) {

        CharacterStyle newStyle = factory.getStyle(font, size, color);

        StyledCharacter newCharacter =
                new StyledCharacter(newValue, newStyle);

        characters.set(index, newCharacter);
    }

    // Get a character at a specific position
    public StyledCharacter getCharacter(int index) {
        return characters.get(index);
    }

    // Get the document text
    public String getText() {

        StringBuilder result = new StringBuilder();

        for (StyledCharacter character : characters) {
            result.append(character.getValue());
        }

        return result.toString();
    }

    // Save document to a binary file
    public void save(String fileName) throws IOException {

        try (DataOutputStream output =
                     new DataOutputStream(Files.newOutputStream(Path.of(fileName)))) {

            // Save the number of characters
            output.writeInt(characters.size());

            // Save each character and its properties
            for (StyledCharacter character : characters) {

                CharacterStyle style = character.getStyle();

                output.writeChar(character.getValue());
                output.writeUTF(style.getFont());
                output.writeInt(style.getSize());
                output.writeUTF(style.getColor());
            }
        }
    }

    // Load document from a binary file
    public void load(String fileName) throws IOException {

        List<StyledCharacter> loadedCharacters = new ArrayList<>();

        try (DataInputStream input =
                     new DataInputStream(Files.newInputStream(Path.of(fileName)))) {

            // Read the number of characters
            int count = input.readInt();

            if (count < 0) {
                throw new IOException("Invalid character count");
            }

            // Read each character and its properties
            for (int i = 0; i < count; i++) {

                char value = input.readChar();
                String font = input.readUTF();
                int size = input.readInt();
                String color = input.readUTF();

                CharacterStyle style =
                        factory.getStyle(font, size, color);

                loadedCharacters.add(
                        new StyledCharacter(value, style));
            }

        } catch (EOFException e) {
            throw new IOException("Incomplete document file", e);
        }

        // Replace the current document content
        characters.clear();
        characters.addAll(loadedCharacters);
    }
}