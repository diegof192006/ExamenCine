package Diego.Ferreiro.ExamenCine.controller;

import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import Diego.Ferreiro.ExamenCine.Entity.Actor;
import Diego.Ferreiro.ExamenCine.Entity.Pelicula;
import Diego.Ferreiro.ExamenCine.Entity.Reparto;
import Diego.Ferreiro.ExamenCine.Repository.IRepositoryActor;
import Diego.Ferreiro.ExamenCine.Repository.IrepositoryPelicula;
import Diego.Ferreiro.ExamenCine.Repository.IrepositoryReparto;
import Diego.Ferreiro.ExamenCine.dto.PeliculaDetalleDTO;
import Diego.Ferreiro.ExamenCine.dto.PeliculaListadoDTO;
import Diego.Ferreiro.ExamenCine.dto.RepartoDTO;
import Diego.Ferreiro.ExamenCine.dto.RepartoRequestDTO;

@RestController
@RequestMapping("/api/peliculas")
public class PeliculaController {
        private final IrepositoryPelicula peliculaRepository;
        private final IRepositoryActor actorRepository;
        private final IrepositoryReparto repartoRepository;

        public PeliculaController(
                        IrepositoryPelicula peliculaRepository,
                        IRepositoryActor actorRepository,
                        IrepositoryReparto repartoRepository) {
                this.peliculaRepository = peliculaRepository;
                this.actorRepository = actorRepository;
                this.repartoRepository = repartoRepository;
        }

        @GetMapping("/buscar/{anio}")
        public List<PeliculaListadoDTO> buscarPosterioresAlAnio(@PathVariable int anio) {
                return peliculaRepository.findByAnioGreaterThanOrderByAnioAsc(anio).stream()
                                .map(pelicula -> new PeliculaListadoDTO(
                                                pelicula.getId(),
                                                pelicula.getTitulo(),
                                                pelicula.getDirector(),
                                                pelicula.getAnio()))
                                .toList();
        }

        @PostMapping("/{id}/reparto")
        @Transactional
        public ResponseEntity<PeliculaDetalleDTO> agregarReparto(
                        @PathVariable Long id,
                        @Valid @RequestBody RepartoRequestDTO request) {
                Pelicula pelicula = peliculaRepository.findById(id).orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Película no encontrada"));
                Actor actor = actorRepository.findById(request.actorId()).orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Actor no encontrado"));
                if (repartoRepository.existsByPelicula_IdAndActor_Id(id, actor.getId())) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "El actor ya forma parte del reparto");
                }
                Reparto reparto = new Reparto();
                reparto.setPersonaje(request.personaje());
                reparto.setSalario(request.salario());
                pelicula.addReparto(reparto);
                actor.addReparto(reparto);
                repartoRepository.save(reparto);
                return ResponseEntity.created(URI.create("/api/peliculas/" + id))
                                .body(toDetalleDTO(pelicula));
        }

        private PeliculaDetalleDTO toDetalleDTO(Pelicula pelicula) {
                List<RepartoDTO> repartoDTOs = pelicula.getRepartos().stream().map(reparto -> new RepartoDTO(
                                reparto.getActor().getId(),
                                reparto.getActor().getNombre(),
                                reparto.getPersonaje(),
                                reparto.getSalario())).toList();
                return new PeliculaDetalleDTO(
                                pelicula.getId(),
                                pelicula.getTitulo(),
                                pelicula.getDirector(),
                                pelicula.getAnio(),
                                pelicula.getDuracion(),
                                repartoDTOs);
        }
}
