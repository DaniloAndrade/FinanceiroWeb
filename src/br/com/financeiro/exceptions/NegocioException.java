package br.com.financeiro.exceptions;

public class NegocioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NegocioException() {
	}

	public NegocioException(String message) {
		super(message);
	}

	public NegocioException(Throwable cause) {
		super(cause);
	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

}
