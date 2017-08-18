Summary
=======
A Spring boot Application with Leader Election using spring-integration.

Features
========
- Leader Election using Zookeeper and Spring-integration
- Loads resource(s) that are decorated with build time properties such as ``project.version``.
    See `docs <https://docs.spring.io/spring-boot/docs/current/reference/html/howto-properties-and-configuration.html>`_
- Use @Primary, when there are more than 1 candidate beans found for autowiring
- DropWizard ScheduledReporters have start and stop methods, but are not designed to be started and stopped more thanonce. Hence using a Prototype Scope with these Reporters to get a different instance on Leader election.
- System Metrics and Health using spring-actuator
- Uses an external XML configuration file in addition  to application.properties
    - Uses apache commons configuration to parse the XML configuration
    - Issue: PlaceHolders in Lists are not expanded

Notes
=====
#. Structured app as shown `here <https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html>`_
#. Spring-Integration `documentation <https://github.com/spring-projects/spring-integration/blob/master/src/reference/asciidoc/zookeeper.adoc>`_ on leader election
#.  Spring-cloud: _<> has been sunset in favor of Spring-Integration.
#. `Interesting issue <https://github.com/spring-cloud/spring-cloud-zookeeper/issues/93>`_
#. Source on `github <https://github.com/spring-projects/spring-integration/tree/master/spring-integration-core/src/main/java/org/springframework/integration/leader>`_
#. `Roles <http://docs.spring.io/spring-integration/reference/html/messaging-endpoints-chapter.html#endpoint-roles>`_

Testing
=======

ZooKeeper
---------
If you don't have Zookeeper installed, the easiest way to run Zookeeper is to start ``TestingServer``.
See com.tutorial.spring.pictogram.PictogramApplicationTests

Leader Election
---------------
Use SSH local port forwarding::

    ssh -NL 9191:localhost:2181 localhost

Starting Multiple Instances of Pictogram
----------------------------------------
Change the following Program Arguments for each instance::

    --zookeeper.port=9191
    --management.port=9091
    --server.port=9090






