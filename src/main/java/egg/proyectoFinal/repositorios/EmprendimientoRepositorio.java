package egg.proyectoFinal.repositorios;

import egg.proyectoFinal.entidades.Emprendimiento;
import egg.proyectoFinal.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmprendimientoRepositorio extends JpaRepository<Emprendimiento, Long> {

    List<Producto> findByIdEmprendimiento(Long idEmprendimiento);

}
