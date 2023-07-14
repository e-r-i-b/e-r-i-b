package com.rssl.phizic.web.common.mobile.deposits;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.web.common.client.deposits.DepositDetailsForm;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Moshenko
 * Date: 22.02.12
 * Time: 16:05
 */
public class DepositDetailsMobileForm  extends DepositDetailsForm
{
	private DepositProductEntity entity;
	private DepositsTDOG entityTDog;
	private Map<String, BigDecimal> minAdditionalFee = new HashMap<String, BigDecimal>();

	/**
	 * дополнительные методы нужны для реализации полного соответствия
	 * спецификации Mobile API 3.0
	 * @return
	 */

	public Long getDepositId()
	{
		return id;
	}

	public void setDepositId(Long depositId)
	{
		this.id = depositId;
	}

	public String getDepositGroup()
	{
		return group;
	}

	public void setDepositGroup(String depositGroup)
	{
		this.group = depositGroup;
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
