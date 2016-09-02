package ru.checkout.shop.api.config;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

public class RootApplicationContextLoader extends ContextLoaderListener {
	public static final String ROOT_ATTRIBUTE_NAME = "rootContext";

	public RootApplicationContextLoader() {
	}

	protected WebApplicationContext createWebApplicationContext(ServletContext sc) {
		ConfigurableWebApplicationContext ctx =
				(ConfigurableWebApplicationContext) super.createWebApplicationContext(sc);
		ctx.setParent((ApplicationContext) sc.getAttribute("rootContext"));
		return ctx;
	}
}
