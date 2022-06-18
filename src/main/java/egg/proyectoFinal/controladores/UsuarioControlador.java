package egg.proyectoFinal.controladores;


import egg.proyectoFinal.entidades.Usuario;
import egg.proyectoFinal.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/lista")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView listarUsuarios(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("lista-usuarios");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("usuarios", usuarioServicio.listarUsuarios());
        return mav;
    }

    @GetMapping("/formulario")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ModelAndView formularioCreacion(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("usuario-formulario");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("usuario", inputFlashMap.get("usuario"));
            mav.addObject("excepcion", inputFlashMap.get("excepcion"));
        } else {
            mav.addObject("usuario", new Usuario());
        }

        mav.addObject("action", "crear");
        return mav;
    }

    @GetMapping("/formulario/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ModelAndView traerUsuarioPorId(@PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("usuario-formulario");

        if (!session.getId().equals(id)) return new ModelAndView("redirect:/");


        mav.addObject("usuario", usuarioServicio.obtenerUsuarioPorId(id));
        mav.addObject("action", "actualizar");
        return mav;
    }


    @PostMapping("/crear")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public RedirectView crear(Usuario usuario, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/usuario");

        try {
            usuarioServicio.crearUsuario(usuario);
            attributes.addFlashAttribute("exito", "La operación fue realizada con éxito.");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("usuario", usuario);
            attributes.addFlashAttribute("excepcion", e.getMessage());
            redirect.setUrl("/usuario/formulario");
        }

        return redirect;
    }

    @PostMapping("/actualizar")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public RedirectView actualizar(Usuario usuario, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/usuario");

        try {
            usuarioServicio.actualizarUsuario(usuario);
            attributes.addFlashAttribute("exito", "La operación fue realizada con éxito.");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("usuario", usuario);
            attributes.addFlashAttribute("excepcion", e.getMessage());
            redirect.setUrl("/usuario/formulario");
        }
            return redirect;

    }

    @PostMapping("/borrar/{id}")
    @PreAuthorize("hasRole('ADMIN')") //PREGUNTAR SOBRE PERMISOS DE ELIMINACION USUARIO/ADMIN
    public RedirectView borrar(@PathVariable Long id) {
        RedirectView redirect = new RedirectView("/usuario");
        usuarioServicio.borrarUsuarioPorId(id);
        return redirect;
    }/*Agregar un mensaje de alerta para que no borre directamente */

}
