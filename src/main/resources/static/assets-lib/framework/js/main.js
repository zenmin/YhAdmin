//全局控制请求弹出框
  $(function() {
  	 	//应用关联服务的伸缩效果2014-6
		$("#linkAdjustBtn").toggle(function(){
			$(".linkServicesWrap").hide();
			$(this).attr({'class':'linkAdjust closeLink'});	
		},function(){
			$(".linkServicesWrap").show();
			$(this).attr({'class':'linkAdjust'});	
		})

    	$('.pubDialog').live({
    		click:function(e){
        		e.preventDefault();
        		e.stopPropagation();
        		var search=$(this).attr("data-month");
        		var address=$(this).attr('address')+search;
        		var href=$(this).attr('address');
        		var title=$(this).attr('publicTitle');
    		    $.ajax({
    				type: "get",
    				url: href,
    				dateType: 'html',
    				success: function(html){
    				 	$('#commonPopDialog .modal-header h3:first').text(title);
    				    $('#commonPopDialog').find('.modal-body').html(html);
    					$('#commonPopDialog').modal('show');
    				}
    			})
    		}
    	});
		$('#right_icon_1').mouseover(function(){
			$('.serviceDropdownIconPos01').addClass('right_icon_1_curret');
			$(this).mouseleave(function(){
				//alert("dsadsa");
			$('.serviceDropdownIconPos01').removeClass('right_icon_1_curret');
			})
		})
		$('#right_icon_2').mouseover(function(){
			$('.serviceDropdownIconPos02').addClass('right_icon_2_curret');
			$(this).mouseleave(function(){
				//alert("dsadsa");
			$('.serviceDropdownIconPos02').removeClass('right_icon_2_curret');
			})
		})
		$('#right_icon_3').mouseover(function(){
			$('.serviceDropdownIconPos03').addClass('right_icon_3_curret');
			$(this).mouseleave(function(){
				//alert("dsadsa");
			$('.serviceDropdownIconPos03').removeClass('right_icon_3_curret');
			})
		})
		$('#right_icon_4').mouseover(function(){
			$('.serviceDropdownIconPos04').addClass('right_icon_4_curret');
			$(this).mouseleave(function(){
				//alert("dsadsa");
			$('.serviceDropdownIconPos04').removeClass('right_icon_4_curret');
			})
		})
		//$('#right_1').mouseleave(function(){
			//alert("dasda");
			//$('.serviceDropdownIconPos01').removeClass('serviceDropdownIcon_curret');
		//})
	})

//2013-05-29  全局控制弹出框 居中
	$(function() {
		$('.modal').on('show', function () {
			 $obj=$(this);
			 //console.log($obj.width()+"    "+$obj.height());
			  var left=($(document).width()-$obj.width())/2;
				var top=($(window).height()-$obj.height())/2;
				$obj.css({
					top:top+'px',
					left:left+'px'
				})
		  })
	})
