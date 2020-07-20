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

public class JsonSchemaObjectTest extends JsonSchemaBuilderTest {

	@Override
	AbstractJsonSchema getRequired() {
		return JsonSchemaObject.create().withRequired().build();
	}

	@Override
	AbstractJsonSchema getNotRequired() {
		return JsonSchemaObject.create().build();
	}

	@Test
	public void testSchema() {
		JSONObject actualSchema = JsonSchemaObject.create().build().schema(null);
		JSONObject expectedSchema = new JSONObject(
				"{\"$schema\":\"http://json-schema.org/draft-07/schema#\",\"type\":\"object\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEmpty() {
		JSONObject actualSchema = JsonSchemaObject.create().build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"object\"}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEmptyMinProperties() {
		JSONObject actualSchema = JsonSchemaObject.create().withMinProperties(30).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"object\",\"minProperties\":30}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEmptyMaxProperties() {
		JSONObject actualSchema = JsonSchemaObject.create().withMaxProperties(3).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"object\",\"maxProperties\":3}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testEmptyNoAdditionalProperties() {
		JSONObject actualSchema = JsonSchemaObject.create().withAdditionalProperties(false).build().schemaJson();
		JSONObject expectedSchema = new JSONObject("{\"type\":\"object\",\"additionalProperties\":false}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testString() {
		JSONObject actualSchema = JsonSchemaObject.create()
				.withProperty("foo", JsonSchemaString.create().withMinLength(2).build()).build().schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"object\",\"properties\":{\"foo\":{\"minLength\":2,\"type\":\"string\"}}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testPatternString() {
		JSONObject actualSchema = JsonSchemaObject.create()
				.withPatternProperty("^S_", JsonSchemaString.create().withMinLength(2).build()).build().schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"object\",\"patternProperties\":{\"^S_\":{\"minLength\":2,\"type\":\"string\"}}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testStringRequired() {
		JSONObject actualSchema = JsonSchemaObject.create()
				.withProperty("foo", JsonSchemaString.create().withMinLength(2).withRequired().build()).build()
				.schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"object\",\"properties\":{\"foo\":{\"minLength\":2,\"type\":\"string\"}},\"required\":[\"foo\"]}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testPropertyNames() {
		JSONObject actualSchema = JsonSchemaObject.create().withPropertyNames("^[A-Za-z_][A-Za-z0-9_]*$").build()
				.schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"object\",\"propertyNames\":{\"pattern\":\"^[A-Za-z_][A-Za-z0-9_]*$\"}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testPropertyDependencies() {
		JSONObject actualSchema = JsonSchemaObject.create()
				.withProperty("name", JsonSchemaString.create().withRequired().build())
				.withProperty("credit_card", JsonSchemaNumber.create().build())
				.withProperty("billing_address", JsonSchemaString.create().build())
				.withPropertyDependency("credit_card", "billing_address").build().schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"credit_card\":{\"type\":\"number\"},\"billing_address\":{\"type\":\"string\"}},\"required\":[\"name\"],\"dependencies\":{\"credit_card\":[\"billing_address\"]}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test
	public void testSchemaDependencies() {
		JSONObject actualSchema = JsonSchemaObject.create()
				.withProperty("name", JsonSchemaString.create().withRequired().build())
				.withProperty("credit_card", JsonSchemaNumber.create().build())
				.withSchemaDependency("credit_card", JsonSchemaObject.create()
						.withProperty("billing_address", JsonSchemaString.create().withRequired().build()).build())
				.build().schemaJson();
		JSONObject expectedSchema = new JSONObject(
				"{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\"},\"credit_card\":{\"type\":\"number\"}},\"required\":[\"name\"],\"dependencies\":{\"credit_card\":{\"properties\":{\"billing_address\":{\"type\":\"string\"}},\"required\":[\"billing_address\"]}}}");
		JSONAssert.assertEquals(expectedSchema, actualSchema, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithPropertyNullName() {
		JsonSchemaObject.create().withProperty(null, JsonSchemaBoolean.create().build()).build().schemaJson();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithPropertyNullValue() {
		JsonSchemaObject.create().withProperty("foo", null).build().schemaJson();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithPatternPropertyNullName() {
		JsonSchemaObject.create().withPatternProperty(null, JsonSchemaBoolean.create().build()).build().schemaJson();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithPatternPropertyNullValue() {
		JsonSchemaObject.create().withPatternProperty("foo", null).build().schemaJson();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMinProperties() {
		JsonSchemaObject.create().withMinProperties(-1).build().schemaJson();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithMaxProperties() {
		JsonSchemaObject.create().withMaxProperties(-1).build().schemaJson();
	}
}
