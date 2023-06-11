package klotski.klotski;

import java.io.*;
import java.util.Vector;

public class SaverLoader {
    private final String path = "save.txt";

    public SaverLoader(){}

    public String save(int conf, Vector<String> History) {
        try {
            File file = new File(path);

            if(!file.exists())
                if (!file.createNewFile())
                    return "error";

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(conf + "\n");
            for(String str : History)
                bw.write(str + "\n");
            bw.flush();
            bw.close();

        } catch (IOException e) {
            return "error";
        }

        return "ok";
    }

    public Vector<String> load(){
        Vector<String> out = new Vector<>();
        try {
            File file = new File(path);

            if(!file.exists())
                return null;

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            while(line != null) {
                out.add(line);
                line = br.readLine();
            }

            return out;

        } catch(Exception e) {
            return null;
        }
    }
}
