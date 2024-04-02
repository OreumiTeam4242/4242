document.addEventListener('DOMContentLoaded', function () {
    // 로그인 버튼 이벤트 리스너
    var loginForm = document.getElementById('login-form');
    loginForm.addEventListener('submit', function (event) {
        event.preventDefault(); // 기본 이벤트 방지

        // 로그인 폼 데이터 수집
        var userEmail = document.querySelector('.input_email').value;
        var userPassword = document.querySelector('.input_password').value;

        // JSON 데이터 준비
        var data = {
            userEmail: userEmail,
            userPassword: userPassword
        };

        // 서버로 POST 요청 보내기
        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(function(response) {
                if (response.ok) {
                    // 로그인 성공 시 메인 페이지로 이동
                    window.location.href = '/page/main';
                } else {
                    // 로그인 실패 시 오류 메시지 표시
                    throw new Error('로그인에 실패했습니다. 다시 시도해주세요.');
                }
            })
            .catch(function(error) {
                // 오류 발생 시 처리
                console.error('Error:', error);
                alert(error.message);
            });
    });

    // 회원가입 버튼 이벤트 리스너
    var joinButton = document.querySelector('.join_button');
    joinButton.addEventListener('click', function (event) {
        // 기본 동작 막기
        event.preventDefault();
        // 회원가입 페이지로 이동
        window.location.href = "/page/join";
    });
});
