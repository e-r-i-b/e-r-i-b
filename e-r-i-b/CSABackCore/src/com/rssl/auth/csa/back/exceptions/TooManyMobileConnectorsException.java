package com.rssl.auth.csa.back.exceptions;

/**
 * @author krenev
 * @ created 20.09.2012
 * @ $Author$
 * @ $Revision$
 * ����������, ��������������� � ���������� ����������� ���������� ��������� ����������� 
 */
public class TooManyMobileConnectorsException extends RestrictionException
{
	public TooManyMobileConnectorsException(Long profileId)
	{
		super("��� ������� " + profileId + " ��������� ���������� ���������� ������������������ ��������� ���������.");
	}
}
