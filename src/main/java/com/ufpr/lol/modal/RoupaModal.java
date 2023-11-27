package com.ufpr.lol.modal;

import jakarta.persistence.*;
import lombok.Data;



@Entity(name = "tb_roupas")
@Data
public class RoupaModal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private double preco;
    private int prazoLavagem;

}