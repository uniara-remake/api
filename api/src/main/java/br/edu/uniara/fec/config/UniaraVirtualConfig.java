package br.edu.uniara.fec.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "uniara.virtual")
public class UniaraVirtualConfig {

  /**
   * URL to resources of legacy uniara virtual.
   */
  private String url;
}
