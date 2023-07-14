package com.rssl.phizic.business.basket.invoiceSubscription;

import com.rssl.phizic.utils.StringHelper;

/**
 * ���������� �� ������
 * @author niculichev
 * @ created 20.06.14
 * @ $Author$
 * @ $Revision$
 */
public class ErrorInfo
{
	private static final char DELEMETER = '|';

	private Type type; // ��� ������
	private String text; // ����� ������

	private ErrorInfo(Type type, String text)
	{
		this.type = type;
		this.text = text;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * ��������� ��������� �������� ������
	 * @param errorInfo ��������� �� ������
	 * @return ��������� �������� ������
	 */
	public static String buildErrorDesc(ErrorInfo errorInfo)
	{
		if(errorInfo == null || (errorInfo.getType() == null && StringHelper.isEmpty(errorInfo.getText())))
			return null;

		return StringHelper.getEmptyIfNull(errorInfo.getType()) + DELEMETER + StringHelper.getEmptyIfNull(errorInfo.getText());
	}

	/**
	 * ��������� ������ ErrorInfo �� ����������� �������� ������
	 * @param errorDesc �������� ������
	 * @return ������ ErrorInfo
	 */
	static ErrorInfo buildErrorInfo(String errorDesc)
	{
		if(StringHelper.isEmpty(errorDesc))
			return null;

		int index = errorDesc.indexOf(DELEMETER);
		if(index <= 0)
			return new ErrorInfo(Type.FORM, errorDesc);

		try
		{
			return new ErrorInfo(Type.valueOf(errorDesc.substring(0, index)), errorDesc.substring(index + 1));
		}
		catch (Exception ignore)
		{
			return new ErrorInfo(Type.FORM, errorDesc);
		}
	}

	/**
	 * ��������� ������ ErrorInfo ��� ������
	 * @param text �����
	 * @return ������ ErrorInfo
	 */
	public static ErrorInfo buildListErrorInfo(String text)
	{
		return new ErrorInfo(Type.LIST, text);
	}

	/**
	 * ��������� ������ ErrorInfo ��� �����
	 * @param text �����
	 * @return ������ ErrorInfo
	 */
	public static ErrorInfo buildFormErrorInfo(String text)
	{
		return new ErrorInfo(Type.FORM, text);
	}

	public static enum Type
	{
		FORM,
		LIST
	}
}
