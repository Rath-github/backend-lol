package com.ufpr.lol.modal;

import jakarta.persistence.*;

@Entity
public class ClienteModal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private String endereco;
    private String telefone;
}
