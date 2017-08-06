Summary
=======
A Spring boot Application with Leader Election using spring-integration.

Features
========
- Leader Election using Zookeeper and Spring-integration
- Loads resource(s) that are decorated with build time properties such as `project.version`.
See `docs <https://docs.spring.io/spring-boot/docs/current/reference/html/howto-properties-and-configuration.html>`_

Notes
=====
#. Structured app as shown `here <https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html>`_
#. Spring-Integration `docs <https://github.com/spring-projects/spring-integration/blob/master/src/reference/asciidoc/zookeeper.adoc>`_ on leader election
#.  Spring-cloud: _<> has been sunset in favor of Spring-Integration.
#. `Interesting issue <https://github.com/spring-cloud/spring-cloud-zookeeper/issues/93>`_
#. Source on `github <https://github.com/spring-projects/spring-integration/tree/master/spring-integration-core/src/main/java/org/springframework/integration/leader>`_
#. `Roles <http://docs.spring.io/spring-integration/reference/html/messaging-endpoints-chapter.html#endpoint-roles>`_



