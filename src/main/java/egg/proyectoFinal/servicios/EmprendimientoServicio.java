package egg.proyectoFinal.servicios;

import egg.proyectoFinal.entidades.Emprendimiento;
import egg.proyectoFinal.entidades.Producto;
import egg.proyectoFinal.repositorios.EmprendimientoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Service
public class EmprendimientoServicio {

    private final EmprendimientoRepositorio emprendimientoRepositorio;
    private final ImagenServicio imagenServicio;

    public EmprendimientoServicio(EmprendimientoRepositorio emprendimientoRepositorio, ImagenServicio imagenServicio) {
        this.emprendimientoRepositorio = emprendimientoRepositorio;
        this.imagenServicio = imagenServicio;
    }

    @Transactional
    public void crearEmprendimiento(Emprendimiento emprendimiento, MultipartFile logoEmprendimiento) {

        Emprendimiento emprendimiento1 = new Emprendimiento();

        emprendimiento1.setNombreEmprendimiento(emprendimiento.getNombreEmprendimiento());
        emprendimiento1.setDescripcionEmprendimiento(emprendimiento.getDescripcionEmprendimiento());
        emprendimiento1.setLocalidad(emprendimiento.getLocalidad());
        emprendimiento1.setDireccion(emprendimiento.getDireccion());
        emprendimiento1.setListaProducto(emprendimiento.getListaProducto());
        emprendimiento1.setTelefonoEmprendimiento(emprendimiento.getTelefonoEmprendimiento());
        emprendimiento1.setInstagram(emprendimiento.getInstagram());
        if (!logoEmprendimiento.isEmpty()) emprendimiento1.setLogo(imagenServicio.copiar(logoEmprendimiento));

        emprendimientoRepositorio.save(emprendimiento1);
    }

    @Transactional
    public void actualizarEmprendimiento(Emprendimiento emprendimiento, MultipartFile logoEmprendimiento) {
        Emprendimiento emprendimiento1 = emprendimientoRepositorio.findById(emprendimiento.getIdEmprendimiento()).get();

        emprendimiento1.setNombreEmprendimiento(emprendimiento.getNombreEmprendimiento());
        emprendimiento1.setDescripcionEmprendimiento(emprendimiento.getDescripcionEmprendimiento());
        emprendimiento1.setLocalidad(emprendimiento.getLocalidad());
        emprendimiento1.setDireccion(emprendimiento.getDireccion());
        emprendimiento1.setListaProducto(emprendimiento.getListaProducto());
        emprendimiento1.setTelefonoEmprendimiento(emprendimiento.getTelefonoEmprendimiento());
        emprendimiento1.setInstagram(emprendimiento.getInstagram());
        if (!logoEmprendimiento.isEmpty()) emprendimiento1.setLogo(imagenServicio.copiar(logoEmprendimiento));


        emprendimientoRepositorio.save(emprendimiento1);
    }

    @Transactional(readOnly = true)
    public Emprendimiento obtenerEmprendimientoPorId(Long idEmprendimiento) {
        return emprendimientoRepositorio.findById(idEmprendimiento).get();
    }

    @Transactional(readOnly = true)
    public List<Emprendimiento> listarEmprendimientos() {
        return emprendimientoRepositorio.findAll();
    }

    @Transactional
    public void borrarEmprendimientoPorId(Long idEmprendimiento) {
        emprendimientoRepositorio.deleteById(idEmprendimiento);
    }

    @Transactional
    public List<Producto> traerProductosPorEmprendimiento(Long id){
        return emprendimientoRepositorio.traerProductosPorEmprendimiento(id);
    }
}
