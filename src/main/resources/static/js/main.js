const $topBtn = document.querySelector(".btn-to-top");

// 버튼 클릭 시 맨 위로 이동
$topBtn.onclick = () => {
    window.scrollTo({top: 0, behavior: "smooth"});
}

// --------------------- 상세 페이지로 이동

document.addEventListener('DOMContentLoaded', function () {
    var boxTopContents = document.querySelectorAll('.box-top-content');
    var boxFindContents = document.querySelectorAll('.find-box-content');

    // boxTopContents에 대한 클릭 이벤트 등록
    boxTopContents.forEach(function(boxTopContent) {
        boxTopContent.addEventListener('click', function() {
            const post_id = boxTopContent.dataset.post_id;
            fetchAndDisplayPostDetail(post_id);
        });
    });

    // boxFindContents에 대한 클릭 이벤트 등록
    boxFindContents.forEach(function(boxFindContent) {
        boxFindContent.addEventListener('click', function() {
            const post_id = boxFindContent.dataset.post_id;
            fetchAndDisplayPostDetail(post_id);
        });
    });

    function fetchAndDisplayPostDetail(post_id) {
        fetch(`/api/post/${post_id}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(post => {
                window.location.href = `/page/recruitPostDetail?id=${post_id}`;
            })
            .catch(error => console.error('Error fetching post by ID:', error));
    }
});



// ------------------내 정보 버튼 클릭 시
// 내 정보 버튼 클릭 시
document.getElementById('my-info').addEventListener('click', function () {
    // REST API 호출
    fetch("/api/members", {
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


// ------------------로그아웃 버튼 클릭 시
document.getElementById('logout').addEventListener('click', function () {
    // REST API 호출
    fetch("/api/auth/logout", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(function(response) {
            // 응답 처리
            if (response.ok) {
                // 성공 시 인트로 페이지로 리다이렉트
                window.location.href = '/page/intro';
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

// ------------------모집분야별
// todo 이 아래로는 줄곧 수정 예정..

// 이전에 선택된 major_id를 저장할 변수
let previousSelectedValue = null;

// 선택한 주요 항목에 따라 게시물을 가져오고 표시하는 함수
async function fetchAndDisplayPosts() {
    try {
        const postTypeSelect = document.getElementById('post-type');
        let selectedValue = postTypeSelect.value.trim();

        const findBox = document.querySelector('.find-box .boxes');

        let url = '/api/posts';
        if (selectedValue !== '') {
            url += `/major/${selectedValue}`;
        }

        const response = await fetch(url);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const posts = await response.json();
        if (!posts || posts.length === 0) {
            findBox.innerHTML = '<p>해당 분야의 게시물이 없습니다.</p>';
            return;
        }

        // find-box-content를 제거
        findBox.innerHTML = ''; // 기존 게시물을 모두 제거

        posts.forEach(post => {
            const postDiv = document.createElement('div');
            postDiv.classList.add('find-box-content');
            postDiv.innerHTML = `
                <div class="box-top-content" data-post-id="${post.id}">
                    <p class="box-in-text">${post.title}</p>
                    <img class="heart" src="/image/empty-heart.png" alt="빈 하트 이미지"/>
                </div>
                <div class="box-text-content-2">
                    <p>${post.nickname}</p>
                    <p>조회수 ${post.viewCount}</p>
                    <p>댓글 ${post.commentList.length}</p>
                </div>
            `;
            findBox.appendChild(postDiv);
        });
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




// ------------------ 스터디 / 프로젝트 모집 구분
// 버튼 클릭 이벤트 리스너 등록
document.querySelectorAll('.study-button, .project-button').forEach(button => {
    button.addEventListener('click', async () => {
        try {
            // 버튼의 데이터 타입 가져오기
            const dataType = button.getAttribute('data-type');

            // API 요청을 위한 URL 생성
            const url = `/api/posts/type/${dataType}`;

            // API 요청 및 응답 처리
            const response = await fetch(url);

            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const posts = await response.json();

            // 해당 버튼에 따라 게시물을 표시하는 함수 호출
            displayPosts(posts);
        } catch (error) {
            console.error('Error fetching posts:', error);
        }
    });
});

// 게시물을 표시하는 함수
function displayPosts(posts) {
    // find-box-content를 제거
    const findBox = document.querySelector('.find-box .boxes');
    findBox.innerHTML = ''; // 기존 게시물을 모두 제거

    posts.forEach(post => {
        const postDiv = document.createElement('div');
        postDiv.classList.add('find-box-content');
        postDiv.innerHTML = `
            <div class="box-top-content" data-post-id="${post.id}">
                <p class="box-in-text">${post.title}</p>
                <img class="heart" src="/image/empty-heart.png" alt="빈 하트 이미지"/>
            </div>
            <div class="box-text-content-2">
                <p>${post.nickname}</p>
                <p>조회수 ${post.viewCount}</p>
                <p>댓글 ${post.commentList.length}</p>
            </div>
        `;
        findBox.appendChild(postDiv);
    });
}


// ------------------ 스크랩 GET


