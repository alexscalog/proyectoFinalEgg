package egg.proyectoFinal.controladores;


import egg.proyectoFinal.entidades.Producto;
import egg.proyectoFinal.servicios.ProductoServicio;
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
@RequestMapping("/producto")
public class ProductoControlador {

    private final ProductoServicio productoServicio;

    public ProductoControlador(ProductoServicio productoServicio) {
        this.productoServicio = productoServicio;
    }

    @GetMapping
    public ModelAndView listarProductos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("lista-productos");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("productos", productoServicio.listarProductos());
        return mav;
    }

    @GetMapping("/formulario")
    public ModelAndView formularioCreacion(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("producto-formulario");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("producto", inputFlashMap.get("producto"));
            mav.addObject("excepcion", inputFlashMap.get("excepcion"));
        } else {
            mav.addObject("producto", new Producto());
        }

        mav.addObject("accion", "crear");
        return mav;
    }

    @GetMapping("/formulario/{id}")
    public ModelAndView traerProductoPorId(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("producto-formulario");
        mav.addObject("producto", productoServicio.obtenerProductoPorId(id));
        mav.addObject("accion", "actualizar");
        return mav;
    }


    @PostMapping("/crear")
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

    @PostMapping("/actualizar")
    public RedirectView actualizar(Producto producto, RedirectAttributes attributes) {
        RedirectView redirect = new RedirectView("/producto");

        try {
           productoServicio.actualizarProducto(producto);
            attributes.addFlashAttribute("exito", "La operación fue realizada con éxito.");
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("producto", producto);
            attributes.addFlashAttribute("excepcion", e.getMessage());
            redirect.setUrl("/producto/formulario");
        }
        return redirect;

    }

    @PostMapping("/borrar/{id}")
    public RedirectView borrar(@PathVariable Long id) {
        RedirectView redirect = new RedirectView("/producto");
        productoServicio.borrarProductoPorId(id);
        return redirect;
    }/*Agregar un mensaje de alerta para que no borre directamente */

}
