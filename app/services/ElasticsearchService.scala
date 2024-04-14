package services

import javax.inject.Inject
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.indices.CreateIndexRequest
import org.elasticsearch.xcontent.XContentType

import scala.concurrent.{ExecutionContext, Future}

class ElasticsearchService @Inject()(client: RestHighLevelClient)(implicit ec: ExecutionContext) {

  def indexData(indexName: String, documentId: String, jsonData: String): Future[String] = Future {
    val request = new IndexRequest(indexName)
      .id(documentId)
      .source(jsonData, XContentType.JSON)

    val response = client.index(request)
    response.getId
  }

  def createIndexWithMappings(indexName: String): Future[Boolean] = Future {
    val mappings =
      """
        |{
        |  "mappings": {
        |    "properties": {
        |      "title": { "type": "text" },
        |      "timestamp": { "type": "date" },
        |      "content": { "type": "text" }
        |    }
        |  }
        |}
        |""".stripMargin

    val request = new CreateIndexRequest(indexName)
      .mapping("_doc", mappings, XContentType.JSON)

    val response = client.indices.create(request)
    response.isAcknowledged
  }
}
