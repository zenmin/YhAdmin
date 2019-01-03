//以下是对上传控件的处理
function MySwfUpload(){
	var swfu = null;
	var thisObj = this;
	this.formId = "publishMicro";
	var option = {
		// File Upload Settings
		upload_url: upload_url+"/upload",
		file_post_name: "FileData",
		// File Upload Settings
		
		file_size_limit : "3 MB",	// 1000MB
		file_types : "*.jpg;*.gif;*.jpeg;*.bmp;*.png",
		file_types_description : "图片文件",
		file_upload_limit : "3",

		// 以下对相关事件进行定义，相关事件的实现查看handlers-resize.js
		swfupload_preload_handler : preLoad,
		swfupload_load_failed_handler : loadFailed,
		file_queue_error_handler : fileQueueError,
		file_queued_handler:fileQueueHandler,
		file_dialog_complete_handler : fileDialogComplete,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : function(file, serverData){thisObj.uploadSuccess(file, serverData) },
		upload_complete_handler : uploadComplete,
		upload_resize_start_handler : resizeStart,
		// Button Settings
		button_image_url : app_path+"/assets-lib/digitalchina/js/swfupload2.5/SmallSpyGlassWithTransperancy_17x18.png",//按钮的图片地址
		button_placeholder_id : "spanButtonPlaceholder",//按钮的位置
		button_width: 90,
		button_height: 18,
		button_text : '<span class="button">选择图片</span>',
		button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
		button_text_top_padding: 0,
		button_text_left_padding: 18,
		button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor: SWFUpload.CURSOR.HAND,
		
		// Flash Settings
		flash_url : app_path+"/assets-lib/digitalchina/js/swfupload2.5/swfupload.swf",
		flash9_url : app_path+"/assets-lib/digitalchina/js/swfupload2.5/swfupload_fp9.swf",
		post_params: {
           "zoom" : "thumb"
       	}, 
		custom_settings : {//图片压缩的设置
			reSizeLimit: 1024*500,//(自定义的)超过500K才进行压缩
			thumbnail_height: 1000,
			thumbnail_width: 1000,
			thumbnail_quality: 100
		}
	};
	this.init = function(){
		swfu = new SWFUpload(option);
		//删除图片
		$(".closeBtn").live("click",function(){
			var fileName = $(this).attr("fileName");
			var attachmentId = $(this).attr("attachmentId");
			var url = "mo/momgr/service/Momgr.deleteImg.json";
			var thisObj = $(this);
			thisObj.parent().remove();
			$("input[name=FILE_NAME]").each(function(){
				if($(this).val()==fileName){
					$(this).remove();
				}
			});
			var stats = swfu.getStats();
			stats.successful_uploads--;
			swfu.setStats(stats);
			
		});
		
		$(".setCoverChkbox").live("click",function(){
			var fileName = $(this).attr("fileName");
			$(".setCoverChkbox").not(this).attr("checked",false);
			if($(this).attr("checked")){
				$("#coverFileName").val(fileName);
			}
		});
	};
	
	this.uploadSuccess = function(file, serverData) {
		var data = JSON.parse(serverData);
		var fileName = data.body.responseData.url;
		this.addHidden(fileName);
		if (fileName) {
			addImage(upload_path+fileName,file,fileName);
		} else {
			addImage("images/error.gif",file);
		}
	};
	
	this.addHidden = function(fileName){
		$("<input type='hidden' name='FILE_NAME'/>").val(fileName).appendTo($("#"+thisObj.formId));
	};
	
	this.reset = function(){
		$("#"+thisObj.formId).find("input[name=FILE_NAME]").remove();//清空所有隐藏域
		$("#thumbnails").empty();	//清空所有图片
		swfu.setStats(0);			//上传统计的清零
	};
	
	this.cancelAll = function(){
		swfu.cancelUpload(null,false);
		$("#ingDiv").empty();
		$.each($("input[name=FILE_NAME]"),function(i,o){
			var fileName = $(this).val();
			var url = "mo/momgr/service/Momgr.deleteImg.json";
			var thisObj = $(this);
			
			ajaxJsonCall(url,{FILE_NAME:fileName},function(data){
				thisObj.remove();
				$("#thumbnails div").has("span[fileName="+fileName+"]").remove();

				var stats = swfu.getStats();
				stats.successful_s--;
				swfu.setStats(stats);
			});	
		});
	};
}