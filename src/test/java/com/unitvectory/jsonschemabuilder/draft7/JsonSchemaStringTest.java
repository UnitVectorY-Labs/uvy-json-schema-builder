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

public class JsonSchemaStringTest {

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaString.Builder.create().build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMinLength() {
		JSONObject actualSchema = JsonSchemaString.Builder.create().withMinLength(10).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"minLength\":10}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testMaxLength() {
		JSONObject actualSchema = JsonSchemaString.Builder.create().withMaxLength(10).build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"maxLength\":10}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testPattern() {
		JSONObject actualSchema = JsonSchemaString.Builder.create().withPattern(".*").build().schema();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"string\",\"pattern\":\".*\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}
}