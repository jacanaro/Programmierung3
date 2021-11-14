package GUI;

import domainLogic.automat.Automat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MVVM extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parameters parameters = getParameters();
        System.out.println("Kommandozeilenparameter: "+parameters.getUnnamed());

        FXMLLoader loader= new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        ViewModel controller = loader.getController();
        if(parameters.getUnnamed().size()==2){
            controller.setAutomat(new Automat(Integer.parseInt(parameters.getUnnamed().get(0))));
            controller.setLogger(parameters.getUnnamed().get(1));
        }else if(parameters.getUnnamed().size()==1){
            controller.setAutomat(new Automat(Integer.parseInt(parameters.getUnnamed().get(0))));
        }else{
            System.out.println("Kommandozeilenparameter: Automat-Capacity angeben!");
            System.exit(1);
        }

        primaryStage.setTitle("mvvm.MVVM");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {launch(args);}
}
