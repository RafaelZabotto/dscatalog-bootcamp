package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.ClientDTO;
import com.devsuperior.dscatalog.entities.Client;
import com.devsuperior.dscatalog.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    //Dependency Injection
    @Autowired
    private ClientRepository clientRepository;

    //Find All
    public List<ClientDTO> findAll() {
        List<Client> list = clientRepository.findAll();
        return list.stream().map(ClientDTO :: new).collect(Collectors.toList());
    }

    //Find By Id

    //Insert

    //Update

    //Delete

}
