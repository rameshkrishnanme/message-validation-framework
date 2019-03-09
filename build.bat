mvn clean install
java -jar target/semantikos-0.0.1-SNAPSHOT.jar --server.port=8081 --spring.datasource.url=jdbc:hsqldb:file:///C:/d_db2/semantic;shutdown=true;hsqldb.write_delay=false;