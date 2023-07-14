package com.rssl.phizic.mdm.web.service.processors;

import com.rssl.phizic.mdm.web.service.generated.RequestData;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���� ��������� � ���������� ���
 */

public enum MessageType
{
	GetStoredMDMId(new GetStoredMDMIdRequestProcessor()),
	GetExternalSystemMDMId(new GetExternalSystemMDMIdRequestProcessor()),
	unknown(null)
	;

	private final ProcessorBase processor;

	private MessageType(ProcessorBase processor)
	{
		this.processor = processor;
	}

	/**
	 * @return ��������� �������
	 */
	public ProcessorBase getProcessor()
	{
		return processor;
	}

	/**
	 * �������� ��� �������
	 * @param requestData ������ �������
	 * @return ��� ���������
	 */
	public static MessageType getType(RequestData requestData)
	{
		if (requestData.getGetStoredMDMIdRq() != null)
			return GetStoredMDMId;
		if (requestData.getGetExternalSystemMDMIdRq() != null)
			return GetExternalSystemMDMId;

		return unknown;
	}
}
