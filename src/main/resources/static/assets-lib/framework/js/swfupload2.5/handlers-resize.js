function preLoad() {
	if (!this.support.imageResize) {
//		alert("系统检测到，你未安装Flash Player或安装的版本过低，您当前无法上传图片。建议您安装Flash Player 10或以上版本。");
//		return false;
	}
//	if (!this.support.loading) {
//		alert("You need the Flash Player to use SWFUpload.");
//		return false;
//	} else if (!this.support.imageResize) {
//		alert("You need Flash Player 10 to upload resized images.");
//		return false;
//	}
}
function loadFailed() {
	alert("Something went wrong while loading SWFUpload. If this were a real application we'd clean up and then give you an alternative");
}

function fileQueueError(file, errorCode, message) {
	try {
		var imageName = "error.gif";
		var errorName = "";
		if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
			errorName = "You have attempted to queue too many files.";
		}
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			errorName = "最多只能上传"+this.settings.file_upload_limit+"个图片!";
		}

		if (errorName !== "") {
			alert(errorName);
			return;
		}

		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			popAny("你选择的文件有误，文件："+file.name+" 大小为0！");
			imageName = "zerobyte.gif";
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			popAny("你选择的文件有误，文件："+file.name+" 太大了！");
			imageName = "toobig.gif";
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
		default:
			alert(message);
			break;
		}

//		addImage("images/" + imageName,file);

	} catch (ex) {
		this.debug(ex);
	}

}

