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
 * Бин логера для целей записи информации о процедуре загрузки предодобренных кредитных карт.
 */
public class CardOfferLoadLoggerBean
{
	/**
	 * Имя файла.
	 */
	private String fileName;
	/**
	 * Размер файла в байтах.
	 */
	private long size;
	/**
	 *  Количество загруженных предложений.
	 */
	private int loadCount;
	/**
	 *	Количество предложений в файле.
	 */
	private int totalCount;
	/**
	 *	Дата/время начала загрузки.
	 */
	private Calendar startTime;
	/**
	 *	Дата/время окончания загрузки.
	 */
	private Calendar endTime;
	/**
	 * 	Продолжительность загрузки (в мс).
	 */
	private long loadTime;
	/**
	 *	Статус загрузки (в случае неуспешной загрузки всего файла целиком – сообщение о коде ошибки).
	 */
	private CardOfferLoadState status;
	/**
	 * 	Незагруженные предложения с указанием причины (формат записей соответствует формату загружаемых записей по каждому клиенту).
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
