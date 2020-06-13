# Jaskell Core

Jaskell Core 重新提炼 Jaskell 中的 Parsec 和 SQL 组件库，利用 Scala 
的编程能力，重建更高质量的代码。

[![Maven Central](https://img.shields.io/maven-central/v/io.github.marchliu/jaskell-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.marchliu%22%20AND%20a:%22jaskell-core%22)

# INSTALLATION

## Maven

```xml
<dependency>
  <groupId>io.github.marchliu</groupId>
  <artifactId>jaskell-core</artifactId>
  <version>0.5.3</version>
</dependency>
```

## Gradle

```groovy
implementation 'io.github.marchliu:jaskell-core:0.5.3'
```

## Gradle Kotlin

```
implementation("io.github.marchliu:jaskell-core:0.5.3")
```

## SBT

```scala
libraryDependencies += "io.github.marchliu" % "jaskell-core" % "0.5.3"
```

## Apache Ivy

```xml
<dependency org="io.github.marchliu" name="jaskell-core" rev="0.5.3" />
```

## Groovy Grap

```groovy
@Grapes(
  @Grab(group='io.github.marchliu', module='jaskell-core', version='0.5.3')
)
```

## Leiningen

```clojure
[io.github.marchliu/jaskell-core "0.5.3"]
```

## Apache Bluildr

```
'io.github.marchliu:jaskell-core:jar:0.5.3'
```

## Maven Central Badge

```
[![Maven Central](https://img.shields.io/maven-central/v/io.github.marchliu/jaskell-core.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.github.marchliu%22%20AND%20a:%22jaskell-core%22)```
```

## PURL

```
pkg:maven/io.github.marchliu/jaskell-core@0.5.3
```

### Bazel

```
maven_jar(
    name = "jaskell-core",
    artifact = "io.github.marchliu:jaskell-core:0.5.3",
    sha1 = "6a405e182efbd5bf1923b5b83cbbc1890329e759",
)
```


# 更新日志

## 0.1

 - 重新实现了 parsec 组件，对有序列表（Seq）和文本提供更有针对性的支持
 
## 0.2

 - 提供了支持位置查询的 txt state
 - State apply string 生成 txt state。
 
## 0.3

 - upgrade to scala 2.13
 - finish sql specs

## 0.4
 
 - add documents for parsec parser
 - bug fixed about statement write action 
 - add <|> adn <?> operator
 - add opt, endBy and sepEndBy
 - fixed int parser bug
 - fixed skipWhitespaces function error
 - add ? operator
 
### 0.4.1

 - add expression parser

### 0.4.2
 
 - fixed a binary bug.
 
### 0.5

 - rewrite all parsec as functional

### 0.5.1
 
 - add ! operator
 
### 0.5.2

 - add parameter expression

### 0.5.3

 - add distinct
 