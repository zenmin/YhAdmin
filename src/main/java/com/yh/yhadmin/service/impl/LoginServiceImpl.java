package com.yh.yhadmin.service.impl;

import com.yh.yhadmin.domain.AdminUser;
import com.yh.yhadmin.domain.OperateLogs;
import com.yh.yhadmin.domain.vo.AdminUserVo;
import com.yh.yhadmin.foundation.CommonException;
import com.yh.yhadmin.foundation.DefinedCode;
import com.yh.yhadmin.foundation.constant.CommonConstant;
import com.yh.yhadmin.service.AdminUserService;
import com.yh.yhadmin.service.LoginService;
import com.yh.yhadmin.service.OperateLogsService;
import com.yh.yhadmin.util.IpHelper;
import com.yh.yhadmin.util.StaticUtil;
import com.yh.yhadmin.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @Describle This Class Is
 * @Author ZengMin
 * @Date 2019/2/17 18:12
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    UserInfoUtil userInfoUtil;

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    OperateLogsService operateLogsService;

    /**
     * @param u
     * @param p
     * @return 登录
     */
    @Override
    public Object login(String u, String p,HttpServletRequest request) {
        // 验证用户名密码
        AdminUser adminUser = adminUserService.findByUP(u, p);
        if(adminUser == null)
            throw new CommonException(DefinedCode.AUTHERROR,"用户名或密码错误！");
        Integer status = adminUser.getStatus();
        if(status == CommonConstant.STATUS_ERROR)
            throw new CommonException(DefinedCode.AUTHERROR_DISABLED,"用户已被禁用！");

        AdminUserVo adminUserVo = new AdminUserVo();
        // 判断用户是否登录
        Object userInfoByUserId = userInfoUtil.getUserInfoByUserId(adminUser.getId());
        if(userInfoByUserId != null){
            AdminUserVo oldUser = (AdminUserVo) userInfoByUserId;
            adminUserVo = new AdminUserVo(adminUser.getId(),adminUser.getRealName(), oldUser.getToken(), adminUser.getPhone(), adminUser.getQq(), adminUser.getStatus(),adminUser.getIsAdministrator() == CommonConstant.STATUS_OK);
        }else {
            // 未登录 生成token
            String token = StaticUtil.getToken();
            adminUserVo = new AdminUserVo(adminUser.getId(),adminUser.getRealName(), token, adminUser.getPhone(), adminUser.getQq(), adminUser.getStatus(),adminUser.getIsAdministrator() == CommonConstant.STATUS_OK);
            userInfoUtil.addSession(token,adminUserVo);
        }
        //更新最后一次登录时间和IP
        String ipAddr = IpHelper.getRequestIpAddr(request);
        adminUser.setLastloginIP(ipAddr);
        adminUser.setLastloginTime(new Date());
        adminUserService.updateLoginTime(adminUser);
        operateLogsService.saveLogs(new OperateLogs("系统登录",adminUser.getRealName(),adminUser.getId(),ipAddr,"管理员登录"));
        return adminUserVo;
    }

    /**
     * @param token
     * @return
     */
    @Override
    public Object info(String token) {
        Object userInfo = userInfoUtil.getUserInfo(token);
        return userInfo;
    }

    @Override
    public boolean loginOut(HttpServletRequest request) {
        String token = request.getHeader("token");
        String ipAddr = IpHelper.getRequestIpAddr(request);
        AdminUserVo adminUser = userInfoUtil.getUserInfo(token);
        if(adminUser == null)
            return true;
        operateLogsService.saveLogs(new OperateLogs("系统登录",adminUser.getName(),adminUser.getId(),ipAddr,"管理员退出"));
        return true;
    }
}
