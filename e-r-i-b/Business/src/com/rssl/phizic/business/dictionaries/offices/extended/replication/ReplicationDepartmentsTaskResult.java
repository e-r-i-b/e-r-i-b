package com.rssl.phizic.business.dictionaries.offices.extended.replication;

import static com.rssl.phizic.business.dictionaries.offices.extended.replication.Constants.*;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.operations.background.ReplicationTaskResult;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author niculichev
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 */
public class ReplicationDepartmentsTaskResult extends ReplicationTaskResult
{
	private static final String UPDATE_TEMPLATE = "%s %s %s    %s";
	private static final String SAVE_TEMPLATE = "%s %s %s    %s";
	private static final String SAVE_DECENTR_OSB_TEMPLATE = "%s, %s (%s)- ���������� ��� � ��������� ��������������������";
	private static final String SAVE_DECENTR_VSP_TEMPLATE = "%s, %s, %s  (%s) - ���������� ��� � ��������� ��������������������";
	private static final String ERROR_FORMAT_TEMPLATE = "%s, %s, %s (%s) - ������������ ������ ��������";
	private static final String ERROR_PARENT_TEMPLATE = "%s, %s, %s (%s) - ������������ ������ �������� ������������� �������������";
	private static final String UNACCESS_REPLICATE_SOURCE = "%s, %s, %s - �������� ���������� ����������";

	// ������������ ������ ��������
	private StringBuilder errorFormatReport = new StringBuilder();
	// ������ ���� ���������� ��������
	private StringBuilder errorParentReport = new StringBuilder();
	// ���������� ���(���) ��� ������������� � ��������� "������������������"
	private StringBuilder destinationInseredDecentrRecordsReport = new StringBuilder();
	// ���������� �������
	private StringBuilder destinationUpdatedRecordsReport = new StringBuilder();
	// ����������� ������
	private StringBuilder destinationInseredRecordsReport = new StringBuilder();

	/**
	 * �������� ������ �� ���������� �������������
	 * @param code ��� �������������
	 */
	public void addToDestinationUpdatedReport(Code code, String name)
	{
		ExtendedCodeImpl extCode = new ExtendedCodeImpl(code);
		destinationUpdatedRecordsReport
				.append(String.format(UPDATE_TEMPLATE, extCode.getRegion(), StringHelper.getEmptyIfNull(extCode.getBranch()),  StringHelper.getEmptyIfNull(extCode.getOffice()), name))
				.append(DELIMITER);

		destionanionRecordUpdated();
	}

	/**
	 * �������� ������ � ���������� �������������
	 * @param code ��� �������������
	 */
	public void addToDestinationInseredReport(Code code, String name)
	{
		ExtendedCodeImpl extCode = new ExtendedCodeImpl(code);
		destinationInseredRecordsReport
				.append(String.format(SAVE_TEMPLATE, extCode.getRegion(), StringHelper.getEmptyIfNull(extCode.getBranch()), StringHelper.getEmptyIfNull(extCode.getOffice()), name))
				.append(DELIMITER);

		destionanionRecordInsered();
	}

	/**
	 * ���������� ������ "������������ ������ ��������"
	 * @param code ��� �������������
	 */
	public void addToErrorFormatReport(Code code, String name)
	{
		ExtendedCodeImpl extCode = new ExtendedCodeImpl(code);
		errorFormatReport
				.append(String.format(ERROR_FORMAT_TEMPLATE, extCode.getRegion(), StringHelper.getEmptyIfNull(extCode.getBranch()), StringHelper.getEmptyIfNull(extCode.getOffice()), name))
				.append(DELIMITER);

		sourceRecordFailed();
	}

	/**
	 * ���������� ������ "���������� ���(���) ��� ������������� � ��������� "������������������""
	 * @param code ��� �������������
	 */
	public void addToDestinationInseredDecentrReport(Code code, String name)
	{
		ExtendedCodeImpl extCode = new ExtendedCodeImpl(code);
		String message = StringHelper.isEmpty(extCode.getOffice()) ?
				String.format(SAVE_DECENTR_OSB_TEMPLATE, extCode.getRegion(),  StringHelper.getEmptyIfNull(extCode.getBranch()), name) :
				String.format(SAVE_DECENTR_VSP_TEMPLATE, extCode.getRegion(),  StringHelper.getEmptyIfNull(extCode.getBranch()),  StringHelper.getEmptyIfNull(extCode.getOffice()), name);

		destinationInseredDecentrRecordsReport.append(message).append(DELIMITER);
		sourceRecordFailed();
	}

	/**
	 * ���������� ������ "������������ ������ �������� ������������� �������������"
	 * @param code ��� �������������
	 */
	public void addToErrorParentReport(Code code, String name)
	{
		ExtendedCodeImpl extCode = new ExtendedCodeImpl(code);
		errorParentReport
				.append(String.format(ERROR_PARENT_TEMPLATE, extCode.getRegion(),  StringHelper.getEmptyIfNull(extCode.getBranch()),  StringHelper.getEmptyIfNull(extCode.getOffice()), name))
				.append(DELIMITER);

		sourceRecordFailed();
	}

	/**
	 * @return ���������������� ����� ������ ������� ������
	 */
	public String getErrorFormatReport()
	{
		if (errorFormatReport == null)
			return null;

		return errorFormatReport.toString();
	}

	public void setErrorFormatReport(String report)
	{
		errorFormatReport = new StringBuilder(StringHelper.getEmptyIfNull(report));
	}

	/**
	 * @return ���������������� ����� ����������� �������
	 */
	public String getDestinationInseredRecordsReport()
	{
		if (destinationInseredRecordsReport == null)
			return null;

		return destinationInseredRecordsReport.toString();
	}

	public void setDestinationInseredRecordsReport(String report)
	{
		destinationInseredRecordsReport = new StringBuilder(StringHelper.getEmptyIfNull(report));
	}

	/**
	 * @return ���������������� ����� ����������� �������
	 */
	public String getDestinationUpdatedRecordsReport()
	{
		if (destinationUpdatedRecordsReport == null)
			return null;

		return destinationUpdatedRecordsReport.toString();
	}

	public void setDestinationUpdatedRecordsReport(String report)
	{
		destinationUpdatedRecordsReport = new StringBuilder(StringHelper.getEmptyIfNull(report));
	}

	/**
	 * @return ���������������� ����� ���������� ������� � ��������� "��������������������"
	 */
	public String getDestinationInseredDecentrRecordsReport()
	{
		if (destinationInseredDecentrRecordsReport == null)
			return null;

		return destinationInseredDecentrRecordsReport.toString();
	}

	public void setDestinationInseredDecentrRecordsReport(String report)
	{
		destinationInseredDecentrRecordsReport = new StringBuilder(StringHelper.getEmptyIfNull(report));
	}

	/**
	 * @return ���������������� ����� ��������� � ��������� ������������� �������������
	 */
	public String getErrorParentReport()
	{
		if (errorParentReport == null)
			return null;

		return errorParentReport.toString();
	}

	public void setErrorParentReport(String report)
	{
		errorParentReport = new StringBuilder(StringHelper.getEmptyIfNull(report));
	}

	/**
	 * �������� ��������� � ����� � ������������� ��������� ����������
	 * @param code ��� �������������, ��� �������� ���������� ��������
	 */
	public void addUnAccessReplicaSourceMessage(Code code)
	{
		ExtendedCodeImpl extendedCode = new ExtendedCodeImpl(code);
		String message = String.format(UNACCESS_REPLICATE_SOURCE, extendedCode.getRegion(),  StringHelper.getEmptyIfNull(extendedCode.getBranch()),  StringHelper.getEmptyIfNull(extendedCode.getOffice()));

		addToReport(message);
	}

	/**
	 * ���������� ������ � ����� � �������� ������
	 * � ����������� �������� ��������� �������
	 * @param line ������.
	 */
	public void addToReport(String line)
	{
		super.addToReport(line + DELIMITER);
		sourceRecordFailed();
	}
}