var fadeOut = null;//控制交互框的定时任务
$(function() {
	$(".serviceComment").mouseover(function(){
		var temp = $(this).html() + "个人评论";
		$(this).attr("title",temp);
	});
	$(".serviceFavCounts").mouseover(function(){
		var temp = $(this).html() + "个人收藏";
		$(this).attr("title",temp);
	});
	
	//2013-05-28全局控制服务下面的评分---随机生成4-5分
	// var startNum=Math.floor(Math.random()*2+4);
	// var startNumString=startNum+".0分";
	// $('.serviceStars:eq(0)').next('span').text(startNumString);
	// $('.serviceStars:eq(0)>span').each( function(i) {
	// 	if( i <= startNum-1 ){
	// 	$(this).attr('class','serverStarsSel')	
	// 	}else {
	// 		$(this).attr('class','serverStarsNoSel')
	// 	}
	// }); 
	//2013-05-29全局控制服务下面请为我们 评分，默认为5
	 $('.commentTopLeft .serviceStars span').removeClass().addClass('serverStarsSel');
	 $('.commentTopLeft span.score').text('5.0分');
	 $(".commentTopLeft .serviceStars span").mouseenter(function  () {
	 	var sIndex=$(".commentTopLeft .serviceStars span").index(this)+1;
	 	$(".commentTopLeft .serviceStars span:lt("+sIndex+")").removeClass().addClass("serviceStarsSel");
	 	$(".commentTopLeft .serviceStars span:gt("+(sIndex-1)+")").removeClass().addClass("serverStarsNoSel");
	 	$('.commentTopLeft span.score').text(sIndex+".0分")
	 });
	 $(".commentTopLeft .serviceStars span").mouseout(function  () {
	 	
	 })
	 $(".commentTopLeft .serviceStars span").click(function  () {
	 	var sIndex=$(".commentTopLeft .serviceStars span").index(this)+1;
	 	$(".commentTopLeft .serviceStars span:lt("+sIndex+")").removeClass().addClass("serviceStarsSel");
	 	$('.commentTopLeft span.score').text(sIndex+".0分")
	 });
	//yangyt-2013-05-14  收藏全局控制
	var hasCollect = false;
	$('.serviceFavCounts').click(function() {
		if (!hasCollect) {
			//未收藏时的操作
			hasCollect = true;
			$(this).text(Number($(this).text()) + 1);
			$(this).css({
				backgroundPosition: '-65px 1px'
			});
			popAny('您已经成功收藏该服务！','smile');
		}else{
			//已经被收藏时的操作
			hasCollect = false;
			$(this).text(Number($(this).text()) - 1);
			$(this).css({
				backgroundPosition: '-25px 2px'
			});
			popAny('您已经取消收藏该服务！','cry');
		}
	});
	//2013-05-28 IE10.0全局控制弹出框中的输入框,解决第一获取焦点立即失去焦点的BUG
	  if($.browser.msie && $.browser.version>=10)  
	    {  
		  $('.modal :input').live( {
	    		blur:function(){
	    			$(this).focus();
	    			$('.modal :input').die('blur');
	    		}
	    	});
	    }
	//服务内页左侧切换 
	
    $("#fornum03").click(function() {
		$("#bill10").siblings("div").removeClass("active").end().addClass("active");
        $(".tab-content-wrap").getNiceScroll().resize();
        $("#voiceIcon01").removeClass("voiceIconPos_01").addClass("voiceIconPos_focus_01").siblings("a").attr("style","color:#fb9440");
        $("#voiceIcon02").removeClass("voiceIconPos_focus_02").addClass("voiceIconPos_02").siblings("a").removeAttr("style");
        $("#voiceIcon03").removeClass("voiceIconPos_focus_03").addClass("voiceIconPos_03").siblings("a").removeAttr("style");
        $("#voiceIcon04").removeClass("voiceIconPos_focus_04").addClass("voiceIconPos_04").siblings("a").removeAttr("style");
	});
	
    $("#fornum07").click(function() {
		$("#bill07").siblings("div").removeClass("active").end().addClass("active");
        //$(".tab-content-wrap").getNiceScroll().resize();
        $("#voiceIcon01").removeClass("voiceIconPos_01").addClass("voiceIconPos_focus_01").siblings("a").attr("style","color:#fb9440");
        $("#voiceIcon02").removeClass("voiceIconPos_focus_02").addClass("voiceIconPos_02").siblings("a").removeAttr("style");
        $("#voiceIcon03").removeClass("voiceIconPos_focus_03").addClass("voiceIconPos_03").siblings("a").removeAttr("style");
        $("#voiceIcon04").removeClass("voiceIconPos_focus_04").addClass("voiceIconPos_04").siblings("a").removeAttr("style");
	});
	$("#fornum10").click(function() {
		$("#bill08").siblings("div").removeClass("active").end().addClass("active");
		$(".voiceModeToggle").text("地图模式");
		$(".active .voiceMapMode").hide();
		$(".active .voiceList").show();
		$(".active .voiceMapMode .msgPanel").hide();
        $(".tab-content-wrap").getNiceScroll().resize();
        $("#voiceIcon01").removeClass("voiceIconPos_focus_01").addClass("voiceIconPos_01").siblings("a").removeAttr("style");
        $("#voiceIcon02").removeClass("voiceIconPos_focus_02").addClass("voiceIconPos_02").siblings("a").removeAttr("style");
        $("#voiceIcon03").removeClass("voiceIconPos_focus_03").addClass("voiceIconPos_03").siblings("a").removeAttr("style");
        $("#voiceIcon04").removeClass("voiceIconPos_04").addClass("voiceIconPos_focus_04").attr("style","color:#fb9440");
	});
	/*==================================================================================================*/
	$("#fornum11").click(function() {
		$("#bill11").siblings("div").removeClass("active").end().addClass("active");
        $(".tab-content-wrap").getNiceScroll().resize();
        $("#voiceIcon11").removeClass("voiceIconPos_11").addClass("voiceIconPos_focus_11").attr("style","color:#fb9440");
        $("#voiceIcon12").removeClass("voiceIconPos_focus_12").addClass("voiceIconPos_12").siblings("a").removeAttr("style");
        $("#voiceIcon13").removeClass("voiceIconPos_focus_13").addClass("voiceIconPos_13").siblings("a").removeAttr("style");
	});
	$("#fornum12").click(function() {
		$("#bill12").siblings("div").removeClass("active").end().addClass("active");
		$(".active .voiceMapMode").hide();
		$(".active .voiceList").show();
		$(".active .voiceMapMode .msgPanel").hide();
        $(".tab-content-wrap").getNiceScroll().resize();
        $("#voiceIcon12").removeClass("voiceIconPos_12").addClass("voiceIconPos_focus_12").attr("style","color:#fb9440");
        $("#voiceIcon11").removeClass("voiceIconPos_focus_11").addClass("voiceIconPos_11").siblings("a").removeAttr("style");
        $("#voiceIcon13").removeClass("voiceIconPos_focus_13").addClass("voiceIconPos_13").siblings("a").removeAttr("style");
	});
	$("#fornum13").click(function() {
		$("#bill13").siblings("div").removeClass("active").end().addClass("active");
		$(".active .voiceMapMode").hide();
		$(".active .voiceList").show();
		$(".active .voiceMapMode .msgPanel").hide();
        $(".tab-content-wrap").getNiceScroll().resize();
        $("#voiceIcon13").removeClass("voiceIconPos_13").addClass("voiceIconPos_focus_13").attr("style","color:#fb9440");
        $("#voiceIcon12").removeClass("voiceIconPos_focus_12").addClass("voiceIconPos_12").siblings("a").removeAttr("style");
        $("#voiceIcon11").removeClass("voiceIconPos_focus_11").addClass("voiceIconPos_11").siblings("a").removeAttr("style");
	});
	$(".serviceTabs li").click(function  () {
		var thisId = $(this).attr("id").replace(/[^\d]/g,"");
		var vlength=$(".serviceTabs li").length;
		var focusClass = "actToggleSel_" + thisId;
		var loseClass = "actToggleSel_Lose_" + thisId;
		$(this).siblings().each(function  () {
			var thisId = $(this).attr("id").replace(/[^\d]/g,"");
			var focusClass = "actToggleSel_" + thisId;
			var loseClass = "actToggleSel_Lose_" + thisId;
			$(this).removeClass(focusClass).addClass(loseClass);
		});
		$(this).removeClass(loseClass).addClass(focusClass);
	});

	$(".voiceCommon .pullTab").stop().toggle(
		function  () {
		$(".techSupport,.serviceIntroMore,.serviceTags").slideUp("fast",function  () {
			$(".serviceIntroTop").append($(".serviceFav"));
		});
		$(".serviceTop").stop().animate({"height":"0"},"fast",function(){
			$(".tab-content-wrap").getNiceScroll().resize();
		});
		$(".tab-content-wrap").stop().animate({"top":"70px"},function(){
        	$(".tab-content-wrap").getNiceScroll().resize();
        });
	},function  () {
		$(".techSupport,.serviceIntroMore,.serviceTags").slideDown("fast");
		$(".serviceIntroBottom").append($(".serviceFav"));
		$(".serviceTop").stop().animate({"height":"118px"},"fast",function(){
			$(".tab-content-wrap").getNiceScroll().resize();
		});
		// $(".tab-content-wrap").css({"top":"180px"});
		
		});
/*
$(".voiceCommon .pullTab").stop().toggle(function  () {
		$(".techSupport,.serviceIntroMore,.serviceTags").slideDown("fast");
		$(".serviceIntroBottom").append($(".serviceFav"));
		$(".serviceTop").stop().animate({"height":"118px"},"fast",function(){
			$(".tab-content-wrap").getNiceScroll().resize();
		});
		$(".tab-content-wrap").css({"top":"180px"});
		
	},function  () {
		$(".techSupport,.serviceIntroMore,.serviceTags").slideUp("fast",function  () {
			$(".serviceIntroTop").append($(".serviceFav"));
		});
		$(".serviceTop").stop().animate({"height":"0"},"fast",function(){
			$(".tab-content-wrap").getNiceScroll().resize();
		});
		$(".tab-content-wrap").stop().animate({"top":"70px"},function(){
        	$(".tab-content-wrap").getNiceScroll().resize();
        });
	});
*/
	$(".serverCommon .pullTab").toggle(function  () {
		$(".techSupport,.serviceIntroMore,.serviceTags").slideUp("fast",function  () {
			$(".serviceIntroTop").append($(".serviceFav"));
		});
		$(".serviceTop").animate({"height":"25px"},"fast");
		//var height='$(".serviceTop").height();';
		//alert(height);
			$(".serviceTop").addClass("service_shadow");
        $(".tab-content-wrap").animate({"top":"70px"});
		//if($(selector).height()==96)
	
		//{
			//$(".serviceTop").removeClass("service_shadow");
			//}
		//$(".serviceTop").removeClass("service_shadow");
	},function  () {
		$(".techSupport,.serviceIntroMore,.serviceTags").slideDown("fast");
		$(".serviceIntroBottom").append($(".serviceFav"));
		$(".serviceTop").animate({"height":"76px"},"fast");
        // $(".tab-content-wrap").css({"top":"180px"});
		$(".serviceTop").removeClass("service_shadow");
	});


	$('#brandBg').click(function(){
		var src = window.location.href;
		var flag = src.match('private');
		if ('null' == flag || null == flag) {
			$("#brandBg").attr("href","index.html");
		} else {
			$("#brandBg").attr("href","../../index.html");
		}
	});
	$(".corner").before("<a href='../../ServerMain.html' class='corner'></a>").remove();
})
function popAny(objContent, style) {
	if(fadeOut != null){
		clearTimeout(fadeOut);
		$('.popAnyWhere').find('p').text(objContent);
	}else{
		$("body").append("<div class='popAnyWhere'></div>");
		var boxContent="<p>"+objContent+"</p>";
		$(".popAnyWhere").removeClass("smile").removeClass("cry").addClass(style);
		$(".popAnyWhere").append(boxContent);
		var boxWidth=$(".popAnyWhere").width();
		var leftx=(document.body.clientWidth-boxWidth)/2+"px";
		var bottomx="20px";
		$(".popAnyWhere").attr("style","display:block; left:"+leftx+";bottom:"+bottomx);
	}
	fadeOut = setTimeout(popFadeout,2000);
}
function popFadeout(){
	$('.popAnyWhere').fadeOut();
	$('.popAnyWhere').remove();
	fadeOut = null;
}
$(function  () {
		$(".footLinkL").hover(function  () {
			$(".footImgL").stop(true,false).animate({bottom:2},{easing:'easeOutElastic',duration:1500});
		},function  () {
			$(".footImgL").stop(true,false).animate({bottom:-5},{easing:'easeOutElastic',duration:1500});
		});
		$(".footLinkR").hover(function  () {
			$(".footImgR").stop(true,false).animate({bottom:2},{easing:'easeOutElastic',duration:1500});
		},function  () {
			$(".footImgR").stop(true,false).animate({bottom:-5},{easing:'easeOutElastic',duration:1500});
		});
})


