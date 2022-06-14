package egg.proyectoFinal.controladores;


import egg.proyectoFinal.entidades.Producto;
import egg.proyectoFinal.servicios.ProductoServicio;
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
@RequestMapping("/producto")
public class ProductoControlador {

    private final ProductoServicio productoServicio;

    public ProductoControlador(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ModelAndView listarProductos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("lista-productos");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("productos", productoServicio.listarProductos());
        return mav;
    }

    @GetMapping("/formulario")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ModelAndView formularioCreacion(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("producto-formulario");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("producto", inputFlashMap.get("producto"));
            mav.addObject("excepcion", inputFlashMap.get("excepcion"));
        } else {
            mav.addObject("producto", new Producto());
        }

        mav.addObject("action", "crear");
        return mav;
    }

    @GetMapping("/formulario/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ModelAndView traerProductoPorId(@PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("producto-formulario");

        if (!session.getId().equals(id)) return new ModelAndView("redirect:/");

        mav.addObject("producto", productoServicio.obtenerProductoPorId(id));
        mav.addObject("action", "actualizar");
        return mav;
    }

/*
    @PostMapping("/crear")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public RedirectView crear(Producto producto, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/producto");

        try {
            productoServicio.crearProducto(producto);
            attributes.addFlashAttribute("exito", "La operación fue realizada con éxito.");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("producto", producto);
            attributes.addFlashAttribute("excepcion", e.getMessage());
            redirect.setUrl("/producto/formulario");
        }

        return redirect;
    }

 */

    @PreAuthorize("hasRole('ADMIN', 'USER')")
    @PostMapping("/crear")
    //Recibe el autor y además recibe la foto, required en false pq es opcional que mande la foto
    //Tipo MultipartFile tiene diversos métodos.
    public RedirectView crear(Producto producto, @RequestParam(required = false) MultipartFile imagen, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/producto");

        try {
            productoServicio.crearProducto(producto, imagen);
            attributes.addFlashAttribute("success", "The operation has been carried out successfully");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("producto", producto);
            attributes.addFlashAttribute("exception", e.getMessage());
            redirect.setUrl("/producto/formulario");
        }

        return redirect;
    }


    @PostMapping("/actualizar")
    @PreAuthorize("hasAnyRole('ADMIN, USER')")
    public RedirectView actualizar(Producto producto, MultipartFile imagen, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/producto");

        try {
            productoServicio.actualizarProducto(producto, imagen);
            attributes.addFlashAttribute("exito", "La operación fue realizada con éxito.");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("producto", producto);
            attributes.addFlashAttribute("excepcion", e.getMessage());
            redirect.setUrl("/producto/formulario");
        }
        return redirect;

    }

    @PostMapping("/borrar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public RedirectView borrar(@PathVariable Long id) {
        RedirectView redirect = new RedirectView("/producto");
        productoServicio.borrarProductoPorId(id);
        return redirect;
    }/*Agregar un mensaje de alerta para que no borre directamente */

}
