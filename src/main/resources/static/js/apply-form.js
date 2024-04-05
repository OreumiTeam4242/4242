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
    modal.style.display = 'none';
});

$(document).ready(function() {
    var postId = extractPostIdFromUrl();
    $('#modal_confirm').click(function() {
        var selectedDays ='';
        var selectedTimes ='';

        // 체크된 요일을 배열에 추가
        $('.day-check input:checked').each(function() {
            selectedDays.append($(this).val());
        });
        $('.time-check input:checked').each(function() {
            selectedTimes.append($(this).val());
        });

        var formData = new FormData();
        var request = {
            title: $('#title').val(),
            available_times: selectedTimes,
            available_days : selectedDays,
            process_type: $('#process-type').val(),
            content: $('#motivation').val(),
        };
        formData.append('request', new Blob([JSON.stringify(request)], {type: "application/json"}));
        formData.append('file', $('#file')[0].files[0]);
        $.ajax({
            url: '/api/post/'+postId+'/apply',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response) {
                console.log("게시물이 성공적으로 추가되었습니다.");
                // 필요에 따라 추가적인 처리를 할 수 있습니다.
                window.location.href = '/page/main';
            },
            error: function(xhr, status, error) {
                console.error("게시물 추가에 실패했습니다:", xhr.responseText);
                alert("게시물 추가에 실패했습니다. 다시 시도해주세요.");
            }
        });
    });
});

function extractPostIdFromUrl() {
    var pathname = window.location.pathname; // 현재 페이지의 경로를 가져옴
    var pathParts = pathname.split('/'); // 경로를 '/'로 나눔
    var postIdIndex = pathParts.length - 2; // postId는 뒤에서 두 번째 요소일 것임
    var postId = pathParts[postIdIndex]; // postId 추출
    return postId;
}


// 모달 외부 클릭 시 모달 닫기
window.addEventListener('click', function(event) {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});



