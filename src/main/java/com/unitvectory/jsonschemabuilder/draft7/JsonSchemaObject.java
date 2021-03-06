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
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonSchemaObject extends AbstractJsonSchema {

	private final JsonSchemaType type = JsonSchemaType.OBJECT;

	private final boolean required;

	private final String title;

	private final String description;

	private final Boolean readOnly;

	private final Boolean writeOnly;

	private final Map<String, AbstractJsonSchema> properties;

	private final Map<String, AbstractJsonSchema> patternProperties;

	private final String propertyNames;

	private final Map<String, Set<String>> propertyDependencies;

	private final Map<String, JsonSchemaObject> schemaDependencies;

	private final Boolean additionalProperties;

	private final AbstractJsonSchema additionalPropertiesObj;

	private final Integer minProperties;

	private final Integer maxProperties;

	private JsonSchemaObject(Builder builder) {
		this.required = builder.required;
		this.title = builder.title;
		this.description = builder.description;
		this.readOnly = builder.readOnly;
		this.writeOnly = builder.writeOnly;

		Map<String, AbstractJsonSchema> propertiesMap = new HashMap<String, AbstractJsonSchema>();
		propertiesMap.putAll(builder.properties);
		this.properties = Collections.unmodifiableMap(propertiesMap);

		Map<String, AbstractJsonSchema> patternPropertiesMap = new HashMap<String, AbstractJsonSchema>();
		patternPropertiesMap.putAll(builder.patternProperties);
		this.patternProperties = Collections.unmodifiableMap(patternPropertiesMap);

		this.additionalProperties = builder.additionalProperties;
		this.additionalPropertiesObj = builder.additionalPropertiesObj;

		this.propertyNames = builder.propertyNames;

		Map<String, Set<String>> propertyDependenciesMap = new TreeMap<String, Set<String>>();

		for (Entry<String, Set<String>> entry : builder.propertyDependencies.entrySet()) {

			Set<String> set = new TreeSet<String>();
			set.addAll(entry.getValue());
			propertyDependenciesMap.put(entry.getKey(), Collections.unmodifiableSet(set));
		}

		this.propertyDependencies = Collections.unmodifiableMap(propertyDependenciesMap);

		Map<String, JsonSchemaObject> schemaDependenciesMap = new TreeMap<String, JsonSchemaObject>();
		schemaDependenciesMap.putAll(builder.schemaDependencies);
		this.schemaDependencies = Collections.unmodifiableMap(schemaDependenciesMap);

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
	JSONObject schemaJson() {
		JSONObject json = new JSONObject();
		json.put("type", type.getType());

		if (this.title != null) {
			json.put("title", this.title);
		}

		if (this.description != null) {
			json.put("description", this.description);
		}

		if (this.readOnly != null) {
			json.put("readOnly", this.readOnly.booleanValue());
		}

		if (this.writeOnly != null) {
			json.put("writeOnly", this.writeOnly.booleanValue());
		}

		Set<String> required = new TreeSet<String>();

		if (this.properties.size() > 0) {
			JSONObject propertiesObj = new JSONObject();
			json.put("properties", propertiesObj);

			for (Entry<String, AbstractJsonSchema> entry : this.properties.entrySet()) {
				propertiesObj.put(entry.getKey(), entry.getValue().schemaJson());

				if (entry.getValue().isRequired()) {
					required.add(entry.getKey());
				}
			}
		}

		if (this.patternProperties.size() > 0) {
			JSONObject patternPropertiesObj = new JSONObject();
			json.put("patternProperties", patternPropertiesObj);

			for (Entry<String, AbstractJsonSchema> entry : this.patternProperties.entrySet()) {
				patternPropertiesObj.put(entry.getKey(), entry.getValue().schemaJson());

				if (entry.getValue().isRequired()) {
					required.add(entry.getKey());
				}
			}
		}

		if (this.propertyNames != null) {
			JSONObject propertyNamesObj = new JSONObject();
			json.put("propertyNames", propertyNamesObj);

			propertyNamesObj.put("pattern", this.propertyNames);
		}

		if (this.propertyDependencies.size() > 0 || this.schemaDependencies.size() > 0) {
			JSONObject dependencies = new JSONObject();
			json.put("dependencies", dependencies);

			for (Entry<String, Set<String>> entry : this.propertyDependencies.entrySet()) {
				JSONArray set = new JSONArray();
				for (String val : entry.getValue()) {
					set.put(val);
				}

				dependencies.put(entry.getKey(), set);
			}

			for (Entry<String, JsonSchemaObject> entry : this.schemaDependencies.entrySet()) {
				JSONObject dependencySchema = entry.getValue().schemaJson();
				dependencySchema.remove("type");
				dependencies.put(entry.getKey(), dependencySchema);
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
			json.put("additionalProperties", this.additionalPropertiesObj.schemaJson());
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

	public static class Builder extends AbstractJsonSchemaBuilder<Builder, JsonSchemaObject> {

		private boolean required;

		private String title;

		private String description;

		private Boolean readOnly;

		private Boolean writeOnly;

		private Map<String, AbstractJsonSchema> properties;

		private Map<String, AbstractJsonSchema> patternProperties;

		private String propertyNames;

		private final Map<String, Set<String>> propertyDependencies;

		private final Map<String, JsonSchemaObject> schemaDependencies;

		private Boolean additionalProperties;

		private AbstractJsonSchema additionalPropertiesObj;

		private Integer minProperties;

		private Integer maxProperties;

		private Builder() {
			this.properties = new HashMap<String, AbstractJsonSchema>();
			this.patternProperties = new HashMap<String, AbstractJsonSchema>();
			this.propertyDependencies = new HashMap<String, Set<String>>();
			this.schemaDependencies = new HashMap<String, JsonSchemaObject>();
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

		/**
		 * The title can be is used to decorate a user interface with information about
		 * the data produced by this user interface. A title will preferably be short.
		 * 
		 * @param title
		 * @return
		 */
		public Builder withTitle(String title) {
			synchronized (this) {
				this.title = title;
				return this;
			}
		}

		/**
		 * The description can be used to decorate a user interface with information
		 * about the data produced by this user interface. A description will provide
		 * explanation about the purpose of the instance described by this schema.
		 * 
		 * @param description
		 * @return
		 */
		public Builder withDescription(String description) {
			synchronized (this) {
				this.description = description;
				return this;
			}
		}

		/**
		 * If "readOnly" has a value of boolean true, it indicates that the value of the
		 * instance is managed exclusively by the owning authority, and attempts by an
		 * application to modify the value of this property are expected to be ignored
		 * or rejected by that owning authority.
		 * 
		 * An instance document that is marked as "readOnly for the entire document MAY
		 * be ignored if sent to the owning authority, or MAY result in an error, at the
		 * authority's discretion.
		 * 
		 * @return
		 */
		public Builder withReadOnly() {
			synchronized (this) {
				this.readOnly = true;
				this.writeOnly = null;
				return this;
			}
		}

		/**
		 * If "writeOnly" has a value of boolean true, it indicates that the value is
		 * never present when the instance is retrieved from the owning authority. It
		 * can be present when sent to the owning authority to update or create the
		 * document (or the resource it represents), but it will not be included in any
		 * updated or newly created version of the instance.
		 * 
		 * An instance document that is marked as "writeOnly" for the entire document
		 * MAY be returned as a blank document of some sort, or MAY produce an error
		 * upon retrieval, or have the retrieval request ignored, at the authority's
		 * discretion.
		 * 
		 * @return
		 */
		public Builder withWriteOnly() {
			synchronized (this) {
				this.writeOnly = true;
				this.readOnly = null;
				return this;
			}
		}

		/**
		 * The properties (key-value pairs) on an object are defined using the
		 * properties keyword.
		 * 
		 * @param name
		 * @param jsonSchemaBuilder
		 * @return
		 */
		public Builder withProperty(String name, AbstractJsonSchema jsonSchemaBuilder) {
			synchronized (this) {
				if (name == null) {
					throw new IllegalArgumentException("name must not be null");
				} else if (jsonSchemaBuilder == null) {
					throw new IllegalArgumentException("jsonSchemaBuilder must not be null");
				}

				this.properties.put(name, jsonSchemaBuilder);
				return this;
			}
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
		public Builder withPatternProperty(String name, AbstractJsonSchema jsonSchemaBuilder) {
			synchronized (this) {
				if (name == null) {
					throw new IllegalArgumentException("name must not be null");
				} else if (jsonSchemaBuilder == null) {
					throw new IllegalArgumentException("jsonSchemaBuilder must not be null");
				}

				this.patternProperties.put(name, jsonSchemaBuilder);
				return this;
			}
		}

		/**
		 * The names of properties can be validated against a schema, irrespective of
		 * their values. This can be useful if you don’t want to enforce specific
		 * properties, but you want to make sure that the names of those properties
		 * follow a specific convention.
		 * 
		 * @param propertyNames
		 * @return
		 */
		public Builder withPropertyNames(String propertyNames) {
			synchronized (this) {
				this.propertyNames = propertyNames;
				return this;
			}
		}

		/**
		 * 
		 * @param propertyName
		 * @param propertyDependency
		 * @return
		 */
		public Builder withPropertyDependency(String propertyName, String propertyDependency) {
			synchronized (this) {
				if (propertyName == null) {
					throw new IllegalArgumentException("propertyName must not be null");
				} else if (propertyDependency == null) {
					throw new IllegalArgumentException("propertyDependency must not be null");
				}

				this.schemaDependencies.remove(propertyName);

				Set<String> set = this.propertyDependencies.get(propertyName);
				if (set == null) {
					set = new HashSet<String>();
					this.propertyDependencies.put(propertyName, set);
				}

				set.add(propertyDependency);

				return this;
			}
		}

		/**
		 * 
		 * @param property
		 * @param schemaDependency
		 * @return
		 */
		public Builder withSchemaDependency(String propertyName, JsonSchemaObject schemaDependency) {
			synchronized (this) {
				if (propertyName == null) {
					throw new IllegalArgumentException("propertyName must not be null");
				} else if (schemaDependency == null) {
					throw new IllegalArgumentException("schemaDependency must not be null");
				}

				this.propertyDependencies.remove(propertyName);
				this.schemaDependencies.put(propertyName, schemaDependency);

				return this;
			}
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
			synchronized (this) {
				this.additionalProperties = additionalProperties;
				this.additionalPropertiesObj = null;
				return this;
			}
		}

		/**
		 * If additionalProperties is an object, that object is a schema that will be
		 * used to validate any additional properties not listed in properties.
		 * 
		 * @param additionalProperties
		 * @return
		 */
		public Builder withAdditionalProperties(AbstractJsonSchema additionalProperties) {
			synchronized (this) {
				this.additionalProperties = null;
				this.additionalPropertiesObj = additionalProperties;
				return this;
			}
		}

		/**
		 * The number of properties on an object can be restricted using the
		 * minProperties keyword. Each of these must be a non-negative integer.
		 * 
		 * @param minProperties
		 * @return
		 */
		public Builder withMinProperties(int minProperties) {
			synchronized (this) {
				if (minProperties < 0) {
					throw new IllegalArgumentException("minProperties must be non-negative");
				}

				this.minProperties = minProperties;
				return this;
			}
		}

		/**
		 * The number of properties on an object can be restricted using the
		 * maxProperties keywords. Each of these must be a non-negative integer.
		 * 
		 * @param maxProperties
		 * @return
		 */
		public Builder withMaxProperties(int maxProperties) {
			synchronized (this) {
				if (maxProperties < 0) {
					throw new IllegalArgumentException("minProperties must be non-negative");
				}

				this.maxProperties = maxProperties;
				return this;
			}
		}

		public JsonSchemaObject build() {
			synchronized (this) {
				return new JsonSchemaObject(this);
			}
		}
	}
}
