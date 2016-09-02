package ru.checkout.shop.api.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Вспомогательный класс для гибкости настроки контекстов.
 */
public class ContextMain {
	private static final Log log = LogFactory.getLog(ContextMain.class);

	private static final String APPLICATION_NAME_PROPERTY = "app";
	private static final String DEFAULT_APPLICATION_NAME = "application";
	private static final String LOG4J_CONFIG = "log4j.xml";

	private final GenericApplicationContext context;
	private final String[] args;

	public ContextMain(final String[] args) {
		initializeLogger();
		context = new GenericApplicationContext();
		this.args = args;
	}

	/**
	 * Запускает конфигурацию.
	 * @return конфигурацию.
	 */
	public ConfigurableApplicationContext startContext() {
		initEnvironment();
		initContext();
		context.refresh();
		context.registerShutdownHook();
		log.info("Context started");
		return context;
	}

	/**
	 * Инициализация log4j
	 */
	public static void initializeLogger() {
		if (new File(LOG4J_CONFIG).exists()) {
			DOMConfigurator.configure(LOG4J_CONFIG);
		}
	}

	protected PropertySource buildMainPropertySource() {
		return new SimpleCommandLinePropertySource("commandLineProps", this.args);
	}

	protected static String buildDefaultPropertiesLocation(String applicationName) {
		return "file:" + applicationName + "-local" + ".properties" + "," + "file:" + applicationName +
				".properties" + "," + "classpath:" + applicationName + ".properties";
	}

	protected static String buildDefaultContextLocation(String applicationName) {
		return "classpath:" + applicationName + ".xml" + "," + "file:" + applicationName + ".xml" + "," +
				"file:" + applicationName + "-local" + ".xml";
	}

	/**
	 * Конфигурация окружения.
	 * @return конфигурация окружения.
	 */
	public ConfigurableEnvironment initEnvironment() {
		ConfigurableEnvironment environment = this.context.getEnvironment();
		MutablePropertySources propertySources = environment.getPropertySources();
		PropertySource commandLinePropertySource = this.buildMainPropertySource();
		propertySources.addFirst(commandLinePropertySource);
		String applicationName = this.buildApplicationName();
		String propertiesLocation = this.context.getEnvironment().getProperty("props", (String)null);
		propertiesLocation = buildDefaultPropertiesLocation(applicationName);

		String[] propertiesLocations = propertiesLocation.split("\\s*,\\s*");
		String[] profiles = propertiesLocations;
		int var9 = propertiesLocations.length;

		for(int var10 = 0; var10 < var9; ++var10) {
			String location = profiles[var10];

			try {
				propertySources.addLast(new ResourcePropertySource(location, location));
				log.debug("Read properties from [" + location + "]");
			} catch (IOException var13) {
				log.warn("Cannot read properties from [" + location + "]");
			}
		}

		profiles = environment.getProperty("profile", "dev").split("\\s*,\\s*");
		log.debug("Using profiles: " + Arrays.toString(profiles));
		environment.setActiveProfiles(profiles);
		return environment;
	}

	/**
	 * Инициализация основного контекста.
	 * @return основной контекст.
	 */
	public GenericApplicationContext initContext() {
		String applicationName = this.buildApplicationName();
		String contextLocation = this.context.getEnvironment().getProperty("ctx", (String)null);
		contextLocation = buildDefaultContextLocation(applicationName);

		String[] contextLocations = contextLocation.split("\\s*,\\s*");
		XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(this.context);
		String[] var6 = contextLocations;
		int var7 = contextLocations.length;

		for(int var8 = 0; var8 < var7; ++var8) {
			String location = var6[var8];
			Resource resource = this.context.getResource(location);
			if(!resource.exists()) {
				log.warn("XML context doesn\'t exist [" + location + "]");
			} else {
				xmlReader.loadBeanDefinitions(location);
				log.debug("Read XML context from [" + location + "]");
			}
		}

		return this.context;
	}

	protected String buildApplicationName() {
		return context.getEnvironment().getProperty(APPLICATION_NAME_PROPERTY, DEFAULT_APPLICATION_NAME);
	}
}
