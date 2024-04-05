package med.voll.api.domain.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long>{

	Page<Paciente> findAllByAtivoTrue(Pageable paginacao);

	@Query("""
			SELECT
					P
			FROM
				Paciente P
			WHERE
				P.id = :idPaciente
				AND P.ativo = 1
			""")
	Paciente findByIdAndAtivo(Long idPaciente);
}
