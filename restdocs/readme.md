# Spring REST Docs
* https://spring.io/projects/spring-restdocs#learn
* https://docs.spring.io/spring-restdocs/docs/current/reference/html5/
## docs
### Introduction
* Spring REST Docs:帮助为RESTFUL服务制作精确 可读的文档
* Spring REST Docs默认使用 [Asciidoctor](https://asciidoctor.org/)
    * Asciidoctor:基于Ruby的快速 开源文本处理器,处理纯文本文件并生成HTML以及其样式和布局,也可以配置为使用Markdown格式
* Spring REST Docs使用Spirng MVC test框架以及 Spring WebFlux的WebTestClient和[REST Assured 3](http://rest-assured.io/)产生的测试的片段.
    * 测试驱动方式帮助保证service文档的准确性
    * 如果片段不正确,测试也会失败
* 记录一个 RESTful 服务主要是描述它的资源。每个资源描述的两个关键部分是它使用的 HTTP 请求的详细信息和它产生的 HTTP 响应。 
* Spring REST Docs 允许您使用这些资源以及 HTTP 请求和响应，从而使您的文档免受服务实现的内部细节的影响。这种分离有助于您记录服务的 API，而不是其实现。它还使您可以自由地改进实现，而无需重新编写文档。
