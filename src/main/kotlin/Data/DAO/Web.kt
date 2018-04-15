package Data.DAO

import Data.Entity.Feedback
import Data.Entity.User
import Util.Const.CRUD
import Util.Const.Result
import java.io.Serializable

object Web : DAO {
    override fun createUser(user: User): Feedback {
        return object : Feedback{
            override val result = Result.OK
            override var response: Serializable? = ""
        }
    }
}