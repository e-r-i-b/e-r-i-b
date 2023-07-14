package com.rssl.phizic.gate.cache.proxy;

import com.rssl.phizic.common.types.transmiters.Pair;

import java.io.Serializable;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ��� �������� ������� �� �������������� ���������� �������� � ����� ���� � �������
 */
public interface CacheKeyComposer
{
	/**
	 * ������������ ����
	 * @param args ��������� ������ ������
	 * @param params ��������� ��� ������ ���������
	 * @return ����
	 */
	Serializable getKey(Object[] args, String params);

	/**
	 * ������������ ����� ��� ������� ������������ groupResult � ����������� �������
	 * @param args ��������� ������ ������
	 * @param params ��������� ��� ������ ���������
	 * @return ������ ��� - ���� - �������� ������
	 */
	List<Pair<Serializable, Object>> getKeys(Object[] args, String params);

	/**
	 * ������� �� ���������� ��������� ��������, �� �� ������� ��������� ��� ����
	 * @param args ��������� ������ ������
	 * @param params ��������� ��� ������ ���������
	 * @return ��������� ��� ������ ������, ��� null ���� ����� �� ���������
	 */
	Object[] substractReadyArgs(Object[] args, String params, List<Object> readyKeys);

	/**
	 * ��������� ���� ��� ������� ����.
	 * @param result ��������� ���������� ������
	 * @param params �������������� ���������
	 * @return ����, ��� null ���� �������������� �� ����� ���� ���������.
	 */
	Serializable getClearCallbackKey(Object result, Object[] params);

	/**
	 * �������������� �� ������������ ����� �� ����������
	 * @return true - ��������������.
	 */
	boolean isKeyFromResultSupported();
}
