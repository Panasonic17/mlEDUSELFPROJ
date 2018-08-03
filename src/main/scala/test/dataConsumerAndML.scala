package test

import data.source.SocketTestSource
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object dataConsumerAndML {
  def main(args: Array[String]): Unit = {
    val inverseFuture: Future[Unit] = Future {
      SocketTestSource.main(null)
    }

    val spark =
      SparkSession
        .builder.master("local[*]")
        .appName("StructuredNetworkWordCount")
        .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    import spark.implicits._


    //    C:\WORK_DIR\sharedeletme

    val lines: DataFrame = spark.readStream
      //      .format("file")
//      .option("path", "C:\\WORK_DIR\\sharedeletme")
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999).load()
    //      .schema(userSchema).
    //      .csv("C:\\WORK_DIR\\sharedeletme")

    // Split the lines into words
    val words: Dataset[String] = lines.as[String].flatMap(_.split(" "))


    // Generate running word count
    val wordCounts = words.map(w => {
      println(w)
      w
    }).groupBy("value").count()


    //wordCounts.show()
    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()
    //    wordCounts.show()
    query.awaitTermination()
    //    wordCounts
  }
}
