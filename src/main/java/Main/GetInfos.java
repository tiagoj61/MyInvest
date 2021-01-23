package Main;

import bean.Ativo;
import bean.DayInfos;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.model.OpenDocument;
import org.jopendocument.model.text.TextA;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.applet.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetInfos {
    private double maximaDoDia;
    private double minimaDoDia;


    public static void main(String[] args) {
        File file = new File("D:/a.ods");

        List<Ativo> ativos = new ArrayList<>();
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
                Ativo ativo = new Ativo();
                DayInfos dayInfos = new DayInfos();
                dayInfos.setDate(new Date());
                ativo.setName(vetor[i]);
                URL url = new URL("https://query1.finance.yahoo.com/v8/finance/chart/" + vetor[i] + ".SA?region=US&lang=pt-BR&includePrePost=false&interval=2m&range=1d&corsDomain=finance.yahoo.com&.tsrc=finance");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                String jsonString = inputStreamToString(inputStream);
                JSONObject jsonObject = new JSONObject(jsonString);

                AtomicReference<Double> m = new AtomicReference<>(0.0);
                double fechamento = jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("meta").getDouble("regularMarketPrice");
                dayInfos.setFechamento(fechamento);
                JSONArray high = new JSONArray(jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("high"));
                high.forEach((max) -> {
                    if (!max.equals(null)) {
                        System.out.println(max);
                        System.out.println();

                        System.out.println((Double) max);
                        if ((Double) max >= m.get()) {
                            m.set((Double) max);
                        }
                    }
                });
                dayInfos.setMax(m.get());
                AtomicReference<Double> a = new AtomicReference<>(Double.MAX_VALUE);
                JSONArray low = new JSONArray(jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getJSONArray("low").getJSONObject(0).getJSONArray("low"));
                low.forEach((min) -> {
                    if (!min.equals(null)) {
                        if ((Double) min <= a.get()) {
                            a.set((Double) min);
                        }
                    }
                });
                dayInfos.setMin(a.get());
                ativo.addDayInfos(dayInfos);
                ativos.add(ativo);

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
