# kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 3 --topic employees

echo "kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 3 --topic employees"
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 3 --topic employees