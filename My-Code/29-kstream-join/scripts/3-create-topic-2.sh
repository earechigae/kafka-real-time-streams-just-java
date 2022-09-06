# kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 2 --topic payment_confirmation

echo "kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 2 --topic payment_confirmation"
kafka-topics --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 2 --topic payment_confirmation