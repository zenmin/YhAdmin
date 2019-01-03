function replacechar(data){

	var str = JSON.stringify(data);
	str = str.replace(/</g,'&lt;');
	str = str.replace(/>/g,'&gt;');
	var obj = JSON.parse(str); 
	return obj;
}
