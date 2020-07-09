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

public class JsonSchemaInteger extends JsonSchemaBuilder {

	private final JsonSchemaType type = JsonSchemaType.INTEGER;

	private final boolean required;

	private final Integer multipleOf;

	private final Integer minimum;

	private final Integer exclusiveMinimum;

	private final Integer maximum;

	private final Integer exclusiveMaximum;

	private JsonSchemaInteger(Builder builder) {
		this.required = builder.required;
		this.multipleOf = builder.multipleOf;
		this.minimum = builder.minimum;
		this.exclusiveMinimum = builder.exclusiveMinimum;
		this.maximum = builder.maximum;
		this.exclusiveMaximum = builder.exclusiveMaximum;
	}

	JSONObject schema() {
		JSONObject json = new JSONObject();
		json.put("type", type.getType());

		if (this.multipleOf != null) {
			json.put("multipleOf", this.multipleOf.intValue());
		}

		if (this.minimum != null) {
			json.put("minimum", this.minimum.intValue());
		}

		if (this.exclusiveMinimum != null) {
			json.put("exclusiveMinimum", this.exclusiveMinimum.intValue());
		}

		if (this.maximum != null) {
			json.put("maximum", this.maximum.intValue());
		}

		if (this.exclusiveMaximum != null) {
			json.put("exclusiveMaximum", this.exclusiveMaximum.intValue());
		}

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder {

		private boolean required;

		private Integer multipleOf;

		private Integer minimum;

		private Integer exclusiveMinimum;

		private Integer maximum;

		private Integer exclusiveMaximum;

		private Builder() {
		}

		public static Builder create() {
			return new Builder();
		}

		public Builder withRequired() {
			this.required = true;
			return this;
		}

		public Builder withMultipleOf(int multipleOf) {
			this.multipleOf = multipleOf;
			return this;
		}

		public Builder withMinimum(int minimum) {
			this.minimum = minimum;
			return this;
		}

		public Builder withExclusiveMinimum(int exclusiveMinimum) {
			this.exclusiveMinimum = exclusiveMinimum;
			return this;
		}

		public Builder withMaximum(int maximum) {
			this.maximum = maximum;
			return this;
		}

		public Builder withExclusiveMaximum(int exclusiveMaximum) {
			this.exclusiveMaximum = exclusiveMaximum;
			return this;
		}

		public JsonSchemaInteger build() {
			return new JsonSchemaInteger(this);
		}
	}
}