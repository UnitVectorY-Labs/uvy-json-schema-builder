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

public class JsonSchemaBooleanTest extends JsonSchemaBuilderTest {

	@Override
	AbstractJsonSchema getRequired() {
		return JsonSchemaBoolean.create().withRequired().build();
	}

	@Override
	AbstractJsonSchema getNotRequired() {
		return JsonSchemaBoolean.create().build();
	}

	@Test
	public void testSchema() {
		JSONObject actualSchema = JsonSchemaBoolean.create().build().schema(null);
		JSONObject expectedSchema = new JSONObject(
				"{\"$schema\":\"http://json-schema.org/draft-07/schema#\",\"type\":\"boolean\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaBoolean.create().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"boolean\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTitle() {
		JSONObject actualSchema = JsonSchemaBoolean.create().withTitle("My Title").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"boolean\",\"title\":\"My Title\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testDescription() {
		JSONObject actualSchema = JsonSchemaBoolean.create().withDescription("My Description").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"boolean\",\"description\":\"My Description\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testReadOnly() {
		JSONObject actualSchema = JsonSchemaBoolean.create().withReadOnly().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"boolean\",\"readOnly\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testWriteOnly() {
		JSONObject actualSchema = JsonSchemaBoolean.create().withWriteOnly().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"boolean\",\"writeOnly\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}
}
