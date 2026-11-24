package Diego.Ferreiro.ExamenCine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Diego.Ferreiro.ExamenCine.Entity.Actor;

public interface IRepositoryActor extends JpaRepository<Actor, Long> {
}
