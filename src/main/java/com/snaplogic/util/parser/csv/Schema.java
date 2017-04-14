package com.snaplogic.util.parser.csv;

import java.util.List;

public class Schema {
	
	private String type;
	private List<Field> fields;
	public Schema() {
		super();
	}
	public Schema(String type, List<Field> fields) {
		super();
		this.type = type;
		this.fields = fields;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	@Override
	public String toString() {
		return "Schema [type=" + type + ", fields=" + fields + "]";
	}

	
}
