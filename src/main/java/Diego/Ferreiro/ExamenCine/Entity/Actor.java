package Diego.Ferreiro.ExamenCine.Entity;

import jakarta.persistence.Column;
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
@Table(name = "actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "nacionalidad", length = 60, nullable = false)
    private String nacionalidad;

    @OneToMany(mappedBy = "actor")
    private List<Reparto> repartos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public List<Reparto> getRepartos() {
        return repartos;
    }

    public void addReparto(Reparto reparto) {
        Objects.requireNonNull(reparto, "reparto no puede ser null");

        Actor actorAnterior = reparto.getActor();
        if (actorAnterior != null && actorAnterior != this) {
            actorAnterior.repartos.remove(reparto);
        }
        if (!repartos.contains(reparto)) {
            repartos.add(reparto);
        }
        reparto.setActor(this);
    }
}
