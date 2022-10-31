package br.com.jdsb.todolistapi.resource;

import br.com.jdsb.todolistapi.model.dto.CredenciaisDTO;
import br.com.jdsb.todolistapi.model.dto.TokenDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/user")
public interface UserResource {

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredenciaisDTO credenciaisDTO);

}
