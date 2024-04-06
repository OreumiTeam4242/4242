// 로고 클릭
const logoImage = document.querySelector(".logo-top");

if(logoImage) {
    logoImage.addEventListener('click', () => {
        window.location.href = "/page/main";
    });
}

// 뒤로가기 버튼
const goBackButton = document.querySelector(".msg-go-back");

if(goBackButton) {
    goBackButton.addEventListener('click', () =>
        window.location.href = "/page/administrator_list"
    );
}

const userSuspendButton = document.querySelector(".btn-user-suspend");

userSuspendButton.addEventListener('click', () => {
    // 클릭할 때마다 memberId를 가져옴
    const memberId = document.getElementById("notify-member-id").value;

    // 회원 정지
    const currentTime = new Date(Date.now());
    const suspensionTime = new Date(currentTime.getTime() + 7 * 24 * 60 * 60 * 1000); // 현재 시간으로부터 7일 후의 시간

    console.log("현재 시간:", currentTime.toISOString());
    console.log("정지 시간:", suspensionTime.toISOString());

    console.log("memberId : " + memberId);

    fetch(`/api/members/${memberId}/disabled`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            memberId : memberId
        })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('서버 응답이 실패했습니다.');
            }
            return response.json();
        })
        .then(data => {
            // 서버 응답 처리
            alert("유저의 활동을 정지하였습니다.");
        })
        .catch(error => {
            console.error('에러 발생:', error);
        });
});




