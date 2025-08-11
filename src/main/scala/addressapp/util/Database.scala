package addressapp.util
import scalikejdbc.*


trait Database: 
  val derbyDriverClassname = 
    "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:myDB;create=true;"
  
  // Initiatilise JDBC driver & connection pool 
  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL, "me", "mine")
  
  // Ad-hoc Session Provider on the REPL
  given AutoSession = AutoSession
    
  def setupDB() =
    if (!hasDBInitialize) then 
      import addressapp.model.Person
      Person.initializeTable()
  def hasDBInitialize: Boolean =
    DB getTable "Person" match
      case Some(x)  => true
      case None     => false

object Database extends Database: 
        