package Diego.Ferreiro.ExamenCine.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "pelicula")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", length = 150, nullable = false, unique = true)
    private String titulo;

    @Column(name = "director", length = 100, nullable = false)
    private String director;

    @Column(name = "anio")
    private int anio;

    @Column(name = "duracion")
    private int duracion;

    @OneToMany(mappedBy = "pelicula", cascade = CascadeType.REMOVE)
    private List<Reparto> repartos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public List<Reparto> getRepartos() {
        return repartos;
    }

    public void addReparto(Reparto reparto) {
        Objects.requireNonNull(reparto, "reparto no puede ser null");

        Pelicula peliculaAnterior = reparto.getPelicula();
        if (peliculaAnterior != null && peliculaAnterior != this) {
            peliculaAnterior.repartos.remove(reparto);
        }
        if (!repartos.contains(reparto)) {
            repartos.add(reparto);
        }
        reparto.setPelicula(this);
    }
}
