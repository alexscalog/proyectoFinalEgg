package egg.proyectoFinal.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


public class PrincipalControlador {
    @GetMapping
    public ModelAndView traerIndex() {
        return new ModelAndView("index");
    }
}