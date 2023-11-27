package com.ufpr.lol.modal;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "tb_ItemPedidoModal")
@Data

public class ItemPedidoModal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PedidoModal pedido;

    @ManyToOne
    private RoupaModal roupa;
}
