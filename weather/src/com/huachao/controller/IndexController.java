package com.huachao.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.huachao.bean.Message;
import com.huachao.helper.WeatherUtils;
@Controller
@RequestMapping("/")
public class IndexController {
	private WeatherUtils weatherUtils;
	@Autowired
	public IndexController(WeatherUtils weatherUtils){
		this.weatherUtils=weatherUtils;
	}
	@RequestMapping(method=RequestMethod.GET)
    public String index(Model model){
		model.addAttribute(new Message());
    	return "index";
    }
	@RequestMapping(method=RequestMethod.POST)
	public String getData(@Valid Message message,Errors errors,Model model){
		if (errors.hasErrors()) {
			return "index";
		}
		String code=weatherUtils.getCityCode(message);
		if (code.equals("")) {
			String error="名称输入有误！请重新输入！";
			model.addAttribute("error", error);
		}else {
		   com.huachao.bean.Model result=weatherUtils.getWeather(code);
		   model.addAttribute("result", result);
		}
		return "index";
	}
}
