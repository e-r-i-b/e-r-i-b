package com.rssl.phizic.gate.owners.client;

import com.rssl.phizgate.common.services.types.ClientDocumentImpl;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.clients.GUIDComparator;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author khudyakov
 * @ created 23.04.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientHelper
{
	/**
	 * ����� ��� ������� �� ���� ���������
	 * @param client ������
	 * @param documentType ��� ���������
	 * @return ��������
	 * @throws Exception
	 */
	public static ClientDocument getClientDocumentByType(Client client, ClientDocumentType documentType) throws Exception
	{
		return getClientDocumentByType(client.getDocuments(), documentType);
	}

	/**
	 * ����� ��� ������� �� ���� ���������
	 * @param documents ������ ���������� �������
	 * @param documentType ��� ���������
	 * @return ��������
	 * @throws Exception
	 */
	public static ClientDocument getClientDocumentByType(List<? extends ClientDocument> documents, ClientDocumentType documentType) throws Exception
	{
		if (CollectionUtils.isEmpty(documents))
		{
			throw new Exception("� ������ ����������� ���");
		}

		for (ClientDocument document : documents)
		{
			if (documentType == document.getDocumentType())
			{
				return document;
			}
		}
		return null;
	}

	/**
	 * ����� Way ��� �������.
	 *
	 * �����������: ������� way, ����� ������������� ��� ������ ��������� ������� �� ������� �������
	 *
	 * @param client ������
	 * @return ��������
	 * @throws Exception
	 */
	public static ClientDocument getClientWayDocument(Client client) throws Exception
	{
		return getClientWayDocument(client.getDocuments());
	}

	/**
	 * ����� Way ��� �������.
	 *
	 * �����������: ������� way, ����� ������������� ��� ������ ��������� ������� �� ������� �������
	 *
	 * @param documents ��������� �������
	 * @return ��������
	 * @throws Exception
	 */
	public static ClientDocument getClientWayDocument(List<? extends ClientDocument> documents) throws Exception
	{
		ClientDocument document = getClientDocumentByType(documents, ClientDocumentType.PASSPORT_WAY);
		if (document == null)
		{
			throw new Exception("� ������� ����������� ������� way");
		}
		return document;
	}

	/**
	 * ������� ������� way
	 * @param passport ����� � ����� ���������
	 * @return ������� way
	 */
	public static ClientDocument getWayDocument(String passport)
	{
		ClientDocumentImpl document = new ClientDocumentImpl();
		document.setDocSeries(passport);
		document.setDocumentType(ClientDocumentType.PASSPORT_WAY);

		return document;
	}

	/**
	 * �������� ������ ���������� �������� �������
	 * @param guids ������� �������
	 * @return ������ ��������
	 */
	public static List<GUID> getUniqueClientHistory(List<GUID> guids)
	{
		if (CollectionUtils.isEmpty(guids))
		{
			return Collections.emptyList();
		}

		List<GUID> result = new ArrayList<GUID>();
		for (GUID guid : guids)
		{
			if (isAdded(guid, result))
			{
				continue;
			}
			result.add(guid);
		}
		return result;
	}

	private static boolean isAdded(GUID guid, List<GUID> guids)
	{
		GUIDComparator comparator = new GUIDComparator();
		for (GUID unique : guids)
		{
			if (comparator.compare(guid, unique) != 0)
			{
				continue;
			}
			return true;
		}
		return false;
	}
}
