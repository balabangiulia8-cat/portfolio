package com.giuliabalaban.portfolio.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ContactRequest {

@NotBlank(message="Inserisci il nome.")
@Size(max=80,message="Il nome è troppo lungo.")
@Pattern(regexp="^[^\\r\\n]*$",message="Il nome contiene caratteri non validi.")
private String name;

@NotBlank(message="Inserisci l'email.")
@Email(message="Inserisci un indirizzo email valido.")
@Size(max=150,message="L'email è troppo lunga.")
@Pattern(regexp="^[^\\r\\n]*$",message="L'email contiene caratteri non validi.")
private String email;

@NotBlank(message="Inserisci un messaggio.")
@Size(min=5,max=2000,message="Il messaggio deve contenere tra 5 e 2000 caratteri.")
private String message;

private String website;

public String getName(){
return name;
}

public void setName(String name){
this.name=name;
}

public String getEmail(){
return email;
}

public void setEmail(String email){
this.email=email;
}

public String getMessage(){
return message;
}

public void setMessage(String message){
this.message=message;
}

public String getWebsite(){
return website;
}

public void setWebsite(String website){
this.website=website;
}

}