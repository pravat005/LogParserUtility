package com.parser.logs.LogParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		// String startDate = "2017-01-01.23:59:59";
		CommandLineParser parser = new CommandLineParser();
		parser.parseCommandLineArgs(
				new String[] { "--startDate=2017-01-01 23:59:59", "--duration=DAILY", "--threshold=100" });
		String duration = "daily";
		Date finalDate = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		try {
			startDate = dateFormat.parse("2017-01-01 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(startDate);
		if (duration.equals(Duration.DAILY)) {
			cal.add(Calendar.HOUR_OF_DAY, 24);
			finalDate = cal.getTime();
		} else {
			cal.add(Calendar.HOUR_OF_DAY, 1);
			finalDate = cal.getTime();
		}

		// hourly initial date 1hourr-- final date
		// daily basis initial date 24-- final date

		// String finalDate = "2017-01-01 23:59:59";
		int threshHold = 500;
		String generatedPatten = createPattern(startDate.toString(), finalDate.toString());
		// String patternAsString =
		// "^2017-01-01(\\s{1}(0[0-9]|1[0-9]|2[0-3])[:](0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])[:](0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])[.](\\d{3})[|](\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b))";
		String filePath = "/Users/pravat/Downloads/access.log";
		Pattern pattern = Pattern.compile(generatedPatten);
		readLineByLineJava8(filePath, pattern, threshHold);

	}

	private static void readLineByLineJava8(String filePath, Pattern pattern, int threshHold)
			throws FileNotFoundException, IOException {
		Map<String, Integer> hitCount = new HashMap<>();
		try (BufferedReader fileBufferReader = new BufferedReader(new FileReader(filePath))) {
			String fileLineContent;

			while ((fileLineContent = fileBufferReader.readLine()) != null) {
				String matchedString = null;
				Matcher matcher = pattern.matcher(fileLineContent);

				if (matcher.find()) {
					matchedString = matcher.group(0);
					String ipAddress = matchedString.substring(matchedString.lastIndexOf("|") + 1,
							matchedString.length());

					if (hitCount.get(ipAddress) == null) {
						hitCount.put(ipAddress, 1);
					} else {
						hitCount.put(ipAddress, hitCount.get(ipAddress) + 1);
					}

				}

			}
		}
		for (String key : hitCount.keySet()) {
			if (hitCount.get(key) >= threshHold) {
				System.out.println(key);
			}
		}
	}

	private static String getRange(String initialRange, String finalRAnge) {
		char[] initialCharArray = initialRange.toCharArray();
		char[] finalCharArray = finalRAnge.toCharArray();

		StringBuffer buffer = new StringBuffer();

		if ((initialCharArray[0] == finalCharArray[0]) && (initialCharArray[1] == finalCharArray[1])) {
			buffer.append(initialCharArray[0]).append("[").append(initialCharArray[1]).append("-")
					.append(finalCharArray[1]).append("]");
		} else if ((initialCharArray[0] == finalCharArray[0]) && (initialCharArray[1] != finalCharArray[1])) {
			buffer.append(initialCharArray[0]).append("[").append(initialCharArray[1]).append("-")
					.append(finalCharArray[1]).append("]");
		} else {
			int i = Character.getNumericValue(initialCharArray[0]);
			int j = Character.getNumericValue(finalCharArray[0]);
			int k = Character.getNumericValue(initialCharArray[1]);
			int l = Character.getNumericValue(finalCharArray[1]);
			boolean isFirst = false;
			while (i < j) {
				if (!isFirst) {
					buffer.append(i).append("[").append(k).append("-").append(9).append("]").append("|");
					isFirst = true;
				} else {
					buffer.append(i).append("[").append(0).append("-").append(9).append("]").append("|");
				}

				i++;

			}
			if (i == j) {
				if (j == 0) {
					buffer.append(i).append("[").append(0).append("-").append(0).append("]");
				} else if (j > 0) {
					buffer.append(i).append("[").append(0).append("-").append(l).append("]");
				}

			}
		}
		return buffer.toString();
	}

	private static String createPattern(String initialDateWithTime, String finalDateWithTime) {
		String[] initalDateTimeArray = initialDateWithTime.split("\\s{1}");
		String[] finalDateTimeWithArray = finalDateWithTime.split("\\s{1}");

		StringBuffer buffer = new StringBuffer();
		// Add logic for date range if applicable now its not required for hourly and
		// daily

		String[] initialTimeArray = initalDateTimeArray[1].split(":");
		String[] finalTimeArray = finalDateTimeWithArray[1].split(":");
		buffer.append("^").append(initalDateTimeArray[0]).append("(").append("\\s{1}");
		buffer.append("(");

		String initialFirstRange = initialTimeArray[0];
		String finalFirstRange = finalTimeArray[0];

		String firstRangeData = getRange(initialFirstRange, finalFirstRange);

		buffer.append(firstRangeData).append(")").append("[:]");

		String initialSecondRange = initialTimeArray[1];
		String finalSecondRange = finalTimeArray[1];
		String secondRangeData = getRange(initialSecondRange, finalSecondRange);

		buffer.append("(").append(secondRangeData).append(")").append("[:]");

		String initialThirdRange = initialTimeArray[2];
		String finalThirdRange = finalTimeArray[2];
		String thirdRangeData = getRange(initialThirdRange, finalThirdRange);

		buffer.append("(").append(thirdRangeData).append(")");

		buffer.append("[.]").append("(").append("\\d{3}").append(")").append("[|]");
		buffer.append("(").append("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b").append(")").append(")");

		return buffer.toString();

	}

}
