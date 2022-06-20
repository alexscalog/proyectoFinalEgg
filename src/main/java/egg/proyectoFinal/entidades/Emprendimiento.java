package egg.proyectoFinal.entidades;


import javax.persistence.*;


import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "emprendimiento")
public class Emprendimiento {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_emprendimiento")
    private Long idEmprendimiento;

    @Column(name = "emprendimiento_nombre", length = 50, nullable = false)
    private String nombreEmprendimiento;

    @Lob
    @Column(name = "descripcion_emprendimiento", nullable = false)
    private String descripcionEmprendimiento;

    @Column(name = "logo")
    private String logo;

    @Column(name = "localidad", length = 100, nullable = false)
    private String localidad;

    @Column(name = "direccion", length = 200, nullable = false)
    private String direccion;

    @Column(name = "telefono_emprendimiento")
    private Long telefonoEmprendimiento;

    @Column(name = "instagram")
    private String instagram;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "emprendimiento_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "nombreEmprendimiento")
    private List<Producto>listaProducto;

    public Emprendimiento() {
    }

    public Emprendimiento(Long idEmprendimiento, String nombreEmprendimiento, String descripcionEmprendimiento, String logo, String localidad, String direccion, Long telefonoEmprendimiento, String instagram, Usuario usuario, List<Producto> listaProducto) {
        this.idEmprendimiento = idEmprendimiento;
        this.nombreEmprendimiento = nombreEmprendimiento;
        this.descripcionEmprendimiento = descripcionEmprendimiento;
        this.logo = logo;
        this.localidad = localidad;
        this.direccion = direccion;
        this.telefonoEmprendimiento = telefonoEmprendimiento;
        this.instagram = instagram;
        this.usuario = usuario;
        this.listaProducto = listaProducto;
    }

    public Long getIdEmprendimiento() {
        return idEmprendimiento;
    }

    public void setIdEmprendimiento(Long idEmprendimiento) {
        this.idEmprendimiento = idEmprendimiento;
    }

    public String getNombreEmprendimiento() {
        return nombreEmprendimiento;
    }

    public void setNombreEmprendimiento(String nombreEmprendimiento) {
        this.nombreEmprendimiento = nombreEmprendimiento;
    }

    public String getDescripcionEmprendimiento() {
        return descripcionEmprendimiento;
    }

    public void setDescripcionEmprendimiento(String descripcionEmprendimiento) {
        this.descripcionEmprendimiento = descripcionEmprendimiento;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public Long getTelefonoEmprendimiento() {
        return telefonoEmprendimiento;
    }

    public void setTelefonoEmprendimiento(Long telefonoEmprendimiento) {
        this.telefonoEmprendimiento = telefonoEmprendimiento;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