// 搜索类型下拉选择
$(function () {
	$(".searchType").click(function(event) {
		event.stopPropagation();
		$(".searchDropDown").toggle();
	});
	$(document).click(function(event) {
		if ($(".searchDropDown").is(':visible'))
		 {
		 	$(".searchDropDown").hide();
		 };
	});
	$(".searchDropDown a").click(function() {
		//alert($(this).index());
		var $html=$(this).html();
		$(".searchType").html($html);
		$(this).parents('.searchDropDown').hide();
		return false;
	});
})


	function autoIndex(){
		 $(".indexContentWrap").css({height: document.documentElement.clientHeight - 200})
		}
$(function  () {
		var winHeight= document.documentElement.clientHeight;
		var navBarHeight=$(".navbar").height();
		var linkHeight=$(".linkServices").height();
		var innHeight=winHeight-navBarHeight-linkHeight-20;
		$(".serviceIn .tab-content").attr("style","min-height:"+innHeight+"px");
		
	})
$(function  () {
	$(".personUserInfo .userAvatar").hover(function  () {
		$(this).find(".uploadImg").stop(false,true).animate({"bottom":"0"},"fast");
	},function  () {
		$(this).find(".uploadImg").stop(false,true).animate({"bottom":"-30px"},"fast");
	});
})
$(function  () {
	$(".serviceIconWrap").toggle(function  () {
		$(".serviceFav").removeClass("serviceFavCollect").addClass("serviceFavNoCollect").text("取消");
	},function  () {
		$(".serviceFav").removeClass("serviceFavNoCollect").addClass("serviceFavCollect").text("收藏");
	});
})
//关于邮箱下拉提示

