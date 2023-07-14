package com.rssl.phizicgate.esberibgate.utils;

import com.rssl.phizic.gate.utils.EntityCompositeId;

/**
 * @author osminin
 * @ created 31.03.2011
 * @ $Author$
 * @ $Revision$
 * Объектное представление композитного идентификатора карты или счета
 * обший вид идентификатора:
 * <id сущности>^<systemId>^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>
 */
public class CardOrAccountCompositeId extends EntityCompositeId
{
	private String agencyId; //Номер подразделения(ОСБ).
	private String branchId; //Номер филиала(ВСП).
	private String regionId; //Номер тербанка(ТБ).

	CardOrAccountCompositeId(String id)
	{
		super(id);
		String[] parameters = id.split("\\^");
		regionId = parameters.length > 4 ? parameters[4] : null;
		branchId = parameters.length > 5 ? parameters[5] : null;
		agencyId = parameters.length > 6 ? parameters[6] : null;
	}

	CardOrAccountCompositeId(String entityId, String systemId, String rbBrchId, Long loginId, String regionId, String branchId, String agencyId)
	{
		super(entityId, systemId, rbBrchId, loginId);
		this.regionId = regionId;
		this.branchId = branchId;
		this.agencyId = agencyId;
	}

	public String getAgencyId()
	{
		return agencyId;
	}

	public String getBranchId()
	{
		return branchId;
	}

	public String getRegionId()
	{
		return regionId;
	}

	public String toString()
	{
		StringBuilder compositeId = new StringBuilder();
		String commonId = super.toString();
		compositeId.append(commonId).append(ID_DELIMETER);
		compositeId.append(regionId).append(ID_DELIMETER);
		compositeId.append(branchId).append(ID_DELIMETER);
		compositeId.append(agencyId);
		return compositeId.toString();
	}
}
