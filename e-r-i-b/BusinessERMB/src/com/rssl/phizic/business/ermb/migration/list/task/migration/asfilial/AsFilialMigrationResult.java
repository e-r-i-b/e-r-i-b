package com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial;

import com.rssl.phizic.business.ermb.ErmbProfileImpl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * ��������� �������� ���->���� � �� ������
 * @author Puzikov
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings("JavaDoc")
public class AsFilialMigrationResult
{
	private final ErmbProfileImpl profile;    //��������� �� ����������� �������� �������

	private final boolean infoCardConflict;   //���� �� �������� �� ���. ����� (���� � ����� -6 �� ��)

	private final Set<String> engagedPhones;  //����� ����������� ���������

	public AsFilialMigrationResult(ErmbProfileImpl profile, boolean infoCardConflict, Set<String> engagedPhones)
	{
		this.profile = profile;
		this.infoCardConflict = infoCardConflict;
		this.engagedPhones = new HashSet<String>(engagedPhones);
	}

	public ErmbProfileImpl getProfile()
	{
		return profile;
	}

	public boolean isInfoCardConflict()
	{
		return infoCardConflict;
	}

	public Set<String> getEngagedPhones()
	{
		return Collections.unmodifiableSet(engagedPhones);
	}
}
