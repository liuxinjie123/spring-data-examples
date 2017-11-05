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
package com.example.users.entity;

import javax.persistence.*;

import com.example.users.service.Password;
import com.example.users.service.Username;
import lombok.*;

/**
 * A {@link User} domain object. The primary entity of this example. Basically a combination of a {@link Username} and
 * {@link Password}.
 * 
 * @author Oliver Gierke
 */
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(of = "id")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private final Username username;
	private final Password password;

	User() {
		this.username = null;
		this.password = null;
	}

	/**
	 * Makes sure only {@link User}s with encrypted {@link Password} can be persisted.
	 */
	@PrePersist
	@PreUpdate
	void assertEncrypted() {
		if (!password.isEncrypted()) {
			throw new IllegalStateException("Tried to persist/load a user with a non-encrypted password!");
		}
	}
}
