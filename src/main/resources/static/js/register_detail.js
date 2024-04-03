const goBackButton = document.querySelector(".msg-go-back");

if(goBackButton) {
    goBackButton.addEventListener('click', () =>
        location.replace(`/page/administrator_list`)
    );
}

const acceptButton = document.querySelector(".btn-register-accept");
const modal = document.querySelector(".modal-register-accept");
acceptButton.addEventListener('click', () =>
   modal.style.display = "block"
);

const modalCloseButton = document.querySelector(".btn-modal-close");

modalCloseButton.addEventListener('click', () =>
    modal.style.display = "none"
);

const refuseButton = document.querySelector(".btn-register-refuse");

refuseButton.addEventListener('click', () =>
    location.replace("/page/register_list")
);