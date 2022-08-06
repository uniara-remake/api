package br.edu.uniara.fec.entity.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IndexResponse {
  String message;
}
