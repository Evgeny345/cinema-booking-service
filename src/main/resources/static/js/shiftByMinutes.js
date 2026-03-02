let hourWidthInPx = $(".hall").css("width").replace("px", "");
$(".hall").each(function(){
	let minutesOfFilmDuration = $(this).attr("data-film-time-start");
	if (minutesOfFilmDuration == 0) {
		$(this).css("margin-left", '.2em');
	} else {
		let minutesInPercents = minutesOfFilmDuration/60*100;
		let marginBasedOnMinutes = hourWidthInPx*minutesInPercents/100 + 'px';
		$(this).css("margin-left", marginBasedOnMinutes);
	}
});