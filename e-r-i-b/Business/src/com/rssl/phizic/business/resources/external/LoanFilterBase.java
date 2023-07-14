package com.rssl.phizic.business.resources.external;

/**
 * @author gladishev
 * @ created 06.08.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class LoanFilterBase implements LoanFilter
{
	public boolean evaluate(Object object)
	{
		if (object instanceof LoanLink)
		{
			return accept(((LoanLink) object).getLoan());
		}
		else
			return false;
  }
}
