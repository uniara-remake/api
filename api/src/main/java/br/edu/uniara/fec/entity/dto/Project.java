package br.edu.uniara.fec.entity.dto;

import lombok.*;

/**
 * A FEC project registered by students.
 *
 * @author Leonardo Elias
 */

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
public class Project {

  String title;
  String description;

  byte[] picture;
}


