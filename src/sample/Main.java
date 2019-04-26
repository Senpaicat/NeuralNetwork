package sample;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;


public class Main extends Application {
    ImageView imageView;
    TextArea textArea;
    int[][] input = new int[3][5];
    Web NW1 = new Web(3, 5, input);
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Neural network");
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout, 500, 500, Color.rgb(0,0,0,0));

        imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);


        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream("weights"), StandardCharsets.UTF_8));

        String line;
        String sl[];
        int k=0;
        while ((line = reader.readLine()) != null) {
            sl = line.split(" ");

            for(int i=0;i<sl.length;i++){
                if(k<5){
                    NW1.weight[i][k] = Integer.parseInt(sl[i]);
                    System.out.print(NW1.weight[i][k]+" ");
                }
            }
            System.out.println();
            k++;
        }

        Button openImage = new Button("Open image");
        openImage.setOnAction(openImageListener);

        Button update = new Button("False");
        update.setOnAction(e ->{
            NW1.mul_w();

            NW1.Sum();

            if (NW1.Rez())
                textArea.appendText("True, Sum = " + NW1.sum);
            else
                textArea.appendText("False, Sum = " + NW1.sum);
            String s="";
            String[] s1 = new String[5];
            File file = new File("weights");
            file.delete();
            try {
                PrintWriter writer = new PrintWriter("weights", "UTF-8");

                for (int y = 0; y <= 4; y++)
                {

                    s = String.valueOf(NW1.weight[0][y]) + " " + String.valueOf(NW1.weight[1][y]) + " " + String.valueOf(NW1.weight[2][y]);

                    s1[y] = s;

                    writer.println(s);

                    textArea.clear();
                }
                writer.close();
            }catch (Exception ex){
                System.err.println(ex);
            }

        });

        textArea = new TextArea();


        HBox hbox = new HBox(5);
        hbox.getChildren().addAll(imageView, openImage, update);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15,0,0,10));
        vbox.getChildren().addAll(hbox,textArea);

        layout.setCenter(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
        reader.close();
    }


    public static void main(String[] args) {
        launch(args);
    }

    EventHandler<ActionEvent> openImageListener
            = new EventHandler<ActionEvent>(){

        @Override
        public void handle(ActionEvent t) {
            FileChooser fileChooser = new FileChooser();

            File file = fileChooser.showOpenDialog(null);

            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);


                for(int x=0;x<5;x++){
                    for(int y=0;y<3;y++){
                        int pixel = bufferedImage.getRGB(y, x);
                        if(pixel==-16777216)
                            pixel=1;
                        else
                            pixel=0;
                        textArea.appendText(pixel + " ");
                        input[y][x] = pixel;
                    }
                    textArea.appendText("\n");
                }

            } catch (IOException ex) {
                System.err.println(ex);
            }
            recognize();
        }
    };



    public void recognize()
    {
        NW1.mul_w();

        NW1.Sum();

        if (NW1.Rez()) {
            System.out.print("True, Sum = " + NW1.sum);
            textArea.appendText("True, Sum = " + NW1.sum);
        }
        else {
            System.out.println("False, Sum = " + NW1.sum);
            textArea.appendText("False, Sum = " + NW1.sum);
        }
    }
}
