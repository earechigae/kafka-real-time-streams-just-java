# kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic person-age

echo "kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic person-age"
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic person-age