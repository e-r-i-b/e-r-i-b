package com.rssl.phizic.web.common.client.finances.targets;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ����� ��������� ������ ����� �������
 * @author lepihina
 * @ created 20.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class ListTargetForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();

	private List<AccountTarget> targets = new ArrayList<AccountTarget>(); //������ ����� �������
	private Map<AccountLink, AccountAbstract> accountAbstract; // ������� �� �������
	private Map<AccountLink, String> accountAbstractErrors; //������, ���������� ��� ������� ������� �� �������

	public List<AccountTarget> getTargets()
	{
		return targets;
	}

	public void setTargets(List<AccountTarget> targets)
	{
		this.targets = targets;
	}

	public Map<AccountLink, AccountAbstract> getAccountAbstract()
	{
		return accountAbstract;
	}

	public void setAccountAbstract(Map<AccountLink, AccountAbstract> accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public Map<AccountLink, String> getAccountAbstractErrors()
	{
		return accountAbstractErrors;
	}

	public void setAccountAbstractErrors(Map<AccountLink, String> accountAbstractErrors)
	{
		this.accountAbstractErrors = accountAbstractErrors;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		return formBuilder.build();
	}
}
