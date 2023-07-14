package com.rssl.phizicgate.esberibgate.utils;

import com.rssl.phizic.gate.utils.EntityCompositeId;

/**
 * @author osminin
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 * объектное представление компоситного идентифкатора длительного поручения
 * идентификатор имеет вид:
 * <id сущности>^<systemId>^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>^<SvcType>
 */
public class LongOfferCompositeId extends EntityCompositeId
{
	private String agencyId; //Номер подразделения(ОСБ).
	private String branchId; //Номер филиала(ВСП).
	private String regionId; //Номер тербанка(ТБ).
	private Boolean svcType; //

	public LongOfferCompositeId(String id)
	{
		super(id);
		String[] strings = id.trim().split("\\^");
		regionId = strings[4];
		branchId = strings[5];
		agencyId = strings[6];
		svcType = Boolean.parseBoolean(strings[7]);
	}

	public LongOfferCompositeId(String entityId, String systemId, String rbBrchId, Long loginId, String regionId, String branchId, String agencyId, Boolean svcType)
	{
		super(entityId, systemId, rbBrchId, loginId);
		this.agencyId = agencyId;
		this.branchId = branchId;
		this.regionId = regionId;
		this.svcType = svcType;
	}

	public String getBranchId()
	{
		return branchId;
	}

	public String getAgencyId()
	{
		return agencyId;
	}

	public String getRegionId()
	{
		return regionId;
	}

	public Boolean getSvcType()
	{
		return svcType;
	}

	public String toString()
	{
		StringBuilder compositeId = new StringBuilder();
		String commonId = super.toString();
		compositeId.append(commonId).append(ID_DELIMETER);
		compositeId.append(regionId).append(ID_DELIMETER);
		compositeId.append(branchId).append(ID_DELIMETER);
		compositeId.append(agencyId).append(ID_DELIMETER);
		compositeId.append(svcType);
		return compositeId.toString();
	}
}
