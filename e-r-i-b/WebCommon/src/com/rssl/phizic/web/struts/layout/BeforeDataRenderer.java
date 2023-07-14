package com.rssl.phizic.web.struts.layout;

/**
 * @author Krenev
 * @ created 13.01.2009
 * @ $Author$
 * @ $Revision$
 */
public interface BeforeDataRenderer
{
	void startBeforeData(StringBuffer buffer);

	void endBeforeData(StringBuffer buffer);

	void printBeforeDataElement(StringBuffer buffer, String value, int spanSize);
}
