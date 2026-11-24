package Diego.Ferreiro.ExamenCine.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Diego.Ferreiro.ExamenCine.Entity.Pelicula;

public interface IrepositoryPelicula extends JpaRepository<Pelicula, Long> {
    boolean existsByTituloIgnoreCase(String titulo);

    List<Pelicula> findByAnioGreaterThanOrderByAnioAsc(int anio);

}
