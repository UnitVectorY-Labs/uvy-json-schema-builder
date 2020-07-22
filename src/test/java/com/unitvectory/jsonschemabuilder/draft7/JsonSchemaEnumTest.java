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
	AbstractJsonSchema getRequired() {
		return JsonSchemaEnum.create().withRequired().build();
	}

	@Override
	AbstractJsonSchema getNotRequired() {
		return JsonSchemaEnum.create().build();
	}

	@Test
	public void testSchema() {
		JSONObject actualSchema = JsonSchemaEnum.create().withEnumValue("A").withEnumValue("B").build().schema(null);
		JSONObject expectedSchema = new JSONObject(
				"{\"$schema\":\"http://json-schema.org/draft-07/schema#\",\"enum\":[\"A\",\"B\"]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTitle() {
		JSONObject actualSchema = JsonSchemaEnum.create().withEnumValue("A").withTitle("My Title").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[\"A\"],\"title\":\"My Title\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testDescription() {
		JSONObject actualSchema = JsonSchemaEnum.create().withEnumValue("A").withDescription("My Description").build()
				.schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[\"A\"],\"description\":\"My Description\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testReadOnly() {
		JSONObject actualSchema = JsonSchemaEnum.create().withEnumValue("A").withReadOnly().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[\"A\"],\"readOnly\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testWriteOnly() {
		JSONObject actualSchema = JsonSchemaEnum.create().withEnumValue("A").withWriteOnly().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[\"A\"],\"writeOnly\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEnumString() {
		JSONObject actualSchema = JsonSchemaEnum.create().withEnumValue("A").withEnumValue("B").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[\"A\",\"B\"]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEnumInteger() {
		JSONObject actualSchema = JsonSchemaEnum.create().withEnumValue(2).withEnumValue(1).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[1, 2]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEnumDouble() {
		JSONObject actualSchema = JsonSchemaEnum.create().withEnumValue(2.3).withEnumValue(1.4).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[1.4, 2.3]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEnumNull() {
		JSONObject actualSchema = JsonSchemaEnum.create().withNull().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[null]}");

		// JSONAssert.assertEquals(expectedSchema, actualSchema, true);

		// JSONAssert wasn't working as expected with null, basic string comparison
		assertEquals(expectedSchema.toString(), actualSchema.toString());
	}

	@Test
	public void testEnumNothing() {
		JSONObject actualSchema = JsonSchemaEnum.create().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"enum\":[null]}");

		// JSONAssert.assertEquals(expectedSchema, actualSchema, true);

		// JSONAssert wasn't working as expected with null, basic string comparison
		assertEquals(expectedSchema.toString(), actualSchema.toString());
	}
}
