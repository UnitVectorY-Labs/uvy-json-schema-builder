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

import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonSchemaStringTest extends JsonSchemaBuilderTest {

	@Override
	JsonSchemaBuilder getRequired() {
		return JsonSchemaString.create().withRequired().build();
	}

	@Override
	JsonSchemaBuilder getNotRequired() {
		return JsonSchemaString.create().build();
	}

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaString.create().build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMinLength() {
		JSONObject actualSchema = JsonSchemaString.create().withMinLength(10).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"minLength\":10}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMaxLength() {
		JSONObject actualSchema = JsonSchemaString.create().withMaxLength(10).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"maxLength\":10}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testPattern() {
		JSONObject actualSchema = JsonSchemaString.create().withPattern(".*").build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"pattern\":\".*\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEnum() {
		JSONObject actualSchema = JsonSchemaString.create().withEnumValue("A").withEnumValue("B").build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"enum\":[\"A\",\"B\"]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMinLength() {
		JsonSchemaString.create().withMinLength(-1).build().schema();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMaxLength() {
		JsonSchemaString.create().withMaxLength(-1).build().schema();
	}
}
