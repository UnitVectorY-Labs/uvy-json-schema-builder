package com.unitvectory.jsonschemabuilder.draft7;

public abstract class AbstractJsonSchemaBuilder<T extends AbstractJsonSchemaBuilder<?, ?>, C extends AbstractJsonSchema> {

	public abstract T withRequired();

	public abstract C build();

}
