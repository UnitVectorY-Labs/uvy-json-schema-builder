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

public class JsonSchemaArrayTest extends JsonSchemaBuilderTest {

	@Override
	AbstractJsonSchema getRequired() {
		return JsonSchemaArray.create().withRequired().build();
	}

	@Override
	AbstractJsonSchema getNotRequired() {
		return JsonSchemaArray.create().build();
	}

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaArray.create().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTitle() {
		JSONObject actualSchema = JsonSchemaArray.create().withTitle("My Title").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"title\":\"My Title\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testDescription() {
		JSONObject actualSchema = JsonSchemaArray.create().withDescription("My Description").build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"description\":\"My Description\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testReadOnly() {
		JSONObject actualSchema = JsonSchemaArray.create().withReadOnly().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"readOnly\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testWriteOnly() {
		JSONObject actualSchema = JsonSchemaArray.create().withWriteOnly().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"writeOnly\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testSchema() {
		JSONObject actualSchema = JsonSchemaArray.create().withItem(JsonSchemaNumber.create().build()).build()
				.schema(null);
		JSONObject expectedSchema = new JSONObject(
				"{\"$schema\":\"http://json-schema.org/draft-07/schema#\",\"type\":\"array\",\"items\":{\"type\":\"number\"}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testItemNumber() {
		JSONObject actualSchema = JsonSchemaArray.create().withItem(JsonSchemaNumber.create().build()).build()
				.schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"items\":{\"type\":\"number\"}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testContainsNumber() {
		JSONObject actualSchema = JsonSchemaArray.create().withContains(JsonSchemaNumber.create().build()).build()
				.schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"contains\":{\"type\":\"number\"}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTupleNumber() {
		JSONObject actualSchema = JsonSchemaArray.create()
				.withItemTuple(JsonSchemaNumber.create().build(), JsonSchemaString.create().build()).build()
				.schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"array\",\"items\":[{\"type\":\"number\"},{\"type\":\"string\"}]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTupleNumberAdditionalItemsFalse() {
		JSONObject actualSchema = JsonSchemaArray.create()
				.withItemTuple(JsonSchemaNumber.create().build(), JsonSchemaString.create().build())
				.withAdditionalItems(false).build().schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"array\",\"items\":[{\"type\":\"number\"},{\"type\":\"string\"}],\"additionalItems\":false}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTupleNumberAdditionalItemsTrue() {
		JSONObject actualSchema = JsonSchemaArray.create()
				.withItemTuple(JsonSchemaNumber.create().build(), JsonSchemaString.create().build())
				.withAdditionalItems(true).build().schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"array\",\"items\":[{\"type\":\"number\"},{\"type\":\"string\"}],\"additionalItems\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTupleNumberAdditionalItemsString() {
		JSONObject actualSchema = JsonSchemaArray.create()
				.withItemTuple(JsonSchemaNumber.create().build(), JsonSchemaString.create().build())
				.withAdditionalItems(JsonSchemaString.create().build()).build().schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"array\",\"items\":[{\"type\":\"number\"},{\"type\":\"string\"}],\"additionalItems\":{\"type\":\"string\"}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testLength() {
		JSONObject actualSchema = JsonSchemaArray.create().withMinItems(2).withMaxItems(3).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"minItems\":2,\"maxItems\":3}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testUniqueness() {
		JSONObject actualSchema = JsonSchemaArray.create().withUniqueItems(true).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"uniqueItems\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMinItemsNegative() {
		JsonSchemaArray.create().withMinItems(-1).build().schemaJson();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMaxItemsNegative() {
		JsonSchemaArray.create().withMaxItems(-1).build().schemaJson();
	}
}
