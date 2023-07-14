package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.common.types.transmiters.Pair;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

/**
 * @author Moshenko
 * @ created 24.02.2014
 * @ $Author$
 * @ $Revision$
 * ��� ������ ��� ����� ������ ���������� � ��������� �������� �������������� ��������� ����.
 */
public class CardOfferLoadLoggerBean
{
	/**
	 * ��� �����.
	 */
	private String fileName;
	/**
	 * ������ ����� � ������.
	 */
	private long size;
	/**
	 *  ���������� ����������� �����������.
	 */
	private int loadCount;
	/**
	 *	���������� ����������� � �����.
	 */
	private int totalCount;
	/**
	 *	����/����� ������ ��������.
	 */
	private Calendar startTime;
	/**
	 *	����/����� ��������� ��������.
	 */
	private Calendar endTime;
	/**
	 * 	����������������� �������� (� ��).
	 */
	private long loadTime;
	/**
	 *	������ �������� (� ������ ���������� �������� ����� ����� ������� � ��������� � ���� ������).
	 */
	private CardOfferLoadState status;
	/**
	 * 	������������� ����������� � ��������� ������� (������ ������� ������������� ������� ����������� ������� �� ������� �������).
	 */
	private List<Pair<String,String>> errorString;

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public long getSize()
	{
		return size;
	}

	public void setSize(long size)
	{
		this.size = size;
	}

	public int getLoadCount()
	{
		return loadCount;
	}

	public void setLoadCount(int loadCount)
	{
		this.loadCount = loadCount;
	}

	public int getTotalCount()
	{
		return totalCount;
	}

	public void setTotalCount(int totalCount)
	{
		this.totalCount = totalCount;
	}

	public Calendar getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Calendar startTime)
	{
		this.startTime = startTime;
	}

	public Calendar getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Calendar endTime)
	{
		this.endTime = endTime;
	}

	public long getLoadTime()
	{
		return loadTime;
	}

	public void setLoadTime(long loadTime)
	{
		this.loadTime = loadTime;
	}

	public CardOfferLoadState getStatus()
	{
		return status;
	}

	public void setStatus(CardOfferLoadState status)
	{
		this.status = status;
	}

	public List<Pair<String, String>> getErrorString()
	{
		return errorString;
	}

	public void setErrorString(List<Pair<String, String>> errorString)
	{
		this.errorString = errorString;
	}
}
