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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonSchemaAllOf extends JsonSchemaBuilder {

	private final boolean required;

	private final List<JsonSchemaBuilder> allOf;

	private JsonSchemaAllOf(Builder builder) {
		this.required = builder.required;

		List<JsonSchemaBuilder> allOfList = new ArrayList<JsonSchemaBuilder>();
		allOfList.addAll(builder.allOf);
		this.allOf = Collections.unmodifiableList(allOfList);
	}

	JSONObject schema() {
		JSONObject json = new JSONObject();

		JSONArray allOfArr = new JSONArray();
		json.put("allOf", allOfArr);

		for (JsonSchemaBuilder schema : this.allOf) {
			allOfArr.put(schema.schema());
		}

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder {

		private boolean required;

		private List<JsonSchemaBuilder> allOf;

		private Builder() {
			this.required = false;
			this.allOf = new ArrayList<JsonSchemaBuilder>();
		}

		public static Builder create() {
			return new Builder();
		}

		public Builder withRequired() {
			this.required = true;
			return this;
		}

		public Builder withAllOf(JsonSchemaBuilder schema) {
			if (schema == null) {
				return this;
			}

			this.allOf.add(schema);
			return this;
		}

		public JsonSchemaAllOf build() {
			return new JsonSchemaAllOf(this);
		}
	}
}