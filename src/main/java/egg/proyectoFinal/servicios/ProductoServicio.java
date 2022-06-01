package egg.proyectoFinal.servicios;

import egg.proyectoFinal.entidades.Producto;
import egg.proyectoFinal.repositorios.ProductoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServicio {

    private ProductoRepositorio productoRepositorio;

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

    @Transactional
    public void actualizarProducto(Producto producto) {
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
    public List<Producto> listarProducto() {
        return productoRepositorio.findAll();
    }

    @Transactional
    public void borrarProductoPorId(Long id) {
        productoRepositorio.deleteById(id);
    }

}
