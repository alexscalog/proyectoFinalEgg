package egg.proyectoFinal.servicios;

import egg.proyectoFinal.entidades.Producto;
import egg.proyectoFinal.repositorios.ProductoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Service
public class ProductoServicio {

    private ProductoRepositorio productoRepositorio;
    private  ImagenServicio imagenServicio;
 /*
 @Transactional
    public void crearProducto(Producto producto) {

        Producto producto1 = new Producto();

        producto1.setNombreProducto(producto.getNombreProducto());
        producto1.setDescripcionProducto(producto.getDescripcionProducto());
        producto1.setCategoria(producto.getCategoria());
        producto1.setImagen(producto.getImagen());
        producto1.setNombreEmprendimiento(producto.getNombreEmprendimiento());

        productoRepositorio.save(producto);
    }

  */
 @Transactional
 public void crearProducto(Producto producto, MultipartFile imagen) {
     if (productoRepositorio.existsById(producto.getId()))
         throw new IllegalArgumentException("Error!");

     Producto producto1 = new Producto();

     producto1.setNombreProducto(producto.getNombreProducto());
     producto1.setDescripcionProducto(producto.getDescripcionProducto());
     producto1.setCategoria(producto.getCategoria());
     producto1.setImagen(producto.getImagen());
     producto1.setNombreEmprendimiento(producto.getNombreEmprendimiento());

     productoRepositorio.save(producto);
 }
    @Transactional
    public void actualizarProducto(Producto producto, MultipartFile imagen) {
        Producto producto1 = productoRepositorio.findById(producto.getId()).get();

        producto1.setNombreProducto(producto.getNombreProducto());
        producto1.setDescripcionProducto(producto.getDescripcionProducto());
        producto1.setCategoria(producto.getCategoria());
        producto1.setImagen(producto.getImagen());
        producto1.setNombreEmprendimiento(producto.getNombreEmprendimiento());

        productoRepositorio.save(producto);
    }

    @Transactional(readOnly = true)
    public Producto obtenerProductoPorId(Long id) {
        return productoRepositorio.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    @Transactional
    public void borrarProductoPorId(Long id) {
        productoRepositorio.deleteById(id);
    }

}
