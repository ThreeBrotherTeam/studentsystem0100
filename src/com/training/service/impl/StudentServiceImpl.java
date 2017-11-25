package com.training.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.training.common.service.CommonService;
import com.training.convert.Convert;
import com.training.dao.StudentDao;
import com.training.model.StudentModel;
import com.training.model.data.StudentData;
import com.training.model.form.StudentForm;
import com.training.page.Pagination;
import com.training.page.SearchResult;
import com.training.service.StudentService;

public class StudentServiceImpl implements StudentService {

	private StudentDao studentDao;
	private CommonService commonService;
	private Convert<StudentModel, StudentData> studentConvert;
	private Convert<StudentForm, StudentModel> convertFrom2model;

	@Override
	public SearchResult<StudentData> findAll(StudentForm studentForm, Pagination page) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(studentForm.getName())) {
			params.put(StudentModel.NAME, studentForm.getName());
		}
		if (StringUtils.isNotBlank(studentForm.getClazz())) {
			params.put(StudentModel.CLASS, studentForm.getClazz());
		}
		SearchResult<StudentModel> searchResults = studentDao.queryStudentByFields(params, page);
		SearchResult<StudentData> results = new SearchResult<StudentData>();
		List<StudentData> datas = new ArrayList<StudentData>();
		for (StudentModel model : searchResults.getResult()) {
			datas.add(studentConvert.convert(model));
		}
		results.setPagination(searchResults.getPagination());
		results.setResult(datas);
		return results;
	}

	@Override
	public void add(StudentForm studentForm) {
		StudentModel studentModel = convertFrom2model.convert(studentForm);
		commonService.saveOrUpdateEntity(studentModel);
	}

	public StudentDao getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public Convert<StudentModel, StudentData> getStudentConvert() {
		return studentConvert;
	}

	public void setStudentConvert(Convert<StudentModel, StudentData> studentConvert) {
		this.studentConvert = studentConvert;
	}

	public Convert<StudentForm, StudentModel> getConvertFrom2model() {
		return convertFrom2model;
	}

	public void setConvertFrom2model(Convert<StudentForm, StudentModel> convertFrom2model) {
		this.convertFrom2model = convertFrom2model;
	}

}
