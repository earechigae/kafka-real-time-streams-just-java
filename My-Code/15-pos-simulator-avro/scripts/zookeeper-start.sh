# %KAFKA_HOME%\bin\windows\zookeeper-server-start.bat %KAFKA_HOME%\etc\kafka\zookeeper.properties
# zookeeper-server-start /usr/local/etc/kafka/zookeeper-my-cluster.properties

echo "/usr/local/Cellar/confluent-6.2.0/bin/zookeeper-server-start /usr/local/Cellar/confluent-6.2.0/etc/kafka/zookeeper-my-cluster.properties"
/usr/local/Cellar/confluent-6.2.0/bin/zookeeper-server-start /usr/local/Cellar/confluent-6.2.0/etc/kafka/zookeeper-my-cluster.properties
