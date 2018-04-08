package Presenters

import Util.Const.CRUD
import Data.DAO
import Data.IDAO
import Util.Const.LifeCycle
import Util.Const.Result
import iView.MainView
import io.reactivex.subjects.PublishSubject
import java.io.Serializable

class PresentersMainView(view: MainView.View) : MainView.Presenters {

    override val infoBus = PublishSubject.create<Pair<CRUD, Result>>()
    override val dataBus = PublishSubject.create<List<Serializable>>()

    private val eventBus = view.eventBus.publish().autoConnect()
    private val lifeCycleBus = view.lifeCycleBus
            .publish()
            .autoConnect()
            .subscribe {
                when(it){
                    LifeCycle.INIT ->{ }
                    LifeCycle.START ->{ }
                    LifeCycle.STOP ->{ stopView() }
                }
            }

    private val dao: IDAO = DAO()
    private val create = eventBus.filter { it.first == CRUD.POST }
            .subscribe {
                infoBus(CRUD.POST, dao.addPerson(it.second.trim()))
            }
    private val update = eventBus.filter { it.first == CRUD.PUT }
            .subscribe {
                if (it.second.trim() == "" || it.third[0].trim() == ""){
                    infoBus(CRUD.PUT, false)
                    return@subscribe
                }
                infoBus(CRUD.PUT, dao.changePersonName(it.second, it.third[0]))

            }
    private val delete = eventBus.filter { it.first == CRUD.DELETE }
            .subscribe {
                if(it.second.trim() == "") infoBus(CRUD.DELETE, dao.delAllPerson())
                else infoBus(CRUD.DELETE, dao.delPerson(it.second.trim()))
            }

    private fun stopView() {
        create.dispose()
        update.dispose()
        delete.dispose()
        lifeCycleBus.dispose()
    }

    private fun infoBus(crud: CRUD, res: Boolean) {
        infoBus.onNext(Pair(crud, if (res) Result.OK else Result.ERR))
        if(res) dataBus.onNext(dao.getAllPersons())
    }
}