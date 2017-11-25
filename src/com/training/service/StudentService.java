package com.training.service;

import com.training.model.data.StudentData;
import com.training.model.form.StudentForm;
import com.training.page.Pagination;
import com.training.page.SearchResult;

public interface StudentService {

	SearchResult<StudentData> findAll(StudentForm studentForm, Pagination page);

	void add(StudentForm studentForm);

}
