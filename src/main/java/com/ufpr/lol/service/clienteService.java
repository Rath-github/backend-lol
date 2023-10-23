package com.ufpr.lol.service;

import com.ufpr.lol.modal.ClienteModal;
import com.ufpr.lol.repository.clienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class clienteService {
    @Autowired
    private clienteRepository clienteRepository;

    public ClienteModal criarCliente(ClienteModal cliente) {
        return clienteRepository.save(cliente);
    }

    public ClienteModal buscarClientePorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
}
