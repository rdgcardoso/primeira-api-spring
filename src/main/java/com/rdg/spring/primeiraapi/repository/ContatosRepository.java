package com.rdg.spring.primeiraapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rdg.spring.primeiraapi.model.Contato;

public interface ContatosRepository extends JpaRepository<Contato, Long>{

}
