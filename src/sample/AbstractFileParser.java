package sample;

public abstract class AbstractFileParser extends Thread implements Parser {
    OnFileParsedListener fileParsedListener;
    OnPacketParsedListener packetParsedListener;


    public void setPacketParsedListener(OnPacketParsedListener packetParsedListener) {
        this.packetParsedListener = packetParsedListener;
    }

    public void setFileParsedListener(OnFileParsedListener fileParsedListener) {
        this.fileParsedListener = fileParsedListener;
    }
}
