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
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonSchemaString extends AbstractJsonSchema {

	private final JsonSchemaType type = JsonSchemaType.STRING;

	private final boolean required;

	private final String title;

	private final String description;

	private final Boolean readOnly;

	private final Boolean writeOnly;

	private final Integer minLength;

	private final Integer maxLength;

	private final String pattern;

	private final Set<String> enumSet;

	private JsonSchemaString(Builder builder) {
		this.required = builder.required;
		this.title = builder.title;
		this.description = builder.description;
		this.readOnly = builder.readOnly;
		this.writeOnly = builder.writeOnly;
		this.minLength = builder.minLength;
		this.maxLength = builder.maxLength;
		this.pattern = builder.pattern;

		Set<String> enumSetCopy = new TreeSet<String>();
		enumSetCopy.addAll(builder.enumSet);
		this.enumSet = Collections.unmodifiableSet(enumSetCopy);
	}

	/**
	 * The string type is used for strings of text. It may contain Unicode
	 * characters.
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

		if (this.minLength != null) {
			json.put("minLength", this.minLength.intValue());
		}

		if (this.maxLength != null) {
			json.put("maxLength", this.maxLength.intValue());
		}

		if (this.pattern != null) {
			json.put("pattern", this.pattern);
		}

		if (this.enumSet.size() > 0) {
			JSONArray enumArr = new JSONArray();
			json.put("enum", enumArr);

			for (String e : this.enumSet) {
				enumArr.put(e);
			}
		}

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder extends AbstractJsonSchemaBuilder<Builder, JsonSchemaString> {

		private boolean required;

		private String title;

		private String description;

		private Boolean readOnly;

		private Boolean writeOnly;

		private Integer minLength;

		private Integer maxLength;

		private String pattern;

		private Set<String> enumSet;

		private Builder() {
			this.enumSet = new TreeSet<String>();
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
		 * The length of a string can be constrained using the minLength keywords. For
		 * both keywords, the value must be a non-negative number.
		 * 
		 * @param minLength
		 * @return
		 */
		public Builder withMinLength(int minLength) {
			synchronized (this) {
				if (minLength < 0) {
					throw new IllegalArgumentException("minLength must be non-negative");
				}

				this.minLength = minLength;
				return this;
			}
		}

		/**
		 * The length of a string can be constrained using the maxLength keywords. For
		 * both keywords, the value must be a non-negative number.
		 * 
		 * @param maxLength
		 * @return
		 */
		public Builder withMaxLength(int maxLength) {
			synchronized (this) {
				if (maxLength < 0) {
					throw new IllegalArgumentException("maxLength must be non-negative");
				}

				this.maxLength = maxLength;
				return this;
			}
		}

		/**
		 * The pattern keyword is used to restrict a string to a particular regular
		 * expression. The regular expression syntax is the one defined in JavaScript
		 * (ECMA 262 specifically).
		 * 
		 * @param pattern
		 * @return
		 */
		public Builder withPattern(String pattern) {
			synchronized (this) {
				this.pattern = pattern;
				return this;
			}
		}

		/**
		 * The enum keyword is used to restrict a value to a fixed set of values. It
		 * must be an array with at least one element, where each element is unique.
		 * 
		 * @param enumValue
		 * @return
		 */
		public Builder withEnumValue(String enumValue) {
			synchronized (this) {
				if (enumValue == null) {
					return this;
				}

				this.enumSet.add(enumValue);
				return this;
			}
		}

		public JsonSchemaString build() {
			synchronized (this) {
				return new JsonSchemaString(this);
			}
		}
	}
}
