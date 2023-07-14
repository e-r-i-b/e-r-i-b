CREATE OR REPLACE AND compile JAVA SOURCE NAMED "utils" AS

import java.util.Random;
import java.lang.String;

public class utils
{
	private static final String LETTERS = "0123456789abcdefjhigklmnopqrstuvwxyzABCDEFJHIGKLMNOPQRSTUVWXYZ";

	private static boolean validPassword(String value, String sequence1)
	{
		String[] strings = split(value, 3);
		for (int i=0; i<strings.length; i++) 
		{
			String subsequence = strings[i];
			String revertedSubsequence = revert(subsequence);
			if (sequence1.indexOf(subsequence) != -1  || sequence1.indexOf(revertedSubsequence)!= -1)
			{
			   return false;
			}
		}	 
		return true;
	}
		
	private static boolean validate(String value)
	{
		boolean result = true;
		if ( !diffrentSymbolsUsage(value)) result = false;
		if ( !validPassword(value, "1234567890-=")) result = false;
		if ( !validPassword(value, "qwertyuiop[]asdfghjkl;'zxcvbnm,./")) result = false;
		if ( !validPassword(value, "qazwsxedcrfvtgbyhnujmik,ol.p;/[']")) result = false;
		if ( !validPassword(value, "741852963")) result = false;
		if ( !validPassword(value, "QWERTYUIOP[]ASDFGHJKL;'ZXCVBNM,./")) result = false;
		if ( !validPassword(value, "QAZWSXEDCRFVTGBYHNUJMIK,OL.P;/[']")) result = false;
		if ( !validPassword(value, "1QAZ2WSX3EDC4RFV5TGB6YHN7UJM8IK,9OL.0P;/-['=]") ) result = false;
		if ( !validPassword(value, "1q2w3e4r5t6y7u8i9o0p-[=]azsxdcfvgbhnjmk,l.;/'qawsedrftgyhujikolp;[']") ) result = false;
		if ( !validPassword(value, "1Q2W3E4R5T6Y7U8I9O0P-[=]AZSXDCFVGBHNJMK,L.;/'QAWSEDRFTGYHUJIKOLP;[']") ) result = false;
		if ( !validPassword(value, "") ) result = false;
		return result;
	}

	private static String revert(String str)
	{
		int length = str.length();
		String result = null;

		for (int i = length - 1; i >= 0; i--)
			result += str.charAt(i);

		return result;
	}

	private static String[] split(String string, int length)
	{
		if (length >= string.length())
		{
			return new String[]{string};
		}
		else
		{
			int size = string.length() - length + 1;
			String[] result = new String[size];

			for (int i = 0; i < size; i++)
				result[i] = string.substring(i, i + length);

			return result;
		}
	}

	private static boolean diffrentSymbolsUsage(String value)
	{
		String noDuplicatesStr = removeDuplicates(value);
		return noDuplicatesStr.length() >= 4;
	}

	private static String removeDuplicates(String str)
	{
		String result = new String();
		int length = str.length();
		for (int i = 0; i < length; i++)
		{
			char ch = str.charAt(i);
			if (result.indexOf(ch) == -1)
				result += ch;
		}
		return result;
	}
	
	public static String rand(int n, String chars)
	{
		Random rand = new Random();
		StringBuffer buffer = new StringBuffer(n);
		int lenght = chars.length();

		if (lenght == 0)
			throw new RuntimeException("0");

		char[] charsArray = new char[lenght];
		chars.getChars(0, lenght, charsArray, 0);

		for (int i = 0; i < n; i++)
		{
			int index = rand.nextInt(lenght);
			buffer.append(charsArray[index]);
		}
		return buffer.toString();
	}
		
	public static String generate()
	{
		for (int i =10; i<100000; i++)
		{
			String randomValue = new String(); 
			randomValue = rand(8, LETTERS);
			try
			{
			   return randomValue;
			}
			catch (Exception e)
			{

			}
		}
		return "E";
	}	
}

