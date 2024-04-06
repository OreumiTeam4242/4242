const goToTopButton = document.querySelector(".btn-to-top");

window.addEventListener('scroll', () => {
    if (document.documentElement.scrollTop > 150 || document.body.scrollTop > 150) {
        goToTopButton.style.display = 'block';
    } else {
        goToTopButton.style.display = 'none';
    }
});

// 가장 위로 이동 버튼 클릭 시
goToTopButton.addEventListener('click', () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
});

// 로그아웃 버튼
document.addEventListener("DOMContentLoaded", function () {
    const logoutButton = document.querySelector(".header-nav-logout");
    if (logoutButton) {
        logoutButton.addEventListener("click", function () {
            // 서버의 로그아웃 엔드포인트 호출
            fetch("/api/auth/logout", {
                method: "POST",
                credentials: "same-origin" // 쿠키를 함께 전송
            })
                .then(function (response) {
                    if (response.ok) {
                        // 로그아웃 성공 시 세션 및 쿠키 삭제 후 로그인 페이지로 리다이렉션
                        sessionStorage.clear(); // 세션 데이터 삭제
                        window.location.href = "/page/login"; // 로그인 페이지 URL로 이동
                    } else {
                        console.error("로그아웃 요청이 실패하였습니다.");
                    }
                })
                .catch(function (error) {
                    console.error("로그아웃 요청 중 오류가 발생하였습니다:", error);
                });
        });
    }
});

// 거절 버튼 클릭 시 신청글 리스트 페이지로 이동
const refuseButton = document.querySelector(".btn-register-refuse");

if (refuseButton) {
    refuseButton.addEventListener('click', () =>
        location.replace("/page/register_list")
    );
}

// 뒤로 가기 버튼
const goBackButton = document.querySelector(".msg-go-back");

if (goBackButton) {
    goBackButton.addEventListener('click', () =>
        location.replace(`/page/register_list`)
    );
}

// 내 정보 버튼
const infoButton = document.querySelector('.header-nav-info');

if (infoButton) {
    infoButton.addEventListener('click', function () {
        // 내 정보 페이지로 리다이렉트
        window.location.href = '/page/my_page';
    });
}

// 수락 버튼 클릭 시 모달 열기
document.addEventListener("DOMContentLoaded", function () {
    const acceptButton = document.querySelector(".btn.btn-register-accept"); // 수락 버튼 선택

    if (acceptButton) {
        acceptButton.addEventListener("click", function () {
            const postId = document.querySelector(".register-post-header-box").getAttribute("th:value"); // 신청글 ID 가져오기

            // AJAX 요청을 통해 팀원 추가
            addTeamMember(postId);
        });
    }

    const modalCloseButton = document.querySelector(".modal-close-img");
    if (modalCloseButton) {
        modalCloseButton.addEventListener("click", function () {
            const modalAccept = document.querySelector(".modal");
            if (modalAccept) {
                modalAccept.style.display = "none"; // 모달 숨기기
            }
        });
    }

    // 팀원 추가 함수
    function addTeamMember(postId) {
        const url = `/api/apply/${postId}/accept`;

        // AJAX를 이용한 POST 요청
        fetch(url, {
            method: "POST",
            credentials: "same-origin", // 쿠키를 함께 전송
            headers: {
                "Content-Type": "application/json"
            }
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }
                throw new Error("팀원 추가 실패");
            })
            .then(data => {
                console.log("팀원 추가 완료:", data);

                // 모달 열기
                const modalAccept = document.querySelector(".modal");
                if (modalAccept) {
                    modalAccept.style.display = "block";
                }
            })
            .catch(error => {
                console.error("팀원 추가 실패:", error.message);
            });
    }
});

