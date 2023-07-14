package com.rssl.phizic.business.dictionaries.productRequirements;

import com.rssl.phizic.business.deposits.DepositProduct;

/**
 * @author lepihina
 * @ created 23.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class AccTypesRequirement
{
	private Long id; // идентификатор
	private DepositProduct depositProduct; // тип вклада
    private RequirementState requirementState; // статус требовани€

	public AccTypesRequirement()
	{
	}

	public AccTypesRequirement(DepositProduct depositProduct, RequirementState requirementState)
	{
		this.depositProduct = depositProduct;
		this.requirementState = requirementState;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public DepositProduct getDepositProduct()
	{
		return depositProduct;
	}

	public void setDepositProduct(DepositProduct depositProduct)
	{
		this.depositProduct = depositProduct;
	}

	public RequirementState getRequirementState()
	{
		return requirementState;
	}

	public void setRequirementState(RequirementState requirementState)
	{
		this.requirementState = requirementState;
	}
}
