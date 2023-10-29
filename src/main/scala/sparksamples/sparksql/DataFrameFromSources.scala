package sparksamples.sparksql

import org.apache.spark.sql.{SparkSession, DataFrame}
import org.slf4j.LoggerFactory

object DataFrameFromSources {
  private val logger = LoggerFactory.getLogger(DataFrameFromSources.getClass)

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("DataFrameFromSources")
      .master("local[*]")
      .getOrCreate()

    logger.info("Reading CSV file into DataFrame")
    val csvFilePath = getClass.getResource("/data.csv").getPath
    val csvDF = spark.read.option("header","true").format("csv").load(csvFilePath)

    logger.info("Showing DataFrame:")
    csvDF.show()

    logger.info("Printing DataFrame schema:")
    csvDF.printSchema()

    logger.info("Selecting specific columns:")
    csvDF.select("name", "age").show()

    logger.info("Reading Parquet file into DataFrame")
    //val parquetDF: DataFrame = spark.read.parquet("path_to_parquet_file.parquet")

    logger.info("Reading JSON file into DataFrame")
    val jsonDF: DataFrame = spark.read.json("data.json")

    // Remember to replace the paths with your actual file paths

    spark.stop()
  }
}

