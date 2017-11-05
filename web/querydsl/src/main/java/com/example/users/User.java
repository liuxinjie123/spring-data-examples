/*
 * Copyright 2015 the original author or authors.
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
package com.example.users;

import lombok.Data;
import lombok.Value;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * @author Christoph Strobl
 * @author Oliver Gierke
 */
@Data
@Document
public class User {
	@Id
	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String nationality;
	@JsonIgnore
	private String password;

	@JsonUnwrapped
	private Address address;
	private Picture picture;

	@Value
	public static class Address {
		String city;
		String street;
		String zip;
	}

	@Value
	public static class Picture {
		String large;
		String medium;
		String small;
	}
}
