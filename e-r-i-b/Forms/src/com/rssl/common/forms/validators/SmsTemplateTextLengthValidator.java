package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

import java.util.regex.Pattern;

/**
 *
 * ��������� ��� �������� ���������� ����������� ������ ���-�������
 *
 * @author  Balovtsev
 * @version 28.03.13 15:49
 */
public class SmsTemplateTextLengthValidator extends FieldValidatorBase
{
	/*
	 * ���� ����� ���������, �������!
	 *
	 * ������ ��������� ������ ���� �� �������� ����������� ������ ������ �������� �� ����������� � ������ ���.
	 * ��������, � ������ "<@compress single_line=true>�������� ���@��. ������ ��� ������������� ��������� ������ � ${password}</@compress>"
	 * ����� ������� ��������� ��������� "<@compress single_line=true>", "${password}", "</@compress>".
	 *
	 * ������ ��������� ��� �������� ���������� ��������, ������������ ��� ���� ����� ���������
	 * ��������� ���� ���������.
	 *
	 */
	private static final String PATTERN_VARIABLE       = "(</?)([@|#])([\\w \\.,!=\"'&?|()>/]+)|(\\$\\{[\\w\\.\\?\"'() :]+})|(\\{\\d})|(%(?=\\w)\\w)|(([ ]+)((?= )|(?=\\t))|([\\t]+))|(\\r?\\n?)";
	private static final String PATTERN_TRANSLIT       = "(<# *\\w+ +translit *[ =\"'\\w]*>)";
	private static final String PATTERN_ENGLISH_LETTER = "[A-z]*";

	private static final int    MAX_FIELD_LENGTH_WITHOUT_PARAMETERS = 499;

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (StringHelper.isEmpty(value))
		{
			return true;
		}

		boolean hasTranslit = Pattern.compile(PATTERN_TRANSLIT).matcher(value).find();
		String  replaced    = Pattern.compile(PATTERN_VARIABLE).matcher(value).replaceAll("");

		if (hasTranslit)
		{
			replaced = replaced.replaceAll(PATTERN_ENGLISH_LETTER, "");
		}

		if (replaced.trim().length() > MAX_FIELD_LENGTH_WITHOUT_PARAMETERS)
		{
			setMessage("����� ��������� ��� ����� �������� ���������� �������� �� ������ ���������� ����� 499 ��������");
			return false;
		}

		return true;
	}
}
