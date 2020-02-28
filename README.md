# combined_logs
Java 8 excercise code

Hello.

The code is tested in jdk8 which I believe most IDEs can automatically recognize compile and run.
If that's not possible for you, you can run it with the following command at the root of the repo:

```
docker run  -i --rm -v "$PWD/src:/src" -v "$PWD/temp:/temp" --tmpfs /output openjdk:8-alpine <<< '(cd /src; find . -type f -name '*.java' -exec javac -d /output {} +); java -classpath /output mx.nhtzr.combinedlogs.CombinedLogs /temp/*.log'
```
