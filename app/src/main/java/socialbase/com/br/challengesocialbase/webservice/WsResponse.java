package socialbase.com.br.challengesocialbase.webservice;

public class WsResponse {

	private String message;
	private boolean status;
	
	public WsResponse(){
		this.message = "Resultado inesperado";
		this.status = false;
	}
	
	public WsResponse(boolean status, String message){
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatusGood() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}	
}
