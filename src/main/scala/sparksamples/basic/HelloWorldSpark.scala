package sparksamples.basic

import org.apache.spark.sql.SparkSession


object HelloWorldSpark {
  def main(args: Array[String]): Unit = {
    // Initialize SparkSession
    val spark = SparkSession.builder()
      .appName("HelloWorldSpark")
      //.master("local[*]") // using local mode for demonstration, adjust as needed
      .getOrCreate()

    // Simple 'Hello World' logic
    val data = Seq("Hello", "World")
    val rdd = spark.sparkContext.parallelize(data)
    rdd.foreach(println)

    import spark.implicits._
    // Creating a DataFrame
    val dataframe = Seq(
      ("John", 25),
      ("Doe", 30),
      ("Jane", 28)
    ).toDF("Name", "Age")

    // Showing the DataFrame content
    dataframe.show()

    // Some basic DataFrame operations
    dataframe.filter($"Age" > 25).show()

    // Stop SparkSession
    spark.stop()
  }
}

