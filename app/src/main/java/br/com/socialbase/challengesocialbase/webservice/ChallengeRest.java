package br.com.socialbase.challengesocialbase.webservice;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import br.com.socialbase.challengesocialbase.model.Post;
import br.com.socialbase.challengesocialbase.util.Constants;

public class ChallengeRest implements Constants {

	private Context context;

	public ChallengeRest(Context context) {
		this.context = context;

	}

	public WsResult getPosts() throws UnsupportedEncodingException {
		String url = WEBSERVICE_URL;
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
			return new WsResult(new WsResponse(response[0].equals("200"), response[1]), list, EXPECTED_RESULT_POSTS);
		} else {
			return new WsResult(new WsResponse(false, response[1]));
		}
	}

}
