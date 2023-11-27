package com.ufpr.lol.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "tb_clientes")
@Data
public class ClienteModal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 14) // CPF tem no máximo 14 caracteres e é obrigatório e único
    private String cpf;

    @Column(nullable = false, length = 100) // Nome é obrigatório e tem no máximo 100 caracteres
    private String nome;

    @Column(nullable = false, unique = true, length = 100) // Email é obrigatório, único e tem no máximo 100 caracteres
    private String email;

    @Column(nullable = false) // Endereço é obrigatório
    private String endereco;

    @Column(nullable = false, length = 20) // Telefone é obrigatório e tem no máximo 20 caracteres
    private String telefone;

    @Column(nullable = false) // Senha é obrigatória
    private String senha;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore // Ignora a serialização deste lado do relacionamento
    private List<PedidoModal> pedidos;
}
