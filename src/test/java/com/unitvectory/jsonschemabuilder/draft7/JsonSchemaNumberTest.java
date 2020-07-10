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
	JsonSchemaBuilder getRequired() {
		return JsonSchemaNumber.Builder.create().withRequired().build();
	}

	@Override
	JsonSchemaBuilder getNotRequired() {
		return JsonSchemaNumber.Builder.create().build();
	}

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMultipleOf() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().withMultipleOf(123).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"multipleOf\":123}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMultipleOfDouble() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().withMultipleOf(123.0).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"multipleOf\":123.0}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMinimum() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().withMinimum(4).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"minimum\":4}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMinimumDouble() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().withMinimum(4.1).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"minimum\":4.1}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testExclusiveMinimum() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().withExclusiveMinimum(5).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"exclusiveMinimum\":5}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testExclusiveMinimumDouble() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().withExclusiveMinimum(5.2).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"exclusiveMinimum\":5.2}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMaximum() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().withMaximum(6).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"maximum\":6}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMaximumDouble() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().withMaximum(6.3).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"maximum\":6.3}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testExclusiveMaximum() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().withExclusiveMaximum(7).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"exclusiveMaximum\":7}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testExclusiveMaximumDouble() {
		JSONObject actualSchema = JsonSchemaNumber.Builder.create().withExclusiveMaximum(7.4).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"number\",\"exclusiveMaximum\":7.4}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}
}
