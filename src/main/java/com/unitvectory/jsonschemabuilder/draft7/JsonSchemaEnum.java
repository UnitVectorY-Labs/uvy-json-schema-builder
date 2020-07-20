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

public class JsonSchemaEnum extends AbstractJsonSchema {

	private final boolean required;

	private final Set<String> enumString;

	private final Set<Integer> enumInteger;

	private final Set<Double> enumDouble;

	private final boolean enumNull;

	private JsonSchemaEnum(Builder builder) {
		this.required = builder.required;

		Set<String> enumStringCopy = new TreeSet<String>();
		enumStringCopy.addAll(builder.enumString);
		this.enumString = Collections.unmodifiableSet(enumStringCopy);

		Set<Integer> enumIntegerCopy = new TreeSet<Integer>();
		enumIntegerCopy.addAll(builder.enumInteger);
		this.enumInteger = Collections.unmodifiableSet(enumIntegerCopy);

		Set<Double> enumDoubleCopy = new TreeSet<Double>();
		enumDoubleCopy.addAll(builder.enumDouble);
		this.enumDouble = Collections.unmodifiableSet(enumDoubleCopy);

		this.enumNull = builder.enumNull;
	}

	/**
	 * The enum keyword is used to restrict a value to a fixed set of values. It
	 * must be an array with at least one element, where each element is unique.
	 * 
	 * @return
	 */
	public static Builder create() {
		return new Builder();
	}

	JSONObject schemaJson() {
		JSONObject json = new JSONObject();

		JSONArray enumArr = new JSONArray();
		json.put("enum", enumArr);

		for (String e : this.enumString) {
			enumArr.put(e);
		}

		for (Integer i : this.enumInteger) {
			enumArr.put(i.intValue());
		}

		for (Double d : this.enumDouble) {
			enumArr.put(d.doubleValue());
		}

		if (this.enumNull) {
			enumArr.put((Object) null);
		}

		// An enumeration schema cannot be empty, so if an empty was built, then allow
		// for null

		if (enumArr.length() == 0) {
			enumArr.put((Object) null);
		}

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder extends AbstractJsonSchemaBuilder<Builder, JsonSchemaEnum> {

		private boolean required;

		private Set<String> enumString;

		private Set<Integer> enumInteger;

		private Set<Double> enumDouble;

		private boolean enumNull;

		private Builder() {
			this.required = false;
			this.enumString = new TreeSet<String>();
			this.enumInteger = new TreeSet<Integer>();
			this.enumDouble = new TreeSet<Double>();
			this.enumNull = false;
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
		 * Enumeration string value
		 * 
		 * @param enumValue
		 * @return
		 */
		public Builder withEnumValue(String enumValue) {
			if (enumValue == null) {
				return this;
			}

			this.enumString.add(enumValue);
			return this;
		}

		/**
		 * Enumeration integer value
		 * 
		 * @param enumValue
		 * @return
		 */
		public Builder withEnumValue(int enumValue) {
			this.enumInteger.add(enumValue);
			return this;
		}

		/**
		 * Enumeration double value
		 * 
		 * @param enumValue
		 * @return
		 */
		public Builder withEnumValue(double enumValue) {
			this.enumDouble.add(enumValue);
			return this;
		}

		/**
		 * Enumeration null value
		 * 
		 * @return
		 */
		public Builder withNull() {
			this.enumNull = true;
			return this;
		}

		public JsonSchemaEnum build() {
			return new JsonSchemaEnum(this);
		}
	}
}