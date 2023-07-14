package com.rssl.phizic.locale.services;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.entities.ERIBStaticMessage;

import java.util.List;

/**
 * ������ ��� ������ � �������������� �����������
 * @author koptyaev
 * @ created 11.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EribStaticMessageService extends MultiInstanceEribStaticMessageService
{
	/**
	 * �������� ������
	 * @param message ���������
	 * @return ����������� ���������
	 * @throws SystemException
	 */
	public ERIBStaticMessage add(ERIBStaticMessage message) throws SystemException
	{
		return super.add(message, null);
	}

	/**
	 * �������� ������
	 * @param messages ���������
	 * @return ����������� ���������
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> add(List<ERIBStaticMessage> messages) throws SystemException
	{
		return super.add(messages, null);
	}

	/**
	 * �������� ������
	 * @param message ���������
	 * @return ���������� ���������
	 * @throws SystemException
	 */
	public ERIBStaticMessage update(ERIBStaticMessage message) throws SystemException
	{
		return super.update(message, null);
	}

	/**
	 * �������� ������
	 * @param message ���������
	 * @return ���������� ���������
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> update(List<ERIBStaticMessage> message) throws SystemException
	{
		return super.update(message, null);
	}

	/**
	 * ������� ���������
	 * @param message ���������
	 * @throws SystemException
	 */
	public void delete(ERIBStaticMessage message) throws SystemException
	{
		super.delete(message, null);
	}

	/**
	 * ������� ���������
	 * @param message ���������
	 * @throws SystemException
	 */
	public void delete(List<ERIBStaticMessage> message) throws SystemException
	{
		super.delete(message, null);
	}

	/**
	 * ���������� ��� ������
	 * @param localeId ������������� ������
	 * @return ���������
	 * @throws SystemException
	 */
	public List<ERIBStaticMessage> getAll(String localeId) throws SystemException
	{
		return super.getAll(localeId, null);
	}
}
