package br.edu.univas.api.plano.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    
    private Long cpf;
    private Long cpfTitular;
    private String nome;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataNascimento;

}
