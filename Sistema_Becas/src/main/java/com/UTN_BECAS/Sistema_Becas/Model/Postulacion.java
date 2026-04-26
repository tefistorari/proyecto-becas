package com.UTN_BECAS.Sistema_Becas.Model;

import com.UTN_BECAS.Sistema_Becas.Enum.EstadoPostulacion;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "postulacion",
    uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "convocatoria_id"}))
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "convocatoria_id", nullable = false)
    private Convocatoria convocatoria;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaEnvio;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPostulacion estado;

    @OneToMany(mappedBy = "postulacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MateriasACursar> materiasACursar = new ArrayList<>();

    @OneToMany(mappedBy = "postulacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MateriasARendir> materiasARendir = new ArrayList<>();

    @OneToMany(mappedBy = "postulacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Archivo> archivos = new ArrayList<>();

    @OneToOne(mappedBy = "postulacion", cascade = CascadeType.ALL)
    private DatosPersonalesHistorial datosPersonalesHistorial;

    @OneToMany(mappedBy = "postulacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrupoFamiliar> grupoFamiliar = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        this.fechaEnvio = LocalDateTime.now();
    }

    public Postulacion(){
    }

    public Postulacion(Usuario usuario, Convocatoria convocatoria, EstadoPostulacion estado) {
        this.usuario = usuario;
        this.convocatoria = convocatoria;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Convocatoria getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(Convocatoria convocatoria) {
        this.convocatoria = convocatoria;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public EstadoPostulacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPostulacion estado) {
        this.estado = estado;
    }

    public List<GrupoFamiliar> getGrupoFamiliar() {
        return grupoFamiliar;
    }

    public List<MateriasACursar> getMateriasACursar() {
        return materiasACursar;
    }

    public void setMateriasACursar(List<MateriasACursar> materiasACursar) {
        this.materiasACursar = materiasACursar;
    }

    public List<MateriasARendir> getMateriasARendir() {
        return materiasARendir;
    }

    public void setMateriasARendir(List<MateriasARendir> materiasARendir) {
        this.materiasARendir = materiasARendir;
    }

    public List<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(List<Archivo> archivos) {
        this.archivos = archivos;
    }

    public DatosPersonalesHistorial getDatosPersonalesHistorial() {
        return datosPersonalesHistorial;
    }

    public void setDatosPersonalesHistorial(DatosPersonalesHistorial datosPersonalesHistorial) {
        this.datosPersonalesHistorial = datosPersonalesHistorial;
    }

    public void setGrupoFamiliar(List<GrupoFamiliar> grupoFamiliar) {
        this.grupoFamiliar = grupoFamiliar;
    }
}
