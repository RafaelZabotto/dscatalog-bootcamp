package com.rafaelzabotto.dscatalog.services;

import com.rafaelzabotto.dscatalog.dto.ClientDTO;
import com.rafaelzabotto.dscatalog.entities.Client;
import com.rafaelzabotto.dscatalog.repositories.ClientRepository;
import com.rafaelzabotto.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public ClientDTO findById(Long id) {
        Optional<Client> obj = clientRepository.findById(id);
        Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ClientDTO(entity);
    }

    //Insert
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        dtoToEntity(entity, dto);
        entity = clientRepository.save(entity);
        return new ClientDTO(entity);
    }

    //Update

    //Delete

    //Aux methods

    public void dtoToEntity(Client entity, ClientDTO dto){
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }


}
