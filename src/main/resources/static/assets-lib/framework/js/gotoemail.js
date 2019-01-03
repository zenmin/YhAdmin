function gotologinemail(email){
	
	var emailtype=email.substr(email.indexOf('@')+1,email.length);
	if(emailtype=="163.com"){
		window.open ("http://mail.163.com");
		return true;
	}
	if(emailtype=="qq.com"){
		window.open ("http://mail.qq.com");
		return true;
	}
	if(emailtype=="126.com"){
		window.open ("http://mail.126.com");
		return true;
	}
	if(emailtype=="hotmail.com"){
		window.open ("http://mail.hotmail.com");
		return true;
	}
	if(emailtype=="gmail.com"){
		window.open ("http://mail.google.com");
		return true;
	}
	if(emailtype=="263.com"){
		window.open ("http://mail.263.com/");
		return true;
	}
	if(emailtype=="sohu.com"){
		window.open ("http://mail.sohu.com/");
		return true;
	}
	if(emailtype=="sina.com"){
		window.open ("http://mail.sina.com.cn/");
		return true;
	}
	if(emailtype=="yeah.net"){
		window.open ("http://mail.yeah.net/");
		return true;
	}
	if(emailtype=="sina.cn"){
		window.open ("http://mail.sina.com.cn/");
		return true;
	}
	if(emailtype=="21cn.com"){
		window.open ("http://mail.21cn.com/");
		return true;
	}
	if(emailtype=="tom.com"){
		window.open ("http://mail.tom.com");
		return true;
	}
	if(emailtype=="sogou.com"){
		window.open ("http://mail.sogou.com/");
		return true;
	}
	if(emailtype=="xinhuanet.com"){
		window.open ("http://mail.xinhuanet.com");
		return true;
	}
	if(emailtype=="hexun.com"){
		window.open ("http://mail.hexun.com/");
		return true;
	}
	if(emailtype=="189.cn"){
		window.open ("http://mail.189.cn");
		return true;
	}
	if(emailtype=="eastday.com"){
		window.open ("http://mail.eastday.com/");
		return true;
	}
	if(emailtype=="188.com"){
		window.open ("http://mail.188.com/");
		return true;
	}
	if(emailtype=="foxmail.com"){
		window.open ("http://mail.foxmail.com");
		return true;
	}
	if(emailtype=="139.com"){
		window.open ("http://mail.10086.cn/");
		return true;
	}
	if(emailtype=="eyou.com"){
		window.open ("http://eyou.com/");
		return true;
	}
	if(emailtype=="citiz.net"){
		window.open ("http://mail.citiz.net/cloudmail/");
		return true;
	}
	return false;
}