package com.rssl.phizic.web.commissions;

import com.rssl.phizic.business.ext.sbrf.commissions.CommissionsTBSetting;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.claims.AccountOpeningClaim;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.operations.commission.EditTBCommissionsOperation;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vagin
 * @ created 20.09.13
 * @ $Author$
 * @ $Revision$
 * ����� ��������� ����������� ���� �������������(��������) ��� ��������.
 */
public class EditTBCommissionForm extends ListFormBase
{
	private Long id; //������������� �������������(��)
	private Map<Pair<String,Boolean>, String> currencySettings = new HashMap<Pair<String,Boolean>, String>();  //��� value =��������� �������� ������� key = <�������, ������� �������>
	private Map<Pair<String,Boolean>, String> rurSettings = new HashMap<Pair<String,Boolean>, String>();  //��� value =��������� �������� ������� key = <�������, ������� �������>

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Map<Pair<String, Boolean>, String> getCurrencySettings()
	{
		return Collections.unmodifiableMap(currencySettings);
	}

	public void setCurrencySettings(Map<Pair<String,Boolean>, String> currencySettings)
	{
		this.currencySettings = currencySettings;
	}

	public Map<Pair<String, Boolean>, String> getRurSettings()
	{
		return rurSettings;
	}

	public void setRurSettings(Map<Pair<String, Boolean>, String> rurSettings)
	{
		this.rurSettings = rurSettings;
	}
}
