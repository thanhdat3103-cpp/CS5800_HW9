package org.example.flyweight;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FlyweightTest {

    @Test
    void testSameStyleIsShared() {

        // Arrange: Create a Flyweight Factory
        CharacterStyleFactory factory = new CharacterStyleFactory();

        // Act: Request the same style twice
        CharacterStyle style1 =
                factory.getStyle("Arial", 12, "Red");

        CharacterStyle style2 =
                factory.getStyle("Arial", 12, "Red");

        // Assert: Both references must point to the same object
        assertSame(style1, style2);

        // Only one unique style should exist
        assertEquals(1, factory.getStyleCount());
    }

    @Test
    void testDifferentStylesAreNotShared() {

        // Arrange
        CharacterStyleFactory factory = new CharacterStyleFactory();

        // Act: Request four different styles
        CharacterStyle style1 =
                factory.getStyle("Arial", 12, "Red");

        CharacterStyle style2 =
                factory.getStyle("Arial", 12, "Blue");

        CharacterStyle style3 =
                factory.getStyle("Arial", 14, "Red");

        CharacterStyle style4 =
                factory.getStyle("Calibri", 12, "Red");

        // Assert: Different properties create different objects
        assertNotSame(style1, style2);
        assertNotSame(style1, style3);
        assertNotSame(style1, style4);

        // The factory should contain four unique styles
        assertEquals(4, factory.getStyleCount());
    }

    @Test
    void testCreateDocumentWithDifferentStyles() {

        // Arrange
        CharacterStyleFactory factory = new CharacterStyleFactory();
        Document document = new Document(factory);

        // Act: Create a document with 4 different styles
        document.addText("Hello", "Arial", 12, "Red");
        document.addText("World", "Calibri", 14, "Blue");
        document.addText("CS", "Verdana", 16, "Black");
        document.addText("5800", "Arial", 12, "Blue");

        // Assert 1: Verify document content
        assertEquals("HelloWorldCS5800", document.getText());

        // Assert 2: Verify the number of unique styles
        assertEquals(4, factory.getStyleCount());

        // Assert 3: Verify Flyweight sharing
        assertSame(
                document.getCharacter(0).getStyle(),
                document.getCharacter(1).getStyle()
        );

        // Assert 4: Verify different character styles
        assertNotSame(
                document.getCharacter(0).getStyle(),
                document.getCharacter(5).getStyle()
        );

        // Assert 5: Verify character properties
        CharacterStyle helloStyle =
                document.getCharacter(0).getStyle();

        assertEquals("Arial", helloStyle.getFont());
        assertEquals(12, helloStyle.getSize());
        assertEquals("Red", helloStyle.getColor());
    }

    @Test
    void testEditCharacterDoesNotAffectOtherCharacters() {

        // Arrange: Create a document
        CharacterStyleFactory factory = new CharacterStyleFactory();
        Document document = new Document(factory);

        document.addText("Hello", "Arial", 12, "Red");

        // Save the original shared style
        CharacterStyle originalStyle =
                document.getCharacter(1).getStyle();

        // Act: Edit only the first character
        document.editCharacter(0, 'Y', "Calibri", 14, "Blue");

        // Assert 1: Verify edited text
        assertEquals("Yello", document.getText());

        // Assert 2: Verify the edited character's properties
        CharacterStyle editedStyle =
                document.getCharacter(0).getStyle();

        assertEquals("Calibri", editedStyle.getFont());
        assertEquals(14, editedStyle.getSize());
        assertEquals("Blue", editedStyle.getColor());

        // Assert 3: Other characters retain the original style
        assertSame(originalStyle,
                document.getCharacter(1).getStyle());

        assertSame(originalStyle,
                document.getCharacter(2).getStyle());

        // Assert 4: Edited style is different from original style
        assertNotSame(originalStyle, editedStyle);

        // Assert 5: Factory now contains two unique styles
        assertEquals(2, factory.getStyleCount());
    }

    @Test
    void testSaveAndLoadDocument(@TempDir Path tempDir)
            throws IOException {

        // Arrange: Create the original document
        CharacterStyleFactory factory = new CharacterStyleFactory();
        Document original = new Document(factory);

        original.addText("Hello", "Arial", 12, "Red");
        original.addText("World", "Calibri", 14, "Blue");
        original.addText("CS", "Verdana", 16, "Black");
        original.addText("5800", "Arial", 12, "Blue");

        // Act 1: Save document to a temporary file
        Path file = tempDir.resolve("testDocument.dat");
        original.save(file.toString());

        // Assert 1: Verify file was created
        assertTrue(Files.exists(file));

        // Act 2: Load into a new Document with a new Factory
        CharacterStyleFactory loadedFactory =
                new CharacterStyleFactory();

        Document loaded = new Document(loadedFactory);
        loaded.load(file.toString());

        // Assert 2: Verify document content
        assertEquals("HelloWorldCS5800", loaded.getText());
        assertEquals(original.getText(), loaded.getText());

        // Assert 3: Verify all characters and their properties
        for (int i = 0; i < original.getText().length(); i++) {

            StyledCharacter originalChar =
                    original.getCharacter(i);

            StyledCharacter loadedChar =
                    loaded.getCharacter(i);

            assertEquals(originalChar.getValue(),
                    loadedChar.getValue());

            assertEquals(originalChar.getStyle().getFont(),
                    loadedChar.getStyle().getFont());

            assertEquals(originalChar.getStyle().getSize(),
                    loadedChar.getStyle().getSize());

            assertEquals(originalChar.getStyle().getColor(),
                    loadedChar.getStyle().getColor());
        }

        // Assert 4: Verify the number of unique styles
        assertEquals(4, loadedFactory.getStyleCount());

        // Assert 5: Verify Flyweight sharing after loading
        assertSame(
                loaded.getCharacter(0).getStyle(),
                loaded.getCharacter(1).getStyle()
        );

        // Different styles must remain different objects
        assertNotSame(
                loaded.getCharacter(0).getStyle(),
                loaded.getCharacter(5).getStyle()
        );
    }
}