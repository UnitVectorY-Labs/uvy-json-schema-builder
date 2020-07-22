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

public class JsonSchemaNull extends AbstractJsonSchema {

	private final JsonSchemaType type = JsonSchemaType.NULL;

	private final boolean required;

	private final String title;

	private final String description;

	private final Boolean readOnly;

	private final Boolean writeOnly;

	private JsonSchemaNull(Builder builder) {
		this.required = builder.required;
		this.title = builder.title;
		this.description = builder.description;
		this.readOnly = builder.readOnly;
		this.writeOnly = builder.writeOnly;
	}

	/**
	 * The not keyword declares that a instance validates if it doesn’t validate
	 * against the given subschema.
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

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder extends AbstractJsonSchemaBuilder<Builder, JsonSchemaNull> {

		private boolean required;

		private String title;

		private String description;

		private Boolean readOnly;

		private Boolean writeOnly;

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

		public JsonSchemaNull build() {
			synchronized (this) {
				return new JsonSchemaNull(this);
			}
		}
	}
}