package com.ufpr.lol.controller;

import com.ufpr.lol.modal.ClienteModal;
import com.ufpr.lol.service.ClienteService;
import com.ufpr.lol.utils.SecureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clientes")
public class clienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/criar")
    public ResponseEntity<String> criarCliente(@RequestBody ClienteModal cliente) {
        clienteService.criarCliente(cliente);
        return ResponseEntity.ok("Cliente criado com sucesso!");
    }
        @GetMapping("/listar")
        public ResponseEntity<List<ClienteModal>> listarClientes() {
            List<ClienteModal> clientes = clienteService.listarClientes();
            return ResponseEntity.ok(clientes);
        }

        // Endpoint para buscar detalhes de um cliente pelo ID
        @GetMapping("listar/{Id}")
        public ResponseEntity<ClienteModal> buscarClientePorId(@PathVariable Long id) {
            ClienteModal cliente = clienteService.buscarClientePorId(id);
            return ResponseEntity.ok(cliente);
        }

        // Endpoint para editar um cliente
        @PutMapping("/editar/{id}")
        public ResponseEntity<String> editarCliente(@PathVariable Long id, @RequestBody ClienteModal cliente) {
            clienteService.editarCliente(id, cliente);
            return ResponseEntity.ok("Cliente editado com sucesso!");
        }

        // Endpoint para deletar um cliente pelo ID
        @DeleteMapping("/excluir/{Id}")
        public ResponseEntity<String> excluirCliente(@PathVariable Long id ) {
            clienteService.excluirCliente(id);
            return ResponseEntity.ok("Cliente excluído com sucesso!");
        }


}

