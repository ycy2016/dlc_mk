package com.mas.dtc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mas.dtc.dao.ICrudUserDAO;
import com.mas.dtc.num.ResponseEnum;
import com.mas.dtc.num.StatusEnum;
import com.mas.dtc.po.UserInfo;
import com.mas.dtc.service.ICrudUserService;
import com.mas.dtc.service.IRoleService;
import com.mas.dtc.utils.MD5Util;
import com.mas.dtc.vo.ResponseResult;

/**
 * 用户管理 业务层处理类
 * 
 * @author Mathartsys
 *
 */
@Service(value = "crudUserServiceImpl")
public class CrudUserServiceImpl implements ICrudUserService {
	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(CrudUserServiceImpl.class);

	/**
	 * 用户添加业务层
	 */
	@Autowired
	private ICrudUserDAO crudUserDao;

	/**
	 * 角色业务层
	 */
	@Resource(name = "roleServiceImpl")
	private IRoleService roleServiceImpl;

	@Override
	public ResponseResult queryUserByAll(String account) {
		List<UserInfo> userInfos = null;
		try {
			userInfos = crudUserDao.selectUserByAll();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		if (userInfos != null && userInfos.size() > 0) {
			Iterator<UserInfo> iterator= userInfos.iterator();
			while(iterator.hasNext()){
				UserInfo userInfo = iterator.next();
				if(userInfo.getAccount().equals(account)){
					//用户列表中不需要查看到登录者的用户信息
					iterator.remove();
				}else{
					String statusDes = getStatusDes(userInfo.getStatus());
					if (statusDes != null) {
						userInfo.setStatus(statusDes);
					}
				}
			}
		}
		ResponseEnum resEnum = null;
		if (userInfos == null) {
			resEnum = ResponseEnum.QUERY_FAILURE;
		} else {
			resEnum = ResponseEnum.QUERY_SUCCESS;
		}
		Map<String, Object> header = new HashMap<String, Object>();
		header.put("data", userInfos);
		ResponseResult reslut = new ResponseResult(resEnum.getCode(), resEnum.getMsg(), header);
//		LOG.info("all user info : " + reslut);
		return reslut;
	}

	@Override
	public ResponseResult addUser(UserInfo userInfo) {
		ResponseEnum resEnum = ResponseEnum.ACTION_FAILURE;
		try {
			boolean verify = verifyUserInfo(userInfo);
			if (verify) {
				// 用户不存在则添加
				List<UserInfo> curUserInfo = findUserByAccount(userInfo);
				if (curUserInfo == null || curUserInfo.size() == 0) {
					// 添加用户角色名
					String roleName = roleServiceImpl.getRoleInfoByRoleName(userInfo.getRoleId());
					userInfo.setRoleName(roleName);
					// 用户密码加密
					String md5Password = encryptPassword(userInfo);
					if (md5Password != null) {
						userInfo.setPassword(md5Password);
					}
					// 如果status没有值，则设置为未删除
					if (userInfo.getStatus() == null) {
						userInfo.setStatus("0");
					}
					if (crudUserDao.insertUser(userInfo) > 0) {
						resEnum = ResponseEnum.ADD_SUCCESS;
					}
				} else {
					resEnum = ResponseEnum.ACCOUNT_EXISTS;
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		ResponseResult reslut = new ResponseResult(resEnum.getCode(), resEnum.getMsg());
		LOG.info("user add info : " + reslut);
		return reslut;
	}

	@Override
	public List<UserInfo> findUserByAccount(UserInfo userInfo) throws RuntimeException {
		List<UserInfo> userInfos = null;
		try {
			if (userInfo != null) {
				userInfos = crudUserDao.selectUserByAccount(userInfo);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return userInfos;
	}

	@Override
	public ResponseResult updateUser(UserInfo userInfo) {
		ResponseEnum resEnum = ResponseEnum.ACTION_FAILURE;
		try {
			if (userInfo != null) {
				String roleName = roleServiceImpl.getRoleInfoByRoleName(userInfo.getRoleId());
				// 如果status没有值，则设置为未删除
				userInfo.setRoleName(roleName);
				// 用户密码加密
				String md5Password = encryptPassword(userInfo);
				if (md5Password != null) {
					userInfo.setPassword(md5Password);
				}
				if (crudUserDao.updateUser(userInfo) > 0) {
					resEnum = ResponseEnum.UPDATE_SUCCESS;
				}
			}

		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		ResponseResult reslut = new ResponseResult(resEnum.getCode(), resEnum.getMsg());
		LOG.info("user update info : " + reslut);
		return reslut;
	}

	/**
	 * 验证not null 字段
	 * @param userInfo  用户信息
	 * @return 验证结果 true验证通过 false验证不通过
	 */
	private boolean verifyUserInfo(UserInfo userInfo) {
		boolean verify = true;
		if (userInfo == null) {
			verify = false;
		} else {
			// 非空字段
			List<Object> listNotNullValue = new ArrayList<Object>(Arrays.asList(userInfo.getAccount(),
					userInfo.getPassword(), userInfo.getRoleId(), userInfo.getUserName()));
			if (listNotNullValue.contains(null)) {
				verify = false;
			}
		}
		return verify;
	}

	/**
	 * 
	 * 用户密码加密
	 * @param userInfo 用户信息
	 * @return MD5加密的用户密码
	 * @throws RuntimeException 加密异常
	 */
	private String encryptPassword(UserInfo userInfo) throws RuntimeException {
		String md5Password = null;
		if (userInfo != null && userInfo.getPassword() != null && !"".equals(userInfo.getPassword())) {
			md5Password = MD5Util.getMD5(userInfo.getPassword());
		}
		return md5Password;
	}
	/**
	 * 根据删除的code获得code对应信息
	 * @param statusCode 状态
	 * @return code对应的值
	 */
	public String getStatusDes(String statusCode) {
		String statusDes = null;
		for (StatusEnum enumStatus : StatusEnum.values()) {
			if (enumStatus.getStatusCode().equals(statusCode)) {
				statusDes = enumStatus.getStatusDes();
				break;
			}
		}
		return statusDes;
	}
}
