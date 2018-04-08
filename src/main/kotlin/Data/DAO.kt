package Data

class DAO : IDAO {
    override fun addPerson(name: String): Boolean {
        if(name.trim() == "") return false
        return DB.persons.add(Person(name))
    }

    override fun getPerson(name_or_id: String): List<Person?> {
        return if (checkID(name_or_id)) {
            DB.persons.filter {
                it.getId() == name_or_id.toInt()
            }
        } else {
            DB.persons.filter {
                it.name == name_or_id
            }
        }
    }

    override fun getAllPersons(): List<Person> {
        return DB.persons
    }

    override fun delPerson(name_or_id: String): Boolean {
        return if (checkID(name_or_id)) {
            DB.persons.removeIf { it.getId() == name_or_id.toInt() }
        } else {
            DB.persons.removeIf { it.name == name_or_id }
        }
    }

    override fun delAllPerson(): Boolean {
        DB.persons.clear()
        return true
    }

    override fun changePersonName(oldName_or_id: String, newName: String): Boolean {
        return if (checkID(oldName_or_id)) {
            DB.persons.filter { it.getId() == oldName_or_id.toInt() }.forEach { it.name = newName }
            DB.persons.filter { it.getId() == oldName_or_id.toInt() && it.name == newName }.count() > 0
        } else {
            DB.persons.filter { it.name == oldName_or_id }.forEach { it.name = newName }
            DB.persons.filter { it.name == newName }.count() > 0
        }
    }

    override fun getAmountPersons() = DB.persons.size

    private fun checkID(str: String) = str.trim().matches(Regex("^[1-9]+$"))
}