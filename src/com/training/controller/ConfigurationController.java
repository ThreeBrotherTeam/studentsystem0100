package com.training.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.training.common.config.Config;

 
@Controller 
@RequestMapping("/config")
public class ConfigurationController
{ 

	@RequestMapping
	public String displayProperties(Model model)
	{
		Map<String, Object> properties = Config.getAllProperties();
		model.addAttribute("properties", properties);
		return "config/index";
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam("key") String key)
	{
		return Boolean.toString(Config.remove(key));
	}

	@RequestMapping(
	{ "/add", "/update" })
	@ResponseBody
	public String add(@RequestParam("key") String key, @RequestParam("value") String value)
	{
		Config.addProperty(key, value);
		return Boolean.toString(true);
	}

}
