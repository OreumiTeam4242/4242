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

modalConfirmButton.addEventListener('click', function() {
    // 폼 데이터 가져오기
    var title = document.getElementById('title').value;
    var content = document.getElementById('content').value;
    var available_time = document.getElementById('available_time').value;
    var available_day = document.getElementById('available_day').value;
    var file = document.getElementById('file').files[0]; // 파일 객체

    var formData = new FormData();
    formData.append('title', title);
    formData.append('content', content);
    formData.append('available_time', available_time);
    formData.append('available_day', available_day);
    formData.append('file', file);

    // AJAX 요청 보내기
    fetch('/apply-form', {
        method: 'POST',
        body: formData,
        headers: {
            'Authorization': 'Bearer ' + YOUR_ACCESS_TOKEN, // 필요한 경우 토큰 추가
        }
    })
        .then(response => response.json())
        .then(data => {
            // 서버로부터 받은 응답 처리
            console.log(data);
            modal.style.display = 'none'; // 모달 닫기
        })
        .catch(error => {
            // 에러 처리
            console.error('Error 발생하였습니다.:', error);
        });
});


// 모달 외부 클릭 시 모달 닫기
window.addEventListener('click', function(event) {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

// 뒤로가기 버튼
document.getElementById('goBack').addEventListener('click', function() {
    window.history.back();
});
