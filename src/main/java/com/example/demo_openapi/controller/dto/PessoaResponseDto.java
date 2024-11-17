package com.example.demo_openapi.controller.dto;

import com.example.demo_openapi.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class PessoaResponseDto {

    private Integer id;
    private String nome;
    private String telefone;
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    public PessoaResponseDto(Pessoa pessoa) {
        this.id = pessoa.getId();
        this.nome = pessoa.getNome();
        this.telefone = pessoa.getTelefone();
        this.cpf = pessoa.getCpf();
        this.dataCriacao = pessoa.getDataCriacao();
        this.dataAtualizacao = pessoa.getDataAtualizacao();
    }
}