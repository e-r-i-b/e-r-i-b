package com.rssl.phizic.business.dictionaries.kbk;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author akrenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class DublicateKBKException extends BusinessLogicException
{
	/**
	 *  ����������, ��������� ��� ���������� ��� � ������ ��������� ������� ������������ code
	 */
	public DublicateKBKException()
	{
		super("��� � ����� ����� ��� ����������");
	}
}
