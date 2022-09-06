# kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic partitioned-producer --from-beginning

echo "kafka-console-consumer --bootstrap-server localhost:9092 --topic partitioned-producer --from-beginning"
kafka-console-consumer --bootstrap-server localhost:9092 --topic partition-producer --from-beginning