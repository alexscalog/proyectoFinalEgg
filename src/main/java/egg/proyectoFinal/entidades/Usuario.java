package egg.proyectoFinal.entidades;


import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre_usuario", length = 50, nullable = false)
    private String nombreUsuario;

    @Column(name = "apellido_usuario", length = 50, nullable = false)
    private String apellidoUsuario;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "telefono", nullable = false)
    private int telefono;

    /*@OneToMany(fetch = EAGER)
    @JoinColumn(name = "nombreEmprendimiento", referencedColumnName = "id", nullable = false)
    private List<Emprendimiento> listaEmprendimiento;*/


    public Usuario() {
    }

    public Usuario(Long id, String nombreUsuario, String apellidoUsuario, String email, int telefono /*List<Emprendimiento> listaEmprendimiento*/) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.email = email;
        this.telefono = telefono;
        /*this.listaEmprendimiento = listaEmprendimiento*/;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

   /* public List<Emprendimiento> getListaEmprendimiento() {
        return listaEmprendimiento;
    }

    public void setListaEmprendimiento(List<Emprendimiento> listaEmprendimiento) {
        this.listaEmprendimiento = listaEmprendimiento;
    }*/
}
