document.addEventListener('DOMContentLoaded', function () {
    var joinButton = document.querySelector('.join_button');
    joinButton.addEventListener('click', function() {
        // 회원가입 폼 데이터 수집
        var userEmail = document.querySelector('.input_email').value;
        var userPassword = document.querySelector('.input_password').value;
        var userNickname = document.querySelector('.input_nickname').value;

        // 모든 필드가 입력되었는지 확인
        if (!userEmail || !userPassword || !userNickname) {
            alert('모든 항목을 입력해주세요.');
            return; // 폼 제출을 중단
        }

        // JSON 데이터 준비
        var data = {
            email: userEmail,
            password: userPassword,
            nickname: userNickname
        };

        // 서버로 POST 요청
        fetch('/api/members/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(function(response) {
                if (response.ok) {
                    // 회원가입 성공 시 모달창 표시
                    alert('회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.');
                    window.location.href = '/page/login'; // 로그인 페이지로 이동
                } else if (response.status === 400) {
                    // 닉네임 중복 오류 처리
                    showDuplicateNicknameModal();
                } else {
                    // 기타 오류 처리
                    throw new Error('회원가입에 실패했습니다. 다시 시도해주세요.');
                }
            })
            .catch(function(error) {
                // 오류 발생 시 처리
                console.error('Error:', error);
                alert(error.message);
            });
    });

    // 닉네임 중복 알림 모달창 닫기 버튼 이벤트 리스너
    var modalCloseBtn = document.querySelector('.modal-close-img');
    if (modalCloseBtn) {
        modalCloseBtn.addEventListener('click', function() {
            document.querySelector('.modal').style.display = 'none';
        });
    }

    // 닉네임 중복 알림 모달창 표시 함수
    function showDuplicateNicknameModal() {
        document.querySelector('.modal').style.display = 'block';
    }
});
