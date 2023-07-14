package com.rssl.phizic.logging.monitoring;

import java.util.Map;

/**
 * ����� �� ������� � ����.
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
	 * @return ���������� �������� ������� �� �����.
	 */
	public Long getOpenedClaimsCount()
	{
		return openedClaimsCount;
	}

	/**
	 * @param openedClaimsCount ���������� �������� ������� �� �����.
	 */
	public void setOpenedClaimsCount(Long openedClaimsCount)
	{
		this.openedClaimsCount = openedClaimsCount;
	}

	/**
	 * @return �����: ���������� �������� ������� �� �����.
	 */
	public Double getOpenedClaimsCountNorma()
	{
		return openedClaimsCountNorma;
	}

	/**
	 * @param openedClaimsCountNorma �����: ���������� �������� ������� �� �����.
	 */
	public void setOpenedClaimsCountNorma(Double openedClaimsCountNorma)
	{
		this.openedClaimsCountNorma = openedClaimsCountNorma;
	}

	/**
	 * @return ���������� �������� ������� �� ����� � ��.
	 */
	public Map<String, Long> getOpenedClaimsCountTB()
	{
		return openedClaimsCountTB;
	}

	/**
	 * @param openedClaimsCountTB ���������� �������� ������� �� ����� � ��.
	 */
	public void setOpenedClaimsCountTB(Map<String, Long> openedClaimsCountTB)
	{
		this.openedClaimsCountTB = openedClaimsCountTB;
	}

	/**
	 * @return �����: ���������� �������� ������� �� ����� � ��.
	 */
	public Map<String, Double> getOpenedClaimsCountTBNorma()
	{
		return openedClaimsCountTBNorma;
	}

	/**
	 * @param openedClaimsCountTBNorma �����: ���������� �������� ������� �� ����� � ��.
	 */
	public void setOpenedClaimsCountTBNorma(Map<String, Double> openedClaimsCountTBNorma)
	{
		this.openedClaimsCountTBNorma = openedClaimsCountTBNorma;
	}

	/**
	 * @return ���������� �������� ������� �� ����� (���).
	 */
	public Long getOpenedClaimsCountALF()
	{
		return openedClaimsCountALF;
	}

	/**
	 * @param openedClaimsCountALF ���������� �������� ������� �� ����� (���).
	 */
	public void setOpenedClaimsCountALF(Long openedClaimsCountALF)
	{
		this.openedClaimsCountALF = openedClaimsCountALF;
	}

	/**
	 * @return �����: ���������� �������� ������� �� ����� (���).
	 */
	public Double getOpenedClaimsCountALFNorma()
	{
		return openedClaimsCountALFNorma;
	}

	/**
	 * @param openedClaimsCountALFNorma �����: ���������� �������� ������� �� ����� (���).
	 */
	public void setOpenedClaimsCountALFNorma(Double openedClaimsCountALFNorma)
	{
		this.openedClaimsCountALFNorma = openedClaimsCountALFNorma;
	}

	/**
	 * @return ���������� �������� ������� �� ����� � �� (���).
	 */
	public Map<String, Long> getOpenedClaimsCountTBALF()
	{
		return openedClaimsCountTBALF;
	}

	/**
	 * @param openedClaimsCountTBALF ���������� �������� ������� �� ����� � �� (���).
	 */
	public void setOpenedClaimsCountTBALF(Map<String, Long> openedClaimsCountTBALF)
	{
		this.openedClaimsCountTBALF = openedClaimsCountTBALF;
	}

	/**
	 * @return �����: ���������� �������� ������� �� ����� � �� (���)
	 */
	public Map<String, Double> getOpenedClaimsCountTBALFNorma()
	{
		return openedClaimsCountTBALFNorma;
	}

	/**
	 * @param openedClaimsCountTBALFNorma �����: ���������� �������� ������� �� ����� � �� (���)
	 */
	public void setOpenedClaimsCountTBALFNorma(Map<String, Double> openedClaimsCountTBALFNorma)
	{
		this.openedClaimsCountTBALFNorma = openedClaimsCountTBALFNorma;
	}
}
