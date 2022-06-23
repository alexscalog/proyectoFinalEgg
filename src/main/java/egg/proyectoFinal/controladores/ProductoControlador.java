package egg.proyectoFinal.controladores;


import egg.proyectoFinal.entidades.Producto;
import egg.proyectoFinal.servicios.EmprendimientoServicio;
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
    private final EmprendimientoServicio emprendimientoServicio;

    public ProductoControlador(ProductoServicio productoServicio, EmprendimientoServicio emprendimientoServicio) {
        this.productoServicio = productoServicio;
        this.emprendimientoServicio = emprendimientoServicio;
    }



    @GetMapping("/detalle/{id}")
    public ModelAndView productoDetalle(HttpServletRequest request, @PathVariable Long id) {
        ModelAndView mav = new ModelAndView("producto-detalle");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("producto", productoServicio.obtenerProductoPorId(id));
        return mav;
    }



    @GetMapping("/listar-productos")
    public ModelAndView listarProductos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("lista-productos");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("productos", productoServicio.listarProductos());
        return mav;
    }


    @GetMapping("/productos-por-emprendimiento/{id}")
    public ModelAndView listarProductosPorEmprendimiento(HttpServletRequest request, @PathVariable Long id) {
        ModelAndView mav = new ModelAndView("lista-productos");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));
        mav.addObject("emprendimientoId", id);
        mav.addObject("productos", productoServicio.productosPorEmprendimiento(id));
        return mav;
    }

    @GetMapping("/productos-por-categoria")
    public ModelAndView listarProductosPorCategoria(HttpServletRequest request, @RequestParam String categoria) {
        ModelAndView mav = new ModelAndView("lista-productos");
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) mav.addObject("exito", inputFlashMap.get("exito"));

        mav.addObject("productos", productoServicio.productosPorCategoria(categoria));
        mav.addObject("categoria", categoria);
        return mav;
    }


    @GetMapping("/formulario/{emprendimientoId}")
    //@PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ModelAndView formularioCreacion(HttpServletRequest request, HttpSession session, @PathVariable Long emprendimientoId) {
        ModelAndView mav = new ModelAndView("producto-formulario");

        Producto producto = new Producto();


        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap != null) {
            mav.addObject("producto", inputFlashMap.get("producto"));
            mav.addObject("excepcion", inputFlashMap.get("excepcion"));
        } else {
            mav.addObject("producto", new Producto());
        }
        mav.addObject("emprendimientoId", emprendimientoId);
        mav.addObject("accion", "crear");
        return mav;
    }



    @GetMapping("/editar/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN, USER')")
    public ModelAndView traerProductoPorId(@PathVariable Long id, HttpSession session) {
        ModelAndView mav = new ModelAndView("producto-formulario");



        mav.addObject("producto", productoServicio.obtenerProductoPorId(id));
        mav.addObject("accion", "actualizar");
        return mav;
    }






    @PostMapping("/crear")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public RedirectView crear(Producto producto, RedirectAttributes attributes, @RequestParam(required = false) MultipartFile imagenProducto) {
        RedirectView redirect = new RedirectView("/producto/listar-productos");

        try {
            productoServicio.crearProducto(producto, imagenProducto);
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
    public RedirectView actualizar(Producto producto, RedirectAttributes attributes, @RequestParam(required = false) MultipartFile imagenProducto) {
        RedirectView redirect = new RedirectView("/producto");

        try {
            productoServicio.actualizarProducto(producto, imagenProducto);
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
