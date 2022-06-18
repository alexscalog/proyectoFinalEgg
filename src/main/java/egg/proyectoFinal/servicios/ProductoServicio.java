package egg.proyectoFinal.servicios;

import egg.proyectoFinal.entidades.Producto;
import egg.proyectoFinal.repositorios.ProductoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductoServicio {

    private final ProductoRepositorio productoRepositorio;
    private final ImagenServicio imagenServicio;

    public ProductoServicio(ProductoRepositorio productoRepositorio, ImagenServicio imagenServicio) {
        this.productoRepositorio = productoRepositorio;
        this.imagenServicio = imagenServicio;
    }


    @Transactional
    public void crearProducto(Producto producto, MultipartFile imagen) {
        if (productoRepositorio.existsById(producto.getIdProducto()))
            throw new IllegalArgumentException("Error!");

        Producto producto1 = new Producto();

        producto1.setNombreProducto(producto.getNombreProducto());
        producto1.setDescripcionProducto(producto.getDescripcionProducto());
        producto1.setCategoria(producto.getCategoria());
        producto1.setImagen(producto.getImagen());
        producto1.setNombreEmprendimiento(producto.getNombreEmprendimiento());

        productoRepositorio.save(producto1);
    }

    @Transactional
    public void actualizarProducto(Producto producto, MultipartFile imagen) {
        Producto producto1 = productoRepositorio.findById(producto.getIdProducto()).get();

        producto1.setNombreProducto(producto.getNombreProducto());
        producto1.setDescripcionProducto(producto.getDescripcionProducto());
        producto1.setCategoria(producto.getCategoria());
        producto1.setImagen(producto.getImagen());
        producto1.setNombreEmprendimiento(producto.getNombreEmprendimiento());

        productoRepositorio.save(producto1);
    }

    @Transactional(readOnly = true)
    public Producto obtenerProductoPorId(Long idProducto) {
        return productoRepositorio.findById(idProducto).get();
    }

    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    @Transactional
    public void borrarProductoPorId(Long idProducto) {
        productoRepositorio.deleteById(idProducto);
    }



}
