package com.rssl.phizic.business.exception.locale;

/**
 * @author akrenev
 * @ created 31.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������������� ����������� ���������
 */

public class LocalizingExceptionMapping
{
	private Long id;
	private String messageKey;
	private String errorKey;
	private String pattern;
	private String formatter;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���� ���������
	 */
	public String getMessageKey()
	{
		return messageKey;
	}

	/**
	 * ������ ���� ���������
	 * @param messageKey ���� ���������
	 */
	public void setMessageKey(String messageKey)
	{
		this.messageKey = messageKey;
	}

	/**
	 * @return ��� ������ ���������
	 */
	public String getErrorKey()
	{
		return errorKey;
	}

	/**
	 * ������ ��� ������ ���������
	 * @param errorKey ��� ������ ���������
	 */
	public void setErrorKey(String errorKey)
	{
		this.errorKey = errorKey;
	}

	/**
	 * @return ������ ���������
	 */
	public String getPattern()
	{
		return pattern;
	}

	/**
	 * ������ ������ ���������
	 * @param pattern ������ ���������
	 */
	public void setPattern(String pattern)
	{
		this.pattern = pattern;
	}

	/**
	 * @return ������ ���������
	 */
	public String getFormatter()
	{
		return formatter;
	}

	/**
	 * ������ ������ ���������
	 * @param formatter ������ ���������
	 */
	public void setFormatter(String formatter)
	{
		this.formatter = formatter;
	}
}
