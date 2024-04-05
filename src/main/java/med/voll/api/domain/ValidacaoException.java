package med.voll.api.domain;

public class ValidacaoException extends RuntimeException {

	private static final long serialVersionUID = 986071186857216619L;

	public ValidacaoException(String mensagem) {
		super(mensagem);
	}
}
