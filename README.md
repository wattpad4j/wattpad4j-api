# Wattpad4J; API (witlab4j-api)<br />Java Client Library for the Wattpad API

[![Maven Central](https://img.shields.io/maven-central/v/org.wattpad4j/wattpad4j-api.svg)](http://mvnrepository.com/artifact/org.wattpad4j/wattpad4j-api)
[![Build Status](https://github.com/wattpad4j/wattpad4j-api/actions/workflows/ci-build.yml/badge.svg?branch=main)](https://github.com/wattpad4j/wattpad4j-api/actions/workflows/ci-build.yml)
[![javadoc.io](https://javadoc.io/badge2/org.wattpad4j/wattpad4j-api/javadoc.io.svg)](https://javadoc.io/doc/org.wattpad4j/wattpad4j-api)

Wattpad4j API (wattpad4j-api) provides a full-featured and easy to consume Java library for working with the Wattpad API.

---

## Using Wattpad4J-API

### **Java 25 Requirement**

As of Wattpad4J-API 1.0.0, Java 25+ is now required to use Wattpad4J-API.

### **Javadocs**

Javadocs are available here: [![javadoc.io](https://javadoc.io/badge2/org.wattpad4j/wattpad4j-api/javadoc.io.svg)](https://javadoc.io/doc/org.wattpad4j/wattpad4j-api)

### **Project Set Up**

To utilize Wattpad4J; API in your Java project, simply add the following dependency to your project's build file:<br />

**Gradle: build.gradle**

```java
dependencies {
    ...
	implementation group:'org.wattpad4j', name:'wattpad4j-api', version:'1.0.0'
}
```

**Maven: pom.xml**

```xml

<dependency>
    <groupId>org.wattpad4j</groupId>
    <artifactId>wattpad4j-api</artifactId>
    <version>1.1.0</version>
</dependency>
```

### **Usage Examples**

Wattpad4J-API is quite simple to use:

```java
// Create a WattpadApi instance to communicate with Wattpad
WattpadApi wattpadApi = new WattpadApi(WattpadConstants.BASE_URL);

// Get the stories of a user
WattpadStories stories = wattpadApi.getStories("USER");
```
