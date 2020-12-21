package sample;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacketListViewCell extends ListCell<Packet> implements Initializable {
    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private ImageView fxIconGender;

    @FXML
    private GridPane gridPane;

    private Packet currentPacket;

    private static final Logger LOG = Logger.getLogger(PacketListViewCell.class.getName());


    @Override
    protected void updateItem(Packet packet, boolean empty) {
        super.updateItem(packet, empty);
        gridPane.getChildrenUnmodifiable().forEach(c -> c.setVisible(!empty));
        if (!empty && packet != null && !packet.equals(this.currentPacket)) {
            label1.setText(packet.getSource());
            label2.setText(packet.getDestination());
        }
        this.currentPacket = packet;
    }
    public static PacketListViewCell newInstance() {
        FXMLLoader loader = new FXMLLoader(PacketListViewCell.class.getResource("PacketListView.fxml"));
        try {
            loader.load();
            return loader.getController();
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setGraphic(gridPane);
    }
}
