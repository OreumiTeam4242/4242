const $topBtn = document.querySelector(".btn-to-top");

// 버튼 클릭 시 맨 위로 이동
$topBtn.onclick = () => {
    window.scrollTo({top: 0, behavior: "smooth"});
}

document.addEventListener('DOMContentLoaded', function () {
    var boxTopContents = document.querySelectorAll('.box-top-content');

    // 각 요소에 클릭 이벤트 추가
    boxTopContents.forEach(function(boxTopContent) {
        boxTopContent.addEventListener('click', function() {
            // 클릭된 요소의 정보를 기반으로 포스트 ID 가져오기
            const postId = boxTopContent.dataset.postId; // 가정: 데이터셋에 postId가 있음

            // 포스트 ID를 이용하여 상세 페이지로 이동
            window.location.href = `/page/recruitPostDetail?id=${postId}`;
        });
    });
});

// 내 정보 버튼 클릭 시
document.getElementById('my-info').addEventListener('click', function () {
    // REST API 호출
    fetch('/page/personal_page', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(function(response) {
            // 응답 처리
            if (response.ok) {
                // 성공 시 내 정보 페이지로 리다이렉트
                window.location.href = '/page/personal_page';
            } else {
                // 실패 시 에러 처리
                throw new Error('내 정보 불러오기에 실패했습니다.');
            }
        })
        .catch(function(error) {
            // 에러 처리
            console.error('Error:', error);
            alert(error.message);
        });
});

// 로그아웃 버튼 클릭 시
document.getElementById('logout').addEventListener('click', function () {
    // REST API 호출
    fetch('/page/logout', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(function(response) {
            // 응답 처리
            if (response.ok) {
                // 성공 시 로그인 페이지로 리다이렉트
                window.location.href = '/page/login';
                // todo 로그아웃되는 기능도 넣어줘야됨 나중에!!
            } else {
                // 실패 시 에러 처리
                throw new Error('로그아웃에 실패했습니다.');
            }
        })
        .catch(function(error) {
            // 에러 처리
            console.error('Error:', error);
            alert(error.message);
        });
});


// todo 이 아래로는 줄곧 수정 예정..

// 이전에 선택된 major_id를 저장할 변수
let previousSelectedValue = null;

// 선택한 주요 항목에 따라 게시물을 가져오고 표시하는 함수
async function fetchAndDisplayPosts() {
    try {
        const postTypeSelect = document.getElementById('post-type');
        let selectedValue = postTypeSelect.value.trim(); // 공백 제거

        const findBox = document.querySelector('.find-box .boxes');

        let url = '/api/posts';

        // 전체를 선택한 경우에는 major_id를 보내지 않음
        if (selectedValue !== '') {
            url += `/major/${selectedValue}`;
        }

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        // JSON 데이터를 파싱하고, 비어있는 경우에 대한 처리
        const posts = await response.json();
        if (!posts || posts.length === 0) {
            findBox.innerHTML = '<p>해당 분야의 게시물이 없습니다.</p>';
            return;
        }

        // find-box-content를 제거
        const existingPosts = document.querySelectorAll('.find-box-content');
        existingPosts.forEach(post => {
            post.remove();
        });

        posts.forEach(post => {
            const postDiv = document.createElement('div');
            postDiv.classList.add('find-box-content');
            postDiv.innerHTML = `
                <div class="box-top-content" data-post-id="${post.id}">
                    <p class="box-in-text">${post.title}</p>
                    <img class="heart" src="/image/empty-heart.png" alt="빈 하트 이미지"/>
                </div>
                <div class="box-text-content-2">
                    <p class="author">${post.member ? post.member.nickname : 'Unknown'}</p>
                    <p>조회수 ${post.viewCount}</p>
                    <p>댓글 ${post.commentList.length}</p>
                </div>
            `;
            findBox.appendChild(postDiv);
        });

        // 현재 선택된 major_id를 이전에 선택된 major_id에 저장
        previousSelectedValue = selectedValue;
    } catch (error) {
        console.error('Error fetching posts:', error);
    }
}

// 페이지가 로드될 때 기본 선택된 주요 항목에 따라 게시물을 가져오는 함수 호출
window.onload = fetchAndDisplayPosts;

// 게시물 유형 선택 변경 이벤트 리스너
document.getElementById('post-type').addEventListener('change', () => {
    // 선택이 변경될 때마다 previousSelectedValue를 갱신하고 fetchAndDisplayPosts 함수 호출
    previousSelectedValue = document.getElementById('post-type').value.trim();
    fetchAndDisplayPosts();
});




// ------------------ 스터디 / 프로젝트 모집 구분 부분


// ------------------ 스크랩 GET