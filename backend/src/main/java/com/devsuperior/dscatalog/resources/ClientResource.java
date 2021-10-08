package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.dto.ClientDTO;
import com.devsuperior.dscatalog.entities.Client;
import com.devsuperior.dscatalog.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAllClients() {
        List<ClientDTO> list = clientService.findAll();
        return ResponseEntity.ok().body(list);
    }
}
