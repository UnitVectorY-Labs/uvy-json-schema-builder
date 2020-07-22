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

public class JsonSchemaNumber extends AbstractJsonSchema {

	private final JsonSchemaType type = JsonSchemaType.NUMBER;

	private final boolean required;

	private final String title;

	private final String description;

	private final Boolean readOnly;

	private final Boolean writeOnly;

	private final Integer multipleOfI;

	private final Double multipleOfD;

	private final Integer minimumI;

	private final Double minimumD;

	private final Integer exclusiveMinimumI;

	private final Double exclusiveMinimumD;

	private final Integer maximumI;

	private final Double maximumD;

	private final Integer exclusiveMaximumI;

	private Double exclusiveMaximumD;

	private JsonSchemaNumber(Builder builder) {
		this.required = builder.required;
		this.title = builder.title;
		this.description = builder.description;
		this.readOnly = builder.readOnly;
		this.writeOnly = builder.writeOnly;
		this.multipleOfI = builder.multipleOfI;
		this.multipleOfD = builder.multipleOfD;
		this.minimumI = builder.minimumI;
		this.minimumD = builder.minimumD;
		this.exclusiveMinimumI = builder.exclusiveMinimumI;
		this.exclusiveMinimumD = builder.exclusiveMinimumD;
		this.maximumI = builder.maximumI;
		this.maximumD = builder.maximumD;
		this.exclusiveMaximumI = builder.exclusiveMaximumI;
		this.exclusiveMaximumD = builder.exclusiveMaximumD;
	}

	/**
	 * The number type is used for any numeric type, either integers or floating
	 * point numbers.
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

		if (this.multipleOfI != null) {
			json.put("multipleOf", this.multipleOfI.intValue());
		} else if (this.multipleOfD != null) {
			json.put("multipleOf", this.multipleOfD.doubleValue());
		}

		if (this.minimumI != null) {
			json.put("minimum", this.minimumI.intValue());
		} else if (this.minimumD != null) {
			json.put("minimum", this.minimumD.doubleValue());
		}

		if (this.exclusiveMinimumI != null) {
			json.put("exclusiveMinimum", this.exclusiveMinimumI.intValue());
		} else if (this.exclusiveMinimumD != null) {
			json.put("exclusiveMinimum", this.exclusiveMinimumD.doubleValue());
		}

		if (this.maximumI != null) {
			json.put("maximum", this.maximumI.intValue());
		} else if (this.maximumD != null) {
			json.put("maximum", this.maximumD.doubleValue());
		}

		if (this.exclusiveMaximumI != null) {
			json.put("exclusiveMaximum", this.exclusiveMaximumI.intValue());
		} else if (this.exclusiveMaximumD != null) {
			json.put("exclusiveMaximum", this.exclusiveMaximumD.doubleValue());
		}

		return json;
	}

	boolean isRequired() {
		return this.required;
	}

	public static class Builder extends AbstractJsonSchemaBuilder<Builder, JsonSchemaNumber> {

		private boolean required;

		private String title;

		private String description;

		private Boolean readOnly;

		private Boolean writeOnly;

		private Integer multipleOfI;

		private Double multipleOfD;

		private Integer minimumI;

		private Double minimumD;

		private Integer exclusiveMinimumI;

		private Double exclusiveMinimumD;

		private Integer maximumI;

		private Double maximumD;

		private Integer exclusiveMaximumI;

		private Double exclusiveMaximumD;

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
		 * Numbers can be restricted to a multiple of a given number, using the
		 * multipleOf keyword. It may be set to any positive number.
		 * 
		 * @param multipleOf
		 * @return
		 */
		public Builder withMultipleOf(int multipleOf) {
			synchronized (this) {
				if (multipleOf <= 0) {
					throw new IllegalArgumentException("multipleOf must be positive");
				}

				this.multipleOfI = multipleOf;
				this.multipleOfD = null;
				return this;
			}
		}

		/**
		 * Numbers can be restricted to a multiple of a given number, using the
		 * multipleOf keyword. It may be set to any positive number.
		 * 
		 * @param multipleOf
		 * @return
		 */
		public Builder withMultipleOf(double multipleOf) {
			synchronized (this) {
				if (multipleOf <= 0) {
					throw new IllegalArgumentException("multipleOf must be positive");
				}

				this.multipleOfD = multipleOf;
				this.multipleOfI = null;
				return this;
			}
		}

		/**
		 * The minimum
		 * 
		 * @param minimum
		 * @return
		 */
		public Builder withMinimum(int minimum) {
			synchronized (this) {
				this.minimumI = minimum;
				this.minimumD = null;
				return this;
			}
		}

		/**
		 * The minimum
		 * 
		 * @param minimum
		 * @return
		 */
		public Builder withMinimum(double minimum) {
			synchronized (this) {
				this.minimumD = minimum;
				this.minimumI = null;
				return this;
			}
		}

		/**
		 * The exclusive minimum
		 * 
		 * @param exclusiveMinimum
		 * @return
		 */
		public Builder withExclusiveMinimum(int exclusiveMinimum) {
			synchronized (this) {
				this.exclusiveMinimumI = exclusiveMinimum;
				this.exclusiveMinimumD = null;
				return this;
			}
		}

		/**
		 * The exclusive minimum
		 * 
		 * @param exclusiveMinimum
		 * @return
		 */
		public Builder withExclusiveMinimum(double exclusiveMinimum) {
			synchronized (this) {
				this.exclusiveMinimumD = exclusiveMinimum;
				this.exclusiveMinimumI = null;
				return this;
			}
		}

		/**
		 * The maximum
		 * 
		 * @param maximum
		 * @return
		 */
		public Builder withMaximum(int maximum) {
			synchronized (this) {
				this.maximumI = maximum;
				this.maximumD = null;
				return this;
			}
		}

		/**
		 * The maximum
		 * 
		 * @param maximum
		 * @return
		 */
		public Builder withMaximum(double maximum) {
			synchronized (this) {
				this.maximumD = maximum;
				this.maximumI = null;
				return this;
			}
		}

		/**
		 * The exclusive maximum
		 * 
		 * @param exclusiveMaximum
		 * @return
		 */
		public Builder withExclusiveMaximum(int exclusiveMaximum) {
			synchronized (this) {
				this.exclusiveMaximumI = exclusiveMaximum;
				this.exclusiveMaximumD = null;
				return this;
			}
		}

		/**
		 * The exclusive maximum
		 * 
		 * @param exclusiveMaximum
		 * @return
		 */
		public Builder withExclusiveMaximum(double exclusiveMaximum) {
			synchronized (this) {
				this.exclusiveMaximumD = exclusiveMaximum;
				this.exclusiveMaximumI = null;
				return this;
			}
		}

		public JsonSchemaNumber build() {
			synchronized (this) {
				return new JsonSchemaNumber(this);
			}
		}
	}
}
