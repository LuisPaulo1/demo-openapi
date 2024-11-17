package com.example.demo_openapi.service;

import com.example.demo_openapi.controller.dto.PessoaRequestDto;
import com.example.demo_openapi.controller.dto.PessoaResponseDto;
import com.example.demo_openapi.model.Pessoa;
import com.example.demo_openapi.repository.PessoaRepository;
import com.example.demo_openapi.service.exception.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public List<PessoaResponseDto> listar() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoas.stream().map(PessoaResponseDto::new).collect(Collectors.toList());
    }

    public PessoaResponseDto buscar(Integer id) {
        Pessoa pessoa = buscarPorId(id);
        return new PessoaResponseDto(pessoa);
    }

    public PessoaResponseDto salvar(PessoaRequestDto pessoaDto) {
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDto, pessoa);
        pessoa = pessoaRepository.save(pessoa);
        return new PessoaResponseDto(pessoa);
    }

    public PessoaResponseDto atualizar(Integer id, PessoaRequestDto pessoaDto) {
        Pessoa pessoa = buscarPorId(id);
        BeanUtils.copyProperties(pessoaDto, pessoa);
        pessoa = pessoaRepository.save(pessoa);
        return new PessoaResponseDto(pessoa);
    }

    public void deletar(Integer id) {
        try{
            pessoaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecursoNaoEncontradoException("Erro ao excluir a pessoa", e);
        }
    }

    public Pessoa buscarPorId(Integer id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Pessoa com id %d não encontrada", id)));
    }
}
