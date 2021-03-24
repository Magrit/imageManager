document.addEventListener("DOMContentLoaded", function () {

    function openLightbox(element) {
        element.style.display = "block";
    }

    function closeLightbox(element) {
        element.style.display = "none";
    }

    const cardImg = document.querySelectorAll(".miniature");
    cardImg.forEach(el => el.addEventListener('click', evt => {
        const lightbox = document.querySelector(".lightBox");
        openLightbox(lightbox);
    }));
    cardImg.forEach(el => console.log(el));

    const lightboxClose = document.querySelectorAll(".lightBox .close");
    lightboxClose.forEach(el => el.addEventListener('click', evt => {
        closeLightbox(el.parentElement);
    }));

});