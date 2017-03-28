package com.brandonmanson.services;

import com.brandonmanson.models.SlackRequest;
import com.brandonmanson.models.User;
import com.brandonmanson.repositories.UserRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Metamodel;
import java.util.List;

/**
 * Created by brandonmanson on 3/27/17.
 */
@Service
public class RetrieveUserService {

    @Autowired
    UserRepository userRepository;


    public List<User> getUserFromSlackRequest(SlackRequest request) {
        List<User> teams = userRepository.findByTeamId(request.getTeamId());
        return teams;
    }
}
