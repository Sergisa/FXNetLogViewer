package sample;

public class FileParserFactory {
    public static AbstractFileParser produce(String filepath) {
        if (filepath.toLowerCase().endsWith(".pcapng")) {
            //return new PCAPFileParser(filepath);
        } else if (filepath.toLowerCase().endsWith(".txt") || filepath.toLowerCase().endsWith(".log")) {
            return new TXTFileParser(filepath);
        }
        return null;
    }
}
