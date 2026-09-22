package com.giuliabalaban.portfolio.controller;

import com.giuliabalaban.portfolio.model.ContactRequest;
import com.giuliabalaban.portfolio.service.ContactRateLimiter;
import com.giuliabalaban.portfolio.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

private final EmailService emailService;
private final ContactRateLimiter rateLimiter;

public ContactController(
EmailService emailService,
ContactRateLimiter rateLimiter){

this.emailService=emailService;
this.rateLimiter=rateLimiter;
}

@PostMapping("/contact")
public String sendMessage(
@Valid ContactRequest contactRequest,
BindingResult bindingResult,
RedirectAttributes redirectAttributes,
HttpServletRequest request){

/* =========================================================
   01. HONEYPOT
========================================================= */
if(contactRequest.getWebsite()!=null&&!contactRequest.getWebsite().isBlank()){
return "redirect:/#contact";
}

/* =========================================================
   02. RATE LIMIT
========================================================= */
String ip=request.getRemoteAddr();

if(!rateLimiter.isAllowed(ip)){
redirectAttributes.addFlashAttribute(
"errorMessage",
"Hai effettuato troppi tentativi. Riprova tra qualche minuto."
);

return "redirect:/#contact";
}

/* =========================================================
   03. VALIDAZIONE DATI
========================================================= */
if(bindingResult.hasErrors()){

redirectAttributes.addFlashAttribute(
"errorMessage",
"Controlla i dati inseriti e riprova."
);

redirectAttributes.addFlashAttribute(
"formName",
contactRequest.getName()
);

redirectAttributes.addFlashAttribute(
"formEmail",
contactRequest.getEmail()
);

redirectAttributes.addFlashAttribute(
"formMessage",
contactRequest.getMessage()
);

return "redirect:/#contact";
}

/* =========================================================
   04. INVIO EMAIL
========================================================= */
try{

emailService.sendContactEmail(
contactRequest.getName(),
contactRequest.getEmail(),
contactRequest.getMessage()
);

redirectAttributes.addFlashAttribute(
"successMessage",
"Messaggio inviato con successo!"
);

}catch(MailException e){

redirectAttributes.addFlashAttribute(
"errorMessage",
"Invio non riuscito. Riprova tra poco oppure contattami direttamente via email."
);

redirectAttributes.addFlashAttribute(
"formName",
contactRequest.getName()
);

redirectAttributes.addFlashAttribute(
"formEmail",
contactRequest.getEmail()
);

redirectAttributes.addFlashAttribute(
"formMessage",
contactRequest.getMessage()
);

}

return "redirect:/#contact";
}

}