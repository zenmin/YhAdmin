namespace("com.digitalchina.head");
com.digitalchina.head.Head = function () {};

com.digitalchina.head.Head.prototype = {
		//读取cookies
		 getCookie:function(name)
		{
			var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			if(arr=document.cookie.match(reg)) return arr[2];
			else return null;
		},
		//删除cookies
		 delCookie:function(name)
		{
			var exp = new Date();
			exp.setTime(exp.getTime() - 1);			
			var cval=head.getCookie(name);
			if(cval!=null)
				/*document.cookie= name + "="+cval+";path=/;max-age=0";*/
				$.cookie( name ,cval,{path:'/',expires:-1});  
		}
}
var head = new com.digitalchina.head.Head();
