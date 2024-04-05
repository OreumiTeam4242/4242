const modal = document.querySelector('.modal');
const modalCloseButton = document.querySelector('#modal_close');
const modalConfirmButton = document.querySelector('#modal_confirm');
const submitButton = document.getElementById('submit-button');

// 제출 버튼 클릭 이벤트
submitButton.addEventListener('click', function () {
    modal.style.display = 'block';
});

// 모달 닫기 버튼 클릭 이벤트
modalCloseButton.addEventListener('click', function() {
    modal.style.display = 'none';
});

// 모달 확인 버튼 클릭 이벤트
modalConfirmButton.addEventListener('click', function() {
    // 여기에 제출 로직을 작성할 수 있습니다. 예를 들면, 폼을 서버로 전송하는 코드를 추가할 수 있습니다.
    // formSubmit();
    modal.style.display = 'none';
});

// 모달 외부 클릭 시 모달 닫기
window.addEventListener('click', function(event) {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

$(document).ready(function() {
    $('#modal_confirm').click(function() {
        var formData = new FormData();
        var request = {
            title: $('#title').val(),
            type_id: $('#post-type').val(),
            member_cnt: $('#member-cnt').val(),
           major_id: $('#post-major').val(),
            process_type: $('#process-type').val(),
            start_date: $('#start-date').val(),
            end_date: $('#end-date').val(),
            content: $('#introduce').val(),
        };
        formData.append('request', new Blob([JSON.stringify(request)], {type: "application/json"}));
        formData.append('file', $('#file')[0].files[0]);
        $.ajax({
            url: '/api/post',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                console.log("게시물이 성공적으로 추가되었습니다.");
                // 필요에 따라 추가적인 처리를 할 수 있습니다.
                //TODO : 상세 조회로 이동
                window.location.href = '/page/main';
            },
            error: function(xhr, status, error) {
                console.error("게시물 추가에 실패했습니다:", xhr.responseText);
                alert("게시물 추가에 실패했습니다. 다시 시도해주세요.");
            }
        });
    });
});
