package com.thymeleaf.web;

import com.jfinal.core.Controller;

public class MainController extends Controller {

	public void index(){
		setAttr("data", "这是一个jfinal-thymeleaf的例子,搞定");
		render("/index");
		//renderText("这是一个jfinal-thymeleaf的例子");
	}
}
