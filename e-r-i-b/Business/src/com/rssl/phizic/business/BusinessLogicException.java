package com.rssl.phizic.business;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * ���������� ��� ������������� ������ � ������������ ����������.  
 * User: Roshka
 * Date: 08.09.2005
 * Time: 14:10:23
 */
public class BusinessLogicException extends LogicException
{
    public BusinessLogicException(String message)
    {
        super(message);
    }

    public BusinessLogicException(Throwable cause)
    {
        super(cause == null ? null : cause.getMessage(), cause);
    }

	public BusinessLogicException(String message, String errCode)
 {
     super(message, errCode);
 }

	public BusinessLogicException(Throwable cause, String errCode)
	{
	 super(cause, errCode);
	}

    public BusinessLogicException(String message, Throwable cause)
    {
        super(message, cause);
    }

	/**
	 * @param cause ����������
	 * @param message ��������� �� ������
	 * @param errCode ��� ������
	 */
	public BusinessLogicException (Throwable cause, String message, String errCode)
	{
		super(cause, message, errCode);
	}
}
