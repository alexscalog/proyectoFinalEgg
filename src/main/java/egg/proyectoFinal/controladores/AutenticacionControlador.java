package egg.proyectoFinal.controladores;

import egg.proyectoFinal.entidades.Rol;
import egg.proyectoFinal.entidades.Usuario;
import egg.proyectoFinal.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/autenticacion")
public class AutenticacionControlador {

    private final UsuarioServicio usuarioServicio;


    @Autowired
    public AutenticacionControlador(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;

    }


    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("login-formulario");

        if (error != null) mav.addObject("error", "Email o contraseña inválidos.");
        if (logout != null) mav.addObject("logout", "You have successfully exited the platform");
        if (principal != null) mav.setViewName("redirect:/");

        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("excepcion", inputFlashMap.get("excepcion"));
            mav.addObject("usuario", inputFlashMap.get("usuario"));
        } else {
            Usuario usuario = new Usuario();
            usuario.setRol(Rol.USER);
            mav.addObject("usuario", usuario);
        }
        return mav;
    }

    /*@GetMapping("/registrarse")
    public ModelAndView registrarse(HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("login-formulario");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (principal != null) mav.setViewName("redirect:/");

        if (inputFlashMap != null) {
            mav.addObject("excepcion", inputFlashMap.get("excepcion"));
            mav.addObject("usuario", inputFlashMap.get("usuario"));
        } else {
            Usuario usuario = new Usuario();
            usuario.setRol(Rol.USER);
            mav.addObject("usuario", usuario);
        }

        return mav;
    }*/

    @PostMapping("/registro")
    public RedirectView registro(Usuario usuario, HttpServletRequest request, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/");

        try {
            usuarioServicio.crearUsuario(usuario);
            request.login(usuario.getEmail(), usuario.getContrasenia());
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("usuario", usuario);
            attributes.addFlashAttribute("excepcion", e.getMessage());
            redirect.setUrl("/autenticacion/login");
        } catch (ServletException e) {
            attributes.addFlashAttribute("error", "Auto login failed");
        }

        return redirect;
    }

}
