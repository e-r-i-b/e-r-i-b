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
 * ������������ 2 ��������.
 * �� ��� ������, ����� ������������ ����� ��������.
 * �� ���� ����� � ���� ���������� ��� �/��� ���.
 * � ������ �������� ����� ������ ��� (��� ����������� ������ �������� �� ���� �����).
 * � ������� ��� �������������� ����
 * ���������� 2.1 - � ������� ���������� ������ ���
 * ���������� 2.2 - � ������� �� ���������� ������ ���
 * ���������� 2.2.1 - ������ VIP ��� MBC
 */
public class SegmentResolver2 implements Resolver
{
	public boolean evaluate(final Client client)
	{
		if (client.getPhones().isEmpty())
			return false;
		Phone phone = MigrationPhoneUtils.getSinglePhone(client);
		if (!(!client.getUDBO() && phone != null && phone.getUnique() && !client.isAdditionalCards()))
			return false;
		setSubSegment(client);
		return client.getSegment_2_1() || client.getSegment_2_2() || client.getSegment_2_2_1();
	}

	/**
	 * ���������� � ���������� ���������� �������
	 * @param client - ������
	 */
	public static void setSubSegment(final Client client)
	{
		client.setSegment_2_1(MigrationPhoneUtils.hasMbvRegistration(client));
		client.setSegment_2_2(!MigrationPhoneUtils.hasMbvRegistration(client));
		client.setSegment_2_2_1(client.getSegment_2_2() && client.getVipOrMvs());
	}
}
