package com.rssl.phizic.business.ermb.migration.list.task.segmentation;

import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationPhoneUtils;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Phone;

/**
 * @author Gulov
 * @ created 13.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ 1 ��������.
 * ��� ������, ����� ������������ ����� ��������.
 * �� ���� ����� � ���� ���������� ��� �/��� ���.
 * � ������ �������� ����� ������ ��� (��� ����������� ������ �������� �� ���� �����).
 * � ������� ��� �������������� ����
 * ���������� 1.1 - � ������� ���������� ������ ���, �� �� ���������� ���
 * ���������� 1.2 - ������ VIP ��� MBC
 */
public class SegmentResolver1 implements Resolver
{
	public boolean evaluate(final Client client)
	{
		if (client.getPhones().isEmpty())
			return false;
		Phone phone = MigrationPhoneUtils.getSinglePhone(client);
		if (!(client.getUDBO() && phone != null && phone.getUnique() && !client.isAdditionalCards()))
			return false;
		setSubSegment(client);
		return client.getSegment_1() || client.getSegment_1_1() || client.getSegment_1_2();
	}

	/**
	 * ���������� � ���������� ���������� �������
	 * @param client - ������
	 */
	public static void setSubSegment(final Client client)
	{
		if (!MigrationPhoneUtils.hasMbkRegistration(client) && MigrationPhoneUtils.hasMbvRegistration(client))
			client.setSegment_1_1(true);
		else
			client.setSegment_1(true);
		client.setSegment_1_2(client.getVipOrMvs());
	}
}
