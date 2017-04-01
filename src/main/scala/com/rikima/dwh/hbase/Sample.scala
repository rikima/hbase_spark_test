package com.rikima.dwh.hbase

import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.spark.HBaseContext
import org.apache.hadoop.hbase.spark.HBaseRDDFunctions._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.spark.{SparkConf, SparkContext}


/**
  * Created by rikitoku_masaki on 3/31/17.
  */
object Sample {
  val envMap = Map[String,String](("Xmx", "512m"))


  def execute(tableName: String, columnFamily: String): Unit = {
    val sparkConf = new SparkConf().setAppName("HBaseBulkPutExample " +
      tableName + " " + columnFamily)

    val sc = new SparkContext(sparkConf)


    try {
      //[(Array[Byte], Array[(Array[Byte], Array[Byte], Array[Byte])])]
      val rdd = sc.parallelize(Array(
        (Bytes.toBytes("1"),
          Array((Bytes.toBytes(columnFamily), Bytes.toBytes("1"), Bytes.toBytes("1")))),
        (Bytes.toBytes("2"),
          Array((Bytes.toBytes(columnFamily), Bytes.toBytes("1"), Bytes.toBytes("2")))),
        (Bytes.toBytes("3"),
          Array((Bytes.toBytes(columnFamily), Bytes.toBytes("1"), Bytes.toBytes("3")))),
        (Bytes.toBytes("4"),
          Array((Bytes.toBytes(columnFamily), Bytes.toBytes("1"), Bytes.toBytes("4")))),
        (Bytes.toBytes("5"),
          Array((Bytes.toBytes(columnFamily), Bytes.toBytes("1"), Bytes.toBytes("5"))))
      ))

      val conf = HBaseConfiguration.create()

      val hbaseContext = new HBaseContext(sc, conf)

      rdd.hbaseBulkPut(hbaseContext, TableName.valueOf(tableName),
        (putRecord) => {
          val put = new Put(putRecord._1)
          putRecord._2.foreach((putValue) => put.addColumn(putValue._1, putValue._2,
            putValue._3))
          put
        })

    } finally {
      sc.stop()
    }
  }

  def main(args: Array[String]): Unit = {
    val tableName = args(0)
    val columnFamily = args(1)

    if (args.length < 2) {
      println("HBaseBulkPutExample {tableName} {columnFamily} are missing an arguments")
      return
    }

    execute(tableName, columnFamily)
  }

}
