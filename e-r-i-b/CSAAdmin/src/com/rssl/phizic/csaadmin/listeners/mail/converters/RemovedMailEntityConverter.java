package com.rssl.phizic.csaadmin.listeners.mail.converters;

import com.rssl.phizic.csaadmin.listeners.GateMessageHelper;
import com.rssl.phizic.csaadmin.listeners.generated.RemovedMailListDataType;
import com.rssl.phizic.gate.mail.RemovedMailListEntity;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

/**
 * @author mihaylov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 *  онвертер удаленного письма, из блочного представлени€ в представление дл€ Axis
 */
public class RemovedMailEntityConverter implements MultiNodeEntityConverter<RemovedMailListDataType,RemovedMailListEntity>
{

	public RemovedMailListDataType convertToGate(RemovedMailListEntity entity)
	{
		RemovedMailListDataType gateMail = new RemovedMailListDataType();
		gateMail.setId(entity.getId());
		gateMail.setNodeId(entity.getNodeId());
		gateMail.setNumber(entity.getNumber());
		gateMail.setSubject(GateMessageHelper.encodeData(entity.getSubject()));
		gateMail.setState(entity.getState());
		gateMail.setStateDescription(entity.getStateDescription());
		gateMail.setType(entity.getType());
		gateMail.setTypeDescription(entity.getTypeDescription());
		gateMail.setCreationDate(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(entity.getCreationDate()));
		gateMail.setResponseMethod(entity.getResponseMethod());
		gateMail.setTheme(entity.getTheme());
		gateMail.setTb(entity.getTb());
		gateMail.setArea(entity.getArea());
		gateMail.setEmployeeFIO(entity.getEmployeeFIO());
		gateMail.setEmployeeUserId(entity.getEmployeeUserId());
		gateMail.setRecipientName(entity.getRecipientName());
		gateMail.setDirectionDescription(entity.getDirectionDescription());
		return gateMail;
	}
}
