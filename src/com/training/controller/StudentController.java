package com.training.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping("/addStudent")
	public String add() {
		return "users/add";
	}

	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public String add(StudentForm studentForm) {
		studentService.add(studentForm);
		return "redirect:loadStudentsByFields";
	}

	@RequestMapping("/loadStudentById")
	public String loadStudentById(Model model, Integer id) {
		StudentData stuData = studentService.findById(id);
		model.addAttribute("stuData", stuData);
		return "users/edit";
	}

	@RequestMapping(value = "/updateStudentById", method = RequestMethod.POST)
	public String updateStudentById(StudentForm studentForm) {
		studentService.updateById(studentForm);
		return "redirect:loadStudentsByFields";
	}

	@RequestMapping("/deleteStudentById")
	public String deleteStudentById(Integer id) {
		studentService.deleteById(id);
		return "redirect:loadStudentsByFields";
	}
}
