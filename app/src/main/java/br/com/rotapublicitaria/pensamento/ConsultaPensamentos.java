package br.com.rotapublicitaria.pensamento;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ConsultaPensamentos extends AsyncTask<Void, Void, String> {

    private static final String URL_JSON = "http://rotapublicitaria.com.br/pensamento_aleatorio/json.php";

    private ConsultaPensamentosListener listener;

    public ConsultaPensamentos(ConsultaPensamentosListener l) {
        this.listener = l;
    }

    @Override
    protected String doInBackground(Void... params) {

        String resultado = null;

        try {
            resultado = ConsultarServidor();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return InterpretaResultado(resultado);

    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);

        this.listener.onConsultaConcluida(s);

    }

    private String InterpretaResultado(String s) {

        try {

            JSONObject jsonObject = new JSONObject(s);
            return jsonObject.getString("pensamento");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

    private String ConsultarServidor() throws IOException {

        HttpURLConnection conn = null;

        URL url = new URL(URL_JSON);
        conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.connect();

        InputStream in = conn.getInputStream();
        StringBuilder buffer = new StringBuilder();
        String line = null;

        if (in == null) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        while((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }

        if (buffer.length() == 0) {
            return null;
        }

        conn.disconnect();

        return buffer.toString();

    }

    public interface ConsultaPensamentosListener  {

        public void onConsultaConcluida(String s);

    }
}