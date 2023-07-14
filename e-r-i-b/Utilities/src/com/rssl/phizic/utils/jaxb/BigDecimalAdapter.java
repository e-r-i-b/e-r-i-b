package com.rssl.phizic.utils.jaxb;

import java.math.BigDecimal;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Puzikov
 * @ created 16.07.14
 * @ $Author$
 * @ $Revision$
 */

public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal>
{
	@Override
	public BigDecimal unmarshal(String v) throws Exception
	{
		return new BigDecimal(v);
	}

	@Override
	public String marshal(BigDecimal v) throws Exception
	{
		return v.toPlainString();
	}
}
