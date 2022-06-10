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

    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @Column(name = "telefono", nullable = false)
    private int telefono;

    @OneToMany(mappedBy = "usuario")
    private List<Emprendimiento>listaEmprendimientos;


    @Column(name = "usuario_rol", nullable = false)
    private Rol rol;



    public Usuario() {
    }

    public Usuario(Long id, String nombreUsuario, String apellidoUsuario, String email, String contrasenia, int telefono, List<Emprendimiento> listaEmprendimientos, Rol rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
        this.listaEmprendimientos = listaEmprendimientos;
        this.rol = rol;
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

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public List<Emprendimiento> getListaEmprendimientos() {
        return listaEmprendimientos;
    }

    public void setListaEmprendimientos(List<Emprendimiento> listaEmprendimientos) {
        this.listaEmprendimientos = listaEmprendimientos;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
