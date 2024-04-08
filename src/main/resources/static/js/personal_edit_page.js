const modal = document.querySelector('.modal');
const modalCloseButton = document.querySelector('#modal_close');
const modalConfirmButton = document.querySelector('#modal_confirm');
const submitButton = document.getElementById('delete-user');

// 제출 버튼 클릭 이벤트
submitButton.addEventListener('click', function () {
    event.preventDefault();
    modal.style.display = 'block';
});

// 모달 닫기 버튼 클릭 이벤트
modalCloseButton.addEventListener('click', function() {
    event.preventDefault();
    modal.style.display = 'none';
});

// 모달 확인 버튼 클릭 이벤트
modalConfirmButton.addEventListener('click', function() {
    // 여기에 제출 로직을 작성할 수 있습니다. 예를 들면, 폼을 서버로 전송하는 코드를 추가할 수 있습니다.
    // formSubmit();
    event.preventDefault();
    modal.style.display = 'none';
});
window.addEventListener('click', function(event) {
    event.preventDefault();
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});
//회원탈퇴
$(document).ready(function() {
    $('#modal_confirm').click(function() {
        $.ajax({
            url: '/api/members/delete', // 요청을 보낼 URL
            type: 'DELETE', // HTTP 메소드
            beforeSend: function(xhr) {
                // 요청을 보내기 전에 헤더에 CSRF 토큰을 설정합니다.
                // Spring Security를 사용하는 경우 CSRF 보호가 활성화되어 있을 수 있으므로,
                // 적절한 토큰 값을 설정해야 합니다.
                // xhr.setRequestHeader('X-CSRF-TOKEN', 'YOUR_CSRF_TOKEN_HERE');
            },
            success: function(response) {
                // 요청이 성공했을 때 실행될 콜백 함수
                // 예를 들어, 성공 메시지를 표시하거나, 사용자를 다른 페이지로 리디렉션할 수 있습니다.
                alert('회원 탈퇴가 성공적으로 처리되었습니다.');
                window.location.href = '/page/intro'; // 홈페이지나 로그인 페이지로 리디렉션
            },
            error: function(xhr, status, error) {
                // 요청이 실패했을 때 실행될 콜백 함수
                // 예를 들어, 에러 메시지를 표시합니다.
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            }
        });

    });
});

document.addEventListener("DOMContentLoaded", function() {

    document.querySelector('.login_top').addEventListener('click', function () {
        // 특정 URL로 이동
        window.location.href = '/page/login'; // 로그인 페이지 URL로 변경
    });

    // 로그아웃 버튼
    document.querySelector('.logout_top').addEventListener('click', function () {
        // 특정 URL로 이동
        window.location.href = '/page/logout'; // 로그아웃 처리를 담당하는 URL로 변경
    });

    // 내 정보 버튼
    document.querySelector('.mypage').addEventListener('click', function () {
        // 특정 URL로 이동
        window.location.href = '/page/my_page'; // 내 정보 페이지 URL로 변경
    });
})

$(document).ready(function() {
    // 수정 버튼 클릭 시
    $('.edit').click(function() {
        $('#nicknameInput').prop('disabled', false);
        $('#fileInput').prop('disabled', false);
        $('.upload-btn').prop('disabled', false);

        $('.edit').hide();
        $('.cancel').show();
        $('.save').show();
    });

    // 취소 버튼 클릭 시
    $('.cancel').click(function() {
        $('#nicknameInput').prop('disabled', true).val('${userInfo.nickname}');
        $('#fileInput').prop('disabled', true);
        $('.upload-btn').prop('disabled', true);

        $('.edit').show();
        $('.cancel').hide();
        $('.save').hide();
    });

    // 저장 버튼 클릭 시
    $('.save').click(function() {
        var formData = new FormData();
        var userInfo = {
            nickname: $('#nicknameInput').val(),
            // 다른 필요한 사용자 정보 필드를 여기에 추가할 수 있습니다.
        };
        formData.append('request', new Blob([JSON.stringify(userInfo)], {type: "application/json"}));
        formData.append('img', $('#fileInput')[0].files[0]);

        $.ajax({
            url: '/api/members/update',
            type: 'PUT',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                console.log("회원 정보가 성공적으로 수정되었습니다.");
                window.location.href = '/page/my_page';
            },
            error: function(xhr, status, error) {
                console.error("회원 정보 수정에 실패했습니다:", xhr.responseText);
                alert("회원 정보 수정에 실패했습니다. 다시 시도해주세요.");
            }
        });
    });

    // 프로필 이미지 업로드 버튼 클릭 시
    $('.upload-btn').click(function() {
        $('#fileInput').click();
    });

    // 이미지 파일 선택 시 미리보기 기능
    $('#fileInput').change(function(e) {
        if (this.files && this.files[0]) {
            var reader = new FileReader();
            reader.onload = function(e) {
                $('#user-img').attr('src', e.target.result);
            };
            reader.readAsDataURL(this.files[0]);
        }
    });
});

