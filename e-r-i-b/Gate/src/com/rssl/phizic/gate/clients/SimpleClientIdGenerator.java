package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.HashHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author khudyakov
 * @ created 29.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class SimpleClientIdGenerator
{
	private static final String SEPARATOR = "^";                                //�����������

	/**
	 * ���������� ������� ������������� ������� �� �������� ������
	 * ������: hash(surName+firstName+middleName)^birthDay^documentNumber^RbBrchId
	 *
	 * @param client ������
	 * @return ����
	 */
	public static String generateClientId(Client client)
	{
		return generateClientId(client.getSurName(), client.getFirstName(), client.getPatrName(), client.getBirthDay() == null ? null : String.format("%1$td.%1$tm.%1$tY", client.getBirthDay()), getClientDocument(client), getRbTbBrunch(client.getOffice()));
	}

	/**
	 * ���������� ������� ������������� ������� �� �������� ������
	 * ������: hash(surName+firstName+middleName)^birthDay^documentNumber^RbBrchId
	 *
	 * @param surName �������
	 * @param firstName ���
	 * @param middleName ��������
	 * @param birthDay ���� ��������
	 * @param documentNumber ����� ���������
	 * @param rbTbBrunch rbTbBrunch
	 * @return id
	 */
	public static String generateClientId(String surName, String firstName, String middleName, String birthDay, String documentNumber, String rbTbBrunch)
	{
		StringBuilder userId = new StringBuilder();
		if (StringHelper.isNotEmpty(surName) || StringHelper.isNotEmpty(firstName) || StringHelper.isNotEmpty(middleName))
		{
			//��������� hash ��� (�� ����, ��� ����)
			StringBuilder fio = new StringBuilder().append(StringHelper.getEmptyIfNull(surName)).append(StringHelper.getEmptyIfNull(firstName)).append(StringHelper.getEmptyIfNull(middleName));
			userId.append(HashHelper.hash(fio.toString()));
		}
		if (StringHelper.isNotEmpty(birthDay))
		{
			//��������� ���� ��������
			userId.append(birthDay).append(SEPARATOR);
		}
		if (StringHelper.isNotEmpty(documentNumber))
		{
			//��������� ����� ���������
			userId.append(documentNumber).append(SEPARATOR);
		}
		if (StringHelper.isNotEmpty(rbTbBrunch))
		{
			//��������� ����� RbTbBrunch
			userId.append(rbTbBrunch);
		}
		return userId.toString();
	}

	private static String getRbTbBrunch(Office office)
	{
		if (office == null)
			return null;

		if (office.getCode() == null)
			return null;

		return StringHelper.appendLeadingZeros(office.getCode().getFields().get("region"), 2) + "000000"; 
	}

	private static String getClientDocument(Client client)
	{
		if (CollectionUtils.isNotEmpty(client.getDocuments()))
		{
			List<? extends ClientDocument> clientDocuments = new ArrayList<ClientDocument>(client.getDocuments());
			Collections.sort(clientDocuments, new DocumentTypeComparator());
			ClientDocument document = clientDocuments.get(0);
			return document.getDocSeries() + document.getDocNumber();
		}
		return null;
	}
}
