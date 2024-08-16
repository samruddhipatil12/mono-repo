package patient_add;

import java.net.URL;

import common.StageFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class PatientAddScreen {
  public static void showPatientAddScreen() throws Exception {
    try {

      Parent actorGroup =
          FXMLLoader.load(
              new URL(
                  "file:///C:\\\\Users\\\\hemag\\\\eclipse-workspace\\\\/hospital-console/src/main/java/patient_add/AddPatient.fxml"));
      StageFactory.stage.setTitle("Customer Login");
      Scene scene = new Scene(actorGroup, 1000, 400);
      StageFactory.stage.setScene(scene);
      StageFactory.stage.setFullScreen(true);
      StageFactory.stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
