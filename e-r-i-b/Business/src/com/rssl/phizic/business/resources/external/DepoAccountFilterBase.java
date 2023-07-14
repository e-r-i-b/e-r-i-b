package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.utils.MockHelper;

/**
 * @author mihaylov
 * @ created 03.09.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class DepoAccountFilterBase implements DepoAccountFilter
{
	public boolean evaluate(Object object)
	  {
		  if (object instanceof DepoAccountLink)
		  {
			  DepoAccount depoAccount = ((DepoAccountLink) object).getDepoAccount();
			  if (MockHelper.isMockObject(depoAccount))
			    return false;
			  
			  return accept(depoAccount);
		  }
		  else
			  return false;
	  }
}