function mail_step(){
    $("#mail_step2").show().siblings(".mailCheck").hide();
    $("#progressStep02").addClass("current").siblings("a").removeClass("current");
}
function mail_step_next(){
 $("#mail_step1").hide();
    $("#mail_step2").hide();
   $("#mail_step3").show();
   $("#progressStep01").removeClass("current");
   $("#progressStep02").removeClass("current");
   $("#progressStep03").addClass("current");

}
function mail_step_1(){ 
    $("#mail_step2").show();
    $("#mail_step1").hide();
    $("#mail_step3").hide();
    $("#mail_step4").hide();
    $("#progressStep01").removeClass("current");
    $("#progressStep02").addClass("current");

}
function mail_step_2() {
	$("#mail_step3").show().siblings(".mailCheck").hide();
	$("#progressStep03").addClass("current").siblings("a").removeClass("current");
}
function mail_step_2_5() {
	$("#mail_step2_5").show().siblings(".mailCheck").hide();
	$("#progressStep02").addClass("current").siblings("a").removeClass("current");
}
function mail_step_3() {
	$("#mail_step4").show().siblings(".mailCheck").hide();
	$("#progressStep04").addClass("current").siblings("a").removeClass("current");
}
function realName_scuess(){
  //  alert("dsda");
    $("#mail_step1").hide();
    $("#mail_step2").hide();
    $("#mail_step3").hide();
    $("#mail_step4").show();
    $("#progressStep01").removeClass("current");
    $("#progressStep02").removeClass("current");
    $("#progressStep03").removeClass("current");
    $("#progressStep04").addClass("current");
}
function binding(){
      // alert("das");
   $("#mail_step1").hide();
    $("#mail_step2").hide();
    $("#mail_step3").hide();
    $("#mail_step4").show();
    $("#progressStep01").removeClass("current");
    $("#progressStep02").removeClass("current");
    $("#progressStep03").addClass("current");}



