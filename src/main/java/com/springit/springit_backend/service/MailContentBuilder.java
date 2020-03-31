package com.springit.springit_backend.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
class MailContentBuilder {

  private final TemplateEngine templateEngine;
  String build(String message){
    Context context = new Context();
    context.setVariable("messge", message);
    return templateEngine.process("mailTemplate", context);
  }
}
