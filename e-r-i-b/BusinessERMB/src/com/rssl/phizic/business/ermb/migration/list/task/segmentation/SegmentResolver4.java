package com.rssl.phizic.business.ermb.migration.list.task.segmentation;

import com.rssl.phizic.business.ermb.migration.list.entity.migrator.Client;
import com.rssl.phizic.business.ermb.migration.list.entity.migrator.MigrationPhoneUtils;

/**
 * @author Gulov
 * @ created 14.01.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ 4 ��������.
 * � ������� ��������� ������ ���������, �� ������� ���������� ������ ������.
 * � ������ �������� ��� �������� ����� ����� ����.
 * � ������� ����� ���� �������������� �����
 * �������� �� ����������� �� ������������.
 */
class SegmentResolver4 implements Resolver
{
	public boolean evaluate(final Client client)
	{
		client.setSegment_4(!client.getPhones().isEmpty() && MigrationPhoneUtils.hasMultiPhones(client));
		return false;
	}
}
