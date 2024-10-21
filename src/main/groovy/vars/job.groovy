package main.groovy.vars

import hudson.model.Item
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowRun
// pipeline-graph-analysis-plugin
import org.jenkinsci.plugins.workflow.pipelinegraphanalysis.StageChunkFinder
// Pipeline Stage View Plugin
import com.cloudbees.workflow.rest.external.StageNodeExt
import com.cloudbees.workflow.rest.external.ChunkVisitor

/**
* ジョブの説明を設定します
* @param jobName ジョブ名
* @param description 説明
*/
static void setJobDescription(String jobName, String description) {
    Item item = Jenkins.instanceOrNull.getItemByFullName(jobName)
    if (item != null) {
        item.description = description
        item.save()
    }
}

 /**
 * 失敗したステージ名を取得する
 * [Stage View Plugin]が必要
 * https://plugins.jenkins.io/pipeline-stage-view/
 */
String getFailedStageName() {
    String errorStageName = ''
    WorkflowRun workFlowRun = currentBuild.rawBuild
    ChunkVisitor visitor = new ChunkVisitor( workFlowRun )
    // stage 情報の取得
    ForkScanner.visitSimpleChunks( workFlowRun.execution.currentHeads, visitor, new StageChunkFinder() )
    // 全ての Stage のステータスを検索
    for ( StageNodeExt stageExt : visitor.stages ) {
        if ( stageExt.status == StatusExt.FAILED ) {
            errorStageName = stageExt.name
            break
        }
    }
    return errorStageName
}

return this
