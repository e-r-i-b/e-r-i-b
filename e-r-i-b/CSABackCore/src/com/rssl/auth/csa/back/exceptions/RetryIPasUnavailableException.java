package com.rssl.auth.csa.back.exceptions;

/**
 * @ author: Vagin
 * @ created: 17.04.2013
 * @ $Author
 * @ $Revision
 * ����������, ��������������� � ������������� ������� iPas(��������� ������������� ������)
 */
public class RetryIPasUnavailableException extends ServiceUnavailableException
{
	private final String description;

	public RetryIPasUnavailableException(String description, Exception cause)
	{
		super(cause);
		this.description = description;
	}

	public RetryIPasUnavailableException(String description) {
		super(description);
		this.description = description;
	}

	/**
	 * @return �������� ������ ��� ������������
	 */
	public String getDescription()
	{
		return description;
	}
}
