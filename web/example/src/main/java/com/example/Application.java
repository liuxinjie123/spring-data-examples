/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example;

import com.example.users.service.Password;
import com.example.users.form.UserForm;
import com.example.users.service.UserManagement;
import com.example.users.service.Username;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Central Spring Boot application class to bootstrap the application. Excludes Spring Security auto-configuration as we
 * don't need it for the example but only want to use a {@link PasswordEncoder} (see {@link #passwordEncoder()}).
 * <p>
 * Spring Data web support is transparently activated by Boot for you. In case you want to manually activate it, use
 * {@link EnableSpringDataWebSupport}. The core aspects of the enabled functionality shown in this example are:
 * <ol>
 * <li>Automatic population of a {@link Pageable} instances from request parameters (see
 * {@link UserController#users(Pageable)})</li>
 * <li>The ability to use proxy-backed interfaces to bind request payloads (see
 * {@link UserForm})</li>
 * </ol>
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	public static void main(String... args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(Application.class);
		Environment env = app.run(args).getEnvironment();
		logger.info("Access URLs:\n----------------------------------------------------------\n\t" +
						"Local: \t\thttp://127.0.0.1:{}\n\t" +
						"External: \thttp://{}:{}\n----------------------------------------------------------",
				env.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));
	}

	@Autowired
	UserManagement userManagement;

	/**
	 * Creates a few sample users.
	 */
	@PostConstruct
	public void init() {
		IntStream.range(0, 41).forEach(index -> {
			userManagement.register(new Username("user" + index), Password.raw("foobar"));
		});
	}

	/**
	 * A Spring Security {@link PasswordEncoder} to encrypt passwords for newly created users, used in
	 * {@link UserManagement}.
	 * @return
	 */
	public @Bean PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
