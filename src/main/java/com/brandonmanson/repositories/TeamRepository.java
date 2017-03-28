package com.brandonmanson.repositories;

import org.springframework.data.repository.CrudRepository;
import com.brandonmanson.models.Team;
import java.util.List;

/**
 * Created by brandonmanson on 3/27/17.
 */

public interface TeamRepository extends CrudRepository<Team, Long> {
    List<Team> findByTeamId(String teamId);
}
