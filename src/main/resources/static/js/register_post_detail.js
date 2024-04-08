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

// 뒤로 가기 버튼
const goBackButton = document.querySelector(".msg-go-back");
const postId = document.getElementById("post-id").value;

if (goBackButton) {
    goBackButton.addEventListener('click', () =>
        location.replace(`/page/post/` +postId + `/apply-list`)
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

// 파일 다운로드
document.addEventListener("DOMContentLoaded", function () {
    const downloadButton = document.querySelector(".btn-download");

    if (downloadButton) {
        downloadButton.addEventListener("click", function () {
            const fileUrl = document.querySelector(".file-url").textContent;
            if (fileUrl) {
                window.location.href = fileUrl; // 파일 URL로 이동하여 다운로드
            } else {
                console.error("File URL is not defined.");
            }
        });
    }
});

document.querySelector(".logo").addEventListener('click', () =>
    window.location.href = '/page/main'
);


// ---------------- 수락 버튼 눌렀을 때
$('.btn.btn-register-accept').click(function() {
    // 버튼의 data-apply-id 속성을 통해 신청 ID 가져오기
    var applyId = $('.register-post-header-box').attr('data-apply-id');

    // AJAX 요청 보내기
    $.ajax({
        url: '/api/apply/' + applyId + '/accept',
        type: 'POST',
        success: function(response) {
            // 성공적으로 요청을 보내고 응답을 받았을 때의 처리
            console.log(response); // 응답을 로그에 출력하거나 필요한 다른 작업 수행

            // 팀원 추가 성공 시 모달 열기
            $('.modal').css('display', 'block');

            // "닫기" 버튼 클릭 시 모달 숨기기
            $('.modal-close-img').click(function() {
                $('.modal').css('display', 'none');
            });
        },
        error: function(xhr, status, error) {
            // 요청이 실패하거나 에러 응답을 받았을 때의 처리
            console.error(xhr.responseText); // 에러 메시지를 콘솔에 출력하거나 다른 에러 처리 작업 수행
        }
    });
});



