package br.com.fiap.cashflowpro.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.cashflowpro.model.Categoria;
import br.com.fiap.cashflowpro.repository.CategoriaRepository;
import lombok.var;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/categoria")
@Slf4j
public class CategoriaController {

    Logger log = LoggerFactory.getLogger(getClass());
     
    @Autowired
    CategoriaRepository repository;

    @GetMapping
    public List<Categoria> index() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(OK)
    public Categoria create(@RequestBody Categoria categoria) {
        log.info("cadastrando categoria: {}", categoria);
        return repository.save(categoria);
    }

    @GetMapping("{id}")
    public ResponseEntity<Categoria> get(@PathVariable Long id) {
        log.info("buscando categoria com id {}", id);
        return repository
                .findById(id)
                .map(ResponseEntity::ok)//reference method
                .orElse(ResponseEntity.notFound().build());
    }

        // stream
        // var categoria = repository.findById(id);

        // log.info("categoria encontrada: {}", categoria);

        // if (categoria.isEmpty()) {
        //     return ResponseEntity.notFound().build();
        // }
    

    @DeleteMapping(path = "{id}")
    @ResponseStatus(NO_CONTENT)
    public void destroy(@PathVariable Long id) {
        log.info("apagando categoria{}",id);
        extracted(id);
        repository.deleteById(id);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Object> update(
        @PathVariable Long id, 
        @RequestBody Categoria categoria ) {       
            log.info("atualizando categoria com id: {} para: {}", id,categoria);
            extracted(id);
            categoria.setId(id);
            repository.save(categoria);

            return ResponseEntity.ok(categoria);
        
    }

    private void extracted(Long id) {
        repository.findById(id)
        .orElseThrow(()-> new ResponseStatusException(NOT_FOUND, 
        "id categoria não encontrada"));
    }

    // private Optional<Categoria> getCategoriaById(Long id) {
    //     var encontrada = repository.stream().filter(c -> c.id().equals(id)).findFirst();
    //     return encontrada;
    // }
}
