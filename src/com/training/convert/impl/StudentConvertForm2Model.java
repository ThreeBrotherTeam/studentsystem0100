package com.training.convert.impl;

import java.util.Date;

import com.training.convert.Convert;
import com.training.model.StudentModel;
import com.training.model.form.StudentForm;
import com.training.utils.DateUtil;

public class StudentConvertForm2Model implements Convert<StudentForm, StudentModel> {

	@Override
	public StudentModel createTarget() {
		return new StudentModel();
	}

	@Override
	public StudentModel convert(StudentForm studentForm) {
		StudentModel studentModel = createTarget();
		studentModel.setAvailable(true);
		studentModel.setBirthday(DateUtil.getDateByString(studentForm.getBirthday()));
		studentModel.setClazz(studentForm.getClazz());
		studentModel.setCreateTime(new Date());
		studentModel.setId(studentForm.getId());
		studentModel.setModifyTime(new Date());
		studentModel.setName(studentForm.getName());
		return studentModel;
	}

}
