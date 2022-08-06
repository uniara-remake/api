package br.edu.uniara.fec.controller;

import br.edu.uniara.fec.annotation.isAuthenticated;
import br.edu.uniara.fec.entity.dto.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@isAuthenticated
@RestController
public class ProjectController {

  @GetMapping("/project")
  public List<Project> list() {
    return new ArrayList<>();
  }
}
