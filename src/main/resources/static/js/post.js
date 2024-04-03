// 뒤로 가기
const goBackButton = document.querySelector(".msg-go-back");

if(goBackButton) {
    goBackButton.addEventListener('click', () =>
        location.replace(`/page/main`)
    );
}

// 스크랩 버튼
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

// 수정하기 버튼
const editButton = document.querySelector(".btn-edit");

if(editButton) {

    editButton.addEventListener('click', () => {
        const completeButton = document.querySelector(".btn-edit-complete");
        const cancelButton = document.querySelector(".btn-edit-cancel");
        const divTextArea = document.querySelector(".recruit-post-text");
        const editTextArea = document.querySelector(".recruit-post-text2");
        const contentAreaInner = divTextArea.textContent.trim();

        editButton.style.display = "none";
        divTextArea.style.display = "none";
        completeButton.style.display = "block";
        cancelButton.style.display = "block";
        editTextArea.style.display = "block";

        alert(contentAreaInner);

        // 페이지에서 내용 바로 수정
        // const contentArea = document.querySelector(".register-post-text");
        // // const beforeContent = contentDiv.textContent.trim();
        // //
        // // const contentTextarea = document.createElement('textarea');
        // // contentTextarea.value = beforeContent;
        // contentArea.replaceWith('<textarea>' + $('.register-post-text').html() +'</textarea>'); // div를 textarea로 교체


        // contentArea.replaceWith('<textarea>' + $('.register-post-text').html() +'</textarea>'); // div를 textarea로 교체

        completeButton.addEventListener('click', () => {
            editButton.style.display = "block";
            completeButton.style.display = "none";
            cancelButton.style.display = "none";
            editTextArea.style.display = "none";
            divTextArea.style.display = "block";

            divTextArea.textContent = editTextArea.value;
        });

        cancelButton.addEventListener('click', () => {
            editButton.style.display = "block";
            completeButton.style.display = "none";
            cancelButton.style.display = "none";
            editTextArea.style.display = "none";
            divTextArea.style.display = "block";

            divTextArea.textContent = contentAreaInner;
        });
    });
}

// 신청하기 버튼
const applyButton = document.querySelector(".btn-apply");

if(applyButton) {
    applyButton.addEventListener('click', () =>
        location.replace(`/page/apply-form`)
    );
}

// 댓글 등록 버튼
const commentRegisterButton = document.querySelector(".btn-comment-register");

commentRegisterButton.addEventListener('click', event => {
    let commentContent = document.querySelector(".comment-input").value;

    if(commentContent.trim() !== '') {
        let newComment = document.createElement('div');
        newComment.classList.add('comment-detail');

        // 닉네임
        let nicknameParagraph = document.createElement('p');
        nicknameParagraph.classList.add('comment-nickname');
        nicknameParagraph.textContent = "현재 로그인한 멤버의 닉네임";
        newComment.appendChild(nicknameParagraph);

        // 작성 시간
        let timeParagraph = document.createElement('p');
        timeParagraph.classList.add('comment-time');
        let currentTime = new Date();
        const options = {
            hour12 : false,
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
        };
        timeParagraph.textContent = currentTime.toLocaleString('ko-KR', options).replace(/\./g, '. ');
        newComment.appendChild(timeParagraph);

        let contentParagraph = document.createElement('p');
        contentParagraph.classList.add('comment-content');
        contentParagraph.textContent = commentContent;
        newComment.appendChild(contentParagraph);

        // 삭제 버튼
        let deleteButton = document.createElement('button');
        deleteButton.classList.add('btn-comment-delete');
        deleteButton.textContent = "삭제";
        newComment.appendChild(deleteButton);

        // 새로운 댓글 추가
        document.querySelector('.comment-exist').appendChild(newComment);

        // 댓글 입력 창 지우기
        document.querySelector('.comment-input').value = '';
    } else {
        alert("댓글이 입력되지 않았습니다.");
    }
});

// 댓글 삭제 버튼 및 댓글 삭제 모달창 버튼
const commentDeleteButtons = document.querySelectorAll(".btn-comment-delete");
const commentModal = document.querySelector('.modal-comment-delete');
const modalCloseButton = document.querySelector(".modal-close");
const modalCheckButton = document.querySelector(".modal-check");

commentDeleteButtons.forEach(button => {
    button.addEventListener('click', () => {
        commentModal.style.display = "flex";
    });
});


// 모달 닫힘 버튼
modalCloseButton.addEventListener('click', () => {
    commentModal.style.display = "none";
});

modalCheckButton.addEventListener('click', () => {
    commentModal.style.display = "none";
});


