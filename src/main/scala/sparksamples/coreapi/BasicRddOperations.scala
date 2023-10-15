package sparksamples.coreapi

import org.apache.spark.sql.SparkSession

object BasicRddOperations {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("BasicRDDOperations")
      .master("local[*]")
      .getOrCreate()

    val sc = spark.sparkContext

    // Creating an RDD from a sequence
    val numbers = sc.parallelize(Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))

    // Applying transformations
    val squared = numbers.map(num => num * num)
    val evenNumbers = numbers.filter(num => num % 2 == 0)

    // Applying actions
    println("Squared numbers:")
    squared.collect().foreach(println)

    println("\nEven numbers:")
    evenNumbers.collect().foreach(println)

    // Stopping SparkSession
    spark.stop()
  }
}

