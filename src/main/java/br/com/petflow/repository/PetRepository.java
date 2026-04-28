package br.com.petflow.repository;

import br.com.petflow.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Long, Pet> {
    List<Pet> findByEspecie(String especie);
    List<Pet> findByTutorNomeContainingIgnoreCase(String tutorNome);
}
