package com.rssl.common.forms.parsers;

import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * @author Evgrafov
 * @ created 01.12.2005
 * @ $Author: krenev_a $
 * @ $Revision: 51251 $
 */

public class BigDecimalParser implements FieldValueParser<BigDecimal>
{
	private final int scale;

	/**
	 * ����������� �������, �� ��������� ��������: 2 ����� ����� �������
	 */
	public BigDecimalParser()
	{
		this(2);
	}

	/**
	 * ����������� ������� � �������� ���������
	 * @param scale ��������
	 */
	public BigDecimalParser(int scale)
	{
		this.scale = scale;
	}

	/**
	 * ���� ����� �������� ���������� ����� ����� (��� ������� �����), �� ��������������� ��� �
	 * ����������� ���� (������ ��������� ��������� � ����� � �������������� ������ � ������ �������
	 * ������� getDecimal(), setDecimal() ������ Money)
	 * @return BigDecilmal
	 */
	public BigDecimal parse ( String value ) throws ParseException
	{
		if (StringHelper.isEmpty(value))
			return null;

		BigDecimal temp = NumericUtil.parseBigDecimal(StringUtils.deleteWhitespace(value));

		return temp.scale() == 0 ? temp.setScale(scale) : temp;
	}
}
