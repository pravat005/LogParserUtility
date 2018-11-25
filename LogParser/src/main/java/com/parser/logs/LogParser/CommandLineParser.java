package com.parser.logs.LogParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;

public class CommandLineParser implements Parser {

	public void parseCommandLineArgs(String[] args) {
		String startDateArgs = args[0];
		String durationArgs = args[1];
		String threshHoldArgs = args[2];
		Date startDate = null;
		Date startDateWithDuration = null;
		String duration = null;

		if (startDateArgs == null && durationArgs == null && threshHoldArgs == null && startDateArgs == null
				&& durationArgs == null && threshHoldArgs == null) {
			throw new ArgumentException("Please provide all the argument");
		}
		if (!startDateArgs.startsWith("--startDate=") || !durationArgs.startsWith("--duration=")
				|| !threshHoldArgs.startsWith("--threshold=")) {
			throw new ArgumentException("Please provide valid argumant");
		}

		String[] startDateArgsArray = startDateArgs.split("=");
		if (startDateArgsArray.length > 1) {
			String dateTime = startDateArgsArray[1];
			if (dateTime != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dateFormat.setLenient(false);
				try {
					startDate = dateFormat.parse(dateTime);
				} catch (Exception e) {
					throw new ArgumentException("Please provide valid dateTime");
				}
			}

		} else {
			throw new ArgumentException("Please provide date");
		}
		String[] durationArgsArray = durationArgs.split("=");
		if (durationArgsArray.length > 1) {
			duration = durationArgsArray[1];
			if (duration != null) {
				EnumSet<Duration> except = EnumSet.of(Duration.DAILY, Duration.HOURLY);
				try {
					except.contains(Duration.valueOf(duration));
				} catch (Exception e) {
					throw new ArgumentException("Please provide duration as 'hourly' or 'daily' ");
				}
			}
		} else {
			throw new ArgumentException("Please provide duration");
		}

		String[] threshHoldArgsArray = threshHoldArgs.split("=");
		if (threshHoldArgsArray.length > 1) {
			String threshold = threshHoldArgsArray[1];
			if (threshold == null)
				throw new ArgumentException("Please provide threshold");

		
		} else {
			throw new ArgumentException("Please provide threshold");
		}

		// Null and empty check through custom exception

		// check whether the argument starts with --
		// throw custom ex
		// split by equal
		// [0] = date
		// [1] = null, "", vsh
		// [1] validate yyyy-mm-dd hh:mm:ss
		// throw ex
		// do same thing for duration check for "hourly" or "Daily"

		// parse threshhold

		// String[] commandLibneArgs;

		// [0]-Time
		// [1]-Duration
		// [2]-threshhold

	}

}
