package com.rssl.phizic.business.dictionaries.productRequirements;

/**
 * @author lepihina
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ProductRequirement
{
	private Long id; // идентификатор
	private ProductRequirementType requirementType; // тип требования
    private RequirementState requirementState; // статус требования

	public ProductRequirement()
	{
	}

	public ProductRequirement(ProductRequirementType requirementType, RequirementState requirementState)
	{
		this.requirementType = requirementType;
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

	public ProductRequirementType getRequirementType()
	{
		return requirementType;
	}

	public void setRequirementType(ProductRequirementType requirementType)
	{
		this.requirementType = requirementType;
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
