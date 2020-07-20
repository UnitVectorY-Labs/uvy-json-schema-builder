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

public class JsonSchemaAllOfTest extends JsonSchemaBuilderTest {

	@Override
	AbstractJsonSchema getRequired() {
		return JsonSchemaAllOf.create().withRequired().build();
	}

	@Override
	AbstractJsonSchema getNotRequired() {
		return JsonSchemaAllOf.create().build();
	}

	@Test
	public void testSchema() {
		JSONObject actualSchema = JsonSchemaAllOf.create().withAllOf(JsonSchemaString.create().withMaxLength(5).build())
				.withAllOf(JsonSchemaNumber.create().withMinimum(0).build()).build().schema(null);
		JSONObject expectedSchema = new JSONObject(
				"{\"$schema\":\"http://json-schema.org/draft-07/schema#\",\"allOf\":[{\"type\":\"string\",\"maxLength\":5},{\"type\":\"number\",\"minimum\":0}]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testAnyOf() {
		JSONObject actualSchema = JsonSchemaAllOf.create().withAllOf(JsonSchemaString.create().withMaxLength(5).build())
				.withAllOf(JsonSchemaNumber.create().withMinimum(0).build()).build().schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"allOf\":[{\"type\":\"string\",\"maxLength\":5},{\"type\":\"number\",\"minimum\":0}]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

}
