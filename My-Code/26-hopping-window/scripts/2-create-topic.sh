# kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 3 --topic simple-invoice

echo "kafka-topics --create --bootstrap-server localhost:2181 --replication-factor 3 --partitions 3 --topic simple-invoice"
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 3 --topic simple-invoice