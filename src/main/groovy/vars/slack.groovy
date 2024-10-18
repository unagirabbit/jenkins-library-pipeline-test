package main.groovy.vars

import groovy.json.JsonOutput

/**
* Slack通知を行う
* @param params values
* credentialsId: SlackトークンID
* postChannel: 通知チャンネル(#はあってもなくても良い)
* text: メッセージ
+ color: 16進カラーコードを指定,プリセット(good, warning, danger)有り
* attachments: 配列,装飾を指定
*/
String postSlackMessage(Map params) {
    if (!params.credentialsId || !params.postChannel || !params.text) {
        println 'postSlackMessage error: param invalid.'
        return ''
    }
    Map body = [
        'channel': params.postChannel,
        'text': params.text,
        'thread_ts': params.thread_ts ?: '',
        'attachments': params.attachments ?: []
    ]
    if (params.color) {
        body['attachments'].add(['color': params.color , 'text': params.text])
        // 色指定時は重複しないよう空にする
        body['text'] = ''
    }
    String jsonString = JsonOutput.toJson(body)
    String response = ''
    withCredentials([string(credentialsId: params.credentialsId, variable: 'token')]) {
        withEnv(["_jsonString=${jsonString}"]) {
            response = sh script: '''curl -X POST https://slack.com/api/chat.postMessage \
            -s \
            -H "Authorization: Bearer $token" \
            -H "Content-Type: application/json; charset=utf-8" \
            -H "User-Agent: jenkins" \
            -d "$_jsonString"''', returnStdout: true
        }
    }
    if (!response || (response && response.contains('\"error\"'))) {
        println "postSlackMessage error: ${response}"
    }
    return response?.trim()
}

return this
