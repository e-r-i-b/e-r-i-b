package com.rssl.phizic.business.ext.sbrf.deposits.entities;

import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author EgorovaA
 * @ created 07.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityHelper
{
	public static String parsePeriod(String period)
	{
		if (StringHelper.isEmpty(period))
			return null;

		int periodLength = period.length();
		if (periodLength == 8)
		{
			String start = period.substring(3, 5);
			String end = period.substring(6, 8);

			StringBuilder sb = new StringBuilder();
			sb.append(monthsToYYMM(start)).append("-0000U").append(monthsToYYMM(end)).append("-0000");
			return sb.toString();
		}
		else if (periodLength < 4)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("00-00-").append(StringHelper.appendLeadingZeros(period, 4));
			return sb.toString();
		}
		else if (periodLength > 3 && periodLength < 6)
		{
			String month = period.substring(0, periodLength-3);
			String day = period.substring(periodLength-3);
			StringBuilder sb = new StringBuilder();
			sb.append("00-").append(StringHelper.appendLeadingZeros(month, 2)).append("-").append(StringHelper.appendLeadingZeros(day, 4));
			return sb.toString();
		}
		else
		{
			String year = period.substring(0, periodLength-5);
			String month = period.substring(periodLength-5, periodLength-3);
			String day = period.substring(3);

			StringBuilder sb = new StringBuilder();
			sb.append(StringHelper.appendLeadingZeros(year, 2)).append("-").
					append(StringHelper.appendLeadingZeros(month, 2)).append("-").
					append(StringHelper.appendLeadingZeros(day, 4));
			return sb.toString();
		}
	}

	private static String monthsToYYMM(String month)
	{
		int monthNum =  Integer.valueOf(month);
		if (monthNum < 12)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("00-").append(month);
			return sb.toString();
		}
		else
		{
			int quotient = monthNum / 12;
			int remainder = monthNum % 12;

			StringBuilder sb = new StringBuilder();
			sb.append(StringHelper.appendLeadingZeros(String.valueOf(quotient), 2)).append("-")
					.append(StringHelper.appendLeadingZeros(String.valueOf(remainder), 2));
			return  sb.toString();
		}
	}

	public static String preparePeriods(List<String> periodList) throws ParseException
	{

		if (periodList.isEmpty())
			return "бессрочный";

		List<String> staticPeriodList = new ArrayList<String>();
		List<String> intervalPeriodList = new ArrayList<String>();

		for (String period : periodList)
		{
			if (!period.contains("U"))
				staticPeriodList.add(period);
			else
				intervalPeriodList.add(period);
		}

		String from = getMinPeriod(staticPeriodList, intervalPeriodList);
		String to = getMaxPeriod(staticPeriodList, intervalPeriodList);

		if (StringHelper.equals(from, to))
			return prepareShortPeriod(from);
		else
		{
			StringBuilder sb = new StringBuilder();
			sb.append(" от ");
			sb.append(prepareFullPeriod(from, true, true));
			sb.append(" до ");
			sb.append(prepareFullPeriod(to, true, true));
			return sb.toString();
		}
	}

	public static String getDepositMinPeriod(List<String> periodList) throws ParseException
	{

		if (CollectionUtils.isEmpty(periodList))
			return "";

		List<String> staticPeriodList = new ArrayList<String>();
		List<String> intervalPeriodList = new ArrayList<String>();

		for (String period : periodList)
		{
			if (!period.contains("U"))
				staticPeriodList.add(period);
			else
				intervalPeriodList.add(period);
		}

		return getMinPeriod(staticPeriodList, intervalPeriodList);
	}

	public static String getDepositMaxPeriod(List<String> periodList) throws ParseException
	{

		if (CollectionUtils.isEmpty(periodList))
			return "";

		List<String> staticPeriodList = new ArrayList<String>();
		List<String> intervalPeriodList = new ArrayList<String>();

		for (String period : periodList)
		{
			if (!period.contains("U"))
				staticPeriodList.add(period);
			else
				intervalPeriodList.add(period);
		}

		return getMaxPeriod(staticPeriodList, intervalPeriodList);
	}

	private static String getMinPeriod(List<String> staticPeriodList, List<String> intervalPeriodList)
	{
		List<String> partIntervalPeriodList = new ArrayList<String>();
		for (String intervalPeriod : intervalPeriodList)
		{
			partIntervalPeriodList.add(intervalPeriod.substring(0, intervalPeriod.indexOf("U")));
		}
		Collections.sort(partIntervalPeriodList);
		Collections.sort(staticPeriodList);

		String minIntervalPeriod = partIntervalPeriodList.isEmpty() ? "" : partIntervalPeriodList.get(0);
		String minStaticPeriod = staticPeriodList.isEmpty() ? "" : staticPeriodList.get(0);

		if (StringHelper.isEmpty(minIntervalPeriod))
			return minStaticPeriod;
		if (StringHelper.isEmpty(minStaticPeriod) ||
				Integer.valueOf(minIntervalPeriod.replace("-", "")) < Integer.valueOf(minStaticPeriod.replace("-", "")))
			return minIntervalPeriod;
		else
			return minStaticPeriod;
	}

	private static String getMaxPeriod(List<String> staticPeriodList, List<String> intervalPeriodList)
	{
		List<String> partIntervalPeriodList = new ArrayList<String>();
		for (String intervalPeriod : intervalPeriodList)
		{
			partIntervalPeriodList.add(intervalPeriod.substring(intervalPeriod.indexOf("U") + 1));
		}
		Collections.sort(partIntervalPeriodList);
		Collections.sort(staticPeriodList);

		String maxIntervalPeriod = partIntervalPeriodList.isEmpty() ? null : partIntervalPeriodList.get(partIntervalPeriodList.size()-1);
		String maxStaticPeriod = staticPeriodList.isEmpty() ? null : staticPeriodList.get(staticPeriodList.size()-1);

		if (StringHelper.isEmpty(maxIntervalPeriod))
			return maxStaticPeriod;
		if (StringHelper.isEmpty(maxStaticPeriod) ||
				Integer.valueOf(maxIntervalPeriod.replace("-", "")) > Integer.valueOf(maxStaticPeriod.replace("-", "")))
			return maxIntervalPeriod;
		else
			return maxStaticPeriod;
	}

	private static String prepareFullPeriod(String period, boolean numbers, boolean text)
	{
		String year = period.substring(0, 2);
		String month = period.substring(3, 5);
		String day = period.substring(6);

		int yearVal = Integer.valueOf(year);
		int monthVal = Integer.valueOf(month);
		int dayVal = Integer.valueOf(day);

		StringBuilder sb = new StringBuilder();

		if (yearVal != 0)
		{
			if (numbers)
				sb.append(StringHelper.removeLeadingZeros(year));
			if (text)
			{
				if ((yearVal % 10) == 1 && yearVal != 11)
					sb.append(" года ");
				else
					sb.append(" лет ");
			}
		}
		if (monthVal != 0)
		{
			if (numbers)
				sb.append(StringHelper.removeLeadingZeros(month));
			if (text)
			{
				if (monthVal == 1)
					sb.append(" мес€ца ");
				else
					sb.append(" мес€цев ");
			}
		}
		if (dayVal != 0)
		{
			if (numbers)
				sb.append(StringHelper.removeLeadingZeros(day));
			if (text)
			{
				if ((dayVal % 10) == 1 && dayVal != 11)
					sb.append(" дн€ ");
				else
					sb.append(" дней ");
			}
		}
		return sb.toString();
	}

	private static String prepareShortPeriod(String period)
	{
		String year = period.substring(0, 2);
		String month = period.substring(3, 5);
		String day = period.substring(6);

		int yearVal = Integer.valueOf(year);
		int monthVal = Integer.valueOf(month);
		int dayVal = Integer.valueOf(day);

		StringBuilder sb = new StringBuilder();

		if (yearVal != 0)
		{
			sb.append(StringHelper.removeLeadingZeros(year));
			if(yearVal == 1)
				sb.append(" год");
			else if (yearVal < 5)
				sb.append(" года");
			else
				sb.append(" лет");
		}
		else if (monthVal != 0)
		{
			sb.append(StringHelper.removeLeadingZeros(month));
			if(monthVal == 1)
				sb.append(" мес€ц");
			else if (monthVal < 5)
				sb.append(" мес€ца");
			else
				sb.append(" мес€цев");
		}
		else if (dayVal != 0)
		{
			sb.append(StringHelper.removeLeadingZeros(day));
			if(dayVal == 1)
				sb.append(" день");
			else if (dayVal < 5)
				sb.append(" дн€");
			else
				sb.append(" дней");
		}
		return sb.toString();
	}

	public static String preparePeriod(String period)
	{
		int separatorIndex = period.indexOf("U");

		if (separatorIndex == -1)
			return prepareShortPeriod(period);

		String from = period.substring(0, separatorIndex);
		String to = period.substring(separatorIndex+1);

		String fromText = prepareFullPeriod(from, false, true);
		String toText = prepareFullPeriod(to, false, true);

		StringBuilder sb = new StringBuilder();
		sb.append("ќт ");
		sb.append(prepareFullPeriod(from, true, false));
		if (!StringHelper.equals(fromText, toText))
			sb.append(fromText);
		sb.append(" до ");
		sb.append(prepareFullPeriod(to, true, false));
		sb.append(toText);

		return sb.toString();
	}

}
