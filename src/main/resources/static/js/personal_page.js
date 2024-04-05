document.addEventListener("DOMContentLoaded", function() {

    document.querySelector('.login_top').addEventListener('click', function() {
        // 특정 URL로 이동
        window.location.href = '/page/login'; // 로그인 페이지 URL로 변경
    });

    // 로그아웃 버튼
    document.querySelector('.logout_top').addEventListener('click', function() {
        // 특정 URL로 이동
        window.location.href = '/page/logout'; // 로그아웃 처리를 담당하는 URL로 변경
    });

    // 내 정보 버튼
    document.querySelector('.mypage').addEventListener('click', function() {
        // 특정 URL로 이동
        window.location.href = '/page/my_page'; // 내 정보 페이지 URL로 변경
    });
    const goToTopButton = document.querySelector(".scroll_top");

    window.addEventListener('scroll', () => {
        if(document.documentElement.scrollTop > 150 ||
            document.body.scrollTop > 150) {
            goToTopButton.style.display = 'block';
        } else {
            goToTopButton.style.display = 'none';
        }
    });

//
    goToTopButton.addEventListener('click', () => {
        window.scrollTo({top : 0, behavior : "smooth"});
    });
    const links = document.querySelectorAll('.left_side_nav ul li a');

    // 각 링크에 대해 클릭 이벤트를 추가
    links.forEach(link => {
        link.addEventListener('click', function(event) {
            // 기본 동작인 링크 이동을 취소
            event.preventDefault();

            // 클릭된 링크의 href 속성값을 가져옴
            const targetId = this.getAttribute('href').substring(1); // 링크의 #을 제거하고 id만 가져옴
            const targetElement = document.getElementById(targetId); // id에 해당하는 요소를 가져옴

            if (targetElement) {
                // 요소가 존재하면 해당 위치로 스크롤함
                window.scrollTo({
                    top: targetElement.offsetTop, // 요소의 상단 위치로 스크롤
                    behavior: 'smooth' // 부드러운 스크롤 효과 적용
                });
            }
        });
    });

    function setupSliderForClass(className, leftButtonId, rightButtonId,row) {
        function getItems() {
            return document.querySelectorAll('.' + className);
        }

        let items = getItems(); // 초기 항목 리스트
        let currentIndex = 0;

        function showItems(startIndex) {
            items = getItems(); // 항목 리스트를 최신 상태로 갱신
            // 기존 빈 항목들을 제거
            document.querySelectorAll('.empty' + className).forEach(item => item.remove());

            if (className==='ingTeamItem' &&items.length === 0) {
                const emptyItem = document.createElement('div');
                emptyItem.classList.add(className, 'empty' + className);
                emptyItem.style.textAlign = 'center';
                emptyItem.style.display= 'flex';
                emptyItem.style.flexDirection = 'column';
                emptyItem.style.alignItems = 'center';
                emptyItem.style.fontSize = '23px';
                emptyItem.style.lineHeight = "28px";
                emptyItem.innerHTML = "진행중인 스터디가 <br/> 없ㅔㅁ요 ㅜㅜ"; // 아이템이 없을 때의 메시지
                document.querySelector('.row' +row ).appendChild(emptyItem);

                const addButton = document.createElement('button');
                addButton.textContent = '추가하러가기';
                addButton.style.width = '120px';
                addButton.style.height = '45px';
                addButton.style.borderRadius = '25px';
                addButton.style.backgroundColor = '#77A7EE';
                addButton.style.fontSize = '15px';
                addButton.style.textAlign = 'center';
                addButton.style.color = '#FFFFFF';
                addButton.style.left = '20px';
                addButton.style.marginTop = "20px";
                addButton.addEventListener('click', function() {
                    window.location.href = '/page/add/post';
                });
                document.querySelector('.empty'+className ).appendChild(addButton);
                for (let i =0; i < 2; i++) {
                    const emptyItem = document.createElement('div');
                    emptyItem.classList.add(className, 'empty' + className); // 빈 항목 식별을 위한 클래스 추가
                    emptyItem.innerHTML = '&nbsp;';
                    document.querySelector('.row'+row).appendChild(emptyItem);
                }
                return;
            }
            // 모든 항목 숨김
            items.forEach((item) => {
                item.style.display = 'none';
            });

            // startIndex부터 최대 3개 항목 표시
            let displayedItemsCount = 0;
            for(let i = startIndex; i < startIndex + 3 && i < items.length; i++) {
                items[i].style.display = 'inline-block';
                displayedItemsCount++;
            }

            // 빈 항목 추가하기
            for (let i = displayedItemsCount; i < 3; i++) {
                const emptyItem = document.createElement('div');
                emptyItem.classList.add(className, 'empty' + className); // 빈 항목 식별을 위한 클래스 추가
                emptyItem.innerHTML = '&nbsp;';
                document.querySelector('.row'+row).appendChild(emptyItem);
            }
        }

        document.querySelector('#' + leftButtonId).addEventListener('click', function() {
            items = getItems(); // 항목 리스트를 최신 상태로 갱신
            if (currentIndex - 3 >= 0) {
                currentIndex -= 3;
                showItems(currentIndex);
            }
        });

        document.querySelector('#' + rightButtonId).addEventListener('click', function() {
            items = getItems(); // 항목 리스트를 최신 상태로 갱신
            if (currentIndex + 3 < items.length) {
                currentIndex += 3;
                showItems(currentIndex);
            }
        });

        // 초기 항목 표시
        showItems(0);
    }

    // 각 클래스에 대해 함수 실행
    setupSliderForClass('ingTeamItem', 'go_left1', 'go_right1',2);
    setupSliderForClass('studyItem', 'go_left2', 'go_right2',3);
    setupSliderForClass('scrapItem', 'go_left3', 'go_right3',4);
    setupSliderForClass( 'finishedTeamItem','go_left4', 'go_right4',5);
});

$('.is_closed').click(function() {
    // 버튼의 data-team-id 속성을 통해 팀의 ID를 가져오기
    var team_id = $(this).attr('data-team-id');

    // AJAX 요청 보내기
    $.ajax({
        url: '/api/team/' + team_id,
        type: 'PUT',
        success: function(response) {
            // 성공적으로 요청을 보내고 응답을 받았을 때의 처리
            console.log(response); // 응답을 로그에 출력하거나 필요한 다른 작업 수행
        },
        error: function(xhr, status, error) {
            // 요청이 실패하거나 에러 응답을 받았을 때의 처리
            console.error(xhr.responseText); // 에러 메시지를 콘솔에 출력하거나 다른 에러 처리 작업 수행
        }
    });
    window.location.href = '/page/my_page';
});
$('.favorite').click(function() {
    // 버튼의 data-team-id 속성을 통해 팀의 ID를 가져오기
    var scrap_id = $(this).attr('data-scrap-id');

    // AJAX 요청 보내기
    $.ajax({
        url: '/api/posts/'+scrap_id+'/scraps',
        type: 'POST',
        success: function(response) {
            // 성공적으로 요청을 보내고 응답을 받았을 때의 처리
            console.log(response); // 응답을 로그에 출력하거나 필요한 다른 작업 수행
        },
        error: function(xhr, status, error) {
            // 요청이 실패하거나 에러 응답을 받았을 때의 처리
            console.error(xhr.responseText); // 에러 메시지를 콘솔에 출력하거나 다른 에러 처리 작업 수행
        }
    });
    window.location.href = '/page/my_page';
});
$('.edit').click(function (){
    window.location.href = "/page/my_edit_page"
})

$('.logout_top').click(function (){
    $.ajax({
        url: '/api/auth/logout',
        type: 'POST',
        success: function(response) {
            // 성공적으로 로그아웃을 처리한 경우의 처리
            console.log("로그아웃 성공");
            // 필요한 작업 수행, 예: 로그아웃 후 리다이렉트 등
            window.location.href = '/page/login';
        },
        error: function(xhr, status, error) {
            console.error("로그아웃 실패:", xhr.responseText);
            alert("로그아웃에 실패했습니다. 다시 시도해주세요.");
        }
    });
})


