package com.rssl.phizgate.common.payments.offline;

import com.rssl.common.forms.doc.DocumentCommand;

import java.util.Map;

/**
 * ���������� �� ������� ���������
 *
 * @author gladishev
 * @ created 07.10.13
 * @ $Author$
 * @ $Revision$
 */
public class OfflineDocumentInfo
{
	private Long id;
	private String externalId;
	private Long blockNumber;
	private String adapterUUID;
	private String stateCode;
	private String stateDescription;
	private String additInfo;
	private String documentType;

	/**
	 * ctor
	 */
	public OfflineDocumentInfo(){}

	/**
	 * ctor
	 * @param externalId - ������� ������������� ���������
	 * @param documentType - ��� ���������
	 * @param blockNumber - ����� �����, � ������� ��� ������ ��������
	 */
	public OfflineDocumentInfo(String externalId, String documentType, Long blockNumber, String adapterUUID)
	{
		this.externalId = externalId;
		this.blockNumber = blockNumber;
		this.documentType = documentType;
		this.adapterUUID = adapterUUID;
	}

	/**
	 * @return ������������� �������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ���������� �������������
	 * @param id - �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������� ������������� ���������
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * ���������� ������� ������������� ���������
	 * @param externalId - ������� �������������
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return ����� �����, � ������� ��� ������ ��������
	 */
	public Long getBlockNumber()
	{
		return blockNumber;
	}

	/**
	 * ���������� ����� �����, � ������� ��� ������ ��������
	 * @param blockNumber - ����� �����
	 */
	public void setBlockNumber(Long blockNumber)
	{
		this.blockNumber = blockNumber;
	}

	/**
	 * @return ������������� ��������
	 */
	public String getAdapterUUID()
	{
		return adapterUUID;
	}

	/**
	 * ���������� ������������� ��������
	 * @param adapterUUID - ������������� ��������
	 */
	public void setAdapterUUID(String adapterUUID)
	{
		this.adapterUUID = adapterUUID;
	}

	/**
	 * @return ������ ���������
	 */
	public String getStateCode()
	{
		return stateCode;
	}

	/**
	 * ���������� ������ ���������
	 * @param stateCode - ������ ���������
	 */
	public void setStateCode(String stateCode)
	{
		this.stateCode = stateCode;
	}

	/**
	 * @return �������� �������
	 */
	public String getStateDescription()
	{
		return stateDescription;
	}

	/**
	 * ���������� �������� �������
	 * @param stateDescription - �������� �������
	 */
	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}

	/**
	 * @return �������������� ����������
	 */
	public String getAdditInfo()
	{
		return additInfo;
	}

	/**
	 * ���������� �������������� ����������
	 * @param additInfo - �������������� ����������
	 */
	public void setAdditInfo(String additInfo)
	{
		this.additInfo = additInfo;
	}

	/**
	 * @return - ��� ���������
	 */
	public String getDocumentType()
	{
		return documentType;
	}

	/**
	 * ���������� ��� ���������
	 * @param documentType - ��� ���������
	 */
	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	/**
	 * ��������� ���� �������
	 * @param command - �������� �������
	 */
	public void fillState(DocumentCommand command)
	{
		this.stateCode = command.getEvent().name();
		Map<String,Object> additionalFields = command.getAdditionalFields();
		if(additionalFields != null)
			this.stateDescription = (String) additionalFields.get(DocumentCommand.ERROR_TEXT);
	}
}
