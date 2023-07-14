package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ������ ��� ������� ������,
 * ���������� ������ �������� ��������
 * @author Erkin
 * @ created 21.04.2010
 * @ $Author$
 * @ $Revision$
 */
class SamplesListParser implements Parser<SampleInfo>
{
	/**
	 * ���������� ��������� ��� ������� ������,
	 * ���������� ������ �������� ��������
	 * ������ �� ������� ����� ��������� ����������
	 */
	private static final Pattern SAMPLE_FULL_PATTERN = Pattern.compile(
			"CN=(\\d{15,16}|\\d{18}|\\d{19});" +
			"RST=([a-zA-Z0-9]+);" + // ������ - ����� ���������� ���� � ����
			"TF=(F|S);" +
			"PHN=(7\\d{1,10});" +
			"CODE=([^;]+);" +
			"DESTLIST=([^;]*),;" +
			"MOBILE_OPERATOR=([^;]*);"
	);

	/**
	 * ���������� ��������� ��� ������� ������,
	 * ���������� ������ ������ ����� ��������� ����������
	 */
	private static final Pattern SAMPLE_SHORT_PATTERN = Pattern.compile(
			"MOBILE_OPERATOR=([^;]*);"
	);

	/**
	 * ���������� ��������� ��� ������� ������,
	 * ���������� ������ '���_����������:������_���������������_�����������'
	 */
	private static final Pattern DESTLIST_ITEM = Pattern.compile(
			"([a-zA-Z0-9 \\-]+):(.*)"
	);

	/**
	 * ���������� ��������� ��� ������� ������,
	 * ���������� ���_����������
	 */
	public static final Pattern RECIPIENT_CODE_PATTERN = Pattern.compile(
			"[a-zA-Z0-9 \\-]+"
	);

	/**
	 * ���������� ��������� ��� ������� ������,
	 * ���������� ������������� �����������
	 */
	public static final Pattern PAYER_ID_PATTERN = Pattern.compile(
			"[a-zA-Z0-9]+");

	///////////////////////////////////////////////////////////////////////////

	public SampleInfo parse(String string) throws ParseException
	{
		if (StringHelper.isEmpty(string))
			return null;

		// A. ������ ������� ���������
		Matcher sampleMatcher = SAMPLE_FULL_PATTERN.matcher(string);
		if (sampleMatcher.matches()) {
			SampleInfo sample = new SampleInfo();
			sample.setCardNumber(sampleMatcher.group(1));
			sample.setCardStatus(sampleMatcher.group(2));
			sample.setTariff(sampleMatcher.group(3));
			sample.setPhoneNumber(sampleMatcher.group(4));
			sample.setMobileOperator(sampleMatcher.group(5));
			sample.setDestList(parseDestList(sampleMatcher.group(6)));
			sample.setOperatorCodenames(parseOperatorCodenames(sampleMatcher.group(7)));
			return sample;
		}

		// B. ������ ������ ������ ������� ������������
		// ���� ��������� �� �� ���������� ��������� �����
		Matcher operatorsMatcher = SAMPLE_SHORT_PATTERN.matcher(string);
		if (operatorsMatcher.matches()) {
			SampleInfo sample = new SampleInfo();
			sample.setOperatorCodenames(parseOperatorCodenames(operatorsMatcher.group(1)));
			return sample;
		}

		throw new ParseException("�������� ������ " +
				"�� ������� �������� �������� " +
				"�� ������������� �������������� �������: " + string, 0);
	}

	private Map<String, Set<String>> parseDestList(String string) throws ParseException
	{
		if (StringHelper.isEmpty(string))
			return Collections.emptyMap();

		String[] recipients = string.split(",");
		Map<String, Set<String>> result =
				new LinkedHashMap<String, Set<String>>(recipients.length);

		for (String recipient : recipients) {
			if (StringHelper.isEmpty(recipient))
				continue;

			Matcher matcher = DESTLIST_ITEM.matcher(recipient);
			if (!matcher.matches())
				throw new ParseException("�������� ������ " +
						"�� ������� ����������� " +
						"�� ������������� �������������� ������� " +
						"(���������� " + recipient + "�� ������������� ������� " + DESTLIST_ITEM.pattern() + " )", 0);

			String recipientCode = matcher.group(1);
			Set<String> payers = parsePayers(matcher.group(2));
			result.put(recipientCode, payers);
		}

		return result;
	}

	private Set<String> parsePayers(String string) throws ParseException
	{
		if (StringHelper.isEmpty(string))
			return Collections.emptySet();

		String[] payers = string.split(":");
		Set<String> result = new LinkedHashSet<String>(payers.length);

		for (String payer : payers) {
			if (StringHelper.isEmpty(payer))
				continue;

			// TODO: �������� ������ ��������
			Matcher matcher = PAYER_ID_PATTERN.matcher(payer);
			if (!matcher.matches())
				throw new ParseException("�������� ������ " +
						"�� ������� ��������������� ����������� " +
						"�� ������������� �������������� �������: " + string, 0);

			result.add(payer);
		}

		return result;
	}

	private List<String> parseOperatorCodenames(String string) throws ParseException
	{
		if (StringHelper.isEmpty(string))
			return Collections.emptyList();

		String[] operators = string.split(",");
		List<String> result = new ArrayList<String>(operators.length);

		for (String operator : operators) {
			if (StringHelper.isEmpty(operator))
				continue;
			result.add(operator);
		}

		return result;
	}
}
