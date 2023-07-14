package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.exceptions.SystemException;

import java.util.List;

/**
 * @author Erkin
 * @ created 14.10.2011
 * @ $Author$
 * @ $Revision$
 */
/**
 * ��������������� ������ ������ ���� � ������
 * @param <Output> - ��� �������� ��������� ������
 * @param <Input> - ��� �������� �������� ������
 */
public interface ListTransformer<Output, Input>
{
	/**
	 * ����� ��������������
	 * @param input - ����
	 * @return �����
	 */
	List<Output> transform(List<Input> input) throws SystemException;
}
