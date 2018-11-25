package com.parser.logs.LogParser;

public enum Duration {

	DAILY("daily"), HOURLY("hourly");
	
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	private String duration;
	Duration(String input) {
		this.duration = input;
	}
	
	
	
}
