package addressapp.view

import addressapp.model.Person
import addressapp.MainApp
import javafx.fxml.FXML
import javafx.scene.control.{Label, TableColumn, TableView}
import javafx.event.ActionEvent
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
// FROM AddressApp Part 3 
import scalafx.Includes.*
import scalafx.beans.binding.Bindings
import addressapp.util.DateUtil.asString
import scala.util.{ Success, Failure }


@FXML
class PersonOverviewController():

  @FXML
  private var personTable: TableView[Person]                = null
  @FXML
  private var firstNameColumn: TableColumn[Person, String]  = null
  @FXML
  private var lastNameColumn: TableColumn[Person, String]   = null
  @FXML
  private var firstNameLabel: Label                         = null
  @FXML
  private var lastNameLabel: Label                          = null
  @FXML
  private var streetLabel: Label                            = null
  @FXML
  private var postalCodeLabel: Label                        = null
  @FXML
  private var cityLabel: Label                              = null
  @FXML
  private var birthdayLabel: Label                          = null

  // Initialise the Table View display contents model 
  def initialize() =
    personTable.items = MainApp.personData
    // Initialise columns's cell values 
    firstNameColumn.cellValueFactory = {_.value.firstName}
    lastNameColumn.cellValueFactory = {_.value.lastName}

    showPersonDetails(None)

    personTable.selectionModel().selectedItem.onChange(
      (_, _, newValue) => showPersonDetails(Option(newValue))
    )
  end initialize

  // FROM AddressApp Part 3
  private def showPersonDetails(person: Option[Person]): Unit =
    person match
      case Some(person) =>
        // Fill the labels here with info from the Person Object
        firstNameLabel.text   <== person.firstName
        lastNameLabel.text    <== person.lastName
        streetLabel.text      <== person.street
        cityLabel.text        <== person.city
        postalCodeLabel.text  <== person.postalCode.delegate.asString()
        birthdayLabel.text    <== Bindings.createStringBinding(
          () => {person.date.value.asString}, person.date
        )

      case None =>
        // When Person is null, remove all the text
        firstNameLabel.text.unbind()
        lastNameLabel.text.unbind()
        streetLabel.text.unbind()
        cityLabel.text.unbind()
        postalCodeLabel.text.unbind()
        birthdayLabel.text.unbind()

        firstNameLabel.text = " "
        lastNameLabel.text = " "
        streetLabel.text = " "
        cityLabel.text = " "
        postalCodeLabel.text = " "
        birthdayLabel.text = " "
  end showPersonDetails

  def handleDeletePerson(action: ActionEvent) =
    val selectedIndex =
      personTable.selectionModel().selectedIndex.value
    val selectedPerson =
      personTable.selectionModel().selectedItem.value
    if (selectedIndex >= 0) then
      selectedPerson.delete() match
        case Success(x) =>
          personTable.items().remove(selectedIndex)
        case Failure(e) =>
          val alert = new Alert(Alert.AlertType.Warning):
            initOwner(MainApp.stage)
            title = "Failed to Delete"
            headerText = "Database Error"
            contentText = "Database error: Failed to delete"
          alert.showAndWait()
    else
      // Nothing selected
      val alert = new Alert(AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table"
      alert.showAndWait()
    end if
  end handleDeletePerson

  def handleNewPerson(actionEvent: ActionEvent) =
    val person = new Person("","")
    val okClicked = MainApp.showPersonEditDialogue(person)
    if (okClicked) then
      person.save() match
        case Success(x) =>
          MainApp.personData += person
        case Failure(e) =>
          val alert = new Alert(Alert.AlertType.Warning):
            initOwner(MainApp.stage)
            title = "Failed to save"
            headerText = "Database Error"
            contentText = "Database error: Failed to save changes"
          alert.showAndWait()
    end if
  end handleNewPerson


  def handleEditPerson(actionEvent: ActionEvent) =
    val selectedPerson =
      personTable.selectionModel().selectedItem.value
    if (selectedPerson != null) then
      val okClicked = MainApp.showPersonEditDialogue(selectedPerson)
      if (okClicked) then
        selectedPerson.save() match
          case Success(x) =>
            showPersonDetails(Some(selectedPerson))
          case Failure(e) =>
            val alert = new Alert(Alert.AlertType.Warning):
              initOwner(MainApp.stage)
              title = "Failed to Save"
              headerText = "Database Error"
              contentText = "Database error: Failed to save changes"
            alert.showAndWait()
      end if
    else
      // Nothing selected
      val alert = new Alert(AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table"
      alert.showAndWait()
    end if
  end handleEditPerson

end PersonOverviewController

    
    
    
