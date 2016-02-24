package br.com.socialbase.challengesocialbase.util;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class MensagingUtils {

	private static final String MSG_LOADING_DIALOG = "Buscando Dados no Servidor ...";
	private static final String MSG_SAVING_DIALOG = "Adicionando Dados no Servidor ...";

	private static ProgressDialog ringProgressDialog;

	/**
	 * Lança um PopUp com um título e uma mensagem. Usuario necessita apertar 'ok' para fechar.
	 *
	 * @param context
	 * @param title
	 * @param message
	 */
	public static void generateDialog(Context context, String title, String message) {

		new Builder(context)
				.setTitle(title).setMessage(message).setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int which) {
								try {
									this.finalize();
								} catch (Throwable e) {
									e.printStackTrace();
								}
							}
						}).show();
	}

	/**
	 * Lança um popUp de carregamento com uma mensagem padrão de carregamento.
	 *
	 * @param context (Ao passar o contexto, recomenda-se passar Activity.this no parâmetro)
	 */
	public static void triggerLoadingDialog(Context context) {
		triggerDialog(context, MSG_LOADING_DIALOG);
	}

	/**
	 * Lança um popUp de carregamento com uma mensagem padrão de salvamento.
	 *
	 * @param context (Ao passar o contexto, recomenda-se passar Activity.this no parâmetro)
	 */
	public static void triggerSavingDialog(Context context) {
		triggerDialog(context, MSG_SAVING_DIALOG);
	}


	private static void triggerDialog(Context context, String msg){

		try {
		if (ringProgressDialog != null){
			if (ringProgressDialog.isShowing()) {
				ringProgressDialog.dismiss();
			}
			ringProgressDialog = null;
		}

		ringProgressDialog = ProgressDialog.show(context, "Carregando...", msg, true, true);
		} catch (Exception e) {
			ringProgressDialog = null;
//			MensagingUtils.generateLongToast(context, e.getMessage());
		}
	}

	/**
	 * Fecha o progress dialog que está na tela no momento.
	 */
	public static void dismissDialog(){
		try {
			if (ringProgressDialog != null){
				if (ringProgressDialog.isShowing()){
					ringProgressDialog.dismiss();
				}
				ringProgressDialog = null;
			}
		} catch (Exception e) {
			// Erro desconhecido. Unable to attach window
		}
	}

	/**
	 * Gera Toast de duração curta
	 * @param context
	 * @param text
	 */
	public static void generateShortToast(Context context, String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

}
