package med.voll.api.repository.consulta;

import java.time.LocalDateTime;

import med.voll.api.domain.consulta.Consulta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>{

	@Query("""
			SELECT
				CASE WHEN COUNT(C) > 0 THEN true ELSE false END
			FROM
				Consulta C
			WHERE
				C.paciente.id = :idPaciente
				AND CAST(C.data AS DATE) = CAST(:data AS DATE)""")
	Boolean existsByPacienteIdAndDataAndMotivoCancelamentoIsNull(Long idPaciente, LocalDateTime data);
	
	@Query("""
			SELECT 
				CASE WHEN COUNT(C) > 0 THEN true ELSE false END
			FROM
				Consulta C
			WHERE
				C.medico.id = :idMedico
				AND C.data = CAST(:data AS DATE)""")
	Boolean existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(Long idMedico, LocalDateTime data);

	Page<Consulta> findAllBy(Pageable paginacao);
}
