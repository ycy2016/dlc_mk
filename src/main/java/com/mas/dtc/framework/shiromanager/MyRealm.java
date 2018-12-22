package com.mas.dtc.framework.shiromanager;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.mas.dtc.po.UserInfo;
import com.mas.dtc.service.ILoginService;
import com.mas.dtc.utils.MD5Util;

/**
 * 自定义 权限和认证
 * 
 * @author Mathsys
 *
 */
public class MyRealm extends AuthorizingRealm {

	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(MyRealm.class);

	/**
	 * 获取用户信息业务层
	 */
	@Resource(name="loginServiceImpl")
	private ILoginService loginServiceImpl;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserInfo tokenUserInfo = (com.mas.dtc.po.UserInfo) principals.getPrimaryPrincipal();
		//用户每次登录都需验权限,权限需要从数据库取
		UserInfo userInfo = loginServiceImpl.getUserFindbyAccount(tokenUserInfo.getAccount());
//		LOG.info("AuthorizationInfo ..." + userInfo);
		SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
		if(userInfo!=null){
			// 查询用户的角色
			simpleAuthorInfo.addRole(userInfo.getRoleName());
		}
		return simpleAuthorInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		// 得到用户名
		String account =String.valueOf(token.getPrincipal()); 
		String password = null;
		Object tokenPassword = token.getCredentials();
		if(tokenPassword instanceof char[] ){
			// 得到密码
			 password = String.valueOf((char[])token.getCredentials()); 
		}else{
			throw new IncorrectCredentialsException("\"" + account + "\"" + " account/password error ");
		}
	
		String passwordMd = MD5Util.getMD5(password);
	
		String ip = token.getHost();
		
		// 用户帐号不存在
		UserInfo userInfo = loginServiceImpl.getUserFindbyAccount(account);
		if (userInfo != null) {
			if (userInfo.getAccount().equals(account) && userInfo.getPassword().equalsIgnoreCase(passwordMd)) {
				// 登录成功,添加ip和密码置空,userInfo这个实例可以通过shiro的subject全局获取
				userInfo.setPassword(null);
				userInfo.setIp(ip);
				// this.getName()获取MyRealm的名字,因为可以多个MyRealm
				SimpleAuthenticationInfo authenticationInfo =  new SimpleAuthenticationInfo(userInfo, password, getName());
				LOG.info("doGetAuthenticationInfo ... " + ip +" authen success ");
				return authenticationInfo;
			} else {
//				LOG.info("doGetAuthenticationInfo ... " + ip +" authen failure,account/password error ");
				// 密码错误
				throw new IncorrectCredentialsException("\"" + account + "\"" + " account/password error ");
			}
		}
//		LOG.info("doGetAuthenticationInfo ... " + ip +" authen failure "+"\"" + account + "\"" + " is not exist ");
		// 用户不存在
		 throw new UnknownAccountException("\"" + account + "\"" + " is not exist ");

	}

}
