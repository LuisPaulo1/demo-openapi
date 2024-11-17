package com.example.demo_openapi.controller;

import com.example.demo_openapi.controller.dto.PessoaRequestDto;
import com.example.demo_openapi.controller.dto.PessoaResponseDto;
import com.example.demo_openapi.service.PessoaService;
import com.example.demo_openapi.service.exception.RecursoNaoEncontradoException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoa", description = "API para gerenciamento de pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @Operation(summary = "Listar todas as pessoas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<PessoaResponseDto>> findAll() {
        return ResponseEntity.ok(pessoaService.listar());
    }

    @Operation(summary = "Buscar pessoa por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDto> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(pessoaService.buscar(id));
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Salvar nova pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso")
    })
    @PostMapping
    public ResponseEntity<PessoaResponseDto> save(@RequestBody PessoaRequestDto pessoaDto) {
        var pessoa = pessoaService.salvar(pessoaDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoa.getId()).toUri();
        return ResponseEntity.created(uri).body(pessoa);
    }

    @Operation(summary = "Atualizar pessoa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponseDto> update(@PathVariable Integer id, @RequestBody PessoaRequestDto pessoaDto) {
        try {
            return ResponseEntity.ok(pessoaService.atualizar(id, pessoaDto));
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Deletar pessoa por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pessoa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            pessoaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}