package com.rssl.phizic.web.common.mobile.contacts;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.operations.contacts.ContactSyncOperation;
import com.rssl.phizic.operations.contacts.ContactSyncOperationResult;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Синхронизация мобильных контактов
 * @author Dorzhinov
 * @ created 27.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class ContactSyncAction extends OperationalActionBase
{
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ContactSyncOperation operation = createOperation(ContactSyncOperation.class, "ContactSyncService");
		ContactSyncForm frm = (ContactSyncForm) form;

		try
		{
			MobilePlatform platform = mobilePlatformService.findByPlatformId(AuthenticationContext.getContext().getDeviceInfo());
			ContactSyncOperationResult contacts = operation.synchronize(frm.getContacts(), platform != null && platform.isShowSbAttribute());
			frm.setSberContactMap(contacts.getSberbankContacts());
			frm.setNoSberContactMap(contacts.getNoSberbankContacts());
			frm.setIncognitoContactMap(contacts.getIncognitoContacts());
			frm.setUnlimitedContactMap(contacts.getUnlimitedContacts());
			frm.setResult(contacts);
			if (operation.isLimitExceeded())
			{
				saveMessage(request, "Вы превысили количество номеров телефонов на синхронизацию. Обработаны только номера в пределах лимита.");
			}
		}
		catch (BusinessLogicException e)
		{
			saveError(request, e.getMessage());
		}

		return mapping.findForward(FORWARD_START);
	}
}
