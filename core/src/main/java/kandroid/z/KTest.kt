package kandroid.z

import kandroid.config.Config
import kandroid.newdata.JsonWrapper
import kandroid.observer.ApiLoader
import kandroid.proxy.MyProxyServer
import kandroid.utils.Identifiable
import kandroid.utils.Utils

@Throws(Exception::class)
fun main(args: Array<String>) {
    println(Utils.isAssertOpen())

}

fun startServer() {
    Config.config = Config()
    ApiLoader.start()
    MyProxyServer.start()
}

class KTest() : JsonWrapper(), Identifiable {


//    val file: File = File("C:\\Users\\LPJ\\Documents\\GitHub\\Kandroid\\KCAPI\\20161001_232644777S@api_port@port.json")

    override val id: Int
        get() = (100 * Math.random()).toInt()


    var test2: String = ""
        set(v) {
            field = v
        }

    val test3: String get() = test2
}
