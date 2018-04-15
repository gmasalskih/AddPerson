package Views.CreateUser

import Util.Const.Result
import io.reactivex.Observer

interface ICreateUser {
    interface Presenter {
        val UIEvent: Observer<Pair<Result, String?>>
        fun createUser(fields: Map<String, String>)
    }

    interface Controller {
        val presenter: ICreateUser.Presenter
    }
}