// 首页channel的Mask遮罩效果 start
 var divTitleBox={
   	"servicePos01":"足不出户，办理政府部门审批事务...",
   	"servicePos02":"预约就诊，健康状态查询，这里就能搞定...",
   	"servicePos03":"汇聚您的多种账单，一键实现全部支付...",
   	"servicePos04":"学校与家长的桥梁，自学充电的天堂...",
   	"servicePos05":"无论自驾还是公交，出行就是这么轻松...",
   	"servicePos06":"菜肉家政、超市促销，小应用大生活..."
   }
  function addMask() {
  	 jQuery.each(divTitleBox, function(index, val) {
     var newDiv = "<div class='roundChannleMask'></div>"+
               "<div class='roundChannleMaskText'>";
     $("."+index).find(".roundChannleMask,.roundChannleMaskText").remove();
     $("."+index).append(newDiv);
     $("."+index).find(".roundChannleMaskText").text(val);
   });
  }
  $(function  () {
  	
  	$(".roundabout-in-focus p").live({
  mouseenter: function(){
    addMask();
    $(this).find(".roundChannleMask").stop(true,false).slideDown("500",function  () {
    	$(this).siblings(".roundChannleMaskText").stop(true,false).slideDown("500");
    })
  },
  mouseleave: function(){
   $(this).find(".roundChannleMask,.roundChannleMaskText").stop(true,false).slideUp("500");
  }
	});
  })
// 首页channel的Mask遮罩效果 end
$(function  () {
	$("#x").click(function function_name () {
		loading("xxxxxxxx");
		$(this).attr("disabled",'true');
	});
})

