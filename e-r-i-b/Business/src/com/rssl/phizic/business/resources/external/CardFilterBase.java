package com.rssl.phizic.business.resources.external;

/**
 * @ author: filimonova
 * @ created: 12.05.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class CardFilterBase implements CardFilter
{
	  public boolean evaluate(Object object)
	  {
		  if (object instanceof CardLink)
		  {
			  return accept(((CardLink) object).getCard());
		  }
		  else
			  return false;
	  }
}
