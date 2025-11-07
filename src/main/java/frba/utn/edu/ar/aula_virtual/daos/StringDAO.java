package frba.utn.edu.ar.aula_virtual.daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public class StringDAO {

    @PersistenceContext
    private EntityManager em;

    // Consulta nativa para la inyeccion SQL
    public Optional<String> findUserDetailsByUsername(String username) {

        String queryString = "select u.username from Usuario u where u.username = '" + username + "'";

        TypedQuery<String> query = (TypedQuery<String>) em.createNativeQuery(queryString, String.class);

        var resultados = query.getResultList();

        if (resultados.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(String.join(", ", resultados));
    }

}
