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
	JsonSchemaBuilder getRequired() {
		return JsonSchemaArray.Builder.create().withRequired().build();
	}

	@Override
	JsonSchemaBuilder getNotRequired() {
		return JsonSchemaArray.Builder.create().build();
	}

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaArray.Builder.create().build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testItemNumber() {
		JSONObject actualSchema = JsonSchemaArray.Builder.create().withItem(JsonSchemaNumber.Builder.create().build())
				.build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"items\":{\"type\":\"number\"}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testContainsNumber() {
		JSONObject actualSchema = JsonSchemaArray.Builder.create()
				.withContains(JsonSchemaNumber.Builder.create().build()).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"contains\":{\"type\":\"number\"}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTupleNumber() {
		JSONObject actualSchema = JsonSchemaArray.Builder.create()
				.withItemTuple(JsonSchemaNumber.Builder.create().build(), JsonSchemaString.Builder.create().build())
				.build().schema();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"array\",\"items\":[{\"type\":\"number\"},{\"type\":\"string\"}]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTupleNumberAdditionalItemsFalse() {
		JSONObject actualSchema = JsonSchemaArray.Builder.create()
				.withItemTuple(JsonSchemaNumber.Builder.create().build(), JsonSchemaString.Builder.create().build())
				.withAdditionalItems(false).build().schema();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"array\",\"items\":[{\"type\":\"number\"},{\"type\":\"string\"}],\"additionalItems\":false}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTupleNumberAdditionalItemsTrue() {
		JSONObject actualSchema = JsonSchemaArray.Builder.create()
				.withItemTuple(JsonSchemaNumber.Builder.create().build(), JsonSchemaString.Builder.create().build())
				.withAdditionalItems(true).build().schema();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"array\",\"items\":[{\"type\":\"number\"},{\"type\":\"string\"}],\"additionalItems\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testTupleNumberAdditionalItemsString() {
		JSONObject actualSchema = JsonSchemaArray.Builder.create()
				.withItemTuple(JsonSchemaNumber.Builder.create().build(), JsonSchemaString.Builder.create().build())
				.withAdditionalItems(JsonSchemaString.Builder.create().build()).build().schema();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"array\",\"items\":[{\"type\":\"number\"},{\"type\":\"string\"}],\"additionalItems\":{\"type\":\"string\"}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testLength() {
		JSONObject actualSchema = JsonSchemaArray.Builder.create().withMinItems(2).withMaxItems(3).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"minItems\":2,\"maxItems\":3}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testUniqueness() {
		JSONObject actualSchema = JsonSchemaArray.Builder.create().withUniqueItems(true).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"array\",\"uniqueItems\":true}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}
}
