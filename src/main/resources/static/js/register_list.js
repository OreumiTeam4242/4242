document.querySelector(".mypage").addEventListener('click', function () {
    // 내 정보 페이지로 리다이렉트
    window.location.href = '/page/my_page';
});

document.getElementById('go-back').addEventListener('click', function () {
    // 내 정보 페이지로 리다이렉트
    window.location.href = '/page/main';
});

// ------------------로그아웃 버튼 클릭 시
// main.js
document.addEventListener("DOMContentLoaded", function() {
    var logoutButton = document.getElementById("logout");
    if (logoutButton) {
        logoutButton.addEventListener("click", function() {
            // 서버의 로그아웃 엔드포인트 호출
            fetch("/api/auth/logout", {
                method: "POST",
                credentials: "same-origin" // 쿠키를 함께 전송
            })
                .then(function(response) {
                    if (response.ok) {
                        // 로그아웃 성공 시 세션 및 쿠키 삭제 후 로그인 페이지로 리다이렉션
                        sessionStorage.clear(); // 세션 데이터 삭제
                        window.location.href = "/page/login"; // 로그인 페이지 URL로 이동
                    } else {
                        console.error("로그아웃 요청이 실패하였습니다.");
                    }
                })
                .catch(function(error) {
                    console.error("로그아웃 요청 중 오류가 발생하였습니다:", error);
                });
        });
    }
});