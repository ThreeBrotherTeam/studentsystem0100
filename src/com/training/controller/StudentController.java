package com.training.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.training.common.config.Config;
import com.training.model.data.StudentData;
import com.training.model.form.StudentForm;
import com.training.page.Pagination;
import com.training.page.SearchResult;
import com.training.service.StudentService;

@Controller
public class StudentController {

	@Resource
	private StudentService studentService;

	@RequestMapping("/loadStudentsByFields")
	public String loadStudentsByFields(Model model, StudentForm studentForm,
			@RequestParam(value = "currentPage", defaultValue = "1") int currentPage) {
		Pagination page = new Pagination();
		if (currentPage <= 0) {
			currentPage = Integer.parseInt(Config.getStringProperty("page.currentPage"));
			
		}
		page.setCurrentPage(currentPage);
		page.setPageSize(Integer.parseInt(Config.getStringProperty("page.pageSize")));
		SearchResult<StudentData> searchResult = studentService.findAll(studentForm, page);
		model.addAttribute("searchResult", searchResult);
		return "users/students";
	}
}
