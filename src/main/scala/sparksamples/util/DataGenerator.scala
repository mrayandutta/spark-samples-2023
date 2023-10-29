package sparksamples.util

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._
import java.sql.Timestamp
import scala.util.Random

object DataGenerator {
  def main(args: Array[String]): Unit = {
    // Initialize SparkSession
    val spark = SparkSession.builder()
      .appName("HelloWorldSpark")
      .getOrCreate()

    val schema = StructType(Array(
      StructField("user_id", IntegerType, nullable = false),
      StructField("card_id", StringType, nullable = true),
      StructField("transaction_time", TimestampType, nullable = false),
      StructField("transaction_type", StringType, nullable = true),
      StructField("transaction_amount", DoubleType, nullable = false)
    ))

    val estimatedRowSize = 4 + 16 + 8 + 8 + 8 // estimated size of each row in bytes
    val desiredSizeInMB = 1*50 // desired size of the DataFrame in MB
    val numRows = (desiredSizeInMB * 1024 * 1024) / estimatedRowSize // calculate the number of rows needed
    val observedSizeInMB = 3.4
    val scaleFactor = desiredSizeInMB / observedSizeInMB
    val adjustedNumRows = (numRows * scaleFactor).toInt

    val transactionTypes = Array("Credit", "Debit")

    val highSkewUsers = Array(1, 2) // 80% of data
    val mediumSkewUsers = Array(3, 4, 5) // 5% of data
    val lowSkewUsers = Array(6, 7, 8, 9, 10) // 5% of data
    val normalUsers = 11 to 1000 // 10% of data

    val rows = (1 to adjustedNumRows.toInt).map { _ =>
      val skewRand = Random.nextInt(100)
      val userId = skewRand match {
        case x if x < 80 => highSkewUsers(Random.nextInt(highSkewUsers.length))
        case x if x < 85 => mediumSkewUsers(Random.nextInt(mediumSkewUsers.length))
        case x if x < 90 => lowSkewUsers(Random.nextInt(lowSkewUsers.length))
        case _ => normalUsers(Random.nextInt(normalUsers.length))
      }
      val cardId = s"card_${Random.nextInt(1000)}"
      val transactionTime = new Timestamp(System.currentTimeMillis())
      val transactionType = transactionTypes(Random.nextInt(transactionTypes.length))
      val transactionAmount = Random.nextDouble() * 1000
      Row(userId, cardId, transactionTime, transactionType, transactionAmount)
    }

    val rdd = spark.sparkContext.parallelize(rows)
    val df = spark.createDataFrame(rdd, schema)

    // Cache the DataFrame
    df.cache()
    // Trigger an action to populate the cache
    df.count()
    println(s"Generated approximately $desiredSizeInMB MB of data with ${df.count()} rows.")

    // Stop SparkSession
    spark.stop()
  }

}
