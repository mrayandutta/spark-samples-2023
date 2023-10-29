package sparksamples.dataframe

import org.apache.spark.sql.SparkSession

object SimpleDataFrame {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder()
      .appName("Simplified DataFrame")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val data = Seq(
      ("Table1", "Column1", "String", Some(255), true),
      ("Table1", "Column2", "Int", None, false),
      ("Table2", "Column1", "String", Some(100), true)
    )

    val df = data.toDF("TABLE_NAME", "COLUMN_NAME", "DATATYPE", "MAXLENGTH", "NULLABLE")

    df.show()

    spark.stop()
  }
}

