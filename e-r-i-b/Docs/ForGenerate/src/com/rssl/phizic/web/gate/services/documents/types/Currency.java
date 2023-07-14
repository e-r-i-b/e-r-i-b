package com.rssl.phizic.web.gate.services.documents.types;

/**
 * @author egorova
 * @ created 19.06.2009
 * @ $Author$
 * @ $Revision$
 */
public interface Currency
{
	String getNumber();
	void setNumber(String number);

	String getCode();
	void setCode(String code);

	String getName();
	void setName(String name);

	String getExternalId();
	void setExtenalId(String externalId);
}
