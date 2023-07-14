package com.rssl.phizic.business.dictionaries.synchronization.information;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * »нформаци€ о справочнике в блоке
 */

public class DictionaryInformation implements Serializable
{
	private Long nodeId;
	private Calendar lastUpdateDate;
	private DictionaryState state;
	private String errorDetail;

	/**
	 * @return идентификатор блока
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * задать идентификатор блока
	 * @param nodeId идентификатор блока
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return дата последнего обновлени€
	 */
	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	/**
	 * задать дату последнего обновлени€
	 * @param lastUpdateDate дата последнего обновлени€
	 */
	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return статус справочника
	 */
	public DictionaryState getState()
	{
		return state;
	}

	/**
	 * задать статус справочника
	 * @param state статус справочника
	 */
	public void setState(DictionaryState state)
	{
		this.state = state;
	}

	/**
	 * @return детальное описание ошибки
	 */
	public String getErrorDetail()
	{
		return errorDetail;
	}

	/**
	 * задать детальное описание ошибки
	 * @param errorDetail детальное описание ошибки
	 */
	public void setErrorDetail(String errorDetail)
	{
		this.errorDetail = errorDetail;
	}
}
