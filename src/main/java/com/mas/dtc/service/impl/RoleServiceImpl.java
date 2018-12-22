package com.mas.dtc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mas.dtc.num.ResponseEnum;
import com.mas.dtc.num.RoleEnum;
import com.mas.dtc.service.IRoleService;
import com.mas.dtc.vo.ResponseResult;

/**
 * 角色业务层实现类类
 * @author Mathartsys
 *
 */
@Service(value="roleServiceImpl")
public class RoleServiceImpl implements IRoleService{
	/**
	 * 日志
	 */
	private static final Logger LOG=Logger.getLogger(RoleServiceImpl.class);

	@Override
	public ResponseResult getAllRoles() {
		List<Map<String,Object>> rolesInfo = new ArrayList<Map<String,Object>>();
		for (RoleEnum roleEnum:RoleEnum.values()) {
			rolesInfo.add(roleEnum.getRole());
		}

		ResponseEnum resEnum = null;
		if (rolesInfo.size()==0) {
			resEnum = ResponseEnum.QUERY_FAILURE;
		} else {
			resEnum = ResponseEnum.QUERY_SUCCESS;
		}
		Map<String, Object> header = new HashMap<String, Object>();
		header.put("data", rolesInfo);
		ResponseResult reslut = new ResponseResult(resEnum.getCode(), resEnum.getMsg(), header);
		LOG.info("all role info : " + reslut);
		return reslut;
	}
	@Override
	public String getRoleInfoByRoleName(Integer roleId) {
		String roleName=null;
		if(roleId!=null){
			for (RoleEnum roleEnum:RoleEnum.values()) {
				if(roleEnum.getRoleId().equals(roleId)){
					roleName=roleEnum.getRoleName();
					break;
				}
			}
		}
		return roleName;
	}
}
