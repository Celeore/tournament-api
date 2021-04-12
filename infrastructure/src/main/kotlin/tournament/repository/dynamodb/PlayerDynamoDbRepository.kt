package tournament.repository.dynamodb

import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.*
import tournament.entities.Player
import java.net.URI
import kotlin.system.exitProcess

class PlayerDynamoDbRepository {
    val awsEndpointUrl = "http://localstack:4566"
    private val dynamoDbClient: DynamoDbClient = DynamoDbClient.builder()
        .region(Region.EU_WEST_3)
        .endpointOverride(URI.create(awsEndpointUrl))
        .build()
    private val tableName = "Player"

    fun save(player: Player): Player {
        val itemValues = mutableMapOf(
            Pair("pseudo", createAttributeValueString(player.pseudo)),
            Pair("points", createAttributeValueNumber(player.points))
        )
        try {
            dynamoDbClient.putItem {
                it.tableName(tableName)
                it.item(itemValues)
            }
            println("$tableName was successfully updated")
            return Player(player.pseudo, player.points)
        } catch (e: ResourceNotFoundException) {
            System.err.format("Error: The Amazon DynamoDB table \"%s\" can't be found.\n", tableName)
            System.err.println("Be sure that it exists and that you've typed its name correctly!")
            exitProcess(1)
        } catch (e: DynamoDbException) {
            System.err.println(e.message)
            exitProcess(1)
        }
    }

    fun updateTableItem(pseudo: String, points: Int): Boolean {
        val itemKey = mutableMapOf(Pair("pseudo", createAttributeValueString(pseudo)))

        val updatedValues = mutableMapOf(
            Pair(
                "points", AttributeValueUpdate.builder()
                    .value(createAttributeValueNumber(points))
                    .action(AttributeAction.PUT)
                    .build()
            )
        )
        try {
            dynamoDbClient.updateItem {
                it.tableName(tableName)
                it.key(itemKey)
                it.attributeUpdates(updatedValues)
            }.also {
                return true
            }

        } catch (e: ResourceNotFoundException) {
            System.err.println(e.message)
        } catch (e: DynamoDbException) {
            System.err.println(e.message)
        }
        return false
    }

    fun readPlayers(): List<Player> {
        val result = dynamoDbClient.scan {
            it.tableName(tableName)
        }
        return result.items().map {
            Player(it["pseudo"]!!.s(), it["points"]!!.n().toInt())
        }
    }

    private fun createAttributeValueString(value: String): AttributeValue = AttributeValue.builder().s(value).build()
    private fun createAttributeValueNumber(value: Int): AttributeValue =
        AttributeValue.builder().n(value.toString()).build()

}
