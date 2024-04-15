package med.voll.api.controller;

import med.voll.api.domain.consulta.DadosListagemConsulta;
import med.voll.api.service.consulta.ConsultaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosCancelamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

	private final ConsultaService consultaService;

	public ConsultaController(ConsultaService consultaService) {
		this.consultaService = consultaService;
	}

	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoConsulta> agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
		return ResponseEntity.ok(consultaService.agendar(dados));
	}
	
	@DeleteMapping
	@Transactional
	public ResponseEntity<?> cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
		consultaService.cancelar(dados);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemConsulta>> listar(@PageableDefault(size=10, sort={"data"}) Pageable pagina) {
		return ResponseEntity.ok(consultaService.listar(pagina));
	}

	@GetMapping("{id}")
	public ResponseEntity<DadosDetalhamentoConsulta> detalhar(@PathVariable Long id) {
		return ResponseEntity.ok(consultaService.detalhar(id));
	}
}
