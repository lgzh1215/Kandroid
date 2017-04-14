import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection.TRANSACTION_SERIALIZABLE

fun main(args: Array<String>) {
    Database.connect("jdbc:sqlite:file:0.db", driver = "org.sqlite.JDBC") {
        ThreadLocalTransactionManager(it, TRANSACTION_SERIALIZABLE)
    }

    transaction {
        SchemaUtils.create(UserDataCache)

        println(UserDataCache.select { UserDataCache.id eq 2 }.none())
    }
}

object UserDataCache : Table() {
    val id = integer("member_id").primaryKey()
    val json = text("json")
}
