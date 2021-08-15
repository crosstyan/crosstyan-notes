# Maven as package manager

I don't like Java the language, and I even hate Java. You should know I was using dynamic language like JavaScript when I started to learn how to code, which is still popular. I barely learnt C the language. Although had learnt some basic concept before entering the college, I still don't think I was a programmer at that time and am a qualified programmer now.

There's still a long way to go but I've a grasp some basic idea at least, like the package manager. Every proper (or popular) programming language should own a set of tools to help manage dependencies and build projects. In JavaScript we get [npm (Node Package Manager)](https://en.wikipedia.org/wiki/Npm_(software)), whose release was in a time before I started to code, way before, but relatively it's new compared with [Apache Maven](https://en.wikipedia.org/wiki/Apache_Maven) and [Apache Ant](https://en.wikipedia.org/wiki/Apache_Ant), not to mention the older [GNU Autotools](https://en.wikipedia.org/wiki/GNU_Autotools) and [GNU Make](https://en.wikipedia.org/wiki/Make_(software)), which released in the 90s.

You see, building a project is quite complicated, especially with a language which you can write an Operating System, like C and C++, maybe Rust (I have not learnt it yet). Programming a MCU without MMU [(Memory management unit)](https://en.wikipedia.org/wiki/Memory_management_unit) is also challenging. It's not easy to get your mind settle down when you have not full understanding of those details under the hood like [Garbage collection](https://en.wikipedia.org/wiki/Garbage_collection_(computer_science)) and some math stuff like [Currying](https://stackoverflow.com/questions/36314/what-is-currying), but it's easy enough to write some code, click the "build" button and everything works magically. Indeed, modern computer is a kind of magic, modern magic.

What we need to makes a good, competent programmer is kinds of different from what those "good enough" programmers factories have. I think algorithm and the mathematics play a crucial part, unfortunately I sucks at them, but we can't underestimate the importance of tool chains. In some case when people, especially beginners, don't appreciate the tools, these tools become burden and explanation why we need them and *how can we work without them when project is simple* is needed. [IDE (Integrated development environment)](https://en.wikipedia.org/wiki/Integrated_development_environment) is essential when dealing with huge project. However text editor become much cooler with cli (command line) based tool chains (think about vim and vscode)

It's almost impossible to write programme without third-party libraries. Dependencies!

[package-manager · GitHub Topics](https://github.com/topics/package-manager)

- [python-poetry/poetry: Python dependency management and packaging made easy.](https://github.com/python-poetry/poetry)

## Maven Configuration

Some basic knowledge for Maven is needed when working with Java.

- [java - Is Maven similar to npm? - Stack Overflow](https://stackoverflow.com/questions/38388824/is-maven-similar-to-npm)
- [Maven – Introduction to the Build Lifecycle](http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
- [公共代理库 - 云效 - 阿里云](https://help.aliyun.com/document_detail/102512.html?spm=a2c40.aliyun_maven_repo.0.0.36183054eGk3vS)
- [Maven - 构建生命周期 - Maven 教程 - 极客学院Wiki](https://wiki.jikexueyuan.com/project/maven/build-life-cycle.html)
- [Maven介绍 - 廖雪峰的官方网站](https://www.liaoxuefeng.com/wiki/1252599548343744/1309301146648610)
- [介绍maven的作用、核心概念(Pom、Repositories、Artifact、Goal)、用法、常用参数和命令以及简单故障排除、扩展及配置](https://www.trinea.cn/android/maven/)
- [在 VS Code 上使用 Maven - HackMD](https://hackmd.io/@zxcj04/vscode_maven)
- [How to use Maven to build Clojure code](http://alexott.net/en/clojure/ClojureMaven.html)
- [Spring Boot 使用 Maven assembly 插件打包_Mrqiang9001-CSDN博客](https://blog.csdn.net/Mrqiang9001/article/details/114211224)

```bash
nohup java ${JAVA_OPT} -jar ${BASE_PATH}/target/${APPLICATION_JAR} --spring.config.location=${CONFIG_DIR} > ${LOG_PATH} 2>&1 &
```

### Spring Boot Config File

- [是时候彻底搞清楚 Spring Boot 的配置文件 application.properties 了！ - 江南一点雨](http://www.javaboy.org/2019/0530/application.properties.html)

### Gradle

- [Gradle Build Tool](https://gradle.org/)

### Leiningen

- [Leiningen](https://leiningen.org/)

## Maven Polyglot

The readability of `pom.xml`  is poor. [XML Tools](https://marketplace.visualstudio.com/items?itemName=DotJoshJohnson.xml) helps but I wish I can use `yaml`

- [Maven Polyglot | Baeldung](https://www.baeldung.com/maven-polyglot)
- [takari/polyglot-maven: Support alternative markup for Apache Maven POM files](https://github.com/takari/polyglot-maven)

I don't know what `maven.multiModuleProjectDirectory` is and have no idea about `extensions.xml`

> We have created a simple Maven plugin that will help you convert any existing pom.xml files

```bash
# XML converted to yaml
mvn io.takari.polyglot:polyglot-translate-plugin:translate  -Dinput="pom.xml" -Doutput="pom.yaml"
# converted back
mvn io.takari.polyglot:polyglot-translate-plugin:translate  -Dinput="pom.yaml" -Doutput="pom.xml"
```

## JVM Language

I hate Java. But others JVM language?

- [Java 、Groovy、 Scala 的未来会怎样？ - 知乎](https://www.zhihu.com/question/21740715)
- [Scala、Groovy、Clojure 、Kotlin 分别解决了 Java 的什么痛点？ - 知乎](https://www.zhihu.com/question/48633827)

### Clojure

We need a modern lisp language that can be used in production. You should know that lisp is the future!

- [Clojure: Hello Vert.x (vertx-lang-clojure发布) - 知乎](https://zhuanlan.zhihu.com/p/33841273)
- [nrepl](https://github.com/nrepl/nrepl)
- [Calva User Guide](https://calva.io/)

### Scala

[Download Scala 3 | The Scala Programming Language](https://www.scala-lang.org/download/scala3.html)

### Groovy

- [The Apache Groovy programming language](https://groovy-lang.org/)

### Vert.X

- [初创团队选择 Vert.x 的避坑考虑和上手资料 - 知乎](https://zhuanlan.zhihu.com/p/33832486)
- [Documentation | Eclipse Vert.x](https://vertx.io/docs/)

## Design Pattern

I hate design pattern because it will cause a problem.

> I suppose it is tempting, if the only tool you have is a hammer, to treat everything as if it were a nail
>
> Usually the need for patterns arises when people choose a programming language or a technology that lacks the necessary level of abstraction.

- [Refactoring and Design Patterns](https://refactoring.guru/)
- [Are Design Patterns Missing Language Features](http://wiki.c2.com/?AreDesignPatternsMissingLanguageFeatures)
- [Design Patterns with First-Class Functions - Fluent Python [Book]](https://www.oreilly.com/library/view/fluent-python/9781491946237/ch06.html)

```txt
 VisitorPattern  .............. GenericFunctions (MultipleDispatch)
 FactoryPattern  .............. MetaClasses, closures
 SingletonPattern ............. MetaClasses
 IteratorPattern............... AnonymousFunctions 
              (used with HigherOrderFunctions, 
               MapFunction, FilterFunction, etc.)
 InterpreterPattern............ Macros (extending the language)
               EvalFunction, MetaCircularInterpreter
               Support for parser generation (for differing syntax)
 CommandPattern ............... Closures, LexicalScope, 
               AnonymousFunctions, FirstClassFunctions
 HandleBodyPattern............. Delegation, Macros, MetaClasses
 RunAndReturnSuccessor......... TailCallOptimization
 Abstract-Factory,
 Flyweight,
 Factory-Method,
 State, Proxy,
 Chain-of-Responsibility....... FirstClass types (Norvig)
 Mediator, Observer............ Method combination (Norvig)
 BuilderPattern................ Multi Methods (Norvig)
 FacadePattern................. Modules (Norvig)
 StrategyPattern............... higher order functions (Gene Michael Stover?), ControlTable
 AssociationList................Dictionaries, maps, HashTables
                    (these go by numerous names in different languages)
```
