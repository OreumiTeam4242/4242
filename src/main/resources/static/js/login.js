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
                    // 로그인이 성공한 경우의 처리
                    // 예: 리다이렉트 혹은 다른 작업 수행
                    window.location.href = '/page/main'; // 성공 페이지로 리다이렉트 예시
                } else if (response.status === 401) {
                    // 401 에러가 발생한 경우의 처리
                    alert("이메일 또는 비밀번호를 다시 확인해주세요.");
                    window.location.href = '/page/login';
                } else if(response.status === 403) {
                    alert("정지된 회원입니다");
                    window.location.href = '/page/login';
                }
                else {
                    // 기타 다른 에러가 발생한 경우의 처리
                    alert("로그인에 실패했습니다. 다시 시도해주세요.");
                    window.location.href = '/page/login';
                }
            })
            .catch(error => {
                // 네트워크 오류 등의 경우의 처리
                console.error('Error:', error);
                alert("네트워크 오류가 발생했습니다. 다시 시도해주세요.");
                window.location.href = '/page/login';
            });


    });

    // 회원가입 버튼 이벤트 리스너
    var joinButton = document.querySelector('.join_button');
    joinButton.addEventListener('click', function (event) {
        event.preventDefault(); // 기본 클릭 동작 막기
        window.location.href = "/page/join"; // 회원가입 페이지로 이동
    });
});
