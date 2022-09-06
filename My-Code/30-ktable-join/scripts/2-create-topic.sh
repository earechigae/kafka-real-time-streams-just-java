# kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 2 --topic user-master & kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 2 --topic user-login

echo "kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 2 --topic user-master & kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 2 --topic user-login"
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 2 --topic user-master & kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 2 --topic user-login