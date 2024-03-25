package com.mannetroll;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.ObjectMessage;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EcsApp implements CommandLineRunner {
	private static final Logger LOG = LogManager.getLogger(EcsApp.class);
	private static ConfigurableApplicationContext context;
	private static final Marker CUSTOM_MARKER = MarkerManager.getMarker("CUSTOM");

	@Override
	public void run(String... args) {
		LOG.info("######### ECS ~ ELASTIC COMMON SCHEMA #########");
		EcsApp.doLog();
	}

	public static void doLog() {
		LOG.info(CUSTOM_MARKER, "Integer: {}", 42);
		MDC.put("action", "create");
		MDC.put("env", "prod");
		MDC.put("flag", "true");
		LOG.info("MDC labels!");
		ThreadContext.push("TAG");
		LOG.info("Tag from NDC");
		ThreadContext.pop();

		Map<String, Object> logmap = new HashMap<>();
		logmap.put("number", 1024);
		logmap.put("flag", true);
		LOG.info(new ObjectMessage(logmap));
	}

	public static void main(String[] args) {
		context = SpringApplication.run(EcsApp.class, args);
		LOG.info("BeanDefinitionCount: " + context.getBeanDefinitionCount());
	}

}
