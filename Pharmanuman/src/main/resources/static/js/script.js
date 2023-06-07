
function toggleSidebar() {
	const sidebar = document.getElementsByClassName("sidebar")[0];

	const content = document.getElementsByClassName("content")[0];

	if (window.getComputedStyle(sidebar).display === 'none') {
		sidebar.style.display = "block";

		content.style.marginLeft = "20%";
	} else {

		sidebar.style.display = "none";

		content.style.marginLeft = "0%";
	}
}