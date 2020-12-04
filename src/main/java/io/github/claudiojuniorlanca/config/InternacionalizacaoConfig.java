package io.github.claudiojuniorlanca.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class InternacionalizacaoConfig {
    @Bean
    public MessageSource messageSource(){
        /*
        Método criado para ler o arquivo messages.properies que contêm as mensagens de erro/validação
        das classes entity. Tamém se define o charset e o Locale, caso tenha mensagem em outras línguas
        */
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("ISO-8859-1");
        messageSource.setDefaultLocale(Locale.getDefault());
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(){

        /*
        Método criado para converter as referências chaves do arquivo messages em mensagens
        para as classes que contem a interpolação de referencia nos atributos
         */

        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
