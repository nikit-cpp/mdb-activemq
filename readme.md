1) Download and unzip WildFly 8.2.0 and ActiveMQ 5.12.2 (https://archive.apache.org/dist/activemq/5.12.2/)

2) Download ActiveMQ RAR(http://mvnrepository.com/artifact/org.apache.activemq/activemq-rar/5.12.2)

3.0) Start ActiveMQ (http://activemq.apache.org/getting-started.html)
```
cd [activemq_install_dir]/bin
./activemq console
```

3.1) ActiveMQ Web UI available at (http://localhost:8161/admin)

4.0) copy activemq-rar-5.12.2.rar to wildfly-8.2.0.Final/standalone/deployments/

4.1) Configure WildFly (http://www.mastertheboss.com/jboss-server/jboss-jms/integrate-activemq-with-wildfly)

add to standalone.xml (no need standalone-full.xml)
```
		<subsystem xmlns="urn:jboss:domain:resource-adapters:2.0">
            <resource-adapters>
                <resource-adapter id="activemq">
                    <archive>
                        activemq-rar-5.12.2.rar
                    </archive>
                    <transaction-support>XATransaction</transaction-support>
                    <config-property name="ServerUrl">
                        tcp://localhost:61616
                    </config-property>
                    <config-property name="UserName">
                        admin
                    </config-property>
                    <config-property name="UseInboundSession">
                        false
                    </config-property>
                    <config-property name="Password">
                        admin
                    </config-property>
                    <connection-definitions>
                        <connection-definition class-name="org.apache.activemq.ra.ActiveMQManagedConnectionFactory" jndi-name="java:/ConnectionFactory" enabled="true" pool-name="ConnectionFactory">
                            <xa-pool>
                                <min-pool-size>1</min-pool-size>
                                <max-pool-size>20</max-pool-size>
                                <prefill>false</prefill>
                                <is-same-rm-override>false</is-same-rm-override>
                            </xa-pool>
                        </connection-definition>
                    </connection-definitions>
                    <admin-objects>
                        <admin-object class-name="org.apache.activemq.command.ActiveMQTopic" jndi-name="java:jboss/activemq/topic/TestTopic" use-java-context="true" pool-name="TestTopic">
                            <config-property name="PhysicalName">
                                activemq/topic/TestTopic
                            </config-property>
                        </admin-object>
                        <admin-object class-name="org.apache.activemq.command.ActiveMQQueue" jndi-name="java:jboss/activemq/queue/TestQueue" use-java-context="true" pool-name="TestQueue">
                            <config-property name="PhysicalName">
                                activemq/queue/TestQueue
                            </config-property>
                        </admin-object>
                    </admin-objects>
                </resource-adapter>
            </resource-adapters>
        </subsystem>
```

4.2) to 

```
<subsystem xmlns="urn:jboss:domain:ejb3:2.0">
```
add

```
			<mdb>
                <resource-adapter-ref resource-adapter-name="activemq"/>
            </mdb>
```

5) start WildFly

6) build war

7) deploy war

8) 
```
curl http://127.0.0.1:8080/mdb-activemq-1.0/jms
```
... and watch WildFly log
