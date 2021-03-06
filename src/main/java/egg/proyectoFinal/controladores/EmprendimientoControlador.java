package egg.proyectoFinal.controladores;


import egg.proyectoFinal.entidades.Emprendimiento;
import egg.proyectoFinal.entidades.Usuario;
import egg.proyectoFinal.servicios.EmprendimientoServicio;
import egg.proyectoFinal.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    private final UsuarioServicio usuarioServicio;


    @Autowired
    public EmprendimientoControlador(EmprendimientoServicio emprendimientoServicio, UsuarioServicio usuarioServicio) {
        this.emprendimientoServicio = emprendimientoServicio;
        this.usuarioServicio = usuarioServicio;
    }

    @GetMapping("/perfil/{id}")
    public ModelAndView emprendimiento(HttpServletRequest request, @PathVariable Long id){
        ModelAndView mav = new ModelAndView("emprendimiento-perfil");

        mav.addObject("emprendimiento", emprendimientoServicio.obtenerEmprendimientoPorId(id));

        return mav;
    }

    @GetMapping("/lista")
    public ModelAndView listarEmprendimientos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("lista-emprendimientos");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("emprendimientos", emprendimientoServicio.listarEmprendimientos());
        return mav;
    }


    @GetMapping("/mis-emprendimientos/{id}")
    public ModelAndView misEmprendimientos(HttpServletRequest request, @PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("mis-emprendimientos");


        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("misemprendimientos", emprendimientoServicio.traerEmprendimientosPorSesion(id));
        return mav;
    }

    @GetMapping("/formulario")
    //@PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ModelAndView formularioCreacion(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView("emprendimiento-formulario");
        Emprendimiento emprendimiento = new Emprendimiento();



        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);


        if (inputFlashMap != null) {
            mav.addObject("emprendimiento", inputFlashMap.get("emprendimiento"));
            mav.addObject("excepcion", inputFlashMap.get("excepcion"));
        } else {

            mav.addObject("emprendimiento", emprendimiento);

        }

        mav.addObject("accion", "crear");
        return mav;
    }

    @GetMapping("/formulario/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ModelAndView traerEmprendimientoPorId(@PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("emprendimiento-formulario");

        mav.addObject("emprendimiento", emprendimientoServicio.obtenerEmprendimientoPorId(id));
        mav.addObject("accion", "actualizar");
        return mav;
    }




    @PostMapping("/crear")
    //@PreAuthorize("hasAnyRole('ADMIN, USER')")
    public RedirectView crear(Emprendimiento emprendimiento, RedirectAttributes attributes, @RequestParam(required = false) MultipartFile logoEmprendimiento) {
        RedirectView redirect = new RedirectView("/emprendimiento/lista");

        try {
            emprendimientoServicio.crearEmprendimiento(emprendimiento, logoEmprendimiento);
            attributes.addFlashAttribute("exito", "La operaci??n fue realizada con ??xito.");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("emprendimiento", emprendimiento);
            attributes.addFlashAttribute("excepcion", e.getMessage());
            redirect.setUrl("/emprendimiento/formulario");
        }

        return redirect;
    }

    @PostMapping("/actualizar")
    //@PreAuthorize("hasAnyRole('ADMIN, USER')")
    public RedirectView actualizar(Emprendimiento emprendimiento, RedirectAttributes attributes, @RequestParam(required = false) MultipartFile logoEmprendimiento) {
        RedirectView redirect = new RedirectView("/emprendimiento/mis-emprendimientos/" + emprendimiento.getUsuario().getId());

        try {
           emprendimientoServicio.actualizarEmprendimiento(emprendimiento, logoEmprendimiento);
            attributes.addFlashAttribute("exito", "La operaci??n fue realizada con ??xito.");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("emprendimiento", emprendimiento);
            attributes.addFlashAttribute("excepcion", e.getMessage());
            redirect.setUrl("/emprendimiento/formulario");
        }
        return redirect;

    }

    @PostMapping("/borrar/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public RedirectView borrar(@PathVariable Long idEmprendimiento) {
        RedirectView redirect = new RedirectView("/emprendimiento");
        emprendimientoServicio.borrarEmprendimientoPorId(idEmprendimiento);
        return redirect;
    }/*Agregar un mensaje de alerta para que no borre directamente */
}
