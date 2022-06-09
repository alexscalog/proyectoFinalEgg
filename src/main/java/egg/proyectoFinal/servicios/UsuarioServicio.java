package egg.proyectoFinal.servicios;

import egg.proyectoFinal.entidades.Usuario;
import egg.proyectoFinal.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;


@Service
public class UsuarioServicio implements UserDetailsService {

    private UsuarioRepositorio usuarioRepositorio;
    private final BCryptPasswordEncoder codificador;

    @Autowired
    public UsuarioServicio(UsuarioRepositorio usuarioRepositorio, BCryptPasswordEncoder codificador) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.codificador = codificador;
    }


    @Transactional
    public void crearUsuario(Usuario usuario) {

        Usuario usuario1 = new Usuario();

        usuario1.setNombreUsuario(usuario.getNombreUsuario());
        usuario1.setApellidoUsuario(usuario.getApellidoUsuario());
        usuario1.setEmail(usuario.getEmail());
        usuario1.setTelefono(usuario.getTelefono());


        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void actualizarUsuario(Usuario usuario) {
        Usuario usuario1 = usuarioRepositorio.findById(usuario.getId()).get();

        usuario1.setNombreUsuario(usuario.getNombreUsuario());
        usuario1.setApellidoUsuario(usuario.getApellidoUsuario());
        usuario1.setEmail(usuario.getEmail());
        usuario1.setTelefono(usuario.getTelefono());


        usuarioRepositorio.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Transactional
    public void borrarUsuarioPorId(Long id) {
        usuarioRepositorio.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Supplier<UsernameNotFoundException> supplier = () -> new UsernameNotFoundException("mensaje de usuario no encontrado");
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElseThrow(supplier);
        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getContrasenia(), Collections.emptyList());

    }
}

