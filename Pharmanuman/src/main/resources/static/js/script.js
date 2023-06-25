
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
};


// search
const search = () => {
	// console.log("searching");

	let query = $("#search-input").val()

	if (query == '') {
		$(".search-result").hide();
	} else {
		// starting searching
		//console.log(query);
		// sending request to server


		//backtick
		//		let url = 'http://localhost:9494/search/${query}'; 
		let url = `http://localhost:9494/search/${query}`;


		//modern js so u can use fetch api of this, no need of ajax
		fetch(url).then(response => {
			return response.json();
		}).then((data) => {
			// data coming....

			//console.log(data);
			let text = `<div class='list-group'>`;
			data.forEach((medicine) => {
				text += `<a href='/pharmacy/medicine-details/${medicine.mid}' class='list-group-item list-group-item-action'> ${medicine.name} </a>`
			});
			text += `</div>`;

			$(".search-result").html(text);

			$(".search-result").show(); 



		});

		$(".search-result").show();

	}

}



// search
const searchPC = () => {
	// console.log("searching");

	let query = $("#search-input").val()

	if (query == '') {
		$(".search-result").hide();
	} else {
		// starting searching
		//console.log(query);
		// sending request to server


		//backtick
		//		let url = 'http://localhost:9494/search/${query}'; 
		let url = `http://localhost:9494/searchpc/${query}`;


		//modern js so u can use fetch api of this, no need of ajax
		fetch(url).then(response => {
			return response.json();
		}).then((data) => {
			// data coming....

			//console.log(data);
			let text = `<div class='list-group'>`;
			data.forEach((medicineForPC) => {
				text += `<a href='/pc/medicine-details/${medicineForPC.mfcid}' class='list-group-item list-group-item-action'> ${medicineForPC.name} </a>`
			});
			text += `</div>`;

			$(".search-result").html(text);

			$(".search-result").show(); 



		});

		$(".search-result").show();

	}

}