function fileDialogComplete(numFilesSelected, numFilesQueued) {

	try {
		if (numFilesQueued > 0) {
			this.startUpload();
			//if(this.customSettings.reSizeLimit && this.customSettings.reSizeLimit>0 && this.getQueueFile(0).size < this.customSettings.reSizeLimit){
			//	this.startUpload(this.getQueueFile(0).id);
			//}else if((/.png$/i).test(this.getQueueFile(0).name)){
			//	this.startResizedUpload(this.getQueueFile(0).id, this.customSettings.thumbnail_width, this.customSettings.thumbnail_height, SWFUpload.RESIZE_ENCODING.PNG, this.customSettings.thumbnail_quality, false);
			//}else{
			//	this.startResizedUpload(this.getQueueFile(0).id, this.customSettings.thumbnail_width, this.customSettings.thumbnail_height, SWFUpload.RESIZE_ENCODING.JPEG, this.customSettings.thumbnail_quality, false);
			//}
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function fileQueueHandler(fileObj){
	try {
//		alert(fileObj.name);
//		if((/.png$/i).test(fileObj.name)){
//			this.startResizedUpload(fileObj.name.ID, this.customSettings.thumbnail_width, this.customSettings.thumbnail_height, SWFUpload.RESIZE_ENCODING.PNG, this.customSettings.thumbnail_quality, false);
//		}else{
//			this.startResizedUpload(fileObj.name.ID, this.customSettings.thumbnail_width, this.customSettings.thumbnail_height, SWFUpload.RESIZE_ENCODING.JPEG, this.customSettings.thumbnail_quality, false);
//		}
	} catch (ex) {
		this.debug(ex);
	}
}

function resizeStart(file){
//	addImage("images/zerobyte.gif",file);
	addProgresSpan(file,"resize");
}
function uploadProgress(file, bytesLoaded) {

	try {
		var percent = Math.ceil((bytesLoaded / file.size) * 100);
		if(percent>100) percent = 100;
		addProgresSpan(file,"progress",bytesLoaded);
		var progress = new FileProgress(file,  this.customSettings.upload_target);
		progress.setProgress(percent);
		progress.setStatus("Uploading...");
		progress.toggleCancel(true, this);
	} catch (ex) {
		this.debug(ex);
	}
}

/**
 * 获取文件大小
 * 
 * @param filesize
 * @return
 */
function formatFileSize(filesize) {
	if (filesize < 0) {
		return "";
	} else if (filesize >= 1024 * 1024 * 1024){ // 文件大小大于或等于1024MB
		var temp = Math.round(filesize / (1024 * 1024 * 1024)*100)/100;
		return  temp + " GB";
	} else if (filesize >= 1024 * 1024){ // 文件大小大于或等于1024KB
		var temp = Math.round(filesize / (1024 * 1024)*100)/100;
		return  temp + " MB";
	} else if (filesize >= 1024){ // 文件大小大于等于1024bytes
		var temp = Math.round(filesize / (1024)*100)/100;
		return temp + " KB";
	} else {
		return filesize + " bytes";
	}
}
function uploadComplete(file) {
	try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
		this.startUpload();
		//	alert(this.getQueueFile(0).name);
			//if(this.customSettings.reSizeLimit && this.customSettings.reSizeLimit>0 && this.getQueueFile(0).size < this.customSettings.reSizeLimit){
			//	this.startUpload(this.getQueueFile(0).id);
			//}else if((/.png$/i).test(this.getQueueFile(0).name)){
			//	this.startResizedUpload(this.getQueueFile(0).id, this.customSettings.thumbnail_width, this.customSettings.thumbnail_height, SWFUpload.RESIZE_ENCODING.PNG, this.customSettings.thumbnail_quality, false);
			//}else{
			//	this.startResizedUpload(this.getQueueFile(0).id, this.customSettings.thumbnail_width, this.customSettings.thumbnail_height, SWFUpload.RESIZE_ENCODING.JPEG, this.customSettings.thumbnail_quality, false);
			//}
		//	this.startResizedUpload(this.getFile(0).ID, this.customSettings.thumbnail_width, this.customSettings.thumbnail_height, SWFUpload.RESIZE_ENCODING.JPEG, this.customSettings.thumbnail_quality, false);
		} else {
			var progress = new FileProgress(file,  this.customSettings.upload_target);
			progress.setComplete();
			progress.setStatus("All images received.");
			progress.toggleCancel(false);
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	var imageName =  "error.gif";
	var progress;
	try {
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			try {
				progress = new FileProgress(file,  this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("Cancelled");
				progress.toggleCancel(false);
			}
			catch (ex1) {
				this.debug(ex1);
			}
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			try {
				progress = new FileProgress(file,  this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("Stopped");
				progress.toggleCancel(true);
			}
			catch (ex2) {
				this.debug(ex2);
			}
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			alert("文件"+file.name+"大小超过服务器的限制！");
			imageName = "uploadlimit.gif";
			break;
		default:
			alert(message);
			break;
		}

//		addImage("images/" + imageName,file);

	} catch (ex3) {
		this.debug(ex3);
	}

}
function removeProgress(file,i){
	if(i==undefined || i == ''){
		$("#"+file.id,$("#uploadingDiv")).remove();
	}else{
		$("#"+file.id,$("#uploadingDiv"+i)).remove();
	}
	
}

function addProgresSpan(file,type,bytesLoaded) {
	var spanObj = null;
	if($("#"+file.id).length<=0){
		spanObj = $("<span id='"+file.id+"'></span>");
		spanObj.prependTo("#uploadingDiv");
	}else{
		spanObj = $("#"+file.id);
	}
	if(type=="resize"){
		spanObj.html("<p>"+formatFileSize(file.size)+ "<br/>正在压缩</p>");
	}else{
		var percent = Math.ceil((bytesLoaded / file.size) * 100);
		if(percent>100)percent = 100
		spanObj.html("<p>"+formatFileSize(file.size) + "<br/>正在上传…<br/>"+percent+"%</p>");
	}
}

function addImage(src,file,fileName,i) {
	removeProgress(file,i);
	var newImg = $("<div style='display:none;margin:2px;position: relative;float:left;width:60px;height:60px;'>"+
			"<img id='"+file.id+"' style='width:60px;height:60px'/>"+
		//	"<div style='text-align:center;'>设为封面<input type='checkbox' class='setCoverChkbox' fileName='"+fileName+"'/></div>"+
			"<span class='closeBtn' title='删除附件' fileName='"+fileName+"'>X</span>"+
		"</div>");
//	newImg.prependTo("#thumbnails");
	if(i==undefined || i == ''){
		newImg.appendTo("#thumbnails");
	}else{
		newImg.appendTo("#thumbnails"+i);
	}
	
	newImg.ready(function () {
		newImg.fadeIn(500);
	});
	newImg.find("img").attr({"src":src,"width":60,"height":60});;
}

function fadeIn(element, opacity) {
	var reduceOpacityBy = 5;
	var rate = 30;	// 15 fps


	if (opacity < 100) {
		opacity += reduceOpacityBy;
		if (opacity > 100) {
			opacity = 100;
		}

		if (element.filters) {
			try {
				element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
				element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
			}
		} else {
			element.style.opacity = opacity / 100;
		}
	}

	if (opacity < 100) {
		setTimeout(function () {
			fadeIn(element, opacity);
		}, rate);
	}
}



/* ******************************************
 *	FileProgress Object
 *	Control object for displaying file info
 * ****************************************** */

function FileProgress(file, targetID) {
	this.fileProgressID = "divFileProgress";

	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer";

		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));

		var progressText = document.createElement("div");
		progressText.className = "progressName";
		progressText.appendChild(document.createTextNode(file.name));

		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress";

		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus";
		progressStatus.innerHTML = "&nbsp;";

		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);

		this.fileProgressWrapper.appendChild(this.fileProgressElement);

		document.getElementById(targetID).appendChild(this.fileProgressWrapper);
		fadeIn(this.fileProgressWrapper, 0);

	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.fileProgressElement.childNodes[1].firstChild.nodeValue = file.name;
	}

	this.height = this.fileProgressWrapper.offsetHeight;

}
FileProgress.prototype.setProgress = function (percentage) {
	this.fileProgressElement.className = "progressContainer green";
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	this.fileProgressElement.childNodes[3].style.width = percentage + "%";
};
FileProgress.prototype.setComplete = function () {
	this.fileProgressElement.className = "progressContainer blue";
	this.fileProgressElement.childNodes[3].className = "progressBarComplete";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setError = function () {
	this.fileProgressElement.className = "progressContainer red";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setCancelled = function () {
	this.fileProgressElement.className = "progressContainer";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setStatus = function (status) {
	this.fileProgressElement.childNodes[2].innerHTML = status;
};

FileProgress.prototype.toggleCancel = function (show, swfuploadInstance) {
	this.fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfuploadInstance) {
		var fileID = this.fileProgressID;
		this.fileProgressElement.childNodes[0].onclick = function () {
			swfuploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};
