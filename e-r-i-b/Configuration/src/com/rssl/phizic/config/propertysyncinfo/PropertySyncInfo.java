package com.rssl.phizic.config.propertysyncinfo;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: tisov
 * Date: 20.10.14
 * Time: 18:21
 * Информация о синхронизации настроек
 */
public class PropertySyncInfo
{
	private Long id;                        //внутренний идентификатор объекта
	private Long nodeId;                    //ид блока, в который проводилась репликация
	private PropertySyncInfoStatus status;  //статус операции репликации
	private Calendar operationDate;             //дата операции
	private String guid;                    //идентификатор операции

	public PropertySyncInfo(Long nodeId, Calendar operationDate, String guid, PropertySyncInfoStatus status)
	{
		this.nodeId = nodeId;
		this.operationDate = operationDate;
		this.guid = guid;
		this.status = status;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	public PropertySyncInfoStatus getStatus()
	{
		return status;
	}

	public void setStatus(PropertySyncInfoStatus status)
	{
		this.status = status;
	}

	public Calendar getOperationDate()
	{
		return operationDate;
	}

	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}
}
