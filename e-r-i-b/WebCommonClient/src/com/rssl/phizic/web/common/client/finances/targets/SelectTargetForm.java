package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.phizic.business.finances.targets.TargetType;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.utils.ListUtil;

import java.util.List;
import java.util.Collections;

/**
 * ����� ������ ����� ���
 * @author lepihina
 * @ created 19.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class SelectTargetForm extends ActionFormBase
{
	private static final List<TargetType> TARGETS = ListUtil.fromArray(TargetType.values());
	private boolean hasTargets = false; // ���� �� � ������������ ����

	public boolean getHasTargets()
	{
		return hasTargets;
	}

	public void setHasTargets(boolean hasTargets)
	{
		this.hasTargets = hasTargets;
	}

	/**
	 * ���������� ������ ��������� ����� ��� ������
	 * @return ������ ��������� ����� ��� ������
	 */
	public List<TargetType> getTargets()
	{
		return Collections.unmodifiableList(TARGETS);
	}
}
