package kandroid.z

import kandroid.config.Config
import kandroid.observer.ApiLoader
import kandroid.proxy.MyProxyServer
import kandroid.utils.IDDictionary
import kandroid.utils.Identifiable
import kandroid.utils.Utils
import java.io.File
import kotlin.io.readText

data class KTest(val i: Int) : Identifiable {

    companion object {
        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>) {
            println(Utils.isAssertOpen())

            val file : File = File("C:\\Users\\LPJ\\Documents\\GitHub\\Kandroid\\KCAPI\\20161001_232644777S@api_port@port.json")
            val readText = file.readText()
            println(readText)

        }

        fun startServer() {
            Config.config = Config()
            ApiLoader.start()
            MyProxyServer.start()
        }
    }

    var v = IDDictionary<KTest>()
    var ses: IDDictionary<KTest>
        get() = v
        set(va) {
            v = va
        }

    override val id: Int
        get() = (100 * Math.random()).toInt()
}
