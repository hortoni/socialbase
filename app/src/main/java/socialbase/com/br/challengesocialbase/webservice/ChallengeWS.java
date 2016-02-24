package socialbase.com.br.challengesocialbase.webservice;

import android.content.Context;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import socialbase.com.br.challengesocialbase.util.Constants;


/**
 * Classe que implementa os métodos HTTP GET e POST. Esses métodos são
 * utilizados na classe Rest para fazer as requisições ao Web Service.
 * 
 * @author hortoni
 * 
 */
public class ChallengeWS implements Constants {

	public static final String CONNECTION_ERROR = "Falha na conexão";


	public String[] get(Context context, String url) {
		String[] result = new String[2];

		try {
			URL obj = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setRequestMethod("GET");

			//add request header
			conn.setRequestProperty("Content-type", "application/json");
//			conn.setRequestProperty("Authorization", "Token " + AutenticationTokenUtil.getAutenticationToken(context));
			conn.connect();

			result[0] = String.valueOf(conn.getResponseCode());

			BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			result[1] = response.toString();
		} catch (IOException e) {
			e.printStackTrace();
			result[0] = "0";
			result[1] = CONNECTION_ERROR;
		}

		return result;
	}

	public String[] post(Context context, String urlString, String json) {
		String[] result = new String[2];

		try {
			URL url = new URL(urlString);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");

			//add request header
			conn.setRequestProperty("Content-type", "application/json");
//			conn.setRequestProperty("Authorization", "Token" + AutenticationTokenUtil.getAutenticationToken(context));

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();

			// Send post request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(json);
			wr.flush();
			wr.close();

			result[0] = String.valueOf(conn.getResponseCode());

			BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			result[1] = response.toString();

		} catch (IOException e) {
			e.printStackTrace();
			result[0] = "0";
			result[1] = CONNECTION_ERROR;
		}

		return result;
	}

}
