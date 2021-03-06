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

public class JsonSchemaAllOf extends AbstractJsonSchema {

	private final boolean required;

	private final List<AbstractJsonSchema> allOf;

	private JsonSchemaAllOf(Builder builder) {
		this.required = builder.required;

		List<AbstractJsonSchema> allOfList = new ArrayList<AbstractJsonSchema>();
		allOfList.addAll(builder.allOf);
		this.allOf = Collections.unmodifiableList(allOfList);
	}

	/**
	 * To validate against allOf, the given data must be valid against all of the
	 * given subschemas.
	 * 
	 * @return
	 */
	public static Builder create() {
		return new Builder();
	}

	JSONObject schemaJson() {
		JSONObject json = new JSONObject();

		JSONArray allOfArr = new JSONArray();
		json.put("allOf", allOfArr);

		for (AbstractJsonSchema schema : this.allOf) {
			allOfArr.put(schema.schemaJson());
		}

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder extends AbstractJsonSchemaBuilder<Builder, JsonSchemaAllOf> {

		private boolean required;

		private List<AbstractJsonSchema> allOf;

		private Builder() {
			this.required = false;
			this.allOf = new ArrayList<AbstractJsonSchema>();
		}

		/**
		 * By default, the properties defined by the properties keyword are not
		 * required. However, one can provide a list of required properties using the
		 * required keyword.
		 * 
		 * @return
		 */
		public Builder withRequired() {
			synchronized (this) {
				this.required = true;
				return this;
			}
		}

		public Builder withAllOf(AbstractJsonSchema schema) {
			synchronized (this) {
				if (schema == null) {
					return this;
				}

				this.allOf.add(schema);
				return this;
			}
		}

		public JsonSchemaAllOf build() {
			synchronized (this) {
				return new JsonSchemaAllOf(this);
			}
		}
	}
}