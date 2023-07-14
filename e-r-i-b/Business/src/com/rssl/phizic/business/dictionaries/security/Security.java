package com.rssl.phizic.business.dictionaries.security;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.depo.SecurityBase;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author mihaylov
 * @ created 07.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class Security extends DictionaryRecordBase implements SecurityBase
{
	private Long id;
	private String issuer; // Эмиттент
	private String name;   //  Намиенование ЦБ
	private String registrationNumber; // Регистрационные номер ЦБ
	private String type;    // Тип ЦБ
	private String insideCode;  // Депозитарный код выпуска ЦБ
	private Money nominal;     //  Минимальный номинал выпуска ЦБ
	private boolean isDelete; //признак доступна/не доступна ЦБ (true если не доступна)

	public Comparable getSynchKey()
	{
		return registrationNumber;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getIssuer()
	{
		return issuer;
	}

	public void setIssuer(String issuer)
	{
		this.issuer = issuer;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRegistrationNumber()
	{
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber)
	{
		this.registrationNumber = registrationNumber;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getInsideCode()
	{
		return insideCode;
	}

	public void setInsideCode(String insideCode)
	{
		this.insideCode = insideCode;
	}

	public Money getNominal()
	{
		return nominal;
	}

	public void setNominal(Money nominal)
	{
		this.nominal = nominal;
	}
	public boolean getIsDelete()
	{
		return isDelete;
	}

	public void setIsDelete(boolean delete)
	{
		isDelete = delete;
	}
}
