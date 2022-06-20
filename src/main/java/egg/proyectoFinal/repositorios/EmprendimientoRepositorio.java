package egg.proyectoFinal.repositorios;

import egg.proyectoFinal.entidades.Emprendimiento;
import egg.proyectoFinal.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmprendimientoRepositorio extends JpaRepository<Emprendimiento, Long> {

    @Query(value = "SELECT * FROM producto p JOIN emprendimiento e ON p.nombre_emprendimiento=e.id_emprendimiento WHERE emprendimiento_usuario=?1", nativeQuery = true)
    List<Producto> traerProductosPorEmprendimiento(Long id);



}
