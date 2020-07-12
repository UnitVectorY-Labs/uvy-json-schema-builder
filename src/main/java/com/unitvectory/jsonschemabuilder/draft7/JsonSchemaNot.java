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

public class JsonSchemaNot extends JsonSchemaBuilder {

	private final boolean required;

	private final JsonSchemaBuilder not;

	private JsonSchemaNot(Builder builder) {
		this.required = builder.required;
		this.not = builder.not;
	}

	JSONObject schema() {
		JSONObject json = new JSONObject();

		if (this.not != null) {
			json.put("not", this.not.schema());
		}

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder {

		private boolean required;

		private JsonSchemaBuilder not;

		private Builder() {
			this.required = false;
		}

		public static Builder create() {
			return new Builder();
		}

		public Builder withRequired() {
			this.required = true;
			return this;
		}

		public Builder withNot(JsonSchemaBuilder not) {
			this.not = not;
			return this;
		}

		public JsonSchemaNot build() {
			return new JsonSchemaNot(this);
		}
	}
}