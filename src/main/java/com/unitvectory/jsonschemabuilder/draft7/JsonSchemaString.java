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

	private final Integer minLength;

	private final Integer maxLength;

	private final String pattern;

	private final Set<String> enumSet;

	private JsonSchemaString(Builder builder) {
		this.required = builder.required;
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
			this.required = true;
			return this;
		}

		/**
		 * The length of a string can be constrained using the minLength keywords. For
		 * both keywords, the value must be a non-negative number.
		 * 
		 * @param minLength
		 * @return
		 */
		public Builder withMinLength(int minLength) {
			if (minLength < 0) {
				throw new IllegalArgumentException("minLength must be non-negative");
			}

			this.minLength = minLength;
			return this;
		}

		/**
		 * The length of a string can be constrained using the maxLength keywords. For
		 * both keywords, the value must be a non-negative number.
		 * 
		 * @param maxLength
		 * @return
		 */
		public Builder withMaxLength(int maxLength) {
			if (maxLength < 0) {
				throw new IllegalArgumentException("maxLength must be non-negative");
			}

			this.maxLength = maxLength;
			return this;
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
			this.pattern = pattern;
			return this;
		}

		/**
		 * The enum keyword is used to restrict a value to a fixed set of values. It
		 * must be an array with at least one element, where each element is unique.
		 * 
		 * @param enumValue
		 * @return
		 */
		public Builder withEnumValue(String enumValue) {
			if (enumValue == null) {
				return this;
			}

			this.enumSet.add(enumValue);
			return this;
		}

		public JsonSchemaString build() {
			return new JsonSchemaString(this);
		}
	}
}
