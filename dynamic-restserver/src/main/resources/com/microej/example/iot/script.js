/*
 * Javascript
 *
 * Copyright 2019-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
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