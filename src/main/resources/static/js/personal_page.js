document.addEventListener("DOMContentLoaded", function() {
    const items = document.querySelectorAll('.teamItem');
    let currentIndex = 0;

    function showItems(startIndex) {
        // 모든 항목 숨김
        items.forEach(item => {
            item.style.display = 'none';
        });

        // startIndex부터 최대 3개 항목 표시
        for(let i = startIndex; i < startIndex + 3 && i < items.length; i++) {
            items[i].style.display = 'block';
        }
    }

    document.getElementById('goRightBtn').addEventListener('click', function() {
        if (currentIndex + 3 < items.length) {
            currentIndex += 3;
            showItems(currentIndex);
        }
    });

    document.getElementById('goLeftBtn').addEventListener('click', function() {
        if (currentIndex - 3 >= 0) {
            currentIndex -= 3;
            showItems(currentIndex);
        }
    });

    // 초기 항목 표시
    showItems(0);
});

