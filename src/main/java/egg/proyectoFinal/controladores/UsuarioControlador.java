package egg.proyectoFinal.controladores;


import egg.proyectoFinal.entidades.Usuario;
import egg.proyectoFinal.servicios.UsuarioServicio;
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
import java.util.Map;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    public UsuarioControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping
    public ModelAndView listarUsuarios(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("lista-usuarios");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("usuarios", usuarioServicio.listarUsuarios());
        return mav;
    }

    @GetMapping("/formulario")
    public ModelAndView formularioCreacion(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("usuario-formulario");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("usuario", inputFlashMap.get("usuario"));
            mav.addObject("excepcion", inputFlashMap.get("excepcion"));
        } else {
            mav.addObject("usuario", new Usuario());
        }

        mav.addObject("accion", "crear");
        return mav;
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView traerUsuarioPorId(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("usuario-formulario");
        mav.addObject("usuario", usuarioServicio.obtenerUsuarioPorId(id));
        mav.addObject("accion", "actualizar");
        return mav;
    }


    @PostMapping("/crear")
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
    public RedirectView borrar(@PathVariable Long id) {
        RedirectView redirect = new RedirectView("/usuario");
        usuarioServicio.borrarUsuarioPorId(id);
        return redirect;
    }/*Agregar un mensaje de alerta para que no borre directamente */

}
