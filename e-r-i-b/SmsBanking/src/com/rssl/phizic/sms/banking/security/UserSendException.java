package com.rssl.phizic.sms.banking.security;

/**
 * @author emakarov
 * @ created 10.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class UserSendException extends Exception
{
	/**
	 * ����������� �� ���������
	 * @param message
	 */
	public UserSendException(String message)
    {
        super(message);
    }

	/**
	 * ����������� ����� 2
	 * @param cause
	 */
    public UserSendException(Throwable cause)
    {
        super(cause);
    }

	/**
	 * ����������� �� ��������� + ����������� ����� 2, � ������ - ��� ��� super
	 *
	 * Constructs a new throwable with the specified detail message and cause.
	 * 
	 * @param message
	 * @param cause
	 */
	public UserSendException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
