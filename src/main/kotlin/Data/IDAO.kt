package Data

import java.io.Serializable

interface IDAO {
    fun getPerson(name_or_id: String): List<Serializable?>
    fun getAllPersons(): List<Serializable>
    fun getAmountPersons(): Int
    fun addPerson(name: String): Boolean
    fun delPerson(name_or_id: String): Boolean
    fun delAllPerson(): Boolean
    fun changePersonName(oldName_or_id: String, newName: String): Boolean
}