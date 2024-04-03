document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('login-form').addEventListener('submit', function(event) {
        event.preventDefault(); // 기본 제출 동작 막기

        var email = document.querySelector('.input_email').value;
        var password = document.querySelector('.input_password').value;

        var data = {
            email: email,
            password: password
        };

        fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = '/page/main';
                } else {
                    response.text().then(errorMessage => {
                        alert(errorMessage); // 실패한 경우 에러 메시지를 경고창으로 표시
                    });
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });

    // 회원가입 버튼 이벤트 리스너
    var joinButton = document.querySelector('.join_button');
    joinButton.addEventListener('click', function (event) {
        event.preventDefault(); // 기본 클릭 동작 막기
        window.location.href = "/page/join"; // 회원가입 페이지로 이동
    });
});
