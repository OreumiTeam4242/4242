const goBackButton = document.querySelector(".msg-go-back");

if(goBackButton) {
    goBackButton.addEventListener('click', () =>
        location.replace(`/page/main`)
    );
}

const ScrapButton = document.querySelector(".btn-scrap");
const image = document.getElementById("image");

ScrapButton.addEventListener('click', function() {
    const currentSrc = image.src;

    let newSrc; // 새로운 경로 저장용
    if (currentSrc.includes('empty-heart.png')) {
        newSrc = '/image/filled-heart.png';
    } else {
        newSrc = '/image/empty-heart.png';
    }

    image.src = newSrc;

});

const applyButton = document.querySelector(".btn-apply");

if(applyButton) {
    applyButton.addEventListener('click', () =>
        location.replace(`/page/apply-form`)
    );
}

const commentDeleteButtons = document.querySelectorAll(".btn-comment-delete");
const commentModal = document.querySelector('.modal-comment-delete');
const modalCloseButton = document.querySelector(".modal-close");
const modalCheckButton = document.querySelector(".modal-check");

commentDeleteButtons.forEach(button => {
    button.addEventListener('click', () => {
        commentModal.style.display = "block";
    });
});


modalCloseButton.addEventListener('click', () => {
    commentModal.style.display = "none";
});

modalCheckButton.addEventListener('click', () => {
    commentModal.style.display = "none";
});


