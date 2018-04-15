package Views.CreateUser

import io.reactivex.rxjavafx.schedulers.JavaFxScheduler
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.CheckBox
import javafx.scene.control.PasswordField
import javafx.scene.control.RadioButton
import javafx.scene.control.TextField
import javafx.scene.control.ToggleGroup
import javafx.scene.text.Text

class CreateUserController: ICreateUser.Controller {
    override val presenter = CreateUserPresenter
    @FXML private lateinit var plastname: TextField
    @FXML private lateinit var pfirstname: TextField
    @FXML private lateinit var psurname: TextField
    @FXML private lateinit var male: RadioButton
    @FXML private lateinit var sex: ToggleGroup
    @FXML private lateinit var female: RadioButton
    @FXML private lateinit var caidname: TextField
    @FXML private lateinit var rspostidname: TextField
    @FXML private lateinit var personemail: TextField
    @FXML private lateinit var isuser: CheckBox
    @FXML private lateinit var pilogin: TextField
    @FXML private lateinit var pipassword: PasswordField
    @FXML private lateinit var createBtn: Button
    @FXML private lateinit var cancelBtn: Button
    @FXML private lateinit var info: Text
    @FXML private lateinit var baseUrl: TextField
    @FXML private lateinit var secretKey: TextField

    @FXML
    fun initialize(){
        cancelBtn.setOnAction {
            clear()
        }

        createBtn.setOnAction {
            var map = HashMap<String, String>()
            map.put(plastname.id, plastname.text)
            map.put(pfirstname.id, pfirstname.text)
            map.put(psurname.id, psurname.text)
            map.put(personemail.id, personemail.text)


            presenter.createUser(map)
            clear()

        }

        presenter.UIEvent
                .observeOn(JavaFxScheduler.platform())
                .publish()
                .autoConnect()
                .subscribe {
                    info.text = "${it.first.name} - ${it.second}"
                }
    }

    private fun clear(){
        plastname.text = ""
        pfirstname.text = ""
        psurname.text = ""
        male.isSelected = true
        female.isSelected = false
        caidname.text = ""
        rspostidname.text = ""
        personemail.text = ""
        isuser.isSelected = true
        pilogin.text = ""
        pipassword.text = ""
        info.text = ""
    }

}