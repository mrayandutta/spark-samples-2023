package sparksamples.util

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf

object SparkInfoUtil {
  def main(args: Array[String]): Unit = {
    // Initialize SparkSession
    val spark = SparkSession.builder()
      .appName("SparkInfoPrinter")
      .getOrCreate()

    // Access SparkConf
    val conf = spark.sparkContext.getConf

    // Fetch and print various settings
    println(s"Application Name: ${conf.get("spark.app.name")}")
    println(s"Master: ${conf.get("spark.master", "Not Set")}")
    println(s"Driver Memory: ${conf.get("spark.driver.memory", "Not Set")}")
    println(s"Executor Memory: ${conf.get("spark.executor.memory", "Not Set")}")
    println(s"Executor Cores: ${conf.get("spark.executor.cores", "Not Set")}")
    println(s"Total Executor Cores: ${conf.get("spark.cores.max", "Not Set")}")

    // Create a dummy DataFrame and count the number of rows
    val sampleDF = spark.createDataFrame(Seq((1, "Apple"), (2, "Banana"), (3, "Cherry")))
    println(s"Sample DataFrame Row Count: ${sampleDF.count()}")

    // Stop SparkSession
    spark.stop()
  }
}

