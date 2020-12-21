package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ListView<Packet> packetList;
    public BorderPane loaderView;
    public ImageView stateIcon;
    public Label stateText;
    ObservableList<Packet> persons;

    public Controller() {

    }

    public void click(ActionEvent actionEvent) {
        System.out.println("oiwjgoijweg");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //packetList = new ListView<>();
        System.out.println("Startted");
        persons = FXCollections.observableArrayList();
        AbstractFileParser parser = FileParserFactory.produce("src/sample/2-iptraf.log");

        parser.setFileParsedListener(new OnFileParsedListener() {
            @Override
            public void parsed(List<Packet> packets) {
                stateIcon.setImage(new Image("sample/checkbox.png"));
                stateText.setText("Загружено");
                //loaderView.setVisible(false);
            }
        });
        parser.setPacketParsedListener(new OnPacketParsedListener() {
            @Override
            public void parsed(Packet packet) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        persons.add(packet);
                    }
                });

            }
        });
        parser.start();


        packetList.setCellFactory(param -> PacketListViewCell.newInstance());
        packetList.setItems(persons);
        MultipleSelectionModel<Packet> langsSelectionModel = packetList.getSelectionModel();
        // устанавливаем слушатель для отслеживания изменений
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<Packet>() {
            @Override
            public void changed(ObservableValue<? extends Packet> observable, Packet oldValue, Packet newValue) {
                System.out.println(newValue);
            }
        });
    }
}
