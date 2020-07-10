/*
 * Copyright 2020 Jared Hatfield, UnitVectorY Labs
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.unitvectory.jsonschemabuilder.draft7;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonSchemaEnumTest extends JsonSchemaBuilderTest {

	@Override
	JsonSchemaBuilder getRequired() {
		return JsonSchemaEnum.Builder.create().withRequired().build();
	}

	@Override
	JsonSchemaBuilder getNotRequired() {
		return JsonSchemaEnum.Builder.create().build();
	}

	@Test
	public void testEnumString() {
		JSONObject actualSchema = JsonSchemaEnum.Builder.create().withEnumValue("A").withEnumValue("B").build()
				.schema();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[\"A\",\"B\"]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEnumInteger() {
		JSONObject actualSchema = JsonSchemaEnum.Builder.create().withEnumValue(2).withEnumValue(1).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[1, 2]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEnumDouble() {
		JSONObject actualSchema = JsonSchemaEnum.Builder.create().withEnumValue(2.3).withEnumValue(1.4).build()
				.schema();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[1.4, 2.3]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEnumNull() {
		JSONObject actualSchema = JsonSchemaEnum.Builder.create().withNull().build().schema();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[null]}");

		// JSONAssert.assertEquals(expectedSchema, actualSchema, true);

		// JSONAssert wasn't working as expected with null, basic string comparison
		assertEquals(expectedSchema.toString(), actualSchema.toString());
	}

	@Test
	public void testEnumNothing() {
		JSONObject actualSchema = JsonSchemaEnum.Builder.create().build().schema();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[null]}");

		// JSONAssert.assertEquals(expectedSchema, actualSchema, true);

		// JSONAssert wasn't working as expected with null, basic string comparison
		assertEquals(expectedSchema.toString(), actualSchema.toString());
	}
}
