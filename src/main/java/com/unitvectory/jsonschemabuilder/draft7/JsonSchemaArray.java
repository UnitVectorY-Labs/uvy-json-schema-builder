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

public class JsonSchemaArray extends JsonSchemaBuilder {

	private final JsonSchemaType type = JsonSchemaType.ARRAY;

	private final boolean required;

	private final JsonSchemaBuilder contains;

	private final JsonSchemaBuilder itemValidation;

	private final List<JsonSchemaBuilder> itemTuple;

	private final Boolean additionalItems;

	private final JsonSchemaBuilder additionalItemsSchema;

	private final Integer minItems;

	private final Integer maxItems;

	private final Boolean uniqueItems;

	private JsonSchemaArray(Builder builder) {
		this.required = builder.required;

		this.contains = builder.contains;

		this.itemValidation = builder.itemValidation;

		if (builder.itemTuple != null) {
			List<JsonSchemaBuilder> itemTupleList = new ArrayList<>();
			itemTupleList.addAll(builder.itemTuple);
			this.itemTuple = Collections.unmodifiableList(itemTupleList);
		} else {
			this.itemTuple = null;
		}

		this.additionalItems = builder.additionalItems;
		this.additionalItemsSchema = builder.additionalItemsSchema;

		this.minItems = builder.minItems;
		this.maxItems = builder.maxItems;
		this.uniqueItems = builder.uniqueItems;
	}

	JSONObject schema() {
		JSONObject json = new JSONObject();
		json.put("type", type.getType());

		if (this.contains != null) {
			json.put("contains", this.contains.schema());
		}

		if (this.itemValidation != null) {

			json.put("items", this.itemValidation.schema());

		} else if (this.itemTuple != null) {

			JSONArray items = new JSONArray();
			json.put("items", items);

			for (JsonSchemaBuilder item : this.itemTuple) {
				items.put(item.schema());
			}

		}

		if (this.additionalItems != null) {
			json.put("additionalItems", this.additionalItems.booleanValue());
		} else if (this.additionalItemsSchema != null) {
			json.put("additionalItems", this.additionalItemsSchema.schema());
		}

		if (this.minItems != null) {
			json.put("minItems", this.minItems.intValue());
		}

		if (this.maxItems != null) {
			json.put("maxItems", this.maxItems.intValue());
		}

		if (this.uniqueItems != null) {
			json.put("uniqueItems", this.uniqueItems.booleanValue());
		}

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder {

		private boolean required;

		private JsonSchemaBuilder contains;

		private JsonSchemaBuilder itemValidation;

		private List<JsonSchemaBuilder> itemTuple;

		private Boolean additionalItems;

		private JsonSchemaBuilder additionalItemsSchema;

		private Integer minItems;

		private Integer maxItems;

		private Boolean uniqueItems;

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

		public Builder withContains(JsonSchemaBuilder contains) {
			this.contains = contains;
			return this;
		}

		public Builder withItem(JsonSchemaBuilder item) {
			if (item == null) {
				this.itemValidation = null;
				return this;
			}

			this.itemValidation = item;
			this.itemTuple = null;
			return this;
		}

		public Builder withItemTuple(JsonSchemaBuilder... items) {
			if (items == null) {
				this.itemTuple = null;
				return this;
			}

			this.itemValidation = null;

			this.itemTuple = new ArrayList<JsonSchemaBuilder>();
			for (JsonSchemaBuilder item : items) {
				this.itemTuple.add(item);
			}

			return this;
		}

		public Builder withAdditionalItems(boolean additionalItems) {
			this.additionalItems = additionalItems;
			this.additionalItemsSchema = null;
			return this;
		}

		public Builder withAdditionalItems(JsonSchemaBuilder additionalItems) {
			this.additionalItems = null;
			this.additionalItemsSchema = additionalItems;
			return this;
		}

		public Builder withMinItems(int minItems) {
			this.minItems = minItems;
			return this;
		}

		public Builder withMaxItems(int maxItems) {
			this.maxItems = maxItems;
			return this;
		}

		public Builder withUniqueItems(boolean uniqueItems) {
			this.uniqueItems = uniqueItems;
			return this;
		}

		public JsonSchemaArray build() {
			return new JsonSchemaArray(this);
		}
	}
}