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

public class JsonSchemaInteger extends AbstractJsonSchema {

	private final JsonSchemaType type = JsonSchemaType.INTEGER;

	private final boolean required;

	private final String title;

	private final String description;

	private final Boolean readOnly;

	private final Boolean writeOnly;

	private final Integer multipleOf;

	private final Integer minimum;

	private final Integer exclusiveMinimum;

	private final Integer maximum;

	private final Integer exclusiveMaximum;

	private JsonSchemaInteger(Builder builder) {
		this.required = builder.required;
		this.title = builder.title;
		this.description = builder.description;
		this.readOnly = builder.readOnly;
		this.writeOnly = builder.writeOnly;
		this.multipleOf = builder.multipleOf;
		this.minimum = builder.minimum;
		this.exclusiveMinimum = builder.exclusiveMinimum;
		this.maximum = builder.maximum;
		this.exclusiveMaximum = builder.exclusiveMaximum;
	}

	/**
	 * The integer type is used for integral numbers.
	 * 
	 * @return
	 */
	public static Builder create() {
		return new Builder();
	}

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

	public static class Builder extends AbstractJsonSchemaBuilder<Builder, JsonSchemaInteger> {

		private boolean required;

		private String title;

		private String description;

		private Boolean readOnly;

		private Boolean writeOnly;

		private Integer multipleOf;

		private Integer minimum;

		private Integer exclusiveMinimum;

		private Integer maximum;

		private Integer exclusiveMaximum;

		private Builder() {
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
		 * The title can be is used to decorate a user interface with information about
		 * the data produced by this user interface. A title will preferably be short.
		 * 
		 * @param title
		 * @return
		 */
		public Builder withTitle(String title) {
			this.title = title;
			return this;
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
			this.description = description;
			return this;
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
			this.readOnly = true;
			this.writeOnly = null;
			return this;
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
			this.writeOnly = true;
			this.readOnly = null;
			return this;
		}

		/**
		 * Numbers can be restricted to a multiple of a given number, using the
		 * multipleOf keyword. It may be set to any positive number.
		 * 
		 * @param multipleOf
		 * @return
		 */
		public Builder withMultipleOf(int multipleOf) {
			if (multipleOf <= 0) {
				throw new IllegalArgumentException("multipleOf must be positive");
			}

			this.multipleOf = multipleOf;
			return this;
		}

		/**
		 * The minimum
		 * 
		 * @param minimum
		 * @return
		 */
		public Builder withMinimum(int minimum) {
			this.minimum = minimum;
			return this;
		}

		/**
		 * The exclusive minimum
		 * 
		 * @param exclusiveMinimum
		 * @return
		 */
		public Builder withExclusiveMinimum(int exclusiveMinimum) {
			this.exclusiveMinimum = exclusiveMinimum;
			return this;
		}

		/**
		 * The maximum
		 * 
		 * @param maximum
		 * @return
		 */
		public Builder withMaximum(int maximum) {
			this.maximum = maximum;
			return this;
		}

		/**
		 * The exclusive maximum
		 * 
		 * @param exclusiveMaximum
		 * @return
		 */
		public Builder withExclusiveMaximum(int exclusiveMaximum) {
			this.exclusiveMaximum = exclusiveMaximum;
			return this;
		}

		public JsonSchemaInteger build() {
			return new JsonSchemaInteger(this);
		}
	}
}