package egg.proyectoFinal.servicios;

import egg.proyectoFinal.entidades.Usuario;
import egg.proyectoFinal.repositorios.UsuarioRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class UsuarioServicio {

    private UsuarioRepositorio usuarioRepositorio;

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
}

