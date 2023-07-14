package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;
import org.apache.commons.lang.BooleanUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Схема порогового автоплатежа
 * @author niculichev
 * @ created 03.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class ThresholdAutoPayScheme extends AutoPaySchemeBase
{
	private static final String DELEMITER = "\\|";

	private static final String MAX_VALUE_THRESHOLD     = "maxValueThreshold"; // максимальное значение порога
	private static final String MIN_VALUE_THRESHOLD     = "minValueThreshold"; // минимальное значение порога
	private static final String MAX_SUM_THRESHOLD       = "maxSumThreshold"; // максимальная сумма
	private static final String MIN_SUM_THRESHOLD       = "minSumThreshold"; // минимальная сумма
	private static final String ACCESS_TOTAL_MAX_SUM    = "accessTotalMaxSum"; // максимальная сумма за месяц в канале ЕРИБ
	private static final String TOTAL_MAX_SUM           = "totalMaxSum"; // максимальная сумма в определенный период
	private static final String PERIOD_MAX_SUM          = "periodMaxSum"; // Период для максимальной суммы
	private static final String DISCRETE_VALUE          = "discreteValues";
	private static final String INTERVAL                = "interval";

	public BigDecimal getMaxValueThreshold()
	{
		return getBigDecimalValue(MAX_VALUE_THRESHOLD);
	}

	public void setMaxValueThreshold(BigDecimal maxValueThreshold)
	{
		setBigDecimalValue(MAX_VALUE_THRESHOLD, maxValueThreshold);
	}

	public BigDecimal getMinValueThreshold()
	{
		return getBigDecimalValue(MIN_VALUE_THRESHOLD);
	}

	public void setMinValueThreshold(BigDecimal minValueThreshold)
	{
		setBigDecimalValue(MIN_VALUE_THRESHOLD, minValueThreshold);
	}

	public BigDecimal getMaxSumThreshold()
	{
		return getBigDecimalValue(MAX_SUM_THRESHOLD);
	}

	public void setMaxSumThreshold(BigDecimal maxSumThreshold)
	{
		setBigDecimalValue(MAX_SUM_THRESHOLD, maxSumThreshold);
	}

	public BigDecimal getMinSumThreshold()
	{
		return getBigDecimalValue(MIN_SUM_THRESHOLD);
	}

	public void setMinSumThreshold(BigDecimal minSumThreshold)
	{
		setBigDecimalValue(MIN_SUM_THRESHOLD, minSumThreshold);
	}

	public Boolean isInterval()
	{
		return getBooleanValue(INTERVAL);
	}

	public void setInterval(Boolean interval)
	{
		setBooleanValue(INTERVAL, interval);
	}

	public BigDecimal getTotalMaxSum()
	{
		return getBigDecimalValue(TOTAL_MAX_SUM);
	}

	public void setTotalMaxSum(BigDecimal totalMaxSumERIB)
	{
		setBigDecimalValue(TOTAL_MAX_SUM, totalMaxSumERIB);
	}

	public TotalAmountPeriod getPeriodMaxSum()
	{
		return getEnumValue(TotalAmountPeriod.class, PERIOD_MAX_SUM);
	}

	public void setPeriodMaxSum(TotalAmountPeriod periodTotalMaxSum)
	{
		setEnumValue(PERIOD_MAX_SUM, periodTotalMaxSum);
	}

	public boolean isAccessTotalMaxSum()
	{
		return BooleanUtils.isTrue(getBooleanValue(ACCESS_TOTAL_MAX_SUM));
	}

	public void setAccessTotalMaxSum(boolean accessTotalMaxSum)
	{
		setBooleanValue(ACCESS_TOTAL_MAX_SUM, accessTotalMaxSum);
	}

	/**
	 * @return Допустимые значение ввиде листа
	 */
	public List<BigDecimal> getAccessValuesAsList() throws ParseException
	{
		String discreteValues = getDiscreteValues();
		if(StringHelper.isEmpty(discreteValues))
			 return Collections.emptyList();
		
		String[] values = discreteValues.split(DELEMITER);
		List<BigDecimal> result = new ArrayList<BigDecimal>();
		for(String value: values)
			result.add(NumericUtil.parseBigDecimal(value));

		return result;
	}

	public String getDiscreteValues()
	{
		return getStringValue(DISCRETE_VALUE);
	}

	public void setDiscreteValues(String discreteValues)
	{
		setStringValue(DISCRETE_VALUE, discreteValues);
	}

	public String getParametersByXml()
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(ENTITY, Collections.singletonMap("key", "THRESHOLD"));
		builder.append(fieldBuilder(CLIENT_HINT, getClientHint()));
		builder.append(fieldBuilder(MAX_SUM_THRESHOLD, getMaxSumThreshold()));
		builder.append(fieldBuilder(MIN_SUM_THRESHOLD, getMinSumThreshold()));
		builder.append(fieldBuilder(MAX_VALUE_THRESHOLD, getMaxValueThreshold()));
		builder.append(fieldBuilder(MIN_VALUE_THRESHOLD, getMinValueThreshold()));
		builder.append(fieldBuilder(ACCESS_TOTAL_MAX_SUM, isAccessTotalMaxSum()));
		builder.append(fieldBuilder(TOTAL_MAX_SUM, getTotalMaxSum()));
		builder.append(fieldBuilder(PERIOD_MAX_SUM, StringHelper.getEmptyIfNull(getPeriodMaxSum())));
		builder.append(fieldBuilder(INTERVAL, isInterval()));
		builder.append(fieldBuilder(DISCRETE_VALUE, StringHelper.getEmptyIfNull(getDiscreteValues())));
		builder.closeEntityTag(ENTITY);
		return builder.toString();
	}
}
