package br.com.petflow.service;

import br.com.petflow.model.Pet;
import br.com.petflow.repository.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public Pet criar(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> listarTodos() {
        return petRepository.findAll();
    }

    public Pet buscarPorId(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com id: " + id));
    }

    public Pet atualizar(Long id, Pet petAtualizado) {
        Pet petExistente = buscarPorId(id);
        petExistente.setNome(petAtualizado.getNome());
        petExistente.setEspecie(petAtualizado.getEspecie());
        petExistente.setRaca(petAtualizado.getRaca());
        petExistente.setIdade(petAtualizado.getIdade());
        petExistente.setPeso(petAtualizado.getPeso());
        petExistente.setTutorNome(petAtualizado.getTutorNome());
        petExistente.setTutorEmail(petAtualizado.getTutorEmail());
        return petRepository.save(petExistente);
    }

    public void deletar(Long id) {
        Pet pet = buscarPorId(id);
        petRepository.delete(pet);
    }
}
