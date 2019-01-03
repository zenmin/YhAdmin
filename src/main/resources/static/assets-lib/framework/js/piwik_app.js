try {
	var piwikTracker = Piwik.getTracker(pkBaseURL + "piwik.php", pkSiteID);  
	
	//设置没有title标签的标题
	if (typeof(title_flag) != "undefined") {
	    
		if(title_flag=='index'){
			//首页
			piwikTracker.setDocumentTitle(retitle+'-首页');
		}
	}else{
		
	} 
	
 	//设置用户ID
 	if(user_id != null && user_id != "" && user_id != "null"){
		piwikTracker.setCustomVariable(2, user_id, '', 'page');
	}
	
	//设置应用ID
	if(app_id != null && app_id != ""){
		piwikTracker.setCustomVariable(3, app_id, '', 'page'); 
	}
	
	//设置渠道类型
	piwikTracker.setCustomVariable(5, '01', '', 'page');
	piwikTracker.setCustomVariable(5, '01', '', 'visit');

	piwikTracker.trackPageView();
	piwikTracker.enableLinkTracking();
}
catch (err) {
}
