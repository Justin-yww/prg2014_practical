package addressapp.view

import addressapp.model.Person
import addressapp.MainApp
import javafx.scene.control.{Label, TableColumn, TextField}
import scalafx.scene.control.Alert
import scalafx.stage.Stage
import scalafx.Includes.*
import addressapp.util.DateUtil.*
import javafx.fxml.FXML
import javafx.event.ActionEvent

@FXML
class PersonEditDialogController():
  @FXML private var firstNameField: TextField = null
  @FXML private var lastNameField: TextField = null
  @FXML private var streetField: TextField = null
  @FXML private var postalCodeField: TextField = null
  @FXML private var cityField: TextField = null
  @FXML private var birthdayField: TextField = null

  var dialogStage: Stage = null
  private var _person: Person = null
  var okClicked = false

  def person = _person
  def person_= (x:Person): Unit =
    _person = x
    firstNameField.text = _person.firstName.value
    lastNameField.text = _person.lastName.value
    streetField.text = _person.street.value
    postalCodeField.text = _person.postalCode.value.toString
    cityField.text = _person.city.value
    birthdayField.text = _person.date.value.asString
    birthdayField.setPromptText("dd.mm.yyyy")

  def handleOk(action: ActionEvent): Unit =
    if (isInputValid()) then
      _person.firstName  <== firstNameField.text
      _person.lastName   <== lastNameField.text
      _person.street     <== streetField.text
      _person.city       <== cityField.text
      _person.postalCode.value = postalCodeField.getText().toInt
      _person.date.value =
        birthdayField.text.value.parseLocalDate
      okClicked = true
      dialogStage.close()
    end if

  def handleCancel(action: ActionEvent): Unit =
    dialogStage.close()

  def nullChecking (x:String) = x == null || x.length == 0

  def isInputValid(): Boolean =
    var errorMessage = ""

    if (nullChecking(firstNameField.text.value)) then
      errorMessage += "No valid first name!\n"
    if (nullChecking(lastNameField.text.value)) then
      errorMessage += "No valid last name!\n"
    if (nullChecking(streetField.text.value)) then
      errorMessage += "No valid street!\n"
    if (nullChecking(postalCodeField.text.value)) then
      errorMessage += "No valid postal code!\n"
    else
      try
        postalCodeField.getText().toInt
      catch
        case e: NumberFormatException =>
          errorMessage +=
            "No valid postal code (must be an integer)!\n"

      if (nullChecking(cityField.text.value)) then
        errorMessage += "No valid city!\n"
    if (nullChecking(birthdayField.text.value)) then
      errorMessage += "No valid birthday!\n"
    else
      if (!birthdayField.text.value.isValid) then
        errorMessage +=
          "No valid birthday. Use the format dd.mm.yyyy!\n";

    if (errorMessage.length() == 0) then
      true // return true
    else
      // Show error message
      val alert = new Alert(Alert.AlertType.Error):
        initOwner(dialogStage)
        title = "Invalid Fields"
        headerText = "Please correct invalid fields."
        contentText = errorMessage
      alert.showAndWait()
      false // return false
  end isInputValid
