#!/bin/sh
cur=$(dirname $0)

export JAVA_HOME=/usr/java/1.8
export SPARK_HOME=/opt/spark
export HADOOP_HOME=/opt/hadoop

program=com.rikima.dwh.hbase.Sample
appjar=./target/scala-2.10/hbase_spark_test_2.10-1.0.jar
#appjar=./target/scala-2.10/hbase_spark_test-assembly-1.0.jar

jars=""
for jar in $(ls ../hbase-1.3.0/lib/*.jar) ; do
    if [ -z $jars ] ; then
        jars=$jar
    else
        jars=$jars,$jar
    fi
done
for jar in $(ls ./lib/*.jar) ; do
    if [ -z $jars ] ; then
        jars=$jar
    else
        jars=$jars,$jar
    fi
done
echo $jars

export SPARK_SUBMIT_OPTIONS="--num-executors 1 --executor-memory 10GB --jars $jars --conf spark.ui.port=3050"

$SPARK_HOME/bin/spark-submit --master local $SPARK_SUBMIT_OPTIONS --class $program $appjar test cf

