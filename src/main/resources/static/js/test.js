const start = /*[[${start}]]*/ null;
	let currentDate = new Date();
	$(".hall").each(function(){
		let hour = $(this).attr("show-time-start-hour");
		let minutes = $(this).attr("show-time-start-minutes");
		if (typeof hour !== 'undefined' && typeof minutes !== 'undefined') {
			let showTimeDate = new Date();
			showTimeDate.setHours(parseInt(hour), parseInt(minutes));
			console.log("Cur: "+currentDate)
			console.log("Sh: "+showTimeDate)
			console.log(currentDate > showTimeDate)
			if(currentDate > showTimeDate) {
				$(this).addClass('disabled');
			}
		}
	});