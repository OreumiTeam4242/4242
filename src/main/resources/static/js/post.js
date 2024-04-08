const postId = document.getElementById("post-id").value;

// 로고 클릭
const logoImage = document.querySelector(".logo-top");

if(logoImage) {
    logoImage.addEventListener('click', () => {
        window.location.href = "/page/main";
    });
}

// 뒤로 가기
const goBackButton = document.querySelector(".msg-go-back");

if (goBackButton) {
    goBackButton.addEventListener('click', () => {
        window.location.href = "/page/main"
    });
}


// 스크랩 버튼
let image = document.querySelector(".scrap-heart");

document.addEventListener('DOMContentLoaded', async function() {
    const scrapButton = document.querySelector(".btn-scrap");

    // DB의 스크랩 상태 반영
    async function updateScrapStatus() {
        try {
            if (!image) {
                console.error('Image element not found');
                return;
            }

            const response = await fetch(`/api/posts/${postId}/scraps/status`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const isScrapped = await response.json();
            image.src = isScrapped ? '/image/filled-heart.png' : '/image/empty-heart.png';

        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
        }
    }

    // 스크랩 상태 업데이트
    await updateScrapStatus();

    // 스크랩 버튼 클릭 이벤트 핸들러
    if (scrapButton) {
        scrapButton.addEventListener('click', async function() {
            const currentSrc = image.src;

            let scrapStatusChanged = false; // 스크랩 상태가 변경되었는지 여부

            // 스크랩 상태 변경 요청
            try {
                const response = await fetch(`/api/posts/${postId}/scraps`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: currentSrc.includes('empty-heart.png') ? JSON.stringify({}) : null
                });

                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                scrapStatusChanged = true;
            } catch (error) {
                console.error('There was a problem with the fetch operation:', error);
            }

            // 스크랩 상태가 변경 시에 이미지 업데이트
            if (scrapStatusChanged) {
                await updateScrapStatus();
            }
        });
    }
});

// 수정하기 버튼
document.addEventListener('DOMContentLoaded', function () {
    const editButton = document.querySelector(".btn-edit");
    const completeButton = document.querySelector(".btn-edit-complete");
    const cancelButton = document.querySelector(".btn-edit-cancel");

    if (editButton) {
        editButton.addEventListener('click', handleEditClick);
    }

    function handleEditClick() {
        const divTextArea = document.querySelector(".recruit-post-text");
        const editTextArea = document.querySelector(".recruit-post-text2");
        const contentAreaInner = divTextArea.textContent.trim();

        // 수정 버튼만 숨김
        editButton.style.display = "none";

        // 완료 및 취소 버튼은 보이도록 처리
        completeButton.style.display = "block";
        cancelButton.style.display = "block";

        // 편집용 텍스트 영역은 보이도록 처리
        divTextArea.style.display = "none";
        editTextArea.style.display = "block";

        completeButton.addEventListener('click', () => {
            applyChanges(contentAreaInner, divTextArea, editTextArea);
        });

        cancelButton.addEventListener('click', () => {
            cancelEdit(contentAreaInner, divTextArea, editTextArea);
        });
    }

    function applyChanges(contentAreaInner, divTextArea, editTextArea) {
        // 수정된 내용 가져오기
        const fileInput = document.getElementById('fileInput').files[0];

        // FormData 객체 생성
        const formData = new FormData();
        let request = {
            content : $('.recruit-post-text2').val()
        };
        formData.append('request', new Blob([JSON.stringify(request)], {type: "application/json"}));
        if (fileInput) {
            formData.append('file', fileInput);
        }

        // 서버로 데이터 전송
        fetch(`/api/post/${postId}`, {
            method: 'PUT',
            body: formData
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Data updated successfully:', data);
                // 성공적으로 업데이트된 데이터를 처리하는 코드 작성
                alert('수정이 완료되었습니다');
                window.location.href = '/page/post/'+postId;
            })
            .catch(error => {
                console.error('There was a problem updating the data:', error);
            });

        editButton.style.display = "block";
        completeButton.style.display = "none";
        cancelButton.style.display = "none";
        editTextArea.style.display = "none";
        divTextArea.style.display = "block";
    }

    function cancelEdit(contentAreaInner, divTextArea, editTextArea) {
        editButton.style.display = "block";
        completeButton.style.display = "none";
        cancelButton.style.display = "none";
        editTextArea.style.display = "none";
        divTextArea.style.display = "block";
    }
});

