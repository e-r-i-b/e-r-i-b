package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.ClientByTemplateCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.ClientIDCacheKeyComposer;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * ��������� ������ � �������� �� ������� �������
 *
 * @author Kidyaev
 * @ created 03.10.2005
 * @ $Author: bogdanov $
 * @ $Revision$
 */

public interface ClientService extends Service
{
    /**
     * ������ �������� �������
     *
     * @param id ID ������������
     * @return ��������
     * @throws GateLogicException
     * @throws GateException
     */
    @Cachable(keyResolver= ClientIDCacheKeyComposer.class, name = "ClientById")
    Client getClientById(String id) throws GateLogicException,GateException;

    /**
     * ���������� ������ ������ � ������������
     * ����� �� ����������, ��� ��� ��� ����� ��������� ��������: ��� ����� �������� ������������ �� clientId,
     * cleintId ���������� cb_code. � �����, ���� ������� ��� ������� � ����� cb_code (�� ���� ��� ���� �������� ������ ���������),
     * �� ������ ������ ����������� ������� �������, ��� ���� ������ ����������� (������ ����� ����� ����� � �����)
     * ���������� ���� ����� �� ����, ��� ��� �� ���������� ���� ��� �� ������.
     * @param client ��������, ���������� ������������ ����� ������
     * @return ������ ��������
     * @throws GateLogicException
     * @throws GateException
     */
	Client fillFullInfo(Client client) throws GateLogicException, GateException;


	/**
	 * ����� �������� �� ���
	 * @param client - ������
	 * @param office - ����, � ������� ������
	 * @param firstResult ���������� ������ firstResult - 1 ��������� �������
	 * @param maxResults ������������ ���������� ������������ ������
	 * @return ������, ���������� ������� �������� �������������
	 * @throws GateLogicException
	 * @throws GateException
	 */
   @Cachable(keyResolver= ClientByTemplateCacheKeyComposer.class, name = "ClientByTemplate")
   List<Client> getClientsByTemplate(Client client, Office office, int firstResult, int maxResults) throws GateLogicException, GateException;

    /**
     * ���������� ������ ������ � ������������ �� ������ ����� �
     *  �����, � ������� �������� �����, �� ������� ������������ ����� � �������
     *
     * @param cardNumber - ����� �����, �� ������� ����������� �������������.
     * @param rbTbBranchId - ������������� �����, � ������� �������� �����, �� ������� ������������ ����� � �������.
     * @return �������� �������
     * @throws GateLogicException, GateException
     */
	Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException;

}
