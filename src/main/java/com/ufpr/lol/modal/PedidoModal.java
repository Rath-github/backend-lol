package com.ufpr.lol.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class PedidoModal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private double valor;
    private Date dataPedido;
    private String status; // Pode ser um enum para representar o status do pedido

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteModal cliente;

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private FuncionarioModal funcionarioResponsavel;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedidoModal> itens;



}
