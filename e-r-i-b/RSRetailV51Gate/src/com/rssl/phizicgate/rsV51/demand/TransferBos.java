package com.rssl.phizicgate.rsV51.demand;

/**
 * TODO: Написать JavaDoc ко ВСЕМ методам
 * TODO: Наименование методов и самого класса не соответствует правилам оформления кода
 * TODO: Сделать interface (реализацию п соответствующий gate)
 * @author Novikov_A
 * @ created 04.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class TransferBos
{
	private ClaimId id;
	private String attributID = "ПЕРЕВОДЫ_БОС";
    private Long branch;
	private String applicationKey;
	private Long applicationKind;
	private Long refValue = 0L;
	private Long direction;

	public ClaimId getId()
	{
		return id;
	}

	public void setId(ClaimId id)
	{
		this.id = id;
		this.applicationKey = id.getApplicationKey();
		this.applicationKind = id.getApplicationKind();
	}

	public String getAttributID()
	{
		return attributID;
	}

	public void setAttributID(String attributID)
	{
		this.attributID = attributID;
	}

	public Long getBranch()
	{
		return branch;
	}

	public void setBranch(Long branch)
	{
		this.branch = branch;
	}

	public String getApplicationKey()
	{
		return applicationKey;
	}

	public void setApplicationKey(String applicationKey)
	{
		this.applicationKey = applicationKey;
	}

	public Long getApplicationKind()
	{
		return applicationKind;
	}

	public void setApplicationKind(Long applicationKind)
	{
		this.applicationKind = applicationKind;
	}

	public Long getRefValue()
	{
		return refValue;
	}

	public void setRefValue(Long refValue)
	{
		this.refValue = refValue;
	}

	public Long getDirection()
	{
		return direction;
	}

	public void setDirection(Long direction)
	{
		this.direction = direction;
	}
}
