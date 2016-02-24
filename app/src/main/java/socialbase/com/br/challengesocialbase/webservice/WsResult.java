package socialbase.com.br.challengesocialbase.webservice;

import java.util.ArrayList;

import socialbase.com.br.challengesocialbase.model.Post;
import socialbase.com.br.challengesocialbase.util.Constants;

public class WsResult implements Constants {

	private WsResponse response;
	private ArrayList<Post> posts = new ArrayList<>();

	public WsResult(){
		this.response = new WsResponse();
	}
	
	public WsResult(WsResponse response){
		this.response = response;
	}
	
	public WsResult(WsResponse response, ArrayList objects, int expected_result){
		switch (expected_result) {
			case EXPECTED_RESULT_POSTS:
				this.response = response;
				this.posts = objects;
			break;
		}

	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public WsResponse getResponse() {
		return response;
	}

	public void setResponse(WsResponse response) {
		this.response = response;
	}

}
