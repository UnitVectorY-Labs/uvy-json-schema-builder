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

public class JsonSchemaNullTest extends JsonSchemaBuilderTest {

	@Override
	AbstractJsonSchema getRequired() {
		return JsonSchemaNull.create().withRequired().build();
	}

	@Override
	AbstractJsonSchema getNotRequired() {
		return JsonSchemaNull.create().build();
	}

	@Test
	public void testSchema() {
		JSONObject actualSchema = JsonSchemaNull.create().build().schema(null);
		JSONObject expectedSchema = new JSONObject(
				"{\"$schema\":\"http://json-schema.org/draft-07/schema#\",\"type\":\"null\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaNull.create().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"null\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTitle() {
		JSONObject actualSchema = JsonSchemaNull.create().withTitle("My Title").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"null\",\"title\":\"My Title\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testDescription() {
		JSONObject actualSchema = JsonSchemaNull.create().withDescription("My Description").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"null\",\"description\":\"My Description\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testReadOnly() {
		JSONObject actualSchema = JsonSchemaNull.create().withReadOnly().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"null\",\"readOnly\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testWriteOnly() {
		JSONObject actualSchema = JsonSchemaNull.create().withWriteOnly().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"null\",\"writeOnly\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

}
