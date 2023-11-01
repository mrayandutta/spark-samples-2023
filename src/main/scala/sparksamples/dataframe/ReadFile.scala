package sparksamples.dataframe

import org.apache.spark.sql.SparkSession

object ReadFile {
  def main(args: Array[String]): Unit = {
    // Initialize Spark Session
    val spark = SparkSession.builder()
      .appName("Read CSV File from HDFS")
      .getOrCreate()

    val df = spark.read.format("csv").load("hdfs:///input/1GB/*")
    println(s"Count of rows: ${df.count()}")

    // Stop the Spark Session
    spark.stop()
  }
}

