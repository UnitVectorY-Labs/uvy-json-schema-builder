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

public class JsonSchemaNumberTest extends JsonSchemaBuilderTest {

	@Override
	AbstractJsonSchema getRequired() {
		return JsonSchemaNumber.create().withRequired().build();
	}

	@Override
	AbstractJsonSchema getNotRequired() {
		return JsonSchemaNumber.create().build();
	}

	@Test
	public void testSchema() {
		JSONObject actualSchema = JsonSchemaNumber.create().build().schema(null);
		JSONObject expectedSchema = new JSONObject(
				"{\"$schema\":\"http://json-schema.org/draft-07/schema#\",\"type\":\"number\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaNumber.create().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMultipleOf() {
		JSONObject actualSchema = JsonSchemaNumber.create().withMultipleOf(123).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"multipleOf\":123}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMultipleOfDouble() {
		JSONObject actualSchema = JsonSchemaNumber.create().withMultipleOf(123.0).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"multipleOf\":123.0}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMinimum() {
		JSONObject actualSchema = JsonSchemaNumber.create().withMinimum(4).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"minimum\":4}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMinimumDouble() {
		JSONObject actualSchema = JsonSchemaNumber.create().withMinimum(4.1).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"minimum\":4.1}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testExclusiveMinimum() {
		JSONObject actualSchema = JsonSchemaNumber.create().withExclusiveMinimum(5).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"exclusiveMinimum\":5}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testExclusiveMinimumDouble() {
		JSONObject actualSchema = JsonSchemaNumber.create().withExclusiveMinimum(5.2).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"exclusiveMinimum\":5.2}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMaximum() {
		JSONObject actualSchema = JsonSchemaNumber.create().withMaximum(6).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"maximum\":6}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMaximumDouble() {
		JSONObject actualSchema = JsonSchemaNumber.create().withMaximum(6.3).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"maximum\":6.3}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testExclusiveMaximum() {
		JSONObject actualSchema = JsonSchemaNumber.create().withExclusiveMaximum(7).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"exclusiveMaximum\":7}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testExclusiveMaximumDouble() {
		JSONObject actualSchema = JsonSchemaNumber.create().withExclusiveMaximum(7.4).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"exclusiveMaximum\":7.4}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMultipleOfNegativeInt() {
		JsonSchemaNumber.create().withMultipleOf(-1).build().schemaJson();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMultipleOfNegativeDouble() {
		JsonSchemaNumber.create().withMultipleOf(-1.1).build().schemaJson();
	}
}
