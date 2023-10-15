package sparksamples.coreapi

package com.yourcompany.sparksamples.coreapi

import org.apache.spark.sql.SparkSession

object WordCount {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("WordCount")
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    // Accessing the text file from the resources directory
    val filePath = getClass.getResource("/sample.txt").getPath

    val fileRDD = sc.textFile(filePath)

    val wordCounts = fileRDD
      .flatMap(line => line.split("\\s+"))
      .map(word => (word.toLowerCase, 1))
      .reduceByKey(_ + _)

    // Displaying the word counts
    wordCounts.collect().foreach {
      case (word, count) => println(s"$word : $count")
    }

    // Stopping SparkSession
    spark.stop()
  }
}

