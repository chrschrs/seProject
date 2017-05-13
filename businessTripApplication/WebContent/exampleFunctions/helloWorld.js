function showMeWhatUGot(){
	console.log("it worked!!!");
	return $.ajax({
		type : "GET",
		url : "helloServlet",
		async : true,
		success : function(data){
			console.log("successful response");
			document.write("<h1>" + data + "</h1>");
		},
		error: function(data){
			console.log("error response");
		}
	});
}