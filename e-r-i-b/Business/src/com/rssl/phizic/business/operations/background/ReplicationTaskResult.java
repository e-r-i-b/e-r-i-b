package com.rssl.phizic.business.operations.background;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 21.07.2011
 * @ $Author$
 * @ $Revision$
 * ����� ������� � ���������� ����������. 
 */
public class ReplicationTaskResult extends TaskResultBase
{
	// ���������� ��������� ��������� ����������.
	private Long sourceTotalRecordsCount; // ����� ���������� ������� � ��������� ����������, ����� ������������, ���� ������� ����������(��� ����������)
	private Long sourceProcessedRecordsCount; // ���������� ������������ ������� � ��������� ����������
	private Long sourceFailedRecordsCount; // ���������� �������������(���������) ������� � ��������� ����������

	//���������� ����������
	private Long destinationTotalRecordsCount; // ����� ���������� ������� � ��������� ����������, ����� ������������, ���� ������� ����������(��� ����������)
	private Long destinationInseredRecordsCount; // ���������� ����������� �������
	private Long destinationUpdatedRecordsCount; // ���������� ����������� �������
	private Long destinationDeletedRecordsCount; // ���������� �������� �������

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
	 * @return ���������� ��������������(����������) ������� � ��������� ����������. ����� ������������, ���� ����� ���������� ����������
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
	 * @return ������������������� �����
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
	 * @param report ������������������� �����
	 */
	public void setReport(String report)
	{
		this.report = new StringBuilder(StringHelper.getEmptyIfNull(report));
	}

	/**
	 * �������� ������ � ������������������� �����
	 * @param line ������.
	 */

	public void addToReport(String line)
	{
		report.append(line);
	}

	/**
	 * ������ ���������� � ��������� ����������. ����������� ������� ������ ���������� ������������ �������.
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
	 * ������ ���������� ������� � ��������� ����������..� ����������� ������� ������� ������������ �������.
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
	 * ������ ���������� c �������(���������������) � ��������� ����������.. ����������� ������� �������� ������������ �������.
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
	 * ������ ������� ��������� � �������� ����������.
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
	 * ������ ������� ��������� � ��������� ����������.
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
	 * ������ ������� ������� �� ��������� ����������.
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
