package com.UTN_BECAS.Sistema_Becas.Postulaciones.Model;

import com.UTN_BECAS.Sistema_Becas.Auth.Model.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_beca_binid")
public class HistorialBecaBinid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToOne
    @JoinColumn(name = "postulacion_id", nullable = false, unique = true)
    private Postulacion postulacion;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha;

    @PrePersist
    protected void onCreate() {
        this.fecha = LocalDateTime.now();
    }

    public HistorialBecaBinid() {
    }

    public HistorialBecaBinid(Usuario usuario, Postulacion postulacion) {
        this.usuario = usuario;
        this.postulacion = postulacion;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Postulacion getPostulacion() { return postulacion; }
    public void setPostulacion(Postulacion postulacion) { this.postulacion = postulacion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistorialBecaBinid)) return false;
        HistorialBecaBinid that = (HistorialBecaBinid) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
