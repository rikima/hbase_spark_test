name := "hbase_spark_test"

version := "1.0"

scalaVersion := "2.10.6"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}


libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.6.3"
libraryDependencies += "org.apache.spark" % "spark-mllib_2.10" % "1.6.3"
libraryDependencies += "org.apache.spark" % "spark-hive_2.10" % "1.6.3"
libraryDependencies += "org.apache.spark" % "spark-sql_2.10" % "1.6.3"

libraryDependencies += "org.apache.hbase" % "hbase-server" % "1.3.0"
libraryDependencies += "org.apache.hbase" % "hbase-annotations" % "1.3.0"
libraryDependencies += "org.apache.hbase" % "hbase-client" % "1.3.0"
libraryDependencies += "org.apache.hbase" % "hbase-common" % "1.3.0"

