document.addEventListener("DOMContentLoaded", () => {

    /* ===============================
       1️⃣ MENU AUTO SLIDE (IMAGE + TITLE + DESC)
    =============================== */
    const menuPhoto = document.querySelector(".menu-photo");
    const menuName  = document.querySelector(".menu-name");
    const menuDescs = document.querySelectorAll(".menu-desc");

    const menus = [
        {
            img: "/images/food1.png",
            name: "焼き鳥盛り合わせ",
            desc: [
                "炭火で香ばしく焼き上げた",
                "職人自慢の一品",
                "火を入れることで肉のコクと",
                "旨味がじんわり広がる"
            ]
        },
        {
            img: "/images/food2.png",
            name: "ミルフィーユ鍋",
            desc: [
                "丁寧に重ねた白菜と肉の層",
                "見た目も美しい冬の定番",
                "煮込むほどに素材の旨味が",
                "優しく溶け合う一品"
            ]
        }
    ];

    let current = 0;

    function changeMenu() {
        current = (current + 1) % menus.length;

        menuPhoto.src = menus[current].img;
        menuName.textContent = menus[current].name;

        menuDescs.forEach((el, idx) => {
            el.innerHTML = menus[current].desc[idx] || "";
        });
    }

    if (menuPhoto && menuName && menuDescs.length) {
        setInterval(changeMenu, 3500);
    }

    /* ===============================
       2️⃣ SLOGAN BRUSH TYPING
    =============================== */
    const sloganLines = document.querySelectorAll(".slogan-line");
    let lineIndex = 0;

    function typeLine() {
        if (lineIndex >= sloganLines.length) return;

        const chars = Array.from(
            sloganLines[lineIndex].querySelectorAll("span")
        );

        chars.forEach(span => span.style.opacity = "0");

        let charIndex = 0;

        function typeChar() {
            if (charIndex >= chars.length) {
                lineIndex++;
                setTimeout(typeLine, 700);
                return;
            }

            const char = chars[charIndex];
            char.style.opacity = "1";
            char.style.transform = "translateY(-2px)";
            char.style.transition = "opacity 0.3s ease, transform 0.3s ease";

            charIndex++;
            setTimeout(typeChar, 120);
        }

        typeChar();
    }

    setTimeout(typeLine, 600);

	/* ===============================
	   SIGNBOARD CLICK
	   → DOOR APPEAR → DOOR OPEN
	   + SHOJI LIGHT REACTION
	=============================== */

	const signboard = document.querySelector(".signboard");
	const chochin   = document.querySelector(".chochin");

	let isTransitioning = false;

	if (signboard) {
	    signboard.addEventListener("click", () => {

	        // 🔒 중복 클릭 방지
	        if (isTransitioning) return;
	        isTransitioning = true;

	        // 🔄 이전 상태 초기화 (안전)
	        document.body.classList.remove(
	            "door-active",
	            "open-door",
	            "door-open"        // 👈 쇼지 반응용
	        );

	        // 🔥 초친 불빛 약화
	        if (chochin) {
	            chochin.classList.add("dim");
	        }

	        // 1️⃣ 닫힌 문 등장
	        document.body.classList.add("door-active");

	        // 2️⃣ 살짝 텀 후 문 열림 + 쇼지 불빛 반응
	        setTimeout(() => {
	            document.body.classList.add("open-door");
	            document.body.classList.add("door-open"); // 👈 쇼지 창 밝아짐
	        }, 120);

	        // 3️⃣ 화면 전환 (문 애니메이션 끝과 정확히 맞춤)
	        setTimeout(() => {
	            window.location.href = "/";
	        }, 1600);
	    });
	}

});
