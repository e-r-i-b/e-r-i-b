package com.rssl.phizicgate.rsretailV6r20.senders;

/**
 * @author Omeliyanchuk
 * @ created 17.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class ExternalKeyCreator
{
	String appKind;
	String appKey;

	public ExternalKeyCreator(String appKind,String appKey)
	{
		this.appKind = appKind;
		this.appKey = appKey;
	}

	public String createId()
	{
		return "key:"+appKey+", kind:"+appKind;
	}
}
