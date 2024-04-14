package med.voll.api.infra.exception;

import java.io.Serial;

public class ValidacaoException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 986071186857216619L;

	public ValidacaoException(String mensagem) {
		super(mensagem);
	}
}
