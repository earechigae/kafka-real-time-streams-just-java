# %KAFKA_HOME%\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic pos --from-beginning
# kafka-console-consumer --bootstrap-server localhost:9092 --topic pos --from-beginning

echo "/usr/local/Cellar/confluent-6.2.0/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic pos --from-beginning"
/usr/local/Cellar/confluent-6.2.0/bin/kafka-console-consumer --bootstrap-server localhost:9092 --topic pos --from-beginning
