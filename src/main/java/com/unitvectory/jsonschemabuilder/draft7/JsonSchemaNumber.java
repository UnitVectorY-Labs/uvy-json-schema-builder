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

public class JsonSchemaNumber extends JsonSchemaBuilder {

	private final JsonSchemaType type = JsonSchemaType.NUMBER;

	private final boolean required;

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

	JSONObject schema() {
		JSONObject json = new JSONObject();
		json.put("type", type.getType());

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

	public static class Builder {

		private boolean required;

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

		public static Builder create() {
			return new Builder();
		}

		public Builder withRequired() {
			this.required = true;
			return this;
		}

		public Builder withMultipleOf(int multipleOf) {
			this.multipleOfI = multipleOf;
			this.multipleOfD = null;
			return this;
		}

		public Builder withMultipleOf(double multipleOf) {
			this.multipleOfD = multipleOf;
			this.multipleOfI = null;
			return this;
		}

		public Builder withMinimum(int minimum) {
			this.minimumI = minimum;
			this.minimumD = null;
			return this;
		}

		public Builder withMinimum(double minimum) {
			this.minimumD = minimum;
			this.minimumI = null;
			return this;
		}

		public Builder withExclusiveMinimum(int exclusiveMinimum) {
			this.exclusiveMinimumI = exclusiveMinimum;
			this.exclusiveMinimumD = null;
			return this;
		}

		public Builder withExclusiveMinimum(double exclusiveMinimum) {
			this.exclusiveMinimumD = exclusiveMinimum;
			this.exclusiveMinimumI = null;
			return this;
		}

		public Builder withMaximum(int maximum) {
			this.maximumI = maximum;
			this.maximumD = null;
			return this;
		}

		public Builder withMaximum(double maximum) {
			this.maximumD = maximum;
			this.maximumI = null;
			return this;
		}

		public Builder withExclusiveMaximum(int exclusiveMaximum) {
			this.exclusiveMaximumI = exclusiveMaximum;
			this.exclusiveMaximumD = null;
			return this;
		}

		public Builder withExclusiveMaximum(double exclusiveMaximum) {
			this.exclusiveMaximumD = exclusiveMaximum;
			this.exclusiveMaximumI = null;
			return this;
		}

		public JsonSchemaNumber build() {
			return new JsonSchemaNumber(this);
		}
	}
}
