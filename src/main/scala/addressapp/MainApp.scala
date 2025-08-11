package addressapp

import javafx.fxml.FXMLLoader
import javafx.{fxml, scene as jfxs}
import scalafx.stage.{Stage, Modality}
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.collections.ObservableBuffer
import scalafx.scene as sfxs
import scalafx.scene.Scene

import model.Person
import addressapp.view.PersonEditDialogController
import scalafx.scene.image.Image
import addressapp.util.Database

object MainApp extends JFXApp3:
  Database.setupDB()
  // Window Root Plane
  var roots: Option[sfxs.layout.BorderPane] = None
  // StyleSheet
  var cssResource = getClass.getResource("view/DarkTheme.css")

  override def start(): Unit =
    // Transform path of RootLayout.fxml to URL for resource location
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    // Initialise the loader object
    val loader = new FXMLLoader(rootResource)
    // Load root layout from FXML file
    loader.load()
  
    // Retrieve the root component BorderPane from the FXML
    // Refer to slides on scala option monad
    roots = Option(loader.getRoot[jfxs.layout.BorderPane])
  
    stage = new PrimaryStage():
      title = "AddressApp"
      icons += new Image(getClass.getResource("/images/book.png").toExternalForm)
      scene = new Scene():
        stylesheets = Seq(cssResource.toExternalForm)
        root = roots.get
  
    // Call to display PersonOverview when app Starts
    showPersonOverview()

  // Actions for displaying PersonaOverview window
  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.get.center = roots

  // From "AddressApp Part 3
  def showPersonEditDialogue(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots2 = loader.getRoot[jfxs.Parent]
    val control = loader.getController[PersonEditDialogController]
    val dialog = new Stage():
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene:
        stylesheets = Seq(cssResource.toExternalForm)
        root = roots2
    control.dialogStage = dialog
    control.person      = person
    dialog.showAndWait()
    control.okClicked // return true if OK
  end showPersonEditDialogue


  // The Data as an observable list of Persons. 
  val personData = new ObservableBuffer[Person]()
  
  // Assign all Persons into personData Array 
  personData ++= Person.getAllPersons
  
  