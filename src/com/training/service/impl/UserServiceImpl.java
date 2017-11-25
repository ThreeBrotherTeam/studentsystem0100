package com.training.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.training.common.service.CommonService;
import com.training.dao.UserDao;
import com.training.data.UserData;
import com.training.form.UserForm;
import com.training.model.UserModel;
import com.training.service.UserService;
import com.training.util.MD5Tools;

public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;
	@Resource
	private CommonService commonService;
	@Override
	public UserData queryUser(UserForm userForm) {
		UserData data=new UserData();
		List<UserData>userDatas=new ArrayList<UserData>();
		Map<String, Object> fields = new HashMap<>();
		fields.put(UserModel.NAME, userForm.getName());
		String password =MD5Tools.MD5(userForm.getName()+userForm.getPassword()) ;
		fields.put(UserModel.PASSWORD, password);
		List<UserModel> userModels=commonService.getEntitiesByFields(UserModel.class,fields);
		if(null!=userModels){
			for(UserModel user :userModels){
				UserData userData=new UserData();
				userData.setId(user.getId());
				userData.setName(user.getName());
				userData.setMobile(user.getMobile());
				userData.setCreateDate(user.getCreateDate());
				userData.setUpdateDate(user.getUpdateDate());
				userDatas.add(userData);
			}
			data=userDatas.get(0);
		}
		
		return data;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public CommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

}