// 신청하기 버튼
const applyButton = document.querySelector(".btn-apply");

// if(applyButton) {
//     applyButton.addEventListener('click', () =>
//         window.location.href = `/page/post/`+postId+`/apply`
//     )
// }
applyButton.addEventListener('click', () => {
    let userInfoNickname = applyButton.dataset.userinfonickname;
    let postNickname = applyButton.dataset.postnickname;
    let startDate = new Date(applyButton.dataset.startdate);
    if(userInfoNickname === postNickname) {
        window.location.href = `/page/post/`+postId+`/apply-list`;
    } else if(userInfoNickname !== postNickname && new Date() < new Date(startDate)) {
        window.location.href = `/page/post/`+postId+`/apply`;
    }
});

// 댓글 등록 버튼
const commentRegisterButton = document.querySelector(".btn-comment-register");

commentRegisterButton.addEventListener('click', async event => {
    try {
        let commentContent = document.querySelector(".comment-input").value;

        if (commentContent.trim() !== '') {
            // 댓글 등록 API 호출
            const response = await fetch(`/api/posts/${postId}/comments`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ content: commentContent })
            });

            if (!response.ok) {
                throw new Error('Failed to add comment');
            }

            alert('댓글이 등록되었습니다.');
            window.location.href = `/page/post/`+postId;

        } else {
            alert("댓글이 입력되지 않았습니다.");
        }
    } catch (error) {
        console.error('Error:', error);
        alert('댓글 등록에 실패했습니다.');
    }
});


// 댓글 삭제 버튼 및 댓글 삭제 모달창 버튼
const commentList = document.querySelectorAll(".comment-detail");
const commentDeleteButtons = document.querySelectorAll(".btn-comment-delete");
const commentModal = document.querySelector('.modal-comment-delete');
const modalCloseButton = document.querySelector(".modal-close");
const modalCheckButton = document.querySelector(".modal-check");
const commentWriterIds = Array.from(document.querySelectorAll(".comment-member-id")).map(element => element.value);
const nowUserId = document.querySelector(".userInfo-id").value;
let commentIndex;

commentDeleteButtons.forEach((button, index) => {
    button.addEventListener('click', event => {
        const commentElement = event.target.parentNode; // 클릭된 삭제 버튼의 부모 요소인 comment-detail 요소
        commentIndex = Array.from(commentList).indexOf(commentElement); // 삭제 버튼을 포함하는 댓글 요소의 인덱스
        console.log("댓글 인덱스:", commentIndex);
        console.log("댓글 작성자 id:", commentWriterIds[commentIndex].toString());
        console.log("현재 유저 id: " + nowUserId);
        console.log(commentWriterIds[commentIndex].toString() === nowUserId);

        if(commentWriterIds[commentIndex].toString() === nowUserId) {

            commentModal.style.display = "block";
        }
    });
});

// 모달 닫힘 버튼
modalCloseButton.addEventListener('click', () => {
    commentModal.style.display = "none";
});

// 댓글 삭제 체크 버튼
modalCheckButton.addEventListener('click', async () => {
    try {
        commentModal.style.display = "none";

        const commentIdList = Array.from(document.querySelectorAll(".comment-id")).map(element => element.value);
        const commentId = commentIdList[commentIndex];

        // 댓글 삭제 API 호출
        const response = await fetch(`/api/posts/${postId}/comments/${commentId}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            console.log(response.status);
            throw new Error('Failed to delete comment');
        }

        // 댓글 삭제되면 새로고침
        window.location.href = `/page/post/`+postId;

    } catch (error) {
        console.error('Error:', error);
        alert('댓글 삭제에 실패했습니다.');
    }
});






