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

public class JsonSchemaArray extends AbstractJsonSchema {

	private final JsonSchemaType type = JsonSchemaType.ARRAY;

	private final boolean required;

	private final AbstractJsonSchema contains;

	private final AbstractJsonSchema itemValidation;

	private final List<AbstractJsonSchema> itemTuple;

	private final Boolean additionalItems;

	private final AbstractJsonSchema additionalItemsSchema;

	private final Integer minItems;

	private final Integer maxItems;

	private final Boolean uniqueItems;

	private JsonSchemaArray(Builder builder) {
		this.required = builder.required;

		this.contains = builder.contains;

		this.itemValidation = builder.itemValidation;

		if (builder.itemTuple != null) {
			List<AbstractJsonSchema> itemTupleList = new ArrayList<>();
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

	public static Builder create() {
		return new Builder();
	}

	JSONObject schemaJson() {
		JSONObject json = new JSONObject();
		json.put("type", type.getType());

		if (this.contains != null) {
			json.put("contains", this.contains.schemaJson());
		}

		if (this.itemValidation != null) {

			json.put("items", this.itemValidation.schemaJson());

		} else if (this.itemTuple != null) {

			JSONArray items = new JSONArray();
			json.put("items", items);

			for (AbstractJsonSchema item : this.itemTuple) {
				items.put(item.schemaJson());
			}

		}

		if (this.additionalItems != null) {
			json.put("additionalItems", this.additionalItems.booleanValue());
		} else if (this.additionalItemsSchema != null) {
			json.put("additionalItems", this.additionalItemsSchema.schemaJson());
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

	public static class Builder extends AbstractJsonSchemaBuilder<Builder, JsonSchemaArray> {

		private boolean required;

		private AbstractJsonSchema contains;

		private AbstractJsonSchema itemValidation;

		private List<AbstractJsonSchema> itemTuple;

		private Boolean additionalItems;

		private AbstractJsonSchema additionalItemsSchema;

		private Integer minItems;

		private Integer maxItems;

		private Boolean uniqueItems;

		private Builder() {
			this.required = false;
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
		 * The contains schema only needs to validate against one or more items in the
		 * array
		 * 
		 * @param contains
		 * @return
		 */
		public Builder withContains(AbstractJsonSchema contains) {
			this.contains = contains;
			return this;
		}

		/**
		 * List validation is useful for arrays of arbitrary length where each item
		 * matches the same schema. For this kind of array, set the items keyword to a
		 * single schema that will be used to validate all of the items in the array.
		 * 
		 * @param item
		 * @return
		 */
		public Builder withItem(AbstractJsonSchema item) {
			if (item == null) {
				this.itemValidation = null;
				return this;
			}

			this.itemValidation = item;
			this.itemTuple = null;
			return this;
		}

		/**
		 * Tuple validation is useful when the array is a collection of items where each
		 * has a different schema and the ordinal index of each item is meaningful.
		 * 
		 * @param items
		 * @return
		 */
		public Builder withItemTuple(AbstractJsonSchema... items) {
			if (items == null) {
				this.itemTuple = null;
				return this;
			}

			this.itemValidation = null;

			this.itemTuple = new ArrayList<AbstractJsonSchema>();
			for (AbstractJsonSchema item : items) {
				this.itemTuple.add(item);
			}

			return this;
		}

		/**
		 * The additionalItems keyword controls whether it’s valid to have additional
		 * items in the array beyond what is defined in items.
		 * 
		 * @param additionalItems
		 * @return
		 */
		public Builder withAdditionalItems(boolean additionalItems) {
			this.additionalItems = additionalItems;
			this.additionalItemsSchema = null;
			return this;
		}

		/**
		 * The additionalItems keyword may also be a schema to validate against every
		 * additional item in the array.
		 * 
		 * @param additionalItems
		 * @return
		 */
		public Builder withAdditionalItems(AbstractJsonSchema additionalItems) {
			this.additionalItems = null;
			this.additionalItemsSchema = additionalItems;
			return this;
		}

		/**
		 * The minimum number of items in the array.
		 * 
		 * The value of each keyword must be a non-negative number. These keywords work
		 * whether doing List validation or Tuple validation.
		 * 
		 * @param minItems
		 * @return
		 */
		public Builder withMinItems(int minItems) {
			if (minItems < 0) {
				throw new IllegalArgumentException("minItems must be non-negative");
			}

			this.minItems = minItems;
			return this;
		}

		/**
		 * The maximum number of items in the array.
		 * 
		 * The value of each keyword must be a non-negative number. These keywords work
		 * whether doing List validation or Tuple validation.
		 * 
		 * @param maxItems
		 * @return
		 */
		public Builder withMaxItems(int maxItems) {
			if (maxItems < 0) {
				throw new IllegalArgumentException("maxItems must be non-negative");
			}

			this.maxItems = maxItems;
			return this;
		}

		/**
		 * A schema can ensure that each of the items in an array is unique. Simply set
		 * the uniqueItems keyword to true.
		 * 
		 * @param uniqueItems
		 * @return
		 */
		public Builder withUniqueItems(boolean uniqueItems) {
			this.uniqueItems = uniqueItems;
			return this;
		}

		public JsonSchemaArray build() {
			return new JsonSchemaArray(this);
		}
	}
}