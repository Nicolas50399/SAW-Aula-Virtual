package frba.utn.edu.ar.aula_virtual.services;

import frba.utn.edu.ar.aula_virtual.daos.StringDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private StringDAO usuarioDao; // <-- DAO con EntityManager

    @Transactional
    public String getUserDetailsByName(String username) {
        return usuarioDao.findUserDetailsByUsername(username).orElse(null);
    }

}

