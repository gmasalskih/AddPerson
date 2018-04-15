package Views.CreateUser

import Util.Const.Result
import Util.WebHelper.Api
import Util.WebHelper.RestApi
import io.reactivex.subjects.PublishSubject

object CreateUserPresenter : ICreateUser.Presenter {
    override val UIEvent = PublishSubject.create<Pair<Result, String?>>()
    override fun createUser(fields: Map<String, String>) {
        RestApi.webApi.createUser(fields).subscribe({
            UIEvent.onNext(Pair(Result.OK, "Создан пользователь с id - $it"))
            println(it)
            RestApi.webApi.delUser(it).subscribe {
                UIEvent.onNext(Pair(Result.OK, "Удален пользователь"))

                println(it)
            }
        },{
            UIEvent.onNext(Pair(Result.ERR, it.message))
        },{
            println("Done!")
        })
    }
}