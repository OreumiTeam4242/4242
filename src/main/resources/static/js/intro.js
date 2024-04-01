document.addEventListener('DOMContentLoaded', function() {
    // 4242 이용하러 가기 버튼 요소를 가져옵니다.
    var button4242 = document.querySelector('.medium_button');

    // 버튼을 클릭했을 때의 동작을 정의합니다.
    button4242.addEventListener('click', function() {
        // 이동할 URL을 설정합니다.
        var url = 'local'; // 실제 URL로 변경해주세요.

        // 새 탭에서 URL로 이동합니다.
        window.open(url, '_blank');
    });
});
