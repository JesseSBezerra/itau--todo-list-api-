package br.com.jdsb.todolistapi.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CredenciaisDTO {
    private String username;
    private String password;
}
