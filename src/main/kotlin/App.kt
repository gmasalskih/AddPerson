import Util.Const.CREATE_USER_FXML
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class App:Application() {

    override fun start(stage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource(CREATE_USER_FXML))
        stage.title = "Mirapolis"
        stage.scene = Scene(root, 800.0, 600.0)
        stage.show()
    }
}