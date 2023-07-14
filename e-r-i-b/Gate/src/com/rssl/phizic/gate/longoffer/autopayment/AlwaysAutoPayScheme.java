package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.utils.xml.XmlEntityBuilder;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * —хема регул€рного автоплатежа
 * @author niculichev
 * @ created 03.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AlwaysAutoPayScheme extends AutoPaySchemeBase
{
	private static final String MAX_SUM_ALWAYS = "maxSumAlways"; // максимальна€ сумма
	private static final String MIN_SUM_ALWAYS = "minSumAlways"; // минимальна€ сумма

	public BigDecimal getMaxSumAlways()
	{
		return getBigDecimalValue(MAX_SUM_ALWAYS);
	}

	public void setMaxSumAlways(BigDecimal maxSumAlways)
	{
		setBigDecimalValue(MAX_SUM_ALWAYS, maxSumAlways);
	}

	public BigDecimal getMinSumAlways()
	{
		return getBigDecimalValue(MIN_SUM_ALWAYS);
	}

	public void setMinSumAlways(BigDecimal minSumAlways)
	{
		setBigDecimalValue(MIN_SUM_ALWAYS, minSumAlways);
	}

	public String getParametersByXml()
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(ENTITY, Collections.singletonMap("key", "ALWAYS"));
		builder.append(fieldBuilder(CLIENT_HINT, getClientHint()));
		builder.append(fieldBuilder(MAX_SUM_ALWAYS, getMaxSumAlways()));
		builder.append(fieldBuilder(MIN_SUM_ALWAYS, getMinSumAlways()));
		builder.closeEntityTag(ENTITY);
		return builder.toString();
	}
}
