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
	AbstractJsonSchema getRequired() {
		return JsonSchemaString.create().withRequired().build();
	}

	@Override
	AbstractJsonSchema getNotRequired() {
		return JsonSchemaString.create().build();
	}

	@Test
	public void testSchema() {
		JSONObject actualSchema = JsonSchemaString.create().build().schema(null);
		JSONObject expectedSchema = new JSONObject(
				"{\"$schema\":\"http://json-schema.org/draft-07/schema#\",\"type\":\"string\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaString.create().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTitle() {
		JSONObject actualSchema = JsonSchemaString.create().withTitle("My Title").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"title\":\"My Title\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testDescription() {
		JSONObject actualSchema = JsonSchemaString.create().withDescription("My Description").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"description\":\"My Description\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testReadOnly() {
		JSONObject actualSchema = JsonSchemaString.create().withReadOnly().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"readOnly\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testWriteOnly() {
		JSONObject actualSchema = JsonSchemaString.create().withWriteOnly().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"writeOnly\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMinLength() {
		JSONObject actualSchema = JsonSchemaString.create().withMinLength(10).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"minLength\":10}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMaxLength() {
		JSONObject actualSchema = JsonSchemaString.create().withMaxLength(10).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"maxLength\":10}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testPattern() {
		JSONObject actualSchema = JsonSchemaString.create().withPattern(".*").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"pattern\":\".*\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEnum() {
		JSONObject actualSchema = JsonSchemaString.create().withEnumValue("A").withEnumValue("B").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"enum\":[\"A\",\"B\"]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMinLength() {
		JsonSchemaString.create().withMinLength(-1).build().schemaJson();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMaxLength() {
		JsonSchemaString.create().withMaxLength(-1).build().schemaJson();
	}
}
