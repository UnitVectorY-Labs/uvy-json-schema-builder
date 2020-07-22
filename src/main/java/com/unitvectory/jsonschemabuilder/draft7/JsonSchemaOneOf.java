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

public class JsonSchemaOneOf extends AbstractJsonSchema {

	private final boolean required;

	private final List<AbstractJsonSchema> oneOf;

	private JsonSchemaOneOf(Builder builder) {
		this.required = builder.required;

		List<AbstractJsonSchema> oneOfList = new ArrayList<AbstractJsonSchema>();
		oneOfList.addAll(builder.oneOf);
		this.oneOf = Collections.unmodifiableList(oneOfList);
	}

	/**
	 * To validate against oneOf, the given data must be valid against exactly one
	 * of the given subschemas.
	 * 
	 * @return
	 */
	public static Builder create() {
		return new Builder();
	}

	JSONObject schemaJson() {
		JSONObject json = new JSONObject();

		JSONArray oneOfArr = new JSONArray();
		json.put("oneOf", oneOfArr);

		for (AbstractJsonSchema schema : this.oneOf) {
			oneOfArr.put(schema.schemaJson());
		}

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder extends AbstractJsonSchemaBuilder<Builder, JsonSchemaOneOf> {

		private boolean required;

		private List<AbstractJsonSchema> oneOf;

		private Builder() {
			this.required = false;
			this.oneOf = new ArrayList<AbstractJsonSchema>();
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

		public Builder withOneOf(AbstractJsonSchema schema) {
			synchronized (this) {
				if (schema == null) {
					return this;
				}

				this.oneOf.add(schema);
				return this;
			}
		}

		public JsonSchemaOneOf build() {
			synchronized (this) {
				return new JsonSchemaOneOf(this);
			}
		}
	}
}