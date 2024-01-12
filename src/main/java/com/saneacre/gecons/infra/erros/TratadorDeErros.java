package com.saneacre.gecons.infra.erros;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.Arrays;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ErroPadrao> trataErroLoginSenhaErrado(HttpServletRequest request) {
        ErroPadrao err = this.montaErroPadrao(HttpStatus.BAD_REQUEST.value(), "Usuário e/ou senha incorretos", request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroPadrao> trataErroIntegridade(DataIntegrityViolationException e, HttpServletRequest request) {
        ErroPadrao err = this.montaErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMostSpecificCause().getMessage(), request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroPadrao> trataErroParse(HttpMessageNotReadableException e, HttpServletRequest request) {
        ErroPadrao err = this.montaErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMostSpecificCause().getMessage(), request);

        if (e.getMostSpecificCause() instanceof DateTimeException) err.setMessage("Data inválida!");
        if (e.getMostSpecificCause() instanceof InvalidFormatException) {

            StringBuilder valoresEnum = new StringBuilder();
            if (((InvalidFormatException) e.getMostSpecificCause()).getTargetType().isEnum()) {
                var enumValues = ((InvalidFormatException) e.getMostSpecificCause()).getTargetType().getEnumConstants();
                for (Object enumValue : enumValues) {
                    valoresEnum.append(" ");
                    valoresEnum.append(enumValue);
                }
            }
            err.setMessage("Valor inválido para o campo! Valores aceitos:" + valoresEnum);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroPadrao> trataErro404(EntityNotFoundException e, HttpServletRequest request) {
        ErroPadrao err = this.montaErroPadrao(HttpStatus.NOT_FOUND.value(), e.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(UsuarioJaAdminException.class)
    public ResponseEntity<ErroPadrao> trataErroUsuarioJaAdmin(HttpServletRequest request) {
        ErroPadrao err = this.montaErroPadrao(HttpStatus.NOT_ACCEPTABLE.value(),
                                         "Usuario do tipo ADMIN não precisa receber acessos especiais",
                                              request);
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
