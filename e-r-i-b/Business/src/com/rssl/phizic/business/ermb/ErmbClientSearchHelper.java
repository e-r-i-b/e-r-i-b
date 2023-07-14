package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.utils.ExternalSystem;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User: Moshenko
 * Date: 29.05.2013
 * Time: 9:29:42
 * ������ ��� ������ � ��������� � ���, � ��������� ����
 */
public class ErmbClientSearchHelper
{
	/**
	 * @param tmpClient tmp ������ c ������������ ��� ��� ��
	 * @param tb ������� � ������ �������� ���� �������
	 * @return ��������� ������, ���� �����  �� ������ �� null
	 */
	private Client findClient(Client tmpClient, String tb) throws BusinessException, BusinessLogicException
	{
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
		try
		{
			//����� ��������� ������� ���� ��������� ������� ������� �� ����������
			List<? extends ExternalSystem> externalSystems =  externalSystemGateService.findByCodeTB(tb);
			if (CollectionUtils.isNotEmpty(externalSystems))
				ExternalSystemHelper.check(externalSystems);

			// ����������� CEDBO �� ���, ���, � ���� ��������
			return clientService.fillFullInfo(tmpClient);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (OfflineExternalSystemException e)
		{
			List<? extends ExternalSystem> externalSystems = e.getExternalSystems();
			try
			{
				if (!ExternalSystemHelper.isActive(externalSystems))
					 throw new BusinessLogicException("������� �������  ��� �� " + tb + " ����������");
			}
			catch (GateException ex)
			{
				throw new BusinessException(ex);
			}
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		return null;
	}

	/**
	 * ����� ������� �� ������� ������� �� ��� ��� ��
	 * @param surName   �
	 * @param firstName �
	 * @param patrName  �
	 * @param birthDay  ��
	 * @param docSeries  ����� ���.
	 * @param docNumber  ����� ���.
	 * @param docType    ��� ���.
	 * @param tb ��
	 * @return ��������� ������ ���� Null
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Client findClient(String firstName,
	                         String surName,
	                         String patrName,
	                         Calendar birthDay,
	                         String docSeries,
	                         String docNumber,
	                         ClientDocumentType docType,
	                         String tb) throws BusinessException, BusinessLogicException
	{
		//������� ������� ��� ���������� CEDBO
		ClientImpl tmpClient = new ClientImpl();
		tmpClient.setFirstName(firstName);
		tmpClient.setPatrName(patrName);
		tmpClient.setSurName(surName);
		tmpClient.setBirthDay(birthDay);
		ClientDocumentImpl doc = new ClientDocumentImpl();
		if (ClientDocumentType.PASSPORT_WAY == docType)
		{
			//��� CEDBO ����� �������� way ������ ������������ � ���� "�����"
			//(�� �� ����� �� ������ ���� ������� way ���������� � ���� "�����")
			doc.setDocSeries(docNumber);
		}
		else
		{
			doc.setDocSeries(docSeries);
			doc.setDocNumber(docNumber);
		}
		doc.setDocumentType(docType);
		List<ClientDocument> documents = new ArrayList<ClientDocument>(1);
		documents.add(doc);
		tmpClient.setDocuments(documents);
		//��������� �� �������� ��� CB_CODE, �.� 2 ������� � ������ ����� ��
		//��� ����� CEDBO  �� ��������
		tmpClient.setId(tb + "000000");
		Client client = findClient(tmpClient, tb);
		if (client == null || client.getId() == null)
			return null;
		return client;
	}
}
