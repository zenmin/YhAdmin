﻿$(function(){$(".btnSpan").click(function(){var b=$(this).attr("title");$(":radio").val([b])});$("#xxx").click(function(){var r=$("#kc").attr("alt");if(r<=0){layer.msg("该商品库存不足，无法购买！");return}var z=$("#gid").val();var w=$("#gid option:selected").attr("id");var y=$("#need").val();var C=$("#gid option:selected").attr("title");var A=$("#lx").val();var u=$("input:radio[name='type']:checked").val();var s=$("input:radio[name='type']:checked").attr("title");var x=d();var B=parseInt($("#number").val());var t=checkLx(A);if(r<B){layer.msg("选择数量大于库存数量！");return}if(B<=0){layer.msg("选择数量请大于0件！");return}if(t==false){layer.msg("请输入正确的联系方式");return}if(u==null||u==""){layer.msg("请选择您的付款方式");return}if(w==""||y==""||w==null||y==null){layer.msg("当前商品无法创建订单！");return}var a=layer.load(2,{shade:[0.1,"#fff"]});$.ajax({type:"POST",url:"ajax.php?act=selKm",data:{"gid":w},dataType:"json",success:function(c){if(c.code==-1){layer.close(a);layer.msg("该商品卡密库存不足！无法购买！");return}},error:function(c){layer.close(a);layer.msg("服务器错误");return}});layer.close(a);C=C*B;var b="type="+u+"&name="+z+"&money="+C+"&number="+B+"&out_trade_no="+x+"&gid="+w;var v="请核对订单信息！<br>订单编号："+x+"<br>商品名称："+z+"<br>购买数量："+B+"<br>商品价格："+C+"￥"+"<br>联系方式："+A+"<br>付款方式："+s;layer.confirm(v,{btn:["立即付款","取消付款"]},function(){var c=layer.load(2,{shade:[0.1,"#fff"]});$.ajax({type:"POST",url:"ajax.php?act=create",data:{"out_trade_no":x,"gid":w,"money":C,"rel":A,"type":u,"number":B},dataType:"json",success:function(e){layer.close(c);if(e.code!=0){layer.alert("创建订单失败！"+e.msg);return false}window.location.href="other/submit.php?"+b},error:function(e){layer.close(c);layer.msg("服务器错误");return false}})})});$("#submit_query").click(function(){var e=$("#tqm").val();if(e==null||e==""){layer.msg("请输入提取卡密的凭证！");return false}var c=layer.load(2,{shade:[0.1,"#fff"]});$.ajax({type:"POST",url:"ajax.php?act=tqKm",data:{"t":e},dataType:"json",success:function(a){layer.close(c);if(a.code==0){$("#list").empty();$("#list").append(a.msg);$("#result2").slideDown()}else{layer.msg(a.msg)}},error:function(a){layer.close(c);layer.msg("服务器错误");return false}})});$("#copykm").click(function(){copyToClipboard("km");layer.alert("成功复制卡密到剪切板!")});$("#subimt_email").click(function(){var l=$("#txt_email").val();var o=/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;isok=o.test(l);if(!isok){alert("邮箱格式不正确，请重新输入！");return false}var n=$("#td1").text();var m=$("#td2").text();var e=$("#td3").text();var k=$("#td4").text();var j=layer.load(2,{shade:[0.1,"#fff"]});$.ajax({type:"POST",url:"ajax.php?act=email",data:{"od_n":n,"od_b":m,"od_d":e,"od_k":k,"e":l},dataType:"json",success:function(a){layer.close(j);layer.msg(a.msg)},error:function(a){layer.close(j);layer.msg("发送失败！")}})})});function getPoint(e){var g=$(e).val();var f=layer.open({type:2,content:"加载中..."});$.ajax({type:"POST",url:"/goods/getByCondition",data:{"cid":g},dataType:"json",success:function(a){layer.close(f);list=a.data;var b="";if(list.length>0){for(var c=0;c<list.length;c++){var i=list[c];b=b+"<option id='"+i.id+"' pullDiv='"+i.needPwd+"' kc='"+i.kmCount+"' title='"+i.price+"' alt='"+i.goodsDesc+"' value='"+i.id+"' "+(b==""?"selected":"")+">"+i.name+"</option>"}}else{b=b+"<option value='-1' pullDiv='false' kc='0' title='0' alt='' disabled='disabled' selected>该分类下没有商品</option>"}$("#gid").html(b);getPrice()},error:function(a){layer.close(f);layer.msg("服务器错误"+a)}})}function getInfo(){var b=$("#gid option:selected").attr("alt");if(b==""||b==null){$("#ginfo").text("该商品无介绍")}else{$("#ginfo").html(b)}}function getPrice(){var b=$("#gid option:selected").attr("title");$("#need").text("￥"+b);getInfo();getPrice_zongzi()}function getPrice_zongzi(){var e=$("#gid option:selected").attr("title");$("#need").text("￥"+e);checknum_zongzi();var f=$("#gid option:selected").attr("kc");$("#kc").text(f);$("#kc").attr("alt",f);var g=$("#gid option:selected").attr("pullDiv");if(g=="true"){$("#pullDiv").show()}else{$("#pullDiv").hide()}getInfo()}function getImgs(){var b=$("#gid option:selected").attr("imgs");if(b==""||b==null||b=="undefined"){b="./assets/goodsimg/df.jpg"}$("#goodimgs").attr("src",b)}function getSpan(m,k,l,j){var m=m;var k=k;var l=l;var j=j;var n=document.getElementsByTagName("span");for(var o=0;o<n.length;o++){var i=$(n[o]).attr("title");if(i=="alipay"&&m==1){$(n[o]).hide()}if(i=="qqpay"&&k==1){$(n[o]).hide()}if(i=="tenpay"&&l==1){$(n[o]).hide()}if(i=="tenpay"&&l==0){$(n[o]).show()}if(i=="wxpay"&&j==1){$(n[o]).hide()}}}function d(){var c=new Date();var e;e=c.getFullYear()+""+c.getMonth()+""+c.getDate()+""+c.getHours()+""+c.getMinutes()+""+c.getMilliseconds()+""+randomNum(100,999);return e}function checkLx(e){var c=e;if(!isNaN(c)&&c!=""){return true}else{return false}}function randomNum(c,e){switch(arguments.length){case 1:return parseInt(Math.random()*c+1);break;case 2:return parseInt(Math.random()*(e-c+1)+c);break;default:return 0;break}}function copyToClipboard(e){var f=document.createElement("input");var g=document.getElementById(e).innerHTML||document.getElementById(e).value;
    f.setAttribute("value",g);document.body.appendChild(f);f.select();document.execCommand("copy");document.body.removeChild(f)}function Addme(){url=document.URL;title=$("#bttt").text();window.external.AddFavorite(url,title)}function numstepUp(){var b=parseInt($("#number").val());$("#number").val(b+1)}function numstepDown(){var b=parseInt($("#number").val());if(b>1){$("#number").val(b-1)}}function checknum(){var a=$("#kc").attr("alt");var e=parseInt($("#number").val());if(e<=0||e==null||e==""||isNaN(e)){$("#number").val(1)}else{$("#number").val(e)}if(a<e){$("#number").val(a)}}function checknum_zongzi(){var a=$("#kc").attr("alt");var i=parseInt($("#number").val());if(i<=0||i==null||i==""||isNaN(i)){$("#number").val(1)}else{$("#number").val(i)}if(a<i){$("#number").val(a)}var g=parseInt($("#number").val());var h=$("#gid option:selected").attr("title");$("#allprice").text(g*h)}function zongzisub(){var r=$("#kc").attr("alt");if(r<=0){layer.msg("该商品库存不足，无法购买！");return}var z=$("#gid").val();var w=$("#gid option:selected").attr("id");var y=$("#need").val();var C=$("#gid option:selected").attr("title");var A=$("#lx").val();var u=$("input:radio[name='type']:checked").val();var s=$("input:radio[name='type']:checked").attr("title");var x=d();var B=parseInt($("#number").val());var t=checkLx(A);if(r<B){layer.msg("选择数量大于库存数量！",{time:3000,icon:6});return}if(B<=0){layer.msg("选择数量请大于0件！",{time:3000,icon:6});return}if(A.length<5||t==false||A==" "){layer.msg("请输入正确的联系方式",{time:3000,icon:6});return}if(u==null||u==""){layer.msg("请选择您的付款方式",{time:3000,icon:6});return}if(w==""||C==""||w==null||C==null){layer.msg("当前商品无法创建订单！",{time:3000,icon:6});return}var a=layer.load(2,{shade:[0.1,"#fff"]});$.ajax({type:"POST",url:"ajax.php?act=selKm",data:{"gid":w},dataType:"json",success:function(c){if(c.code==-1){layer.close(a);layer.msg("该商品卡密库存不足！无法购买！");return}},error:function(c){layer.close(a);layer.msg("服务器错误");return}});layer.close(a);C=C*B;var b="type="+u+"&name="+z+"&money="+C+"&number="+B+"&out_trade_no="+x+"&gid="+w;var v="请核对订单信息！<br>订单编号："+x+"<br>商品名称："+z+"<br>购买数量："+B+"<br>商品价格："+C+"￥"+"<br>联系方式："+A+"<br>付款方式："+s;layer.confirm(v,{btn:["立即付款","取消付款"]},function(){var c=layer.load(2,{shade:[0.1,"#fff"]});$.ajax({type:"POST",url:"ajax.php?act=create",data:{"out_trade_no":x,"gid":w,"money":C,"rel":A,"type":u,"number":B},dataType:"json",success:function(e){layer.close(c);if(e.code!=0){layer.open({content:e.msg,skin:"msg",time:2});return false}},error:function(e){layer.close(c);layer.msg("服务器错误");return false}})})};