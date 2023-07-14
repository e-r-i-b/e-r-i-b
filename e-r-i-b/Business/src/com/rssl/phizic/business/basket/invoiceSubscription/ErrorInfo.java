package com.rssl.phizic.business.basket.invoiceSubscription;

import com.rssl.phizic.utils.StringHelper;

/**
 * Информация об ошибке
 * @author niculichev
 * @ created 20.06.14
 * @ $Author$
 * @ $Revision$
 */
public class ErrorInfo
{
	private static final char DELEMETER = '|';

	private Type type; // тип ошибки
	private String text; // текст ошибки

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
	 * Построить внутренее описание ошибки
	 * @param errorInfo инфомация об ошибке
	 * @return внутренее описание ошибки
	 */
	public static String buildErrorDesc(ErrorInfo errorInfo)
	{
		if(errorInfo == null || (errorInfo.getType() == null && StringHelper.isEmpty(errorInfo.getText())))
			return null;

		return StringHelper.getEmptyIfNull(errorInfo.getType()) + DELEMETER + StringHelper.getEmptyIfNull(errorInfo.getText());
	}

	/**
	 * Построить объект ErrorInfo по внутреннему описанию ошибки
	 * @param errorDesc описание ошибки
	 * @return объект ErrorInfo
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
	 * Построить объект ErrorInfo для списка
	 * @param text текст
	 * @return объект ErrorInfo
	 */
	public static ErrorInfo buildListErrorInfo(String text)
	{
		return new ErrorInfo(Type.LIST, text);
	}

	/**
	 * Построить объект ErrorInfo для формы
	 * @param text текст
	 * @return объект ErrorInfo
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
