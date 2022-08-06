package br.edu.uniara.fec.controller;

import br.edu.uniara.fec.entity.response.IndexResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

  @GetMapping("/")
  public IndexResponse index() {
    return IndexResponse.builder()
            .message("Welcome to Fec Uniara API 1.0")
            .build();
  }
}
