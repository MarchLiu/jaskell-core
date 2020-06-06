# Jaskell Core

Jaskell Core 重新提炼 Jaskell 中的 Parsec 和 SQL 组件库，利用 Scala 
的编程能力，重建更高质量的代码。

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
 