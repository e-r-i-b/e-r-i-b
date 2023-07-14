package com.rssl.phizic.business.ermb.migration.list.task.segmentation;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;
import com.rssl.phizic.business.ermb.migration.list.task.hibernate.ArchiveClientService;

import java.text.SimpleDateFormat;

/**
 * @author Gulov
 * @ created 14.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ 5 ��������.
 * � ������� ���� �������������� �����.
 * ������� 5_1: ��������� �������������� � �������� ����, ��� ������� ����������� ��������� �������:
 * - � ��� ������� ����������� �������������� ����� (��� ���� �������������� ����� �������� ������������ �������������� ������ � �����������) ;
 * - �������� ������ ����� ������� ���/���
 * ������� 5_2: ��������� �������������� � �������� ����, ��� ������� ����������� ��������� �������:
 * - � ��� ������� ����������� �������������� ����� (��� ���� �������������� ����� �������� ������������ �������������� ������ � �����������);
 * - �������� ������ �� ���/���;
 * - �������� ������ ����� � ���/��� ������ �����������;
 * - �������������� ��������� ����� ����������� ����������� � ���/��� (�� ������ �������)
 * ������� 5_3: ��������� �������������� � �������� ����, ��� ������� ����������� ��������� �������:
 * - � ��� ������� ����������� �������������� ����� (��� ���� �������������� ����� �������� ������������ �������������� ������ � �����������);
 * - �������� ������ �� ���/���;
 * - �������� ������ ����� � ���/��� ������ �����������;
 * - �������������� ��������� �� ����� ����������� ����������� � ���/��� (�� ������ �������)
 * ������� 5_4: ��������� �������������� � �������� ����, ��� ������� ����������� ��������� �������:
 * - � ��� ������� ����������� �������������� ����� (��� ���� �������������� ����� �������� ������������ �������������� ������ � �����������);
 * - �������� ������ �� ���/���;
 * - �������� ������ �� ����� � ���/��� ������ �����������;
 * ������� 5_5: ��������� �������� ����, ��� ������� ����������� ��������� �������:
 * - � ��� ������� ����������� �������������� ����� (��� ���� �������������� ����� �� �������� ������������
 * �������������� ������ � ����������� -  � ����������� ���� ������ �������� �������� ��������� ���������);
 */
public class SegmentResolver5 implements Resolver
{
	private static final ArchiveClientService archiveService = new ArchiveClientService();

	public boolean evaluate(final Client client) throws BusinessException
	{
		if (!(!client.getPhones().isEmpty() && client.isAdditionalCards()))
			return false;
		// ���� ������ - ������ (����� ��� ���� �� ������ �����), �� ������� �� ���������, �������������� ����� ��������
		if (isSultan(client))
			return true;
		boolean isVip = isVipBaseClient(client);
		boolean hasSingleAdditionalCard = hasSingleAdditionalCard(client);
		if (!hasSingleAdditionalCard)
		{
			client.setSegment_5_5(true);
			return true;
		}
		client.setSegment_5_1(hasSingleAdditionalCard && isVip);
		if (client.getSegment_5_1())
			return true;
		boolean hasBaseClientRegistrations = hasBaseClientRegistrations(client);
		boolean hasAdditionalClientRegistration = hasAdditionalClientRegistration(client);
		client.setSegment_5_2(hasSingleAdditionalCard && !isVip && hasBaseClientRegistrations && hasAdditionalClientRegistration);
		if (client.getSegment_5_2())
			return true;
		client.setSegment_5_3(hasSingleAdditionalCard && !isVip && hasBaseClientRegistrations && !hasAdditionalClientRegistration);
		if (client.getSegment_5_3())
			return true;
		client.setSegment_5_4(hasSingleAdditionalCard && !isVip && !hasBaseClientRegistrations);
		if (client.getSegment_5_4())
			return true;
		return true;
	}

	private boolean isSultan(Client client)
	{
		for (Phone phone : client.getPhones())
			if (phone.isSultan())
				return true;
		return false;
	}

	/**
	 * � ��� ���� ����������� �������������� ����� � ��� ����
	 * @param client ������
	 * @return true - ����, false - ���
	 */
	private boolean hasSingleAdditionalCard(Client client)
	{
		for (Phone phone : client.getPhones())
			if (!phone.isOnlyAdditional())
				return false;
		return true;
	}

	/**
	 * �������� �������������� ����� �������� VIP/MBC
	 * @param client ������
	 * @return true - ��, false - ���
	 */
	private boolean isVipBaseClient(Client client) throws BusinessException
	{
		for (Phone phone : client.getPhones())
			if (phone.getVipOrMbc())
				return true;
		return false;
	}

	/**
	 * �������� ������ ����� ������ ����������� � ���/���
	 * @param client ������
	 * @return true - ��, false - ���
	 */
	private boolean hasBaseClientRegistrations(Client client) throws BusinessException
	{
		for (Phone phone : client.getPhones())
			if (archiveService.hasRegistrationByOtherPhone(client.getLastName(),
					client.getFirstName(), client.getMiddleName(), client.getDocument(),
					new SimpleDateFormat("dd.MM.yyyy").format(client.getBirthday().getTime()), phone.getPhoneNumber()))
				return true;
		return false;
	}

	/**
	 * �������������� ��������� ����� ����������� ����������� � ���/��� �� ������ ��������
	 * @param client ������
	 * @return true - ��, false - ���
	 */
	private boolean hasAdditionalClientRegistration(Client client) throws BusinessException
	{
		for (String[] strings : client.getAdditionalRegistration())
			if (archiveService.hasRegistrationByOtherPhone(strings[1], strings[2], strings[3], strings[4], strings[5], strings[0]))
				return true;
		return false;
	}
}
