package com.parser.logs.LogParser;

public class DynamicRegex {
	public String generateRegex(String initialRange, String finalRAnge) {
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
					buffer.append(i).append("[").append(k).append("-").append(9).append("]");
					isFirst = true;
				} else {
					buffer.append(i).append("[").append(0).append("-").append(9).append("]");
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
}
