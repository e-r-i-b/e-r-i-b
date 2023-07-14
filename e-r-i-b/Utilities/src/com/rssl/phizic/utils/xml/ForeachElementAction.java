package com.rssl.phizic.utils.xml;

import org.w3c.dom.Element;

/**
 *
 * @author Roshka
 * @ created 26.06.2006
 * @ $Author$
 * @ $Revision$
 */

public interface ForeachElementAction
{
	void execute(Element element) throws Exception;
}
