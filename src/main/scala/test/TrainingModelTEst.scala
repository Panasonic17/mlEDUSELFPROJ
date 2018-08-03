package test

import data.source.SocketTestSource
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.linalg.{VectorUDT, Vectors}
import org.apache.spark.mllib.regression.LinearRegressionWithSGD
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import scala.collection.immutable

object TrainingModelTEst {
  def main(args: Array[String]): Unit = {
    val data1: IndexedSeq[(Double, Double)] = for (i <- 0.0 until 100.0 by 0.2) yield
      (SocketTestSource.function(i + 0.0), i + 0.0)

    val spark =
      SparkSession
        .builder.master("local[*]")
        .appName("StructuredNetworkWordCount")
        .getOrCreate()
    import spark.implicits._
    spark.sparkContext.setLogLevel("ERROR")

    val data = spark.createDataFrame(data1).toDF("y", "x")


    import org.apache.spark.ml.Pipeline
    import org.apache.spark.ml.feature.VectorAssembler
    import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}

    // Set Features
    val features = new VectorAssembler()
      .setInputCols(Array("x"))
      .setOutputCol("features")



  }
}
/*
%spark2.spark

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}

// Set Features
val features = new VectorAssembler()
    .setInputCols(Array("x"))
    .setOutputCol("features")

val linreg = new LinearRegression().setLabelCol("y")

val pipeline = new Pipeline().setStages(Array(features, linreg))
val model = pipeline.fit(data)


%spark2.spark

val linRegModel = model.stages(1).asInstanceOf[LinearRegressionModel]

println(s"RMSE:  ${linRegModel.summary.rootMeanSquaredError}")
println(s"r2:    ${linRegModel.summary.r2}")
println(s"Model: Y = ${linRegModel.coefficients(0)} * X + ${linRegModel.intercept}")

linRegModel.summary.residuals.show()


%spark2.spark

val result = model.transform(data).select("x", "y", "prediction")

result.show()



%spark2.spark

result.createOrReplaceTempView("linreg")



%spark2.spark

linreg.write.overwrite().save("hdfs:///tmp/linregmodel")




%spark2.spark

import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}

val sameModel = LinearRegression.load("hdfs:///tmp/linregmodel")
val sameLinRegModel = model.stages(1).asInstanceOf[LinearRegressionModel]

// Verify coefficients and intercept
println(s"Coefficient: ${sameLinRegModel.coefficients} Intercept: ${sameLinRegModel.intercept}")

 */