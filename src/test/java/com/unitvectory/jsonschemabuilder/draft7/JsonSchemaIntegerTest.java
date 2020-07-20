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

public class JsonSchemaIntegerTest extends JsonSchemaBuilderTest {

	@Override
	AbstractJsonSchema getRequired() {
		return JsonSchemaInteger.create().withRequired().build();
	}

	@Override
	AbstractJsonSchema getNotRequired() {
		return JsonSchemaInteger.create().build();
	}

	@Test
	public void testSchema() {
		JSONObject actualSchema = JsonSchemaInteger.create().build().schema(null);
		JSONObject expectedSchema = new JSONObject(
				"{\"$schema\":\"http://json-schema.org/draft-07/schema#\",\"type\":\"integer\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaInteger.create().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"integer\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMultipleOf() {
		JSONObject actualSchema = JsonSchemaInteger.create().withMultipleOf(123).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"integer\",\"multipleOf\":123}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMinimum() {
		JSONObject actualSchema = JsonSchemaInteger.create().withMinimum(4).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"integer\",\"minimum\":4}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testExclusiveMinimum() {
		JSONObject actualSchema = JsonSchemaInteger.create().withExclusiveMinimum(5).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"integer\",\"exclusiveMinimum\":5}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMaximum() {
		JSONObject actualSchema = JsonSchemaInteger.create().withMaximum(6).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"integer\",\"maximum\":6}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testExclusiveMaximum() {
		JSONObject actualSchema = JsonSchemaInteger.create().withExclusiveMaximum(7).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"integer\",\"exclusiveMaximum\":7}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMultipleOfNegative() {
		JsonSchemaInteger.create().withMultipleOf(-1).build().schemaJson();
	}
}
