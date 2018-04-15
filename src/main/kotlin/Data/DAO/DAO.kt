package Data.DAO

import Data.Entity.Feedback
import Data.Entity.User

interface DAO {
    fun createUser(user: User): Feedback
}