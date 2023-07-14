package com.rssl.phizic.web.gate.services.documents.types;

import java.util.Map;

/**
 * @author egorova
 * @ created 25.05.2009
 * @ $Author$
 * @ $Revision$
 */
public interface Code
{
	String getRegion();
	void setRegion(String region);

	String getBranch();
	void setBranch(String branch);

	String getOffice();
	void setOffice(String office);

	Map<String, String> getFields();
	void setFields(Map<String, String> fields);

	String getId();
	void setId(String id);
}