function loading(objContent) {
	if(fadeOut != null){
		clearTimeout(fadeOut);
		$('.popAnyWhere').find('p').text(objContent);
	}else{
		$("body").append("<div class='popAnyWhere'></div>");
		var boxContent="<p>"+objContent+"</p>";
		$(".popAnyWhere").addClass("loadingImg");
		$(".popAnyWhere").append(boxContent);
		var boxWidth=$(".popAnyWhere").width();
		var leftx=(document.body.clientWidth-boxWidth)/2+"px";
		var bottomx="20px";
		$(".popAnyWhere").attr("style","display:block; left:"+leftx+";bottom:"+bottomx);
	}
	fadeOut = setTimeout(loadingFadeout,1500);
}
function loadingFadeout(id){
	
	$('.popAnyWhere').fadeOut();
	$('.popAnyWhere').remove();
	fadeOut = null;
	$("#"+id).removeAttr("disabled");
}
 $(function  () {
        $(".setToggle").toggle(function  () {
            $(this).removeClass("on").addClass("off");
        },function  () {
            $(this).removeClass("off").addClass("on");
        });
    })
 $(function() {
 	$(".eventDivide").on("click","li",function () {
 		$(this).siblings("li").removeClass().end().addClass("liActive");
 	})
 	$(".eventInDivide").on("click","li",function () {
 		$(this).siblings("li").removeClass().end().addClass("liActive");
 	})
 })
 function warning (title,text) {
 	$('#commonPopDialog').css('width', '400px');
 	$('#commonPopDialog').modal('show');
 	$('#commonPopDialog .modal-header h3').text(title);
 	$('#commonPopDialog .modal-body').text(text).css({"text-align": 'center',"padding": '40px 0'});
 }
// function confirm (title,text) {
// 	$('#commonPopDialog').css('width', '400px');
// 	$('#commonPopDialog').modal('show');
// 	$('#commonPopDialog .modal-header h3').text(title);
// 	$('#commonPopDialog .modal-body').text(text).css({"text-align": 'center',"padding": '40px 0'});

// 	var cancelBtn='<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>';
// 	$('#commonPopDialog .modal-footer').append(cancelBtn);
// }


//头部的交互
$(function(){
	// 点击下拉按钮，下拉菜单toggle
	$("#type_value").click(function(){
		
		//$(".searchDropDown").toggle();	
		if( $(".searchDropDown").css('display') == 'none' ){
			$(".searchDropDown").show();	
		}else{
			$(".searchDropDown").hide();	
		}
	})
	
	// 点击下拉菜单选项，下拉按钮的内容变为选中的选项内容
	$(".searchDropDown a").click(function(){
		$("#type_value").text( $(this).text() );
		$(".searchDropDown").hide();
	})
	
	// 鼠标滑过搜索按钮，出现搜索框
	$("#searchBtn").mouseover(function(){
		$(".searchBox").css('width','286px');
		$(".moveRight").animate( {'left':'0px'},300 );
	})
	
	// 鼠标移出探索框，搜索框消失
	$("#searchBox").mouseleave(function(){
		$(".moveRight").animate( {'left':'-286px'},300 );
		window.setTimeout('$("#searchBox").css("width","0px")',300);
	})
	
 
	//鼠标滑过分享按钮，出现分享框
	var _c;
	$("#share").hover(function(){
		$(".shareBox").fadeIn();	
	},function(){
		_c = setTimeout(function(){
			$(".shareBox").fadeOut();	
		},200)
	})
	$(".shareBox").hover(function(){
		clearTimeout(_c);	
	},function(){
		$(".shareBox").fadeOut();	
	})
	//具体内容页 点击用户名出现下拉菜单
/*	 $(".haslogin").hover(function(){
		$('.cminfo').show();

	},function(){
		$('.cminfo').hide();	
	})
*/	
})
// 文本框默认文字显示与移除
function removeDefaultText(obj,defaultText ){
	if( $(obj).attr('type') == 'password' ){
		$(obj).siblings('.pwDefault').text('');	
	}else{
		if( $(obj).attr('value') == defaultText  ){
			$(obj).attr('value','')	
		}	
	}
}
function addDefaultText(obj,defaultText){
	if( $(obj).attr('type') == 'password' ){
		if( $(obj).attr('value') == '' ){
			$(obj).siblings('.pwDefault').text( defaultText );
		}
	}else{
		if( $(obj).attr('value') == '' ){
			$(obj).attr('value',defaultText);
		}	
	}
}
function getStr(str){
	var length=str.length;
	var _str = "";
	for(var i=0;i<length;i++){
		_str +="*"; 
	}
	return _str;
}