 <h3>To build run command below</h3>
 
 <code>
 docker build . -t bigstinky/restservice:latest --build-arg JAR_NAME="JAR_FILE_NAME" --build-arg DB_HOST="DATABASE_HOST" --build-arg DB_NAME="DATABASE_NAME" --build-arg DB_USER="DATABASE_USER" --build-arg DB_PASS="DATABASE_PASS"
 </code>
 
 Replace placeholders JAR_FILE_NAME, DATABASE_HOST, DATABASE_NAME, DATABASE_USER, DATABASE_PASS with actual names
 
 docker run --rm -it --network host --name springbootapp bigstinky/restservice:latest
 