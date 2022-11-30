package com.example.spring.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan
public class RootConfig {

	private static final Logger log = LoggerFactory.getLogger(RootConfig.class);

	private static final Set<String> beanNames = new HashSet<>();

	@Component
	static class BeanDefinitionsLogger implements ApplicationListener<ContextRefreshedEvent> {

		@Override
		public void onApplicationEvent(final ContextRefreshedEvent event) {
			ListableBeanFactory context = event.getApplicationContext();
			Map<String, Class<?>> map = new TreeMap<>();
			Set<String> dup = new TreeSet<>();
			StringBuilder msg = new StringBuilder(System.lineSeparator());
			Arrays.stream(context.getBeanDefinitionNames()).sorted().forEachOrdered(name -> map.put(name, context.getType(name, false)));
			msg.append("*******************************************************************************").append(System.lineSeparator());
			msg.append(String.format("***** Listing %d beans of %s *****%n", map.size(), context));
			map.entrySet().stream().forEach(e -> {
				if (beanNames.contains(e.getKey())) {
					dup.add(e.getKey());
				}
				else {
					beanNames.add(e.getKey());
				}
				msg.append(String.format("* %s%s -> %s%n", dup.contains(e.getKey()) ? "DUPLICATE " : "", e.getKey(), e.getValue()));
			});
			msg.append(String.format("***** Found %d duplicates in %s *****%n", dup.size(), context));
			msg.append("*******************************************************************************");
			log.info("{}", msg);
		}
	}

}
