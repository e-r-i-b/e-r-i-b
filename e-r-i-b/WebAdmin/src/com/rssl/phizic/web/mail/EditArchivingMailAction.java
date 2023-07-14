package com.rssl.phizic.web.mail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.mail.EditArchivingMailOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;
import com.rssl.phizic.web.settings.PropertySerializer;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 22.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditArchivingMailAction extends EditPropertiesActionBase<EditArchivingMailOperation>
{
	private static final Map<Class, PropertySerializer> extSerializers;

	static
	{
		extSerializers = new HashMap<Class, PropertySerializer>(serializers);
		extSerializers.put(Date.class, new PropertySerializer()
		{
			public String serialise(Object data)
			{
				return DateHelper.dateToString("%1$tH:%1$tM", (Date) data);
			}
		});
	}

	@Override
	protected EditArchivingMailOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditArchivingMailOperation.class);
	}

	@Override
	protected PropertySerializer getSerializer(Class aClass)
	{
		return extSerializers.get(aClass);
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		super.doSave(operation, mapping, frm);
		String filePath = (String)frm.getField(MailConfig.ARCHIVE_PATH_KEY);
		((EditArchivingMailOperation)operation).checkFilePath(filePath);
		saveMessage(currentRequest(), getResourceMessage("mailBundle", "message.all-data-saved"));
		return mapping.findForward(FORWARD_START);
	}
}
