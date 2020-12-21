package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TXTFileParser extends AbstractFileParser implements Parser {
    private final File file;
    private final int TYPE_COLUMN_INDEX = 1;
    private final int BYTES_COLUMN_INDEX = 3;
    private final int SOURCE_DEST_COLUMN_INDEX = 4;
    private final String COLUMN_SPLITTER = "; ";

    public TXTFileParser(String filepath) {
        this(new File(filepath));
    }

    public TXTFileParser(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        //super.run();
        getPackets();
    }

    public List<String> read() {
        List<String> result = new ArrayList<String>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(this.file));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (bufferedReader.ready()) {
                result.add(bufferedReader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public synchronized List<Packet> getPackets() {
        List<Packet> packets = new ArrayList<>();
        Pattern destSourcePattern = Pattern.compile("from ([^;]*) to ([^;]*)");

        this.read().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {

                if (!s.contains("IP traffic monitor started")) {
                    Matcher m = destSourcePattern.matcher(s.split(COLUMN_SPLITTER)[SOURCE_DEST_COLUMN_INDEX]);
                    if (m.find()) {
                        Packet p = new Packet(
                                Integer.parseInt(s.split(COLUMN_SPLITTER)[BYTES_COLUMN_INDEX].replace(" bytes", "")),
                                s.split(COLUMN_SPLITTER)[TYPE_COLUMN_INDEX],
                                m.group(1),
                                m.group(2)
                        );
                        packetParsedListener.parsed(p);
                        packets.add(p);
                    }
                }
                //TODO: наполнение списка объектов Packet
            }
        });
        this.fileParsedListener.parsed(packets);
        return packets;
    }
}
