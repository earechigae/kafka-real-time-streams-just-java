# kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 3 --topic word-count

echo "kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 3 --topic word-count"
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 3 --topic word-count