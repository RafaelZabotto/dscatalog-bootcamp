package com.rafaelzabotto.dscatalog.repositories;

import com.rafaelzabotto.dscatalog.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {

}
