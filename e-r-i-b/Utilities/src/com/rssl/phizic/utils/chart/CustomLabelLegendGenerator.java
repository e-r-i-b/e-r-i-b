package com.rssl.phizic.utils.chart;

import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.data.general.PieDataset;

import java.text.AttributedString;
import java.text.DecimalFormat;

/**
 * @author mihaylov
 * @ created 17.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class CustomLabelLegendGenerator implements PieSectionLabelGenerator
{
	private static final DecimalFormat decimalFormat = new DecimalFormat("##0");

	public String generateSectionLabel(PieDataset dataset, Comparable comparable)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(decimalFormat.format(dataset.getValue(comparable)));
		sb.append("% ");
		sb.append(comparable.toString());
		return sb.toString();
	}

	public AttributedString generateAttributedSectionLabel(PieDataset dataset, Comparable comparable)
	{
		return null;
	}
}
