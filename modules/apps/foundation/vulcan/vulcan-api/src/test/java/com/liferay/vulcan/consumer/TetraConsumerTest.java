/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.vulcan.consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class TetraConsumerTest {

	@Test
	public void testOnInvokingAndThenShouldExecuteBothFunctions() {
		List<String> list = new ArrayList<>();

		TetraConsumer<String, String, String, String> tetraConsumer =
			(string1, string2, string3, string4) -> {
				list.add(string1);
				list.add(string2);
				list.add(string3);
				list.add(string4);
			};

		tetraConsumer.andThen(
			tetraConsumer
		).accept(
			"| Live", " long", " and", " prosper |"
		);

		String string = String.join("", list);

		assertThat(
			string,
			is(equalTo("| Live long and prosper || Live long and prosper |")));
	}

	@Test(expected = NullPointerException.class)
	public void testOnInvokingAndThenWithNullAfterThrowsException() {
		TetraConsumer.empty().andThen(null);
	}

}