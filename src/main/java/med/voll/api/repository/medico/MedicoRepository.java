package med.voll.api.repository.medico;

import java.time.LocalDateTime;

import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MedicoRepository extends JpaRepository<Medico, Long>{

	Page<Medico> findAllByAtivoTrue(Pageable paginacao);

	@Query("""
			SELECT 
				M
			FROM 
				Medico M
			WHERE
				M.ativo = true
				AND M.especialidade = :especialidade
				AND M.id NOT IN (SELECT 
									C.medico.id
								FROM 
									Consulta C
								WHERE
									C.data = :data
									AND C.motivo is null)
			ORDER BY 
				RAND()
			LIMIT 1
			""")
	Medico escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

	@Query("""
			SELECT 
					M
			FROM
				Medico M
			WHERE
				M.id = :idMedico
				AND M.ativo = true
			 """)
	Medico findByIdAndAtivo(Long idMedico);

	@Query("""
			SELECT 
					CASE WHEN COUNT(M) > 0 THEN true ELSE false END
			FROM
				Medico M
			WHERE
				M.id = :idMedico
				AND M.ativo = true
			 """)
	Boolean existsByIdAndAtivo(Long idMedico);
}
