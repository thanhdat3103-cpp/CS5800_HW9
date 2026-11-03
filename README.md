
# CS5800 - Homework 9
## Structural Design Patterns: Flyweight and Proxy

### Project Overview

This homework implements two Structural Design Patterns
in Java:

1. Flyweight Design Pattern - Text Editor
2. Proxy Design Pattern - Music Streaming Service

The project uses Java 26, Maven, and JUnit 5.

### Part 1: Flyweight Design Pattern

The Text Editor uses Flyweight to share character
formatting objects and reduce redundant object creation.

Features:
- Create documents with different character styles.
- Edit individual characters and their formatting.
- Share CharacterStyle objects using a Factory.
- Save documents to binary files.
- Load documents and restore character properties.

Driver Program:
org.example.flyweight.Main

Sample Document:
HelloWorldCS5800

The sample uses four unique combinations of font,
size, and color.

The driver creates HelloWorldCS5800.dat.

### Part 2: Proxy Design Pattern

The Music Streaming Service implements a Caching Proxy.

Features:
- Search songs by ID.
- Search songs by Title.
- Search songs by Album.
- Cache previously retrieved search results.
- Support case-insensitive Title and Album searches.
- Cache searches that return no results.
- Simulate a one-second server delay.

Driver Program:
org.example.proxy.Main

The driver contains eight sample songs and demonstrates
the performance difference between Cache HIT and
Cache MISS.

### Unit Testing

JUnit 5 is used to test both implementations.

FlyweightTest:
- Shared character styles
- Different character styles
- Document creation
- Character editing
- Document save and load

SongServiceProxyTest:
- Cache by Song ID
- Cache by Title
- Cache by Album
- Negative caching for missing IDs
- Negative caching for missing Titles
- Negative caching for missing Albums

### Test Results

Total Tests: 11
Passed: 11
Failures: 0
Errors: 0
Skipped: 0

Maven Build: SUCCESS

### How to Run

1. Open the project in IntelliJ IDEA.
2. Configure JDK 26.
3. Load the Maven project.
4. Run org.example.flyweight.Main for Part 1.
5. Run org.example.proxy.Main for Part 2.
6. Open Maven > Lifecycle > test to run all Unit Tests.

### Technologies

- Java 26
- Maven
- JUnit 5
- IntelliJ IDEA