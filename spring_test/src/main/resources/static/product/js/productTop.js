document.addEventListener("DOMContentLoaded", () => {
	const popup = document.getElementById("popup");
	if (popup) {
		setTimeout(() => {
			popup.remove();
		}, 3000);//3秒後にDOMから削除
	}
});