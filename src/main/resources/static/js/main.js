document.addEventListener("DOMContentLoaded",()=>{

/* =========================================================
   01. SCROLL NAVBAR
   Scorrimento fluido verso le sezioni della homepage
========================================================= */
const header=document.querySelector(".header");
document.querySelectorAll('a[href^="#"]').forEach(link=>{
link.addEventListener("click",event=>{
const targetId=link.getAttribute("href");
if(!targetId||targetId==="#")return;
const section=document.querySelector(targetId);
if(!section)return;
event.preventDefault();
if(targetId==="#home"){
window.scrollTo({top:0,behavior:"smooth"});
return;
}
const content=section.querySelector(".section-container")||section.querySelector(".hero-content")||section;
const headerHeight=header?header.offsetHeight:0;
const contentTop=content.getBoundingClientRect().top+window.scrollY;
const position=contentTop-headerHeight-45;
window.scrollTo({top:position,behavior:"smooth"});
});
});

/* =========================================================
   02. LUCE DI SFONDO
   Sposta l'effetto luminoso seguendo il mouse
========================================================= */
document.addEventListener("mousemove",event=>{
document.documentElement.style.setProperty("--mouse-x",`${event.clientX}px`);
document.documentElement.style.setProperty("--mouse-y",`${event.clientY}px`);
});

/* =========================================================
   03. EFFETTO LUCE SULLE CARD
   Segue il mouse all'interno delle card
========================================================= */
const interactiveCards=document.querySelectorAll(".skill-card,.project-card,.about-card");
interactiveCards.forEach(card=>{
card.addEventListener("mousemove",event=>{
const rect=card.getBoundingClientRect();
const x=event.clientX-rect.left;
const y=event.clientY-rect.top;
card.style.setProperty("--card-x",`${x}px`);
card.style.setProperty("--card-y",`${y}px`);
});
card.addEventListener("mouseleave",()=>{
card.style.removeProperty("--card-x");
card.style.removeProperty("--card-y");
});
});

/* =========================================================
   04. ANIMAZIONI ALLO SCROLL
   Fa comparire gli elementi quando entrano nello schermo
========================================================= */
const revealElements=document.querySelectorAll(".skill-card,.project-card,.certification-item,.timeline-item,.about-grid,.contact-container");
const observer=new IntersectionObserver(entries=>{
entries.forEach(entry=>{
if(entry.isIntersecting){
entry.target.classList.add("visible");
observer.unobserve(entry.target);
}
});
},{threshold:.12});
revealElements.forEach(element=>{
element.classList.add("reveal");
observer.observe(element);
});

/* =========================================================
   05. NAVBAR INTELLIGENTE
   Evidenzia la sezione attualmente visibile
========================================================= */
const sections=document.querySelectorAll("main section[id]");
const navLinks=document.querySelectorAll('.nav-links a[href^="#"]');
const sectionObserver=new IntersectionObserver(entries=>{
entries.forEach(entry=>{
if(entry.isIntersecting){
const id=entry.target.getAttribute("id");
navLinks.forEach(link=>{
link.classList.remove("active");
if(link.getAttribute("href")===`#${id}`){
link.classList.add("active");
}
});
}
});
},{rootMargin:"-35% 0px -55% 0px",threshold:0});
sections.forEach(section=>{
sectionObserver.observe(section);
});

/* =========================================================
   06. NAVBAR MOBILE
   Apre e chiude il menu hamburger solo su mobile
========================================================= */
const menuToggle=document.querySelector(".menu-toggle");
const navMenu=document.querySelector(".nav-links");
if(menuToggle&&navMenu){
menuToggle.addEventListener("click",()=>{
const isOpen=navMenu.classList.toggle("open");
menuToggle.classList.toggle("active",isOpen);
menuToggle.setAttribute("aria-expanded",isOpen);
});
navMenu.querySelectorAll("a").forEach(link=>{
link.addEventListener("click",()=>{
navMenu.classList.remove("open");
menuToggle.classList.remove("active");
menuToggle.setAttribute("aria-expanded","false");
});
});
}

});