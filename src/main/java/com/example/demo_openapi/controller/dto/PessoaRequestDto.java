package com.example.demo_openapi.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class PessoaRequestDto {

    private String nome;

    private String telefone;

    private String cpf;
}