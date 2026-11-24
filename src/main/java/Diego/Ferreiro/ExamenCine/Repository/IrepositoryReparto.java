package Diego.Ferreiro.ExamenCine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Diego.Ferreiro.ExamenCine.Entity.Reparto;

public interface IrepositoryReparto extends JpaRepository<Reparto, Long> {
    boolean existsByPelicula_IdAndActor_Id(Long peliculaId, Long actorId);
}
