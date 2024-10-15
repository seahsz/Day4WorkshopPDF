package fc;

import java.io.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class Cookie {

    private final String filePath;
    private final List<String> cookies = new ArrayList<>();

    public Cookie (String filePath) {
        this.filePath = filePath;
    }

    public void openFile() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            cookies.add(line);
        }

        br.close();

    }

    public String returnCookie() {

        Random rand = new Random();

        int index = rand.nextInt(cookies.size());
        return "cookie-text %s".formatted(cookies.get(index));
    }
    
}
