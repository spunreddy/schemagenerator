package com.snaplogic.util.parser.csv;

public class Field {
	
	String name;
	String type;
	boolean nullable;
	Metadata metadata;
	
	
	
	public Field() {
		super();
	}
	public Field(String name, String type, boolean nullable, Metadata metadata) {
		super();
		this.name = name;
		this.type = type;
		this.nullable = nullable;
		this.metadata = metadata;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	@Override
	public String toString() {
		return "Field [name=" + name + ", type=" + type + ", nullable=" + nullable + ", metadata=" + metadata + "]";
	}
	
	

}
