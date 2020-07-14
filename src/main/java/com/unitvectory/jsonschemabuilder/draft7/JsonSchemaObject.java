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
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonSchemaObject extends JsonSchemaBuilder {

	private final JsonSchemaType type = JsonSchemaType.OBJECT;

	private final boolean required;

	private final Map<String, JsonSchemaBuilder> properties;

	private final Map<String, JsonSchemaBuilder> patternProperties;

	// TODO: Add propertyNames

	// TODO: Add dependencies

	private final Boolean additionalProperties;

	private final JsonSchemaBuilder additionalPropertiesObj;

	private final Integer minProperties;

	private final Integer maxProperties;

	private JsonSchemaObject(Builder builder) {
		this.required = builder.required;

		Map<String, JsonSchemaBuilder> propertiesMap = new HashMap<String, JsonSchemaBuilder>();
		propertiesMap.putAll(builder.properties);
		this.properties = Collections.unmodifiableMap(propertiesMap);

		Map<String, JsonSchemaBuilder> patternPropertiesMap = new HashMap<String, JsonSchemaBuilder>();
		patternPropertiesMap.putAll(builder.patternProperties);
		this.patternProperties = Collections.unmodifiableMap(patternPropertiesMap);

		this.additionalProperties = builder.additionalProperties;
		this.additionalPropertiesObj = builder.additionalPropertiesObj;

		this.minProperties = builder.minProperties;
		this.maxProperties = builder.maxProperties;
	}

	/**
	 * Objects are the mapping type in JSON. They map “keys” to “values”. In JSON,
	 * the “keys” must always be strings. Each of these pairs is conventionally
	 * referred to as a “property”.
	 * 
	 * @return
	 */
	public static Builder create() {
		return new Builder();
	}

	@Override
	JSONObject schema() {
		JSONObject json = new JSONObject();
		json.put("type", type.getType());

		Set<String> required = new TreeSet<String>();

		if (this.properties.size() > 0) {
			JSONObject propertiesObj = new JSONObject();
			json.put("properties", propertiesObj);

			for (Entry<String, JsonSchemaBuilder> entry : this.properties.entrySet()) {
				propertiesObj.put(entry.getKey(), entry.getValue().schema());

				if (entry.getValue().isRequired()) {
					required.add(entry.getKey());
				}
			}
		}

		if (this.patternProperties.size() > 0) {
			JSONObject patternPropertiesObj = new JSONObject();
			json.put("patternProperties", patternPropertiesObj);

			for (Entry<String, JsonSchemaBuilder> entry : this.patternProperties.entrySet()) {
				patternPropertiesObj.put(entry.getKey(), entry.getValue().schema());

				if (entry.getValue().isRequired()) {
					required.add(entry.getKey());
				}
			}
		}

		if (required.size() > 0) {
			JSONArray requiredArr = new JSONArray();
			json.put("required", requiredArr);

			for (String r : required) {
				requiredArr.put(r);
			}
		}

		if (this.additionalProperties != null) {
			json.put("additionalProperties", this.additionalProperties.booleanValue());
		} else if (additionalPropertiesObj != null) {
			json.put("additionalProperties", this.additionalPropertiesObj.schema());
		}

		if (this.minProperties != null) {
			json.put("minProperties", this.minProperties.intValue());
		}

		if (this.maxProperties != null) {
			json.put("maxProperties", this.maxProperties.intValue());
		}

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder {

		private boolean required;

		private Map<String, JsonSchemaBuilder> properties;

		private Map<String, JsonSchemaBuilder> patternProperties;

		private Boolean additionalProperties;

		private JsonSchemaBuilder additionalPropertiesObj;

		private Integer minProperties;

		private Integer maxProperties;

		private Builder() {
			this.properties = new HashMap<String, JsonSchemaBuilder>();
			this.patternProperties = new HashMap<String, JsonSchemaBuilder>();
		}

		/**
		 * By default, the properties defined by the properties keyword are not
		 * required. However, one can provide a list of required properties using the
		 * required keyword.
		 * 
		 * @return
		 */
		public Builder withRequired() {
			this.required = true;
			return this;
		}

		/**
		 * The properties (key-value pairs) on an object are defined using the
		 * properties keyword.
		 * 
		 * @param name
		 * @param jsonSchemaBuilder
		 * @return
		 */
		public Builder withProperty(String name, JsonSchemaBuilder jsonSchemaBuilder) {
			if (name == null) {
				throw new IllegalArgumentException("name must not be null");
			} else if (jsonSchemaBuilder == null) {
				throw new IllegalArgumentException("jsonSchemaBuilder must not be null");
			}

			this.properties.put(name, jsonSchemaBuilder);
			return this;
		}

		/**
		 * Pattern property maps from regular expressions to schemas. If an additional
		 * property matches a given regular expression, it must also validate against
		 * the corresponding schema.
		 * 
		 * @param name
		 * @param jsonSchemaBuilder
		 * @return
		 */
		public Builder withPatternProperty(String name, JsonSchemaBuilder jsonSchemaBuilder) {
			if (name == null) {
				throw new IllegalArgumentException("name must not be null");
			} else if (jsonSchemaBuilder == null) {
				throw new IllegalArgumentException("jsonSchemaBuilder must not be null");
			}

			this.patternProperties.put(name, jsonSchemaBuilder);
			return this;
		}

		/**
		 * The additionalProperties keyword is used to control the handling of extra
		 * stuff, that is, properties whose names are not listed in the properties
		 * keyword. By default any additional properties are allowed.
		 * 
		 * @param additionalProperties
		 * @return
		 */
		public Builder withAdditionalProperties(boolean additionalProperties) {
			this.additionalProperties = additionalProperties;
			this.additionalPropertiesObj = null;
			return this;
		}

		/**
		 * If additionalProperties is an object, that object is a schema that will be
		 * used to validate any additional properties not listed in properties.
		 * 
		 * @param additionalProperties
		 * @return
		 */
		public Builder withAdditionalProperties(JsonSchemaBuilder additionalProperties) {
			this.additionalProperties = null;
			this.additionalPropertiesObj = additionalProperties;
			return this;
		}

		/**
		 * The number of properties on an object can be restricted using the
		 * minProperties keyword. Each of these must be a non-negative integer.
		 * 
		 * @param minProperties
		 * @return
		 */
		public Builder withMinProperties(int minProperties) {
			if (minProperties < 0) {
				throw new IllegalArgumentException("minProperties must be non-negative");
			}

			this.minProperties = minProperties;
			return this;
		}

		/**
		 * The number of properties on an object can be restricted using the
		 * maxProperties keywords. Each of these must be a non-negative integer.
		 * 
		 * @param maxProperties
		 * @return
		 */
		public Builder withMaxProperties(int maxProperties) {
			if (maxProperties < 0) {
				throw new IllegalArgumentException("minProperties must be non-negative");
			}

			this.maxProperties = maxProperties;
			return this;
		}

		public JsonSchemaObject build() {
			return new JsonSchemaObject(this);
		}
	}
}
