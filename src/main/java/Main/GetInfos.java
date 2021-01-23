package Main;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.model.OpenDocument;
import org.jopendocument.model.text.TextA;
import org.json.JSONObject;
import sun.applet.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetInfos {


    public static void main(String[] args) {
        File file = new File("D:/a.ods");

        final Sheet sheet;
        final TextA t;
        final OpenDocument doc;

        try {
            sheet = SpreadSheet.createFromFile(file).getSheet(0);
            sheet.setValueAt("nome", 0, 0);
            for (int i = 0; i < 3; i++) {
                sheet.insertDuplicatedRows(sheet.getRowCount() - 1, 1);//duplicar a ultima linha para inserir
            }


            String[] vetor = new String[4];
            String[] str = new String[4];
            vetor[0] = "ITSA4";
            vetor[1] = "HYPE3";
            vetor[2] = "EGIE3";
            vetor[3] = "MFII11";
            file.createNewFile();
            for (int i = 0; i < vetor.length; i++) {
                URL url = new URL("https://query1.finance.yahoo.com/v8/finance/chart/" + vetor[i] + ".SA?region=US&lang=pt-BR&includePrePost=false&interval=2m&range=1d&corsDomain=finance.yahoo.com&.tsrc=finance");
                System.out.println("https://query1.finance.yahoo.com/v8/finance/chart/" + vetor[i] + ".SA?region=US&lang=pt-BR&includePrePost=false&interval=2m&range=1d&corsDomain=finance.yahoo.com&.tsrc=finance");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();

                String jsonString = inputStreamToString(inputStream);
                JSONObject jsonObject = new JSONObject(jsonString);


                str[i] = (vetor[i] + " preço atual da ação--> " + jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("meta").getDouble("regularMarketPrice"));
                System.out.println("menor de hoje :   " + vetor[i] + " preço atual da ação--> " + jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("meta").getDouble("previousClose"));
                JSONObject hiugh = new JSONObject(jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("meta"));
                JSONObject w = new JSONObject(jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("close").get(1));
                System.out.println(hiugh);
                System.out.println(w);
                System.out.println(jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("close").getNumber(0));
                AtomicReference<Double> m = new AtomicReference<>(0.0);

                jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("close").forEach((max) -> {
                    if (!max.equals(null)) {
                        System.out.println(max);
                        System.out.println();

                        System.out.println((Double) max);
                        if ((Double) max >= m.get()) {
                            m.set((Double) max);
                        }
                    }
                });
                System.out.println(m.get());

                System.out.println(m.toString());
                // System.out.println("maior de hoje :   " + vetor[i] + " preço atual da ação--> ");
                // System.out.println("dia anterior :   " + vetor[i] + " preço atual da ação--> " + jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getDouble("previousClose"));

            }
//            sheet.getCellAt("A2").setValue(str[0]);//usado somente quando ha valores nas celulas
//            sheet.getCellAt("A3").setValue(new Date());
//            sheet.getCellAt("A4").setValue(str[1]);
//            File outputFile = new File("D:\\","teste2.ods");

//            try {
//                OOUtils.open(sheet.getSpreadSheet().saveAs(outputFile));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

}
