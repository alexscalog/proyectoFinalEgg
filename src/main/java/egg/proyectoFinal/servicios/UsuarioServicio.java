package egg.proyectoFinal.servicios;

import egg.proyectoFinal.entidades.Rol;
import egg.proyectoFinal.entidades.Usuario;
import egg.proyectoFinal.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;


@Service
public class UsuarioServicio implements UserDetailsService {


    private final UsuarioRepositorio usuarioRepositorio;

    private final BCryptPasswordEncoder codificador;

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
        usuario1.setContrasenia(codificador.encode(usuario.getContrasenia()));
        usuario1.setTelefono(usuario.getTelefono());

        if (usuarioRepositorio.findAll().isEmpty()) usuario1.setRol(Rol.ADMIN);
        else usuario1.setRol(Rol.USER);


        usuarioRepositorio.save(usuario1);
    }

    @Transactional
    public void actualizarUsuario(Usuario usuario) {
        Usuario usuario1 = usuarioRepositorio.findById(usuario.getId()).get();

        usuario1.setNombreUsuario(usuario.getNombreUsuario());
        usuario1.setApellidoUsuario(usuario.getApellidoUsuario());
        usuario1.setEmail(usuario.getEmail());
        usuario1.setContrasenia(codificador.encode(usuario.getContrasenia()));
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

        Supplier<UsernameNotFoundException> supplier = () -> new UsernameNotFoundException("No existe un usuario relacionado al email ingresado.");
        Usuario usuario = usuarioRepositorio.findByEmail(email).orElseThrow(supplier);

        GrantedAuthority authority = new SimpleGrantedAuthority("ROL_" + usuario.getRol());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("id", usuario.getId());
        session.setAttribute("email", usuario.getEmail());
        session.setAttribute("rol", usuario.getRol());

        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getContrasenia(), singletonList(authority));
    }
}

