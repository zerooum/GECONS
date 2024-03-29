package com.saneacre.gecons.infra.erros;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErroPadrao implements Serializable {
    private Instant timestamp;
    private Integer status;
    private String message;
    private String path;
}
