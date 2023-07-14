package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 22.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������
 */
@Immutable
public class Passport
{
	private final String series;

	private final String number;

	private final String issuedBy;

	private final String issuedCode;

	private final Calendar issuedDate;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param series - ����� �������� (never null)
	 * @param number - ����� �������� (never null)
	 * @param issuedBy - ������������ �����������, �������� ������� (never null)
	 * @param issuedCode - ��� �������������, ��������� ������ ��� null ��� ����������� ��������
	 * @param issuedDate - ���� ������ �������� (never null)
	 */
	public Passport(String series, String number, String issuedBy, String issuedCode, Calendar issuedDate)
	{
		if (StringHelper.isEmpty(series))
		    throw new IllegalArgumentException("�� ������� ����� ��������");
		if (StringHelper.isEmpty(number))
			throw new IllegalArgumentException("�� ������ ����� ��������");
		if (StringHelper.isEmpty(issuedBy))
			throw new IllegalArgumentException("�� ������� ������������ �����������, �������� �������");
		if (issuedDate == null)
		    throw new IllegalArgumentException("�� ������� ���� ������ ��������");

		this.series = StringUtils.deleteWhitespace(series);
		this.number = StringUtils.deleteWhitespace(number);
		this.issuedBy = issuedBy;
		this.issuedCode = StringHelper.getNullIfEmpty(issuedCode);
		this.issuedDate = issuedDate;
	}

	/**
	 * @return ����� �������� (never null)
	 */
	public String getSeries()
	{
		return series;
	}

	/**
	 * @return ����� �������� (never null)
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @return ������������ �����������, �������� ������� (never null)
	 */
	public String getIssuedBy()
	{
		return issuedBy;
	}

	/**
	 * @return ��� �������������, ��������� ������ ��� null ��� ����������� ��������
	 */
	public String getIssuedCode()
	{
		return issuedCode;
	}

	/**
	 * @return ���� ������ �������� (never null)
	 */
	public Calendar getIssuedDate()
	{
		return issuedDate;
	}

	@Override
	public int hashCode()
	{
		int result = series.toUpperCase().hashCode();
		result = 31 * result + number.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Passport other = (Passport) o;

		boolean rc = series.equalsIgnoreCase(other.series);
		rc = rc && number.equals(other.number);
		rc = rc && issuedDate.equals(other.issuedDate);
		return rc;
	}
}
