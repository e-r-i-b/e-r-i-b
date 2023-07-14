package com.rssl.phizic.utils.chart;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;

import java.text.AttributedString;
import java.text.DecimalFormat;

/**
 * Генерирует подписи вида "Название: проценты" (например, "Вклады: 23%")
 * @author lepihina
 * @ created 23.08.13
 * @ $Author$
 * @ $Revision$
 */
public class PercentAndTitleLabelGenerator implements PieSectionLabelGenerator
{
	private static final DecimalFormat decimalFormat = new DecimalFormat("##0");

	public String generateSectionLabel(PieDataset dataset, Comparable comparable)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(comparable);
		sb.append(": ");
		sb.append(decimalFormat.format(dataset.getValue(comparable)));
		sb.append("%");
		return sb.toString();
	}

	public AttributedString generateAttributedSectionLabel(PieDataset dataset, Comparable comparable)
	{
		return null;
	}
}
