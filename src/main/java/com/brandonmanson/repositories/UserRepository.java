package com.brandonmanson.repositories;

import com.brandonmanson.models.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * Created by brandonmanson on 3/26/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByTeamId(String teamId);

}
