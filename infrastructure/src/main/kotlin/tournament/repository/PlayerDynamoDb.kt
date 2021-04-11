package tournament.repository

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
class PlayerDynamoDb( internal val pseudo: String, var points: Int = 0
) {
}