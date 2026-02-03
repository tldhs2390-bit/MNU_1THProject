document.addEventListener("DOMContentLoaded", () => {

    /* ===============================
       POSTER LIGHTBOX
       (조용한 확대)
    ============================== */

    const posters = document.querySelectorAll(".poster img");
    const posterView = document.getElementById("posterView");
    const posterViewImg = document.getElementById("posterViewImg");
    const posterBackdrop = document.querySelector(".poster-backdrop");

    if (!posters.length || !posterView) return;

    posters.forEach(img => {
        img.addEventListener("click", () => {
            posterViewImg.src = img.src;
            posterView.classList.add("active");
        });
    });

    posterBackdrop.addEventListener("click", () => {
        posterView.classList.remove("active");
        posterViewImg.src = "";
    });

});
