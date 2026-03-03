document.addEventListener("click", function (e) {
    const closeButton = e.target.closest(".close-popover");
    if (!closeButton) return;
    const popover = closeButton.closest("[popover]");
    if (popover && popover.hidePopover) {
        popover.hidePopover();
    }
});