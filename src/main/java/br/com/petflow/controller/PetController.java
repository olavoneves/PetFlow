package br.com.petflow.controller;

import br.com.petflow.model.Pet;
import br.com.petflow.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<Pet> criar(@Valid @RequestBody Pet pet) {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.criar(pet));
    }

    @GetMapping
    public ResponseEntity<List<Pet>> listarTodos() {
        return ResponseEntity.ok(petService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(petService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> atualizar(@PathVariable Long id, @Valid @RequestBody Pet pet) {
        return ResponseEntity.ok(petService.atualizar(id, pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        petService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
