// display : none
// cursor : pointer
const goToTopButton = document.querySelector(".btn-to-top");

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


const logOutText = document.querySelector('.header-nav-logout');

logOutText.addEventListener('click', () =>
    window.location.href = '/page/login'
);

const myInfoText = document.querySelector('.header-nav-info');

myInfoText.addEventListener('click', () =>
    window.location.href = '/page/my_page'
);