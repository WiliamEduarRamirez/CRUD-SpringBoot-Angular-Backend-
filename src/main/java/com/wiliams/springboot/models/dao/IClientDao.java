package com.wiliams.springboot.models.dao;

import com.wiliams.springboot.models.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface IClientDao extends CrudRepository<Client,Long> {

}
