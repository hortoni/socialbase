package socialbase.com.br.challengesocialbase.webservice;

public class WebserviceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public static final String WS_COMUNICATION_ERROR_MSG = "Ocorreu uma falha de comunicação com o servidor";
	
	public WebserviceException() {
		super(WS_COMUNICATION_ERROR_MSG);
	}
	
	public WebserviceException(String msg) {
		super(msg);
	}

}
