package Data

import java.io.Serializable

data class Person(var name: String) : Serializable{
    companion object {
        private var _id = 0
    }
    private var id:Int = 0
    init {
        id=_id++
    }
    fun getId() = id
}
