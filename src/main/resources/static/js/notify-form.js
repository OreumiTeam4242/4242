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

// 뒤로가기 버튼
document.getElementById('goBack').addEventListener('click', function() {
    window.history.back();
});
