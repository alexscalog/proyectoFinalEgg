package egg.proyectoFinal.repositorios;

import egg.proyectoFinal.entidades.Emprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprendimientoRepositorio extends JpaRepository<Emprendimiento, Long> {
}
