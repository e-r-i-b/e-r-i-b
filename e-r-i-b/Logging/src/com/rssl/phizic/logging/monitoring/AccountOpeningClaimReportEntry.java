package com.rssl.phizic.logging.monitoring;

import java.util.Map;

/**
 * Отчет по вкладам в СБОЛ.
 *
 * @author bogdanov
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningClaimReportEntry implements ReportEntry
{
	private String accountType;
	private Long openedClaimsCount;
	private Map<String, Long> openedClaimsCountTB;
	private Double openedClaimsCountNorma;
	private Map<String, Double> openedClaimsCountTBNorma;
	private Long openedClaimsCountALF;
	private Map<String, Long> openedClaimsCountTBALF;
	private Double openedClaimsCountALFNorma;
	private Map<String, Double> openedClaimsCountTBALFNorma;

	public String getName()
	{
		return accountType;
	}

	public void setName(String name)
	{
		this.accountType = name;
	}

	/**
	 * @return Количество открытых вкладов за сутки.
	 */
	public Long getOpenedClaimsCount()
	{
		return openedClaimsCount;
	}

	/**
	 * @param openedClaimsCount Количество открытых вкладов за сутки.
	 */
	public void setOpenedClaimsCount(Long openedClaimsCount)
	{
		this.openedClaimsCount = openedClaimsCount;
	}

	/**
	 * @return Норма: Количество открытых вкладов за сутки.
	 */
	public Double getOpenedClaimsCountNorma()
	{
		return openedClaimsCountNorma;
	}

	/**
	 * @param openedClaimsCountNorma Норма: Количество открытых вкладов за сутки.
	 */
	public void setOpenedClaimsCountNorma(Double openedClaimsCountNorma)
	{
		this.openedClaimsCountNorma = openedClaimsCountNorma;
	}

	/**
	 * @return Количество открытых вкладов за сутки в ТБ.
	 */
	public Map<String, Long> getOpenedClaimsCountTB()
	{
		return openedClaimsCountTB;
	}

	/**
	 * @param openedClaimsCountTB Количество открытых вкладов за сутки в ТБ.
	 */
	public void setOpenedClaimsCountTB(Map<String, Long> openedClaimsCountTB)
	{
		this.openedClaimsCountTB = openedClaimsCountTB;
	}

	/**
	 * @return Норма: Количество открытых вкладов за сутки в ТБ.
	 */
	public Map<String, Double> getOpenedClaimsCountTBNorma()
	{
		return openedClaimsCountTBNorma;
	}

	/**
	 * @param openedClaimsCountTBNorma Норма: Количество открытых вкладов за сутки в ТБ.
	 */
	public void setOpenedClaimsCountTBNorma(Map<String, Double> openedClaimsCountTBNorma)
	{
		this.openedClaimsCountTBNorma = openedClaimsCountTBNorma;
	}

	/**
	 * @return Количество открытых вкладов за сутки (АЛФ).
	 */
	public Long getOpenedClaimsCountALF()
	{
		return openedClaimsCountALF;
	}

	/**
	 * @param openedClaimsCountALF Количество открытых вкладов за сутки (АЛФ).
	 */
	public void setOpenedClaimsCountALF(Long openedClaimsCountALF)
	{
		this.openedClaimsCountALF = openedClaimsCountALF;
	}

	/**
	 * @return Норма: Количество открытых вкладов за сутки (АЛФ).
	 */
	public Double getOpenedClaimsCountALFNorma()
	{
		return openedClaimsCountALFNorma;
	}

	/**
	 * @param openedClaimsCountALFNorma Норма: Количество открытых вкладов за сутки (АЛФ).
	 */
	public void setOpenedClaimsCountALFNorma(Double openedClaimsCountALFNorma)
	{
		this.openedClaimsCountALFNorma = openedClaimsCountALFNorma;
	}

	/**
	 * @return Количество открытых вкладов за сутки в ТБ (АЛФ).
	 */
	public Map<String, Long> getOpenedClaimsCountTBALF()
	{
		return openedClaimsCountTBALF;
	}

	/**
	 * @param openedClaimsCountTBALF Количество открытых вкладов за сутки в ТБ (АЛФ).
	 */
	public void setOpenedClaimsCountTBALF(Map<String, Long> openedClaimsCountTBALF)
	{
		this.openedClaimsCountTBALF = openedClaimsCountTBALF;
	}

	/**
	 * @return Норма: Количество открытых вкладов за сутки в ТБ (АЛФ)
	 */
	public Map<String, Double> getOpenedClaimsCountTBALFNorma()
	{
		return openedClaimsCountTBALFNorma;
	}

	/**
	 * @param openedClaimsCountTBALFNorma Норма: Количество открытых вкладов за сутки в ТБ (АЛФ)
	 */
	public void setOpenedClaimsCountTBALFNorma(Map<String, Double> openedClaimsCountTBALFNorma)
	{
		this.openedClaimsCountTBALFNorma = openedClaimsCountTBALFNorma;
	}
}
