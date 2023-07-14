package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 16.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityDetailsForm extends EditFormBase
{
	private String group;
	private String tariff;
	private DepositProductEntity entity;
	private DepositsTDOG entityTDog;
	private Map<String, BigDecimal> minAdditionalFee = new HashMap<String, BigDecimal>();

	public String getGroup()
	{
		return group;
	}

	public void setGroup(String group)
	{
		this.group = group;
	}

	public String getTariff()
	{
		return tariff;
	}

	public void setTariff(String tariff)
	{
		this.tariff = tariff;
	}

	public DepositProductEntity getEntity()
	{
		return entity;
	}

	public void setEntity(DepositProductEntity entity)
	{
		this.entity = entity;
	}

	public DepositsTDOG getEntityTDog()
	{
		return entityTDog;
	}

	public void setEntityTDog(DepositsTDOG entityTDog)
	{
		this.entityTDog = entityTDog;
	}

	public Map<String, BigDecimal> getMinAdditionalFee()
	{
		return minAdditionalFee;
	}

	public void setMinAdditionalFee(Map<String, BigDecimal> minAdditionalFee)
	{
		this.minAdditionalFee = minAdditionalFee;
	}
}
