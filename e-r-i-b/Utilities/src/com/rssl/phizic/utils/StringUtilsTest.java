package com.rssl.phizic.utils;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Evgrafov
 * @ created 14.02.2007
 * @ $Author: barinov $
 * @ $Revision: 39907 $
 */

@SuppressWarnings({"JavaDoc"})
public class StringUtilsTest extends TestCase
{
	private static final String HEX = "0F037584C99E7FD4F4F8C59550F8F507";

	@SuppressWarnings({"MagicNumber"})
	public void testHex()
	{
		byte[] dataBytes = StringUtils.fromHexString(HEX);
		assertEquals(HEX, StringUtils.toHexString(dataBytes));

		byte[] dataBytes1 = StringUtils.fromHexArray(HEX.toCharArray());
		char[] hex1 = StringUtils.toHexArray(dataBytes1);
		assertEquals(HEX, String.valueOf(hex1));
	}

	public void testSplitToLines()
	{
		final int lineWidth = 140;
		System.out.println(" *** Разбивка текста на строки по " + lineWidth);

		String text = "Александр Дюма родился в 1802 году в семье генерала Тома-Александра Дюма  " +
				"и Марии-Луизы Лабурэ, проживавших в небольшом городе Вилле-Котре. " +
				"Своё детство, отрочество и юность Дюма провёл в родном городе, " +
				"а затем направился в Париж. Несмотря на бедность, семья всё же " +
				"имела хорошую репутацию и аристократические связи, которые помогли " +
				"двадцатилетнему Александру получить должность в Пале-Рояле (Париж) в " +
				"канцелярии при герцоге Орлеанском." +
				"В июле 1830 года во Франции произошла Июльская революция, свергнувшая " +
				"Карла X и утвердившая буржуазное королевство. На престол вступил герцог " +
				"Орлеанский под именем Луи-Филиппа. Александр Дюма был среди инсургентов, " +
				"штурмовавших королевский дворец Тюильри. Впоследствии в своих «Мемуарах» " +
				"он писал:" +
				"\n" +
				"Я видел тех, которые совершали революцию 1830 года, " +
				"и они видели меня в своих рядах… " +
				"Люди, совершившие революцию 1830 года, " +
				"олицетворяли собой пылкую юность героического пролетариата; " +
				"они не только разжигали пожар, но и тушили пламя своей кровью.";

		Pattern spacePattern = Pattern.compile("\\s");

		int i = 0;
		String[] lines = StringHelper.splitToLines(text, lineWidth, true);
		for (String line : lines) {
			System.out.println(String.format("[%02d] %s", i++ + 1, line));
			Matcher spaceMatcher = spacePattern.matcher(line);
			Assert.assertTrue(line.length()<lineWidth || !spaceMatcher.find());
		}
	}
}