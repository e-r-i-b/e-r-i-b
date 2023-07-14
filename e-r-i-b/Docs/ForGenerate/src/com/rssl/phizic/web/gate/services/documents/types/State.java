package com.rssl.phizic.web.gate.services.documents.types;

/**
 * @author egorova
 * @ created 25.05.2009
 * @ $Author$
 * @ $Revision$
 */
public interface State
{
	String getCategory();
	void setCategory(String stateCategory);
	String getCode();
	void setCode(String code);
	String getDescription();
	void setDescription(String description);
}
