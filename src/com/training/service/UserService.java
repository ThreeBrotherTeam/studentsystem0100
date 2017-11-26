package com.training.service;

import com.training.data.UserData;
import com.training.form.UserForm;

public interface UserService {

	UserData queryUser(UserForm userForm);

	UserData queryUserByNameAndPassword(String name, String password);

}
