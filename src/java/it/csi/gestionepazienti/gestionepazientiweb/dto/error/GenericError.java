package it.csi.gestionepazienti.gestionepazientiweb.dto.error;

public class GenericError {
	private String message;

	public GenericError() {
		super();
	}

	public GenericError(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}