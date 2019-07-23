/*
 * Javascript
 *
 * Copyright 2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */

const REFRESH_TIME = 1000

function updateValues() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		try {
			if (xhttp.readyState === XMLHttpRequest.DONE) {
				if (xhttp.status === 200) {
				 const data = JSON.parse(xhttp.response);
				 if (data) {
					if(data.ram){
				 		document.getElementById("ram").innerHTML = data.ram;
				 	} 
				 	if(data.thread){
				 		document.getElementById("thread").innerHTML = data.thread;
				 	}
				 	if(data.up){
				 		document.getElementById("up").innerHTML = data.up;
				 	}
				 }
				}
				
				setTimeout(updateValues, 5000);
			}
		} catch (error) {
			setTimeout(updateValues, 5000);
			console.error(error)
		}
	};

	xhttp.open("GET", "/values", true);
	xhttp.send();
}


document.addEventListener('DOMContentLoaded', async () => {
	setTimeout(updateValues, 5000);
})