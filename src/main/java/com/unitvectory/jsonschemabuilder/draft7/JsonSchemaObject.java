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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

public class JsonSchemaObject extends JsonSchemaBuilder {

	private final JsonSchemaType type = JsonSchemaType.OBJECT;

	private final Map<String, JsonSchemaBuilder> properties;

	private final Boolean additionalProperties;

	private JsonSchemaObject(Builder builder) {
		Map<String, JsonSchemaBuilder> propertiesMap = new HashMap<String, JsonSchemaBuilder>();
		propertiesMap.putAll(builder.properties);
		this.properties = Collections.unmodifiableMap(propertiesMap);

		this.additionalProperties = builder.additionalProperties;
	}

	@Override
	JSONObject schema() {
		JSONObject json = new JSONObject();
		json.put("type", type.getType());

		if (this.properties.size() > 0) {
			JSONObject propertiesObj = new JSONObject();
			json.put("properties", propertiesObj);

			for (Entry<String, JsonSchemaBuilder> entry : this.properties.entrySet()) {
				propertiesObj.put(entry.getKey(), entry.getValue().schema());
			}
		}

		if (this.additionalProperties != null) {
			json.put("additionalProperties", this.additionalProperties.booleanValue());
		}

		return json;
	}

	public static class Builder {

		private Map<String, JsonSchemaBuilder> properties;

		private Boolean additionalProperties;

		private Builder() {
			this.properties = new HashMap<String, JsonSchemaBuilder>();
		}

		public static Builder create() {
			return new Builder();
		}

		public Builder withProperty(String name, JsonSchemaString jsonSchemaString) {
			if (name == null) {
				throw new IllegalArgumentException("name must not be null");
			} else if (jsonSchemaString == null) {
				throw new IllegalArgumentException("jsonSchemaString must not be null");
			}

			this.properties.put(name, jsonSchemaString);
			return this;
		}

		public Builder withAdditionalProperties(boolean additionalProperties) {
			this.additionalProperties = additionalProperties;
			return this;
		}

		public JsonSchemaObject build() {
			return new JsonSchemaObject(this);
		}
	}
}
