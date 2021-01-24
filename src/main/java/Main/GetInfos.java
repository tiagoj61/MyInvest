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
        List<Ativo> ativos = new ArrayList<>();
        final Sheet sheet;
        final TextA t;
        final OpenDocument doc;


        try {
            String[] vetor = {"ITSA4", "HYPE3", "EGIE3", "MFII11", "HFOF11", "HCTR11"};

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

                double fechamento = jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("meta").getDouble("regularMarketPrice");
                dayInfos.setFechamento(fechamento);

                AtomicReference<Double> maximo = new AtomicReference<>(0.0);
                jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("high").forEach((max) -> {
                    if (!max.equals(null)) {

                        if ((Double) max >= maximo.get()) {
                            maximo.set((Double) max);
                        }
                    }
                });
                dayInfos.setMax(maximo.get());

                AtomicReference<Double> minimo = new AtomicReference<>(Double.MAX_VALUE);
                jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("low").forEach((min) -> {
                    if (!min.equals(null)) {
                        if ((Double) min <= minimo.get()) {
                            minimo.set((Double) min);
                        }
                    }
                });
                dayInfos.setMin(minimo.get());

                dayInfos.setAbertura((Double) jsonObject.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("open").get(0));

                if (dayInfos.getMax() < dayInfos.getFechamento()) {
                    dayInfos.setMax(dayInfos.getFechamento());
                }
                if (dayInfos.getMin() > dayInfos.getFechamento()) {
                    dayInfos.setMin(dayInfos.getFechamento());
                }
                ativo.addDayInfos(dayInfos);
                ativos.add(ativo);

            }

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
