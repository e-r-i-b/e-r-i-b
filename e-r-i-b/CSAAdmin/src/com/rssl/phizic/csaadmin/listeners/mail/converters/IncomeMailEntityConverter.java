package com.rssl.phizic.csaadmin.listeners.mail.converters;

import com.rssl.phizic.csaadmin.listeners.GateMessageHelper;
import com.rssl.phizic.csaadmin.listeners.generated.IncomeMailListDataType;
import com.rssl.phizic.gate.mail.IncomeMailListEntity;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

/**
 * @author mihaylov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Конвертер входящего письма, из блочного представления в представление для Axis
 */
public class IncomeMailEntityConverter implements MultiNodeEntityConverter<IncomeMailListDataType,IncomeMailListEntity>
{

	public IncomeMailListDataType convertToGate(IncomeMailListEntity entity)
	{
		IncomeMailListDataType gateMail = new IncomeMailListDataType();
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
		gateMail.setSenderFIO(entity.getSenderFIO());
		gateMail.setSenderId(entity.getSenderId());
		gateMail.setTb(entity.getTb());
		gateMail.setArea(entity.getArea());
		gateMail.setEmployeeFIO(entity.getEmployeeFIO());
		gateMail.setEmployeeUserId(entity.getEmployeeUserId());
		return gateMail;
	}
}
