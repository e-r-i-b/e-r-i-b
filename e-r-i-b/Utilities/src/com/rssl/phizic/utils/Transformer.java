package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * @author Erkin
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������������� ������ ���� ������ � ������
 * @param <Output> - ��� ������
 * @param <Input> - ��� ����� 
 */
public interface Transformer<Output, Input>
{
	/**
	 * ����� ��������������
	 * @param input - ����
	 * @return �����
	 */
	Output transform(Input input) throws SystemException;
}
