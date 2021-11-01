package data.entities;

import javax.persistence.*;
import java.io.Serializable;

class CompetenciaCompositeKey implements Serializable {
    private Long id;
    private Carrera carrera;
}

@Entity
@Table(name = "Competencia")
@IdClass(CompetenciaCompositeKey.class)
public class Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competencia_id")
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @Column(name = "codcompetencia", nullable = false, columnDefinition = "text")
    private String codcompetencia;

    @Column(name = "descripcion", nullable = false, columnDefinition = "text")
    private String descripcion;
}
