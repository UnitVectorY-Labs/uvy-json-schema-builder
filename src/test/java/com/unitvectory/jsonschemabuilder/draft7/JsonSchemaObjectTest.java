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

public class JsonSchemaObjectTest {

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaObject.Builder.create().build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"object\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEmptyNoAdditionalProperties() {
		JSONObject actualSchema = JsonSchemaObject.Builder.create().withAdditionalProperties(false).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"object\",\"additionalProperties\":false}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testString() {
		JSONObject actualSchema = JsonSchemaObject.Builder.create()
				.withProperty("foo", JsonSchemaString.Builder.create().withMinLength(2).build()).build().schema();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"object\",\"properties\":{\"foo\":{\"minLength\":2,\"type\":\"string\"}}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testStringRequired() {
		JSONObject actualSchema = JsonSchemaObject.Builder.create()
				.withProperty("foo", JsonSchemaString.Builder.create().withMinLength(2).withRequired().build()).build()
				.schema();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"object\",\"properties\":{\"foo\":{\"minLength\":2,\"type\":\"string\"}},\"required\":[\"foo\"]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

}
