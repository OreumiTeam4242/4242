const $topBtn = document.querySelector(".btn-to-top");

window.addEventListener('scroll', () => {
    if(document.documentElement.scrollTop > 150 ||
        document.body.scrollTop > 150) {
        $topBtn.style.display = 'block';
    } else {
        $topBtn.style.display = 'none';
    }
});

// 버튼 클릭 시 맨 위로 이동
$topBtn.addEventListener('click', () => {
    window.scrollTo({top : 0, behavior : "smooth"});
});

// --------------------- 상세 페이지로 이동
document.addEventListener('DOMContentLoaded', function () {
    // .hot-box-content와 .boxes의 하위 요소에 대한 클릭 이벤트를 처리할 수 있도록 상위 요소에 이벤트를 등록
    document.querySelector('.main-context').addEventListener('click', function(event) {
        // 클릭된 요소가 .hot-box-content 또는 .boxes 클래스를 포함하고 있는지 확인
        if (event.target.closest('.hot-box-content') || event.target.closest('.boxes')) {
            const postId = event.target.closest('.box-top-content').dataset.postId;
            if (postId) {
                window.location.href = `/page/post/${postId}`;
            } else {
                console.error('Post ID is undefined');
            }
        }
    });
});
document.getElementById('admin').addEventListener('click', function () {
    // 내 정보 페이지로 리다이렉트
    window.location.href = '/page/admin';
});

// -------------모집 공고 생성 버튼 클릭 시
document.getElementById('create').addEventListener('click', function () {
    // 모집 공고 생성 페이지로 리다이렉트
    window.location.href = '/page/post-form';
});

// -----------신고하기 버튼 클릭 시
document.getElementById('notify').addEventListener('click', function () {
    // 신고글 생성 페이지로 리다이렉트
    window.location.href = '/page/notify-form';
});

// ------------------내 정보 버튼 클릭 시

document.getElementById('my-info').addEventListener('click', function () {
    // 내 정보 페이지로 리다이렉트
    window.location.href = '/page/my_page';
});


// ------------------로그아웃 버튼 클릭 시
// main.js
document.addEventListener("DOMContentLoaded", function() {
    var logoutButton = document.getElementById("logout");
    if (logoutButton) {
        logoutButton.addEventListener("click", function() {
            // 서버의 로그아웃 엔드포인트 호출
            fetch("/api/auth/logout", {
                method: "POST",
                credentials: "same-origin" // 쿠키를 함께 전송
            })
                .then(function(response) {
                    if (response.ok) {
                        // 로그아웃 성공 시 세션 및 쿠키 삭제 후 로그인 페이지로 리다이렉션
                        sessionStorage.clear(); // 세션 데이터 삭제
                        window.location.href = "/page/login"; // 로그인 페이지 URL로 이동
                    } else {
                        console.error("로그아웃 요청이 실패하였습니다.");
                    }
                })
                .catch(function(error) {
                    console.error("로그아웃 요청 중 오류가 발생하였습니다:", error);
                });
        });
    }
});

// ------------------모집분야별

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
                    <p> ${post.nickname} </p>
                    <p> 조회수 ${post.viewCount} </p>
                    <p> 댓글 ${post.commentList.length} </p>
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
                <p> ${post.nickname} </p>
                <p> 조회수 ${post.viewCount} </p>
                <p> 댓글 ${post.commentList.length} </p>
            </div>
        `;
        findBox.appendChild(postDiv);
    });
}

//-----------Scrap
// 스크랩 상태를 가져와 UI를 업데이트하는 함수
// 페이지 로드 시 실행
window.onload = function() {
    updateScrapStatus();
    addHotIconToPosts();
};

// 스크랩 상태를 가져와 UI를 업데이트하는 함수
async function updateScrapStatus() {
    const posts = document.querySelectorAll('.box-top-content');

    posts.forEach(async (post) => {
        const postId = post.getAttribute('data-post-id'); // Thymeleaf 속성을 사용하지 않고 일반적인 속성을 사용

        try {
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
            const heartImage = post.querySelector('.heart');

            if (isScrapped) {
                heartImage.src = '/image/filled-heart.png';
            } else {
                heartImage.src = '/image/empty-heart.png';
            }

        } catch (error) {
            console.error('There was a problem with the fetch operation:', error);
        }
    });
}

//-------------- HOT 게시글 불 이미지 표시

// hotPostList와 postList를 비교하여 핫 아이콘을 추가하는 함수
function addHotIconToPosts() {
    const hotPosts = document.querySelectorAll('.hot-box-content');
    const findPosts = document.querySelectorAll('.find-box-content');

    hotPosts.forEach(hotPost => {
        const hotPostId = hotPost.querySelector('.box-top-content').getAttribute('th:data-post-id');
        const hotPostTitle = hotPost.querySelector('.box-in-text').textContent.trim();

        findPosts.forEach(findPost => {
            const findPostId = findPost.querySelector('.box-top-content').getAttribute('th:data-post-id');
            const findPostTitle = findPost.querySelector('.box-in-text').textContent.trim();

            if (hotPostId === findPostId && hotPostTitle === findPostTitle) {
                const hotIcon = document.createElement('img');
                hotIcon.setAttribute('src', '/image/fire.png');
                hotIcon.setAttribute('alt', '핫');
                hotIcon.classList.add('fire');

                const boxTopContent = findPost.querySelector('.box-top-content');
                boxTopContent.appendChild(hotIcon);
            }
        });
    });
}
