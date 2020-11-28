package com.testigos.gesoc.model.domain.ingresos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.testigos.gesoc.model.domain.egresos.Egreso;
import com.testigos.gesoc.model.domain.entidades.Entidad;
import com.testigos.gesoc.model.domain.persistentes.EntidadPersistente;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Entity
@Table(name = "ingresos")
public class Ingreso extends EntidadPersistente {

    @Column
    private @Getter @Setter String descripcion;

    @Column
    private @Getter @Setter double monto;

    @Column
    private @Getter @Setter LocalDate fechaIngreso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entidad_id")
    private @Getter @Setter Entidad entidad;

    @OneToMany(mappedBy = "ingresoAsociado", cascade = CascadeType.ALL)
    private @Getter final List<Egreso> egresosAsociados = new ArrayList<>();

    public Ingreso(String descripcion, double valorTotal) {
        this.descripcion = descripcion;
        this.monto = valorTotal;
    }

    public double valorDisponible() {
        return monto - valorEgresosAsociados();
    }

    private double valorEgresosAsociados() {
        return egresosAsociados.stream().mapToDouble(Egreso::valorTotal).sum();
    }
}
