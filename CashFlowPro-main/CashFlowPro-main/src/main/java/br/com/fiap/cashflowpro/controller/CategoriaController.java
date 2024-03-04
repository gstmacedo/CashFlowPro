package br.com.fiap.cashflowpro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoriaController {
    
    Logger log = LoggerFactory.getLogger(getClass());
    List<Categoria> repository = new ArrayList(); 
    @RequestMapping(method = RequestMethod.GET, path = "/categoria")
    @ResponseBody
    public String index(){
        return repository;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/categoria")
    @ResponseBody
    public ResponseEntity<Categoria> create(@RequestBody Categoria categoria){
       log.info("cadastrando categoria: {}," categoria);
       repository.add(categoria);
       return ResponseEntity.status(201).body(categoria);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/categoria/{id}")
    @ResponseBody
    public ResponseEntity<Categoria> get(@PathVariable Long id){
        log.info("buscando categoria com id {}", id);

        //stream
       var categoria = repository.stream().filter(c -> c.id().equals(id)).findFirst();

       log.info("categoria encontrada: {}", categoria);

       if(categoria.empty()){
        return ResponseEntity.status(404).build();
       }
       return ResponseEntity.status(200).body(categoria.get());
    }
}
