package com.rafaelzabotto.dscatalog.services;

import com.rafaelzabotto.dscatalog.dto.ClientDTO;
import com.rafaelzabotto.dscatalog.entities.Client;
import com.rafaelzabotto.dscatalog.repositories.ClientRepository;
import com.rafaelzabotto.dscatalog.services.exceptions.DatabaseException;
import com.rafaelzabotto.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {

    //Dependency Injection
    @Autowired
    private ClientRepository clientRepository;

    //Find All
    @Transactional(readOnly = true)
    public List<ClientDTO> findAll() {
        List<Client> list = clientRepository.findAll();
        return list.stream().map(ClientDTO :: new).collect(Collectors.toList());
    }

    //Find By Id
    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> obj = clientRepository.findById(id);
        Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ClientDTO(entity);
    }

    //Insert
    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client entity = new Client();
        dtoToEntity(entity, dto);
        entity = clientRepository.save(entity);
        return new ClientDTO(entity);
    }

    //Update
    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = clientRepository.getById(id);
            dtoToEntity(entity, dto);
            entity = clientRepository.save(entity);
            return new ClientDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    //Delete

    public void delete(Long id) {
        try {
            clientRepository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(("Id not Found " + id));

        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");

        }
    }

    //Aux methods

    public void dtoToEntity(Client entity, ClientDTO dto){
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }

}
