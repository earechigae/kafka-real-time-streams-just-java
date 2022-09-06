# %KAFKA_HOME%\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 3 --topic invalid-pos

echo "kafka-topics --create --zookeeper localhost:2181 --replication-factor 3 --partitions 3 --topic invalid-pos"
kafka-topics --create --zookeeper localhost:2181 --replication-factor 3 --partitions 3 --topic invalid-pos
