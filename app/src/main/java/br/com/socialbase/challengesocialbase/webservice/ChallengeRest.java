package br.com.socialbase.challengesocialbase.webservice;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import br.com.socialbase.challengesocialbase.model.Post;
import br.com.socialbase.challengesocialbase.util.Constants;

/**
 * Classe responsável por fazer a comunicação direta com o web service. Possui
 * todos os métodos de Add, Get, Update, Delete da base de dados. Todos os
 * métodos devem ser chamados assincronamente para não interromperem a thread
 * principal do Android.
 * 
 * @author hortoni
 * 
 */
public class ChallengeRest implements Constants {

	private Context context;

	public ChallengeRest(Context context) {
		this.context = context;

	}

	public WsResult getPosts() throws UnsupportedEncodingException {
		String url = WEBSERVICE_URL;
		Log.e("DEBUG", "url: " + url);
		String[] response = new ChallengeWS().get(context, url);
		if (response[0].equals("200")) {
			Gson gson = new Gson();
			ArrayList<Post> list= new ArrayList<Post>();
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(response[1]).getAsJsonArray();
			for (int i = 0; i < array.size(); i++) {
				list.add(gson.fromJson(array.get(i),
						Post.class));
			}
			Log.e("DEBUG", "posts response[0]: " + response[0]);
			Log.e("DEBUG", "posts response[1]: " + response[1]);

			return new WsResult(new WsResponse(response[0].equals("200"), response[1]), list, EXPECTED_RESULT_POSTS);
		} else {
			return new WsResult(new WsResponse(false, response[1]));
		}
	}

}
