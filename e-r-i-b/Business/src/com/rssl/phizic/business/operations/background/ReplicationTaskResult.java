package com.rssl.phizic.business.operations.background;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 21.07.2011
 * @ $Author$
 * @ $Revision$
 * класс отчетов о выполениии репликации. 
 */
public class ReplicationTaskResult extends TaskResultBase
{
	// статистика обработки источника репликации.
	private Long sourceTotalRecordsCount; // общее количество записей в источнике репликации, может отсуствовать, если подсчет невозможен(или недопустим)
	private Long sourceProcessedRecordsCount; // количество обработанных записей в источнике репликации
	private Long sourceFailedRecordsCount; // количество необраотанных(ошибочных) записей в источнике репликации

	//статистика репликации
	private Long destinationTotalRecordsCount; // общее количество записей в приемнике репликации, может отсуствовать, если подсчет невозможен(или недопустим)
	private Long destinationInseredRecordsCount; // количество добавленных записей
	private Long destinationUpdatedRecordsCount; // количество обновленных записей
	private Long destinationDeletedRecordsCount; // количество удалнных записей

	private StringBuilder report = new StringBuilder();

	public Long getSourceTotalRecordsCount()
	{
		return sourceTotalRecordsCount;
	}

	public void setSourceTotalRecordsCount(Long sourceTotalRecordsCount)
	{
		this.sourceTotalRecordsCount = sourceTotalRecordsCount;
	}

	public Long getSourceProcessedRecordsCount()
	{
		return sourceProcessedRecordsCount;
	}

	public void setSourceProcessedRecordsCount(Long sourceProcessedRecordsCount)
	{
		this.sourceProcessedRecordsCount = sourceProcessedRecordsCount;
	}

	public Long getSourceFailedRecordsCount()
	{
		return sourceFailedRecordsCount;
	}

	public void setSourceFailedRecordsCount(Long sourceFailedRecordsCount)
	{
		this.sourceFailedRecordsCount = sourceFailedRecordsCount;
	}

	/**
	 * @return количество необработанных(оставшихся) записей в источнике репликации. Может отсуствовать, если общее количество неизвестно
	 */
	public Long getRemainSourceRecordsCount()
	{
		if (sourceTotalRecordsCount == null || sourceProcessedRecordsCount == null)
		{
			return null;
		}
		return sourceTotalRecordsCount - sourceProcessedRecordsCount;
	}

	public Long getDestinationTotalRecordsCount()
	{
		return destinationTotalRecordsCount;
	}

	public void setDestinationTotalRecordsCount(Long destinationTotalRecordsCount)
	{
		this.destinationTotalRecordsCount = destinationTotalRecordsCount;
	}

	public Long getDestinationInseredRecordsCount()
	{
		return destinationInseredRecordsCount;
	}

	public void setDestinationInseredRecordsCount(Long destinationInseredRecordsCount)
	{
		this.destinationInseredRecordsCount = destinationInseredRecordsCount;
	}

	public Long getDestinationUpdatedRecordsCount()
	{
		return destinationUpdatedRecordsCount;
	}

	public void setDestinationUpdatedRecordsCount(Long destinationUpdatedRecordsCount)
	{
		this.destinationUpdatedRecordsCount = destinationUpdatedRecordsCount;
	}

	public Long getDestinationDeletedRecordsCount()
	{
		return destinationDeletedRecordsCount;
	}

	public void setDestinationDeletedRecordsCount(Long destinationDeletedRecordsCount)
	{
		this.destinationDeletedRecordsCount = destinationDeletedRecordsCount;
	}

	/**
	 * @return неструктурированный отчет
	 */
	public String getReport()
	{
		if (report == null)
		{
			return null;
		}
		return report.toString();
	}

	/**
	 * @param report неструктурированный отчет
	 */
	public void setReport(String report)
	{
		this.report = new StringBuilder(StringHelper.getEmptyIfNull(report));
	}

	/**
	 * Добавить запись в неструктурированный отчет
	 * @param line запись.
	 */

	public void addToReport(String line)
	{
		report.append(line);
	}

	/**
	 * Запись обработана в источнике репликации. Увеличивает счетчик общего количества обработанных записей.
	 */
	public void sourceTotalRecordProcessed()
	{
		if (sourceTotalRecordsCount == null)
		{
			sourceTotalRecordsCount = 0L;
		}
		sourceTotalRecordsCount++;
	}

	/**
	 * Запись обработана успешно в источнике репликации..ю Увеличивает счетчик успешно обработанных записей.
	 */
	public void sourceRecordProcessed()
	{
		if (sourceProcessedRecordsCount == null)
		{
			sourceProcessedRecordsCount = 0L;
		}
		sourceProcessedRecordsCount++;
	}

	/**
	 * Запись обработана c ошибкой(проигнорирована) в источнике репликации.. Увеличивает счетчик ошибочно обработанных записей.
	 */
	public void sourceRecordFailed()
	{
		if (sourceFailedRecordsCount == null)
		{
			sourceFailedRecordsCount = 0L;
		}
		sourceFailedRecordsCount++;
	}

	/**
	 * Запись успешно добавлена в приемник репликации.
	 */
	public void destionanionRecordInsered()
	{
		if (destinationInseredRecordsCount == null)
		{
			destinationInseredRecordsCount = 0L;
		}
		destinationInseredRecordsCount++;
	}

	/**
	 * Запись успешно обновлена в приемнике репликации.
	 */
	public void destionanionRecordUpdated()
	{
		if (destinationUpdatedRecordsCount == null)
		{
			destinationUpdatedRecordsCount = 0L;
		}
		destinationUpdatedRecordsCount++;
	}

	/**
	 * Запись успешно удалена из приемника репликации.
	 */
	public void destionanionRecordDeleted()
	{
		if (destinationDeletedRecordsCount == null)
		{
			destinationDeletedRecordsCount = 0L;
		}
		destinationDeletedRecordsCount++;
	}

	public void decreaseDestinationInseredRecords()
	{
		if(destinationInseredRecordsCount == null || destinationInseredRecordsCount == 0L)
			return;

		destinationInseredRecordsCount--;
	}

	public void decreaseDestinationUpdatedRecords()
	{
		if(destinationUpdatedRecordsCount == null || destinationUpdatedRecordsCount == 0L)
			return;

		destinationUpdatedRecordsCount--;
	}

	public void decreaseDestinationDeletedRecords()
	{
		if(destinationDeletedRecordsCount == null || destinationDeletedRecordsCount == 0L)
			return;
		
		destinationDeletedRecordsCount--;
	}
}
