package com.example.demo_openapi.service.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RecursoNaoEncontradoException extends RuntimeException {
        public RecursoNaoEncontradoException(String mensagem) {
            super(mensagem);
        }

        public RecursoNaoEncontradoException(String mensagem, Throwable causa) {
            super(mensagem, causa);
        }
}