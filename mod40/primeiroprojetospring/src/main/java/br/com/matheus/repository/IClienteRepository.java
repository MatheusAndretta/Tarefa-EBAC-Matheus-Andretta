package br.com.matheus.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.matheus.domain.Cliente;

@Repository
public interface IClienteRepository extends CrudRepository<Cliente,Long>{

}
