package com.rssl.phizic.business.clients.list;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.list.parsers.ClientForMigrationParser;
import com.rssl.phizic.business.clients.list.parsers.ClientListParser;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��������� ������ �������� �� ��� ���
 */

public class ClientInformationService
{
	/**
	 * ����� ���� �� ��������
	 * @param fio ��� �������
	 * @param document ��� �������
	 * @param birthday �� �������
	 * @param login ����� �������
	 * @param creationType ��� ��������
	 * @param agreementNumber ����� ��������
	 * @param phoneNumber ����� ��������
	 * @param tbList ������ �� � ������� ����� ������
	 * @param maxResults ������������ ���������� ��������
	 * @param firstResult �������� �������
	 * @return �������
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public List<ClientInformation> getClientsInformation(String fio, String document, Calendar birthday, String login,
	                                                       CreationType creationType, String agreementNumber, String phoneNumber,
	                                                       List<String> tbList, int maxResults, int firstResult) throws BusinessLogicException, BusinessException
	{
		try
		{
			if (CollectionUtils.isEmpty(tbList))
				return Collections.emptyList();

			Document response = CSABackRequestHelper.getClientsInformationRq(fio, document, birthday, login, creationType, agreementNumber, phoneNumber, tbList, maxResults, firstResult);
			ClientListParser parser = new ClientListParser(response);
			parser.parse();
			return parser.getClients();
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ���� �� ��������
	 * @param fio ��� �������
	 * @param document ��� �������
	 * @param birthday �� �������
	 * @param creationType ��� ��������
	 * @param agreementNumber ����� ��������
	 * @param tbList ������ �� � ������� ����� ������
	 * @param nodeId ������������� ��������� ����� �������
	 * @param maxResults ������������ ���������� ��������
	 * @param firstResult �������� �������
	 * @return �������
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public List<ClientForMigration> getTemporaryNodeClientsInformation(String fio, String document, Calendar birthday, CreationType creationType, String agreementNumber,
	                                                       List<String> tbList, Long nodeId, int maxResults, int firstResult) throws BusinessLogicException, BusinessException
	{
		try
		{
			if (CollectionUtils.isEmpty(tbList))
				return Collections.emptyList();

			Document response = CSABackRequestHelper.getTemporaryNodeClientsInformationRq(fio, document, birthday, creationType, agreementNumber, tbList, nodeId, maxResults, firstResult);
			ClientForMigrationParser parser = new ClientForMigrationParser(response);
			parser.parse();
			return parser.getClients();
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� �������� � ��������� �����
	 * @param nodeId ������������� ��������� ����� �������
	 * @return ����������
	 */
	public Long getTemporaryNodeClientsCount(Long nodeId) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document response = CSABackRequestHelper.getTemporaryNodeClientsCountRq(nodeId);
			String stringValue =  XmlHelper.getSimpleElementValue(response.getDocumentElement(), CSAResponseConstants.COUNT_TAG);
			if (StringHelper.isEmpty(stringValue))
				return null;
			return Long.valueOf(stringValue);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ��������� �������
	 * @param userInfo ���������� � �������
	 * @return ���������
	 */
	public ClientNodeState getClientNodeState(UserInfo userInfo) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document response = CSABackRequestHelper.getClientNodeStateRq(userInfo);
			String stringValue =  XmlHelper.getSimpleElementValue(response.getDocumentElement(), CSAResponseConstants.PROFILE_NODE_STATE_TAG);
			if (StringHelper.isEmpty(stringValue))
				return ClientNodeState.MAIN;
			return ClientNodeState.valueOf(stringValue);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
