package com.rssl.phizgate.common.payments.offline;

import com.rssl.common.forms.doc.DocumentCommand;

import java.util.Map;

/**
 * Информация об оффлайн документе
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
	 * @param externalId - внешний идентификатор документа
	 * @param documentType - тип документа
	 * @param blockNumber - номер блока, в котором был создан документ
	 */
	public OfflineDocumentInfo(String externalId, String documentType, Long blockNumber, String adapterUUID)
	{
		this.externalId = externalId;
		this.blockNumber = blockNumber;
		this.documentType = documentType;
		this.adapterUUID = adapterUUID;
	}

	/**
	 * @return Идентификатор объекта
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Установить идентификатор
	 * @param id - идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return Внешний идентификатор документа
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * Установить внешний идентификатор документа
	 * @param externalId - внешний идентификатор
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return Номер блока, в котором был создан документ
	 */
	public Long getBlockNumber()
	{
		return blockNumber;
	}

	/**
	 * Установить номер блока, в котором был создан документ
	 * @param blockNumber - номер блока
	 */
	public void setBlockNumber(Long blockNumber)
	{
		this.blockNumber = blockNumber;
	}

	/**
	 * @return Идентификатор адаптера
	 */
	public String getAdapterUUID()
	{
		return adapterUUID;
	}

	/**
	 * Установить идентификатор адаптера
	 * @param adapterUUID - идентификатор адаптера
	 */
	public void setAdapterUUID(String adapterUUID)
	{
		this.adapterUUID = adapterUUID;
	}

	/**
	 * @return Статус документа
	 */
	public String getStateCode()
	{
		return stateCode;
	}

	/**
	 * Установить статус документа
	 * @param stateCode - статус документа
	 */
	public void setStateCode(String stateCode)
	{
		this.stateCode = stateCode;
	}

	/**
	 * @return описание статуса
	 */
	public String getStateDescription()
	{
		return stateDescription;
	}

	/**
	 * Установить описание статуса
	 * @param stateDescription - описание статуса
	 */
	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}

	/**
	 * @return Дополнительная информация
	 */
	public String getAdditInfo()
	{
		return additInfo;
	}

	/**
	 * Установить дополнительную информацию
	 * @param additInfo - дополнительная информация
	 */
	public void setAdditInfo(String additInfo)
	{
		this.additInfo = additInfo;
	}

	/**
	 * @return - Тип документа
	 */
	public String getDocumentType()
	{
		return documentType;
	}

	/**
	 * Установить тип документа
	 * @param documentType - тип документа
	 */
	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	/**
	 * Заполнить поля статуса
	 * @param command - описание статуса
	 */
	public void fillState(DocumentCommand command)
	{
		this.stateCode = command.getEvent().name();
		Map<String,Object> additionalFields = command.getAdditionalFields();
		if(additionalFields != null)
			this.stateDescription = (String) additionalFields.get(DocumentCommand.ERROR_TEXT);
	}
}
