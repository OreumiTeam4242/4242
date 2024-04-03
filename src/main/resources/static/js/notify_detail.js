const goBackButton = document.querySelector(".msg-go-back");

if(goBackButton) {
    goBackButton.addEventListener('click', () =>
        location.replace(`/page/main`)
    );
}

const userSuspendButton = document.querySelector(".btn-user-suspend");

userSuspendButton.addEventListener('click', () => {
    // 회원 정지
});




