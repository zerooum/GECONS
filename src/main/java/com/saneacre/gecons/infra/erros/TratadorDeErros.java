package com.saneacre.gecons.infra.erros;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroPadrao> trataErro404(EntityNotFoundException e, HttpServletRequest request) {
        ErroPadrao err = this.montaErroPadrao(HttpStatus.NOT_FOUND.value(), e.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(UsuarioJaAdminException.class)
    public ResponseEntity<ErroPadrao> trataErroUsuarioJaAdmin(HttpServletRequest request) {
        ErroPadrao err = this.montaErroPadrao(HttpStatus.NOT_ACCEPTABLE.value(), "Usuario do tipo ADMIN não precisa receber acessos especiais", request);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity trataErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest()
                .body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

    public ErroPadrao montaErroPadrao(Integer status, String erro, HttpServletRequest request) {
        return new ErroPadrao(Instant.now(),
                status,
                erro,
                request.getRequestURI());
    }
}
