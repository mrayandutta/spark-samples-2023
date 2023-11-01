package sparksamples.util

import org.apache.spark.scheduler._

class DebuggingSparkListener extends SparkListener {

  // Job Events
  override def onJobStart(jobStart: SparkListenerJobStart): Unit = {
    println(s"Job started with ID: ${jobStart.jobId}, Stages: ${jobStart.stageIds.mkString(",")}")
  }

  override def onJobEnd(jobEnd: SparkListenerJobEnd): Unit = {
    println(s"Job ended with ID: ${jobEnd.jobId}, Result: ${jobEnd.jobResult}")
  }

  // Stage Events
  override def onStageSubmitted(stageSubmitted: SparkListenerStageSubmitted): Unit = {
    println(s"Stage submitted: ${stageSubmitted.stageInfo.name}, ID: ${stageSubmitted.stageInfo.stageId}")
  }

  override def onStageCompleted(stageCompleted: SparkListenerStageCompleted): Unit = {
    println(s"Stage completed: ${stageCompleted.stageInfo.name}, ID: ${stageCompleted.stageInfo.stageId}")
  }

  // Task Events
  override def onTaskStart(taskStart: SparkListenerTaskStart): Unit = {
    println(s"Task started with ID: ${taskStart.taskInfo.taskId}, Stage: ${taskStart.stageId}")
  }

  override def onTaskGettingResult(taskGettingResult: SparkListenerTaskGettingResult): Unit = {
    println(s"Task getting result with ID: ${taskGettingResult.taskInfo.taskId}")
  }

  override def onTaskEnd(taskEnd: SparkListenerTaskEnd): Unit = {
    println(s"Task ended with ID: ${taskEnd.taskInfo.taskId}, Metrics: ${taskEnd.taskMetrics}, Reason: ${taskEnd.reason}")
  }

  // Executor Events
  override def onExecutorAdded(executorAdded: SparkListenerExecutorAdded): Unit = {
    println(s"Executor added: ${executorAdded.executorId}")
  }

  override def onExecutorRemoved(executorRemoved: SparkListenerExecutorRemoved): Unit = {
    println(s"Executor removed: ${executorRemoved.executorId}")
  }

  override def onExecutorBlacklisted(executorBlacklisted: SparkListenerExecutorBlacklisted): Unit = {
    println(s"Executor blacklisted: ${executorBlacklisted.executorId}")
  }

  override def onExecutorUnblacklisted(executorUnblacklisted: SparkListenerExecutorUnblacklisted): Unit = {
    println(s"Executor unblacklisted: ${executorUnblacklisted.executorId}")
  }

  // Block Manager Events
  override def onBlockManagerAdded(blockManagerAdded: SparkListenerBlockManagerAdded): Unit = {
    println(s"Block Manager added: ${blockManagerAdded.blockManagerId}")
  }

  override def onBlockManagerRemoved(blockManagerRemoved: SparkListenerBlockManagerRemoved): Unit = {
    println(s"Block Manager removed: ${blockManagerRemoved.blockManagerId}")
  }

  // Environment and Other Events
  override def onEnvironmentUpdate(environmentUpdate: SparkListenerEnvironmentUpdate): Unit = {
    println(s"Environment updated: ${environmentUpdate.environmentDetails}")
  }

  override def onApplicationStart(applicationStart: SparkListenerApplicationStart): Unit = {
    println(s"Application started: ${applicationStart.appName}, ID: ${applicationStart.appId.getOrElse("N/A")}")
  }

  override def onApplicationEnd(applicationEnd: SparkListenerApplicationEnd): Unit = {
    println(s"Application ended at time: ${applicationEnd.time}")
  }

  override def onOtherEvent(event: SparkListenerEvent): Unit = {
    println(s"Other event: $event")
  }

  override def onUnpersistRDD(unpersistRDD: SparkListenerUnpersistRDD): Unit = {
    println(s"RDD Unpersisted with ID: ${unpersistRDD.rddId}")
  }
}

