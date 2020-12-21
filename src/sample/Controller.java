package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public ListView<Packet> packetList;
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
        persons.addAll(FileParserFactory.produce("src/sample/2-iptraf.log").getPackets());

        packetList.setCellFactory(param -> PacketListViewCell.newInstance());
        packetList.setItems(persons);
        MultipleSelectionModel<Packet> langsSelectionModel = packetList.getSelectionModel();
        // устанавливаем слушатель для отслеживания изменений
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<Packet>() {
            @Override
            public void changed(ObservableValue<? extends Packet> observable, Packet oldValue, Packet newValue) {
                System.out.println(oldValue);
                System.out.println(newValue);
            }
        });
    }
}
