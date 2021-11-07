package com.wiliams.springboot.models.services;

import com.wiliams.springboot.models.entity.Client;

import java.util.List;

public interface IClientService {
    public List<Client> findAll();
}
