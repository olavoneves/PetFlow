package br.com.petflow.repository;

import br.com.petflow.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Long, Pet> {

}
