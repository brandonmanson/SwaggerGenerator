package com.brandonmanson.services;

import com.brandonmanson.models.SlackRequest;
import com.brandonmanson.models.Team;
import com.brandonmanson.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by brandonmanson on 3/27/17.
 */
@Service
public class RetrieveTeamService {
    @Autowired
    TeamRepository teamRepository;


    public List<Team> getUserFromSlackRequest(SlackRequest request) {
        List<Team> teams = teamRepository.findByTeamId(request.getTeamId());
        return teams;
    }
}
