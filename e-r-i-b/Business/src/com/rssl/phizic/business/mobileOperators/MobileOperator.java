package com.rssl.phizic.business.mobileOperators;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author Mescheryakova
 * @ created 04.10.2011
 * @ $Author$
 * @ $Revision$
 *
 * ��������, ����������� ���������� ��������� � �������, ������������ �� ����������� ��� ���
 *
 */

public class MobileOperator extends DictionaryRecordBase
{
	private Long id;     // �������������
	private Long code;   // ��� ���������, ��������� �� ��� ���
	private String name; // �������� ���. ���������
	private boolean flAuto; // ������� ������ ������������: true - ���������, false - �� ���������
	private Integer balance; // ������� �� ������� ��������, ��� ������� ����������� �� ����� ����� ������ ����������� ����� ��� ��� ����������, ��� ������������ ����� �����������
	private Integer minSumm; // ����������� ����� ����������� (� ������)
	private Integer maxSumm; // ������������ ����� ����������� (� ������)

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getCode()
	{
		return code;
	}

	public void setCode(Long code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Comparable getSynchKey()
	{
		return code;
	}

	/**
	 * @return ������� ������ ������������
	 */
	public boolean isFlAuto()
	{
		return flAuto;
	}

	/**
	 * ������ ������� ������ ������������
	 * @param flAuto - �������
	 */
	public void setFlAuto(boolean flAuto)
	{
		this.flAuto = flAuto;
	}

	/**
	 * @return ������� �� ������� ��������, ��� ������� ����������� �� ����� ����� ������ ����������� ����� ��� ��� ����������
	 */
	public Integer getBalance()
	{
		return balance;
	}

	/**
	 * ������ ������� �� ������� ��������, ��� ������� ����������� �� ����� ����� ������ ����������� ����� ��� ��� ����������
	 * @param balance - �������
	 */
	public void setBalance(Integer balance)
	{
		this.balance = balance;
	}

	/**
	 * @return ����������� ����� �����������
	 */
	public Integer getMinSumm()
	{
		return minSumm;
	}

	/**
	 * ������ ����������� ����� �����������
	 * @param minSumm - �����
	 */
	public void setMinSumm(Integer minSumm)
	{
		this.minSumm = minSumm;
	}

	/**
	 * @return ������������ ����� �����������
	 */
	public Integer getMaxSumm()
	{
		return maxSumm;
	}

	/**
	 * ������ ������������ ����� �����������
	 * @param maxSumm - �����
	 */
	public void setMaxSumm(Integer maxSumm)
	{
		this.maxSumm = maxSumm;
	}
}
