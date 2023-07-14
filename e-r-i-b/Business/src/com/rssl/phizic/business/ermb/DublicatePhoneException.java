package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * User: moshenko
 * Date: 10.10.12
 * Time: 15:43
 * ������ ������������� ��� ������� �������� ��� ������������ �������
 */
public class DublicatePhoneException extends BusinessLogicException
{
	private String duplicateNumber;

	public DublicatePhoneException(Throwable cause,String duplicateNumber)
	{
		super("������� � ����� ������� ��� ���������� � �������",cause);
		this.duplicateNumber = duplicateNumber;
	}

	public DublicatePhoneException(String message, Throwable cause,String duplicateNumber)
    {
        super(message, cause);
	    this.duplicateNumber = duplicateNumber;
    }

	public String getDuplicateNumber()
	{
		return duplicateNumber;
	}
}
