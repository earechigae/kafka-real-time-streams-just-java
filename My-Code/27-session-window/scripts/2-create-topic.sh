# kafka-topics.bat  --create --zookeeper localhost:2181 --replication-factor 3 --partitions 2 --topic user-clicks --config min.insync.replicas=2

echo "kafka-topics  --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 2 --topic user-clicks --config min.insync.replicas=2"
kafka-topics  --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 2 --topic user-clicks --config min.insync.replicas=2