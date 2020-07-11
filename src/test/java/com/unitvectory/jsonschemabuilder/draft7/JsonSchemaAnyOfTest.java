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

public class JsonSchemaAnyOfTest extends JsonSchemaBuilderTest {

	@Override
	JsonSchemaBuilder getRequired() {
		return JsonSchemaAnyOf.Builder.create().withRequired().build();
	}

	@Override
	JsonSchemaBuilder getNotRequired() {
		return JsonSchemaAnyOf.Builder.create().build();
	}

	@Test
	public void testAnyOf() {
		JSONObject actualSchema = JsonSchemaAnyOf.Builder.create()
				.withAnyOf(JsonSchemaString.Builder.create().withMaxLength(5).build())
				.withAnyOf(JsonSchemaNumber.Builder.create().withMinimum(0).build()).build().schema();
		JSONObject expectedSchema = new JSONObject(
				"{\"anyOf\":[{\"type\":\"string\",\"maxLength\":5},{\"type\":\"number\",\"minimum\":0}]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

}
