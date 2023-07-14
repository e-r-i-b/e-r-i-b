package com.rssl.phizicgate.rsV51.senders;

/**
 * @author hudyakov
 * @ created 18.09.2008
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
		return "key:"+appKey+", kind:5";   // "kind:5", т.к. ритейл 5.1 возвращает kind = 1
	}
}
