document.addEventListener('DOMContentLoaded', () => {
	const fileInput = document.getElementById('fileInput');
	const label = document.querySelector('.image_btn');
	fileInput.addEventListener('change', () => {
 		if (fileInput.files.length > 0) {
			label.textContent = fileInput.files[0].name;
		} else {
			label.textContent = '画像を選択';
		}
	});
});
document.addEventListener('DOMContentLoaded', () => {
	const textarea = document.querySelector('textarea[name="comment"]');
	textarea.addEventListener('focus', () => {
		setTimeout(() => {
			textarea.setSelectionRange(0, 0);
		}, 0);
	});
});