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
    private Long idProducto;

    @Column(name = "nombre_producto", length = 100, nullable = false)
    private String nombreProducto;

    @Lob
    @Column(name = "descripcion_producto", nullable = false)
    private String descripcionProducto;

    @Column(name = "categoria", length = 50, nullable = false)
    private String categoria;

    @Column(name = "imagen")
    private String imagen;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "id_emprendimiento_producto", referencedColumnName = "id_emprendimiento")
    private Emprendimiento emprendimiento;

    public Producto() {
    }

    public Producto(Long idProducto, String nombreProducto, String descripcionProducto, String categoria, String imagen, Emprendimiento emprendimiento) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.categoria = categoria;
        this.imagen = imagen;
        this.emprendimiento = emprendimiento;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setId(Long idProducto) {
        this.idProducto = idProducto;
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

    public Emprendimiento getEmprendimiento() {
        return emprendimiento;
    }

    public void setEmprendimiento(Emprendimiento emprendimiento) {
        this.emprendimiento = emprendimiento;
    }
}
