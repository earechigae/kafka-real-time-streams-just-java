# kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 2 --topic loyalty --config min.insync.replicas=2

echo "kafka-topics --create --zookeeper localhost:2181 --replication-factor 3 --partitions 2 --topic loyalty --config min.insync.replicas=2"
kafka-topics --create --zookeeper localhost:2181 --replication-factor 3 --partitions 2 --topic loyalty --config min.insync.replicas=2