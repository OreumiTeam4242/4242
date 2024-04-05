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

