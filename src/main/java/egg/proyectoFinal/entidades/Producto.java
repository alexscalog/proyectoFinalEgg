package egg.proyectoFinal.entidades;


import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_producto")
    private Long id;

    @Column(name = "nombre_producto", length = 100, nullable = false)
    private String nombreProducto;

    @Lob
    @Column(name = "descripcion_producto", nullable = false)
    private String descripcionProducto;

    @Column(name = "categoria", length = 50, nullable = false)
    private String categoria;

    @Column(name = "imagen", length = 50, nullable = false)
    private String imagen;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "nombre_emprendimiento", referencedColumnName = "id_emprendimiento", nullable = false)
    private Emprendimiento nombreEmprendimiento;

    public Producto() {
    }

    public Producto(Long id, String nombreProducto, String descripcionProducto, String categoria, String imagen, Emprendimiento nombreEmprendimiento) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.categoria = categoria;
        this.imagen = imagen;
        this.nombreEmprendimiento = nombreEmprendimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Emprendimiento getNombreEmprendimiento() {
        return nombreEmprendimiento;
    }

    public void setNombreEmprendimiento(Emprendimiento nombreEmprendimiento) {
        this.nombreEmprendimiento = nombreEmprendimiento;
    }
}
