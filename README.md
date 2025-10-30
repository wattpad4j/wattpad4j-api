# Wattpad4J API (wattpad4j-api)<br />Java Client Library for the Wattpad API

[![Maven Central](https://img.shields.io/maven-central/v/org.wattpad4j/wattpad4j-api.svg)](http://mvnrepository.com/artifact/org.wattpad4j/wattpad4j-api)
[![Build Status](https://github.com/wattpad4j/wattpad4j-api/actions/workflows/maven.yml/badge.svg?branch=main)](https://github.com/wattpad4j/wattpad4j-api/actions/workflows/maven.yml)
[![javadoc.io](https://javadoc.io/badge2/org.wattpad4j/wattpad4j-api/javadoc.io.svg)](https://javadoc.io/doc/org.wattpad4j/wattpad4j-api)

Wattpad4J API (wattpad4j-api) provides a full-featured and easy to consume Java library for working with the Wattpad API.

---

## Using Wattpad4J-API

### **Java 25 Requirement**

Java 25+ is required to use Wattpad4J-API.

### **Javadocs**

Javadocs are available here: [![javadoc.io](https://javadoc.io/badge2/org.wattpad4j/wattpad4j-api/javadoc.io.svg)](https://javadoc.io/doc/org.wattpad4j/wattpad4j-api)

### **Project Set Up**

To utilize Wattpad4J API in your Java project, simply add the following dependency to your project's build file:<br />

**Gradle: build.gradle**

```gradle
dependencies {
    implementation("org.wattpad4j:wattpad4j-api:1.0.0")
}
```

**Maven: pom.xml**

```xml
<dependency>
    <groupId>org.wattpad4j</groupId>
    <artifactId>wattpad4j-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

### **Usage Examples**

Wattpad4J-API is quite simple to use:

```java
// Create a WattpadApi instance to communicate with Wattpad
WattpadApi wattpadApi = new WattpadApi();
```

#### Stories

List the stories of a user.

```java
// Get all the stories of a user, including the all parts of each story
WattpadStories stories = wattpadApi.getStories("USER");

// Get all the stories of a user, without the parts of each story
WattpadStories stories = wattpadApi.getStories("USER", false);

//Get all the stories of a user, with only requesting the id and title fields
WattpadStories stories = wattpadApi.getStories("USER", "id", "title");

// Get the stories of a user, 10 per page which you can iterate over
Pager<WattpadStories> stories = wattpadApi.getStories("USER", 10);
while (stories.hasNext()) {
    WattpadStories s = stories.next();
}
```

#### Lists

List the reading lists of a user.

```java
// Get all the stories of a user, including the all parts of each story
WattpadLists lists = wattpadApi.getLists("USER");

//Get all the stories of a user, with only requesting the id and name fields
WattpadLists lists = wattpadApi.getLists("USER", "id", "name");

// Get the stories of a user, 10 per page which you can iterate over
Pager<WattpadLists> lists = wattpadApi.getLists("USER", 10);
while (lists.hasNext()) {
    WattpadLists l = lists.next();
}
```

#### User

Get information about a user.

```java
// Get information of a user.
WattpadUser wattpadUser = wattpadApi.getUser("USER");
```
