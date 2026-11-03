package org.example.flyweight;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {

        // 1. Create the Flyweight Factory
        CharacterStyleFactory factory = new CharacterStyleFactory();

        // 2. Create a new document
        Document document = new Document(factory);

        // 3. Add text with four different styles
        document.addText("Hello", "Arial", 12, "Red");
        document.addText("World", "Calibri", 14, "Blue");
        document.addText("CS", "Verdana", 16, "Black");
        document.addText("5800", "Arial", 12, "Blue");

        // 4. Display the document
        System.out.println("Original text: " + document.getText());

        // 5. Display the number of unique styles
        System.out.println("Unique styles: " + factory.getStyleCount());

        // Save the original document to a file
        String fileName = "HelloWorldCS5800.dat";

        document.save(fileName);

        System.out.println("Saved document to: "
                + Path.of(fileName).toAbsolutePath());

        // 6. Edit the first character
        document.editCharacter(0, 'h', "Arial", 12, "Red");

        // 7. Display the updated document
        System.out.println("Edited text: " + document.getText());

        // 8. Verify Flyweight object sharing
        CharacterStyle firstStyle = document.getCharacter(0).getStyle();
        CharacterStyle secondStyle = document.getCharacter(1).getStyle();

        System.out.println("First two characters share style: "
                + (firstStyle == secondStyle));

        System.out.println("Unique styles after editing: "
                + factory.getStyleCount());

        // 9. Load the saved document
        System.out.println("\n--- LOAD DOCUMENT TEST ---");

        // Use a new factory to verify the loaded styles
        CharacterStyleFactory loadedFactory = new CharacterStyleFactory();

        // Create a new empty document
        Document loadedDocument = new Document(loadedFactory);

        // Load the document from disk
        loadedDocument.load(fileName);

        // Display the loaded content
        System.out.println("Loaded text: " + loadedDocument.getText());

        // Verify the original text
        boolean textMatches =
                loadedDocument.getText().equals("HelloWorldCS5800");

        System.out.println("Text matches original: " + textMatches);

        // Verify the number of unique styles
        System.out.println("Loaded unique styles: "
                + loadedFactory.getStyleCount());

        // Verify that characters share the same style object
        CharacterStyle loadedStyle1 =
                loadedDocument.getCharacter(0).getStyle();

        CharacterStyle loadedStyle2 =
                loadedDocument.getCharacter(1).getStyle();

        System.out.println("Loaded characters share style: "
                + (loadedStyle1 == loadedStyle2));

        // Display four different character styles
        int[] positions = {0, 5, 10, 12};

        for (int index : positions) {
            StyledCharacter ch = loadedDocument.getCharacter(index);
            CharacterStyle style = ch.getStyle();

            System.out.println(ch.getValue() + ": "
                    + style.getFont() + ", "
                    + style.getSize() + ", "
                    + style.getColor());
        }
    }
}
