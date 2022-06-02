package egg.proyectoFinal.servicios;

import egg.proyectoFinal.entidades.Emprendimiento;
import egg.proyectoFinal.repositorios.EmprendimientoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmprendimientoServicio {

    private EmprendimientoRepositorio emprendimientoRepositorio;

    @Transactional
    public void crearEmprendimiento(Emprendimiento emprendimiento) {

        Emprendimiento emprendimiento1 = new Emprendimiento();

        emprendimiento1.setNombreEmprendimiento(emprendimiento.getNombreEmprendimiento());
        emprendimiento1.setDescripcionEmprendimiento(emprendimiento.getDescripcionEmprendimiento());
        emprendimiento1.setLogo(emprendimiento.getLogo());
        emprendimiento1.setLocalidad(emprendimiento.getLocalidad());
        emprendimiento1.setDireccion(emprendimiento.getDireccion());
        emprendimiento1.setListaProducto(emprendimiento.getListaProducto());
        emprendimiento1.setTelefonoEmprendimiento(emprendimiento.getTelefonoEmprendimiento());
        emprendimiento1.setInstagram(emprendimiento.getInstagram());


        emprendimientoRepositorio.save(emprendimiento);
    }

    @Transactional
    public void actualizarEmprendimiento(Emprendimiento emprendimiento) {
        Emprendimiento emprendimiento1 = emprendimientoRepositorio.findById(emprendimiento.getId()).get();

        emprendimiento1.setNombreEmprendimiento(emprendimiento.getNombreEmprendimiento());
        emprendimiento1.setDescripcionEmprendimiento(emprendimiento.getDescripcionEmprendimiento());
        emprendimiento1.setLogo(emprendimiento.getLogo());
        emprendimiento1.setLocalidad(emprendimiento.getLocalidad());
        emprendimiento1.setDireccion(emprendimiento.getDireccion());
        emprendimiento1.setListaProducto(emprendimiento.getListaProducto());
        emprendimiento1.setTelefonoEmprendimiento(emprendimiento.getTelefonoEmprendimiento());
        emprendimiento1.setInstagram(emprendimiento.getInstagram());


        emprendimientoRepositorio.save(emprendimiento);
    }

    @Transactional(readOnly = true)
    public Emprendimiento obtenerEmprendimientoPorId(Long id) {
        return emprendimientoRepositorio.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Emprendimiento> listarEmprendimientos() {
        return emprendimientoRepositorio.findAll();
    }

    @Transactional
    public void borrarEmprendimientoPorId(Long id) {
        emprendimientoRepositorio.deleteById(id);
    }
}