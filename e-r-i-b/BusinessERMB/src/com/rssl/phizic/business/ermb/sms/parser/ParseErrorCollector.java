package com.rssl.phizic.business.ermb.sms.parser;

/**
 * @author Erkin
 * @ created 17.04.2014
 * @ $Author$
 * @ $Revision$
 */
interface ParseErrorCollector
{
	void addError(String error, int errorOffset);
}
