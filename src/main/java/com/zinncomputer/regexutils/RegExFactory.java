package com.zinncomputer.regexutils;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;

public class RegExFactory {

	public RegExFactory() {
		
	}

	public static void main(String args[]) {

		System.out.println("Begin random tests.");
		Random r = new Random();
		for (int i = 1; i < 16; i++) {
			System.out.println("Test " + i);
			String s = "";
			for (int j = 0; j < r.nextInt(30) + 2; j++) {
				char c = (char) (r.nextInt(27) + 'a');
				if (c > 'z') {
					s += ' ';
				} else {
					s += c;
				}
			}
			System.out.println("Original: " + s);
			System.out.println(System.currentTimeMillis());
			System.out.println(generateRegEx(s));
			System.out.println(System.currentTimeMillis());
		}
		System.out.println("Testing multiple words/non-alpha characters.");
		System.out
				.println(generateRegEx("testing multiple w0rds w!th characterZ"));
		//
		System.out.println("Testing a word with special considerations.");
		HashMap<Character, Character[]> specC = new HashMap<Character, Character[]>();
		specC.put('a', new Character[] { '4' });
		specC.put('i', new Character[] { '1', '!', ':', ';' });
		System.out.println(System.currentTimeMillis());
		System.out.println(generateRegEx("inland", specC));
		System.out.println(System.currentTimeMillis());
		System.out
				.println("Testing a word with special considerations and max lengths.");
		HashMap<Character, Character[]> specC1 = new HashMap<Character, Character[]>();
		specC1.put('a', new Character[] { '4', '@'});
		specC1.put('i', new Character[] { '1', '!', ':', ';' });
		specC1.put('e', new Character[] {'3', '#', '}'});
		System.out.println(System.currentTimeMillis());
		System.out.println(generateRegEx("inland", specC1, 2));
		System.out.println(System.currentTimeMillis());
		/*
		 * Now for the actual matching tests.
		 */
		String randS = RandomStringUtils.randomAlphanumeric(5000);
		System.out.println(randS);
		System.out.println(System.currentTimeMillis());
		Pattern p = Pattern.compile(generateRegEx("mey", specC1, 3));
		System.out.println(System.currentTimeMillis());
		System.out.println(generateRegEx("mey", specC1, 3));
		System.out.println(System.currentTimeMillis());
		Matcher m = p.matcher(randS);
		System.out.println(System.currentTimeMillis());
		while (m.find()) {
			System.out.println("Hit! " + System.currentTimeMillis());
		}
		System.out.println("Done! " + System.currentTimeMillis());

	}

	/**
	 * 
	 * @param word
	 *            the base word that will be converted to RegEx. Typically
	 *            lowercase.
	 * @return the generated RegEx string.
	 */

	private static String generateRegEx(String word) {
		String regex = "";
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (Character.isLetter(c)) {
				regex += "[" + Character.toUpperCase(c)
						+ Character.toLowerCase(c) + "]";
			} else if (Character.isSpaceChar(c)) {
				regex += "[\\s]";
			} else {
				regex += "[" + c + "]";
			}
			if (i != word.length() - 1) {
				regex += ".{0,3}";
			}
		}
		return regex;
	}

	/**
	 * 
	 * Generates a RegEx expression from the supplied word.
	 * 
	 * @param word
	 *            the base word that will be converted to RegEx. Typically
	 *            lowercase.
	 * @param specialConsiderations
	 *            any special considerations the generator should consider.
	 * @return the generated RegEx string.
	 */

	private static String generateRegEx(String word,
			HashMap<Character, Character[]> specialConsiderations) {
		String regex = "";
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (specialConsiderations.containsKey(c)) {
				String specC = "[" + Character.toLowerCase(c)
						+ Character.toUpperCase(c);
				for (char sc : specialConsiderations.get(c)) {
					if (Character.isSpaceChar(sc)) {
						specC += "\\s";
					} else {
						if (Character.isLetter(sc)) {
							specC += Character.toLowerCase(sc)
									+ Character.toUpperCase(sc);
						} else {
							specC += sc;
						}
					}
				}
				specC += "]";
				regex += specC;
			} else {
				regex += "[" + Character.toUpperCase(c)
						+ Character.toLowerCase(c) + "]";
			}
			if (i != word.length() - 1) {
				regex += ".{0,3}";
			}
		}
		return regex;
	}

	/**
	 * 
	 * Generates a RegEx expression from the supplied word, taking into account
	 * the special considerations.
	 * 
	 * @param word
	 *            the base word that will be converted to RegEx. Typically
	 *            lowercase.
	 * @param specialConsiderations
	 *            any special considerations the generator should consider.
	 * @param charactersBetween
	 *            the amount of characters between each letter the generator
	 *            will allow (.{0,x}).
	 * @return the generated RegEx string.
	 */

	private static String generateRegEx(String word,
			HashMap<Character, Character[]> specialConsiderations,
			int charactersBetween) {
		String regex = "";
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (specialConsiderations.containsKey(c)) {
				String specC = "[" + Character.toLowerCase(c)
						+ Character.toUpperCase(c);
				for (char sc : specialConsiderations.get(c)) {
					if (Character.isSpaceChar(sc)) {
						specC += "\\s";
					} else {
						if (Character.isLetter(sc)) {
							specC += Character.toLowerCase(sc)
									+ Character.toUpperCase(sc);
						} else {
							specC += sc;
						}
					}
				}
				specC += "]";
				regex += specC;
			} else {
				regex += "[" + Character.toUpperCase(c)
						+ Character.toLowerCase(c) + "]";
			}
			if (i != word.length() - 1) {
				regex += ".{0," + charactersBetween + "}";
			}
		}
		return regex;
	}

}
