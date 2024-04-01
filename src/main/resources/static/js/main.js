
const $topBtn = document.querySelector(".btn-to-top");

// 버튼 클릭 시 맨 위로 이동
$topBtn.onclick = () => {
    window.scrollTo({top: 0, behavior: "smooth"});
}

document.addEventListener('DOMContentLoaded', function () {
    var boxTopContents = document.querySelectorAll('.box-top-content');

    // 각 요소에 클릭 이벤트 추가
    boxTopContents.forEach(function(boxTopContent) {
        boxTopContent.addEventListener('click', function() {
            // 클릭된 요소의 정보를 기반으로 REST API 호출
            window.location.href = '/page/recruitPostDetail';
        });
    });
});

// 내 정보 버튼 클릭 시
document.getElementById('my-info').addEventListener('click', function () {
    // REST API 호출
    fetch('/page/personal_page', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(function(response) {
            // 응답 처리
            if (response.ok) {
                // 성공 시 내 정보 페이지로 리다이렉트
                window.location.href = '/page/personal_page';
            } else {
                // 실패 시 에러 처리
                throw new Error('내 정보 불러오기에 실패했습니다.');
            }
        })
        .catch(function(error) {
            // 에러 처리
            console.error('Error:', error);
            alert(error.message);
        });
});

// 로그아웃 버튼 클릭 시
document.getElementById('logout').addEventListener('click', function () {
    // REST API 호출
    fetch('/page/login', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(function(response) {
            // 응답 처리
            if (response.ok) {
                // 성공 시 로그인 페이지로 리다이렉트
                window.location.href = '/page/login';
                // todo 로그아웃되는 기능도 넣어줘야됨 나중에!!
            } else {
                // 실패 시 에러 처리
                throw new Error('내 정보 불러오기에 실패했습니다.');
            }
        })
        .catch(function(error) {
            // 에러 처리
            console.error('Error:', error);
            alert(error.message);
        });
});
