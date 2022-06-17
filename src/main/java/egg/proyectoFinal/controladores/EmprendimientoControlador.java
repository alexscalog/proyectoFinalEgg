package egg.proyectoFinal.controladores;


import egg.proyectoFinal.entidades.Emprendimiento;
import egg.proyectoFinal.servicios.EmprendimientoServicio;
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
@RequestMapping("/emprendimiento")
public class EmprendimientoControlador {

    private final EmprendimientoServicio emprendimientoServicio;

    public EmprendimientoControlador(EmprendimientoServicio emprendimientoServicio) {
        this.emprendimientoServicio = emprendimientoServicio;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ModelAndView listarEmprendimientos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("lista-emprendimientos");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("emprendimientos", emprendimientoServicio.listarEmprendimientos());
        return mav;
    }

    @GetMapping("/formulario")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ModelAndView formularioCreacion(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("emprendimiento-formulario");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("emprendimiento", inputFlashMap.get("emprendimiento"));
            mav.addObject("excepcion", inputFlashMap.get("excepcion"));
        } else {
            mav.addObject("emprendimiento", new Emprendimiento());

        }

        mav.addObject("accion", "crear");
        return mav;
    }

    @GetMapping("/formulario/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView traerEmprendimientoPorId(@PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("emprendimiento-formulario");

        if (!session.getId().equals(id)) return new ModelAndView("redirect:/");

        mav.addObject("emprendimiento", emprendimientoServicio.obtenerEmprendimientoPorId(id));
        mav.addObject("accion", "actualizar");
        return mav;
    }


    @PostMapping("/crear")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public RedirectView crear(Emprendimiento emprendimiento, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/emprendimiento");

        try {
            emprendimientoServicio.crearEmprendimiento(emprendimiento);
            attributes.addFlashAttribute("exito", "La operación fue realizada con éxito.");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("emprendimiento", emprendimiento);
            attributes.addFlashAttribute("excepcion", e.getMessage());
            redirect.setUrl("/emprendimiento/formulario");
        }

        return redirect;
    }

    @PostMapping("/actualizar")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public RedirectView actualizar(Emprendimiento emprendimiento, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/emprendimiento");

        try {
           emprendimientoServicio.crearEmprendimiento(emprendimiento);
            attributes.addFlashAttribute("exito", "La operación fue realizada con éxito.");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("emprendimiento", emprendimiento);
            attributes.addFlashAttribute("excepcion", e.getMessage());
            redirect.setUrl("/emprendimiento/formulario");
        }
        return redirect;

    }

    @PostMapping("/borrar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView borrar(@PathVariable Long id) {
        RedirectView redirect = new RedirectView("/emprendimiento");
        emprendimientoServicio.borrarEmprendimientoPorId(id);
        return redirect;
    }/*Agregar un mensaje de alerta para que no borre directamente */
}
