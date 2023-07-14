package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 01.05.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * SMS-�������� � ��
 * � ����� ������� ������������� ������� SMS-�������� (SMS-������ � SMS-������)
 * ������������ � ��� ����� ���:
 *  - SMS-������ �� ������ � �����
 *  - SMS-������ �� ������
 *  - SMS-������ ��� ���������� ������� ��
 */
public class SmsCommand
{
	/**
	 * �������� �������
	 */
	private String name;

	/**
	 * ������ �������
	 */
	private String format;

	/**
	 * ����� �������
	 */
	private String example;

	/**
	 * ��� ���������� (���� �������� ������ ��� ������)
	 */
	private String recipientCode;

	/**
	 * ��� ����������� (���� �������� ������ ��� ������)
	 */
	private String payerCode;

	///////////////////////////////////////////////////////////////////////////

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getFormat()
	{
		return format;
	}

	public void setFormat(String format)
	{
		this.format = format;
	}

	public String getExample()
	{
		return example;
	}

	public void setExample(String example)
	{
		this.example = example;
	}

	public String getRecipientCode()
	{
		return recipientCode;
	}

	public void setRecipientCode(String recipientCode)
	{
		this.recipientCode = recipientCode;
	}

	public String getPayerCode()
	{
		return payerCode;
	}

	public void setPayerCode(String payerCode)
	{
		this.payerCode = payerCode;
	}

	public int hashCode()
	{
		return format != null ? format.hashCode() : 0;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		SmsCommand that = (SmsCommand) o;

		if (StringHelper.isEmpty(format) || StringHelper.isEmpty(that.format))
			return false;

		return format.equalsIgnoreCase(that.format);
	}
}
