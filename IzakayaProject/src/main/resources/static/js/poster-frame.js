// ===============================
// POSTER VIEW OPEN
// ===============================
document.querySelectorAll('.poster').forEach(poster => {
    poster.addEventListener('click', (e) => {
        e.preventDefault();
        e.stopPropagation();

        const img = poster.querySelector('img');
        if (!img) return;

        const view = document.getElementById('posterView');
        const viewImg = document.getElementById('posterViewImg');
        const frame = view.querySelector('.poster-frame');

        // 이미지 교체
        viewImg.src = img.src;

        // 🔴 일본 인장 랜덤 설정
        const seals = ["季節", "限定", "奉納", "人気", "酒"];
        const sealText = seals[Math.floor(Math.random() * seals.length)];
        frame.setAttribute("data-seal-text", sealText);

        frame.style.setProperty(
            "--seal-top",
            `${12 + Math.random() * 40}px`
        );

        frame.style.setProperty(
            "--seal-right",
            `${12 + Math.random() * 40}px`
        );

        frame.style.setProperty(
            "--seal-rotate",
            `${-18 + Math.random() * 14}deg`
        );

        // 열기
        view.classList.add('active');

        // 🔒 배경 스크롤 방지
        document.body.style.overflow = 'hidden';
    });
});

// ===============================
// POSTER VIEW CLOSE
// ===============================
const closePoster = () => {
    const view = document.getElementById('posterView');
    const viewImg = document.getElementById('posterViewImg');
    const frame = view.querySelector('.poster-frame');

    view.classList.remove('active');
    viewImg.src = '';

    // 인장 초기화
    frame.removeAttribute("data-seal-text");
    frame.style.removeProperty("--seal-top");
    frame.style.removeProperty("--seal-right");
    frame.style.removeProperty("--seal-rotate");

    document.body.style.overflow = '';
};

// 배경 클릭 시 닫기
document.querySelector('.poster-backdrop').addEventListener('click', closePoster);

// ESC 키로 닫기
document.addEventListener('keydown', (e) => {
    if (e.key === 'Escape') {
        closePoster();
    }
});
