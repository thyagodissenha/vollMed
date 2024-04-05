package med.voll.api.domain.consulta.validacoes.cancelamento;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {

	public void validar(@Valid DadosCancelamentoConsulta dados);
}
