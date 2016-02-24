package br.com.socialbase.challengesocialbase.webservice;

import android.content.Context;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import br.com.socialbase.challengesocialbase.util.Constants;
import br.com.socialbase.challengesocialbase.util.MessagingUtils;


public abstract class AsyncReceiveRequest extends AsyncTask<Void, Void, WsResult> implements Constants {

	private static final String SUCCESS_MSG = "Informações carregadas com sucesso";

	private Context context;
	private int webserviceKey;
	private ArrayList<String> args;
	private boolean withDialog = true;

	public AsyncReceiveRequest(Context context) {
		this.context = context;
	}

	public void prepareToReceive(int EXPECTED_RESULT, ArrayList<String> args, boolean withDialog){
		this.webserviceKey = EXPECTED_RESULT;
		this.args = args;
		this.withDialog = withDialog;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (withDialog) {
			MessagingUtils.triggerLoadingDialog(context);
		}
	}

	@Override
	protected WsResult doInBackground(Void... params) {
		WsResult result;
		try {
			result = executeRequest(args);
		} catch (WebserviceException e) {
			result = new WsResult(new WsResponse(false, e.getMessage()));
		} catch (UnsupportedEncodingException e) {
			result = new WsResult(new WsResponse(false, e.getMessage()));
		}

		return result;
	}

	@Override
	protected void onPostExecute(WsResult result) {
		super.onPostExecute(result);
		if (withDialog) {
        	MessagingUtils.dismissDialog();
        	MessagingUtils.generateShortToast(context, result.getResponse().isStatusGood() ?
					SUCCESS_MSG : result.getResponse().getMessage());
		}
		onExecutionCompleted(result);
	}

	private WsResult executeRequest(ArrayList<String> args) throws WebserviceException, UnsupportedEncodingException {
		WsResult result = null;
		ChallengeRest rest = new ChallengeRest(context);
		switch (webserviceKey) {
			case EXPECTED_RESULT_POSTS:
				result = rest.getPosts();
				break;
		default:
			break;
		}

		return result;
	}

	public abstract void onExecutionCompleted(WsResult result);

}
