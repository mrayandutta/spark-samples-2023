package sparksamples.util

import org.apache.spark.scheduler._
import scala.collection.mutable
import org.apache.spark.TaskEndReason
import org.apache.spark.Success


  class DebuggingSparkListener extends SparkListener {
    val jobToStages: mutable.Map[Int, List[Int]] = mutable.Map()

    private def log(message: String): Unit = {
      println(s"*******@@@@@ $message")
    }

    override def onJobStart(jobStart: SparkListenerJobStart): Unit = {
      val stageIds = jobStart.stageIds.toList  // Convert Seq[Int] to List[Int]
      jobToStages(jobStart.jobId) = stageIds
      log(s"Job started with ID: ${jobStart.jobId}, Stages: ${stageIds.mkString(",")}")
    }


    override def onJobEnd(jobEnd: SparkListenerJobEnd): Unit = {
      jobEnd.jobResult match {
        case JobSucceeded => log(s"Job ${jobEnd.jobId} succeeded.")
        case JobFailed(exception) =>
          log(s"Job ${jobEnd.jobId} failed due to: ${exception.getMessage}")
          log(s"Stack Trace: ${exception.getStackTrace.mkString("\n")}")
      }
    }

    override def onStageSubmitted(stageSubmitted: SparkListenerStageSubmitted): Unit = {
      val stageInfo = stageSubmitted.stageInfo
      log(s"Stage submitted: ${stageInfo.name}, ID: ${stageInfo.stageId}, Associated Job IDs: ${jobToStages.collect { case (jobId, stageIds) if stageIds.contains(stageInfo.stageId) => jobId }.mkString(",")}")
    }

    override def onStageCompleted(stageCompleted: SparkListenerStageCompleted): Unit = {
      stageCompleted.stageInfo.failureReason match {
        case Some(reason) => log(s"Stage ${stageCompleted.stageInfo.stageId} failed due to: $reason")
        case None => log(s"Stage ${stageCompleted.stageInfo.stageId} completed successfully.")
      }
    }

    override def onTaskStart(taskStart: SparkListenerTaskStart): Unit = {
      log(s"Task started with ID: ${taskStart.taskInfo.taskId}, Stage: ${taskStart.stageId}")
    }

    override def onTaskEnd(taskEnd: SparkListenerTaskEnd): Unit = {
      taskEnd.reason match {
        case Success => log(s"Task ${taskEnd.taskInfo.taskId} succeeded.")
        case _ =>
          log(s"Task ${taskEnd.taskInfo.taskId} failed due to: ${taskEnd.reason}")
          log(s"Task Metrics: ${taskEnd.taskMetrics}")
      }
    }

    override def onExecutorAdded(executorAdded: SparkListenerExecutorAdded): Unit = {
      log(s"Executor added: ${executorAdded.executorId}")
    }

    override def onExecutorRemoved(executorRemoved: SparkListenerExecutorRemoved): Unit = {
      log(s"Executor removed: ${executorRemoved.executorId}, Reason: ${executorRemoved.reason}")
    }

    override def onOtherEvent(event: SparkListenerEvent): Unit = {
      log(s"Other event: $event")
    }

  }
