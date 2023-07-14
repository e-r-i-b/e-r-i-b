package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositEntityVisibilityInfo;
import com.rssl.phizic.web.deposits.EditDepositProductBankForm;

import java.util.List;

/**
 * @author EgorovaA
 * @ created 20.04.15
 * @ $Author$
 * @ $Revision$
 */
public class EditDepositEntityForm extends EditDepositProductBankForm
{
	private boolean available;
	private List<String> departments;
	private String[] terbankIds = new String[]{};
	private String tariff;
	private String visibilityHtml;
	private String[] depositSubTypeIds = new String[]{};
	private String depositName;
	private String group;

	private List<DepositEntityVisibilityInfo> depositEntitySubTypes;

	public String getVisibilityHtml()
	{
		return visibilityHtml;
	}

	public void setVisibilityHtml(String visibilityHtml)
	{
		this.visibilityHtml = visibilityHtml;
	}

	public boolean isAvailable()
	{
		return available;
	}

	public void setAvailable(boolean available)
	{
		this.available = available;
	}

	public List<String> getDepartments()
	{
		return departments;
	}

	public void setDepartments(List<String> departments)
	{
		this.departments = departments;
	}

	public String[] getTerbankIds()
	{
		return terbankIds;
	}

	public void setTerbankIds(String[] terbankIds)
	{
		this.terbankIds = terbankIds;
	}

	public String getTariff()
	{
		return tariff;
	}

	public void setTariff(String tariff)
	{
		this.tariff = tariff;
	}

	public String[] getDepositSubTypeIds()
	{
		return depositSubTypeIds;
	}

	public void setDepositSubTypeIds(String[] depositSubTypeIds)
	{
		this.depositSubTypeIds = depositSubTypeIds;
	}

	public String getDepositName()
	{
		return depositName;
	}

	public void setDepositName(String depositName)
	{
		this.depositName = depositName;
	}

	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}

	public List<DepositEntityVisibilityInfo> getDepositEntitySubTypes()
	{
		return depositEntitySubTypes;
	}

	public void setDepositEntitySubTypes(List<DepositEntityVisibilityInfo> depositEntitySubTypes)
	{
		this.depositEntitySubTypes = depositEntitySubTypes;
	}
}
