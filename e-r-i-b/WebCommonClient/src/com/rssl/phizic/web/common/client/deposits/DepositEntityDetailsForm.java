package com.rssl.phizic.web.common.client.deposits;

import com.rssl.phizic.business.ext.sbrf.deposits.DepositsTDOG;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 10.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityDetailsForm extends EditFormBase
{
	private Long group;
	private String segment;

	private DepositProductEntity entity;
	private DepositsTDOG entityTDog;
	private Map<String, BigDecimal> minAdditionalFee = new HashMap<String, BigDecimal>();

	public Long getGroup()
	{
		return group;
	}

	public void setGroup(Long group)
	{
		this.group = group;
	}

	public String getSegment()
	{
		return segment;
	}

	public void setSegment(String segment)
	{
		this.segment = segment;
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
