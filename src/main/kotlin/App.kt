import Data.Person
import Presenters.PresentersMainView
import Util.Const.CRUD
import Util.Const.LifeCycle
import iView.MainView
import io.reactivex.subjects.PublishSubject
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.text.Text
import javafx.stage.Stage
import java.lang.StringBuilder

class App : Application(), MainView.View {
    override val eventBus = PublishSubject.create<Triple<CRUD, String, List<String>>>()
    override val lifeCycleBus = PublishSubject.create<LifeCycle>()

    private val presenters: MainView.Presenters = PresentersMainView(this)
    private val infoBus = presenters.infoBus
            .publish()
            .autoConnect()
            .subscribe {
                text_result.text = "${it.first.name} - ${it.second.name}"
            }
    private val dataBus = presenters.dataBus
            .publish()
            .autoConnect()
            .subscribe {
                val sb = StringBuilder()
                it.map {
                    it as Person
                }.forEach { sb.append("id: ${it.getId()} name: ${it.name}\n") }
                text_data.text = "amount - ${it.size}\n$sb"
            }

    private val textField: TextField = TextField()
    private val text_result: Text = Text()
    private val text_data: Text = Text()

    private val btn_addPerson: Button = Button("Add person")
    private val btn_delPerson: Button = Button("Del person")
    private val btn_changePersonName: Button = Button("Change person name")
    private val btn_clear: Button = Button("Clear")

    private val root = VBox()
    private val hbox = HBox()

    override fun init() {
        lifeCycleBus.onNext(LifeCycle.INIT)
    }

    override fun start(stage: Stage) {
        btn_addPerson.setOnAction { addEvent(CRUD.POST) }
        btn_delPerson.setOnAction { addEvent(CRUD.DELETE) }
        btn_changePersonName.setOnAction { addEvent(CRUD.PUT) }
        btn_clear.setOnAction { clear() }
        hbox.children.addAll(btn_addPerson, btn_delPerson, btn_changePersonName, btn_clear)
        root.children.addAll(textField, hbox, text_result, text_data)
        stage.title = "Demo"
        stage.scene = Scene(root, 800.0, 600.0)
        stage.show()

        lifeCycleBus.onNext(LifeCycle.START)
    }

    override fun stop() {
        lifeCycleBus.onNext(LifeCycle.STOP)
        infoBus.dispose()
        dataBus.dispose()
    }

    fun addEvent(crud: CRUD){
        eventBus.onNext(Triple(crud, textField.text.trim().split(" ").first(), textField.text.trim().split(" ").drop(1)))
        clear()
    }

    fun clear(){
        textField.text = ""
    }
}
