package com.techchallenge.config;

//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
//Essa classe visa corrigir um erro gerado pelo controle de acessos do navegador, na distribuição de recursos computacionais para fontes diversas.
//Solução apresentada para ambiente de desenvolvimento. Desabilitando/Permitindo acesso pleno.
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // aplica a todos os endpoints
                .allowedOrigins("*") // ou use "http://127.0.0.1:5500" para restringir
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
