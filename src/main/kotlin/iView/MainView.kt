package iView

import Util.Const.CRUD
import Util.Const.LifeCycle
import Util.Const.Result
import io.reactivex.Observable
import java.io.Serializable


interface MainView {
    interface Presenters {
        val dataBus: Observable<List<Serializable>>
        val infoBus: Observable<Pair<CRUD, Result>>
    }

    interface View {
        val eventBus: Observable<Triple<CRUD, String, List<String>>>
        val lifeCycleBus: Observable<LifeCycle>
    }
}