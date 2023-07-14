package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile;

import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.profile.images.*;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author EgorovaA
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Просмотр личной информации клиента
 */
public class ProfileInfoAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ProfileInfoForm frm = (ProfileInfoForm) form;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		ActivePerson person = personData.getPerson();

		ImageInfo info = UserImageService.get().getImageInfoByLoginId(person.getLogin().getId());
		if (info != null)
			frm.setAvatarPath(info.getPath());

		frm.setSurName(person.getSurName());
		frm.setFirstName(person.getFirstName());
		frm.setPatrName(person.getPatrName());
		frm.setMobilePhone(PhoneNumberUtil.getCutPhoneNumber(person.getMobilePhone()));
		frm.setJobPhone(PhoneNumberUtil.getCutPhoneNumber(person.getJobPhone()));
		frm.setHomePhone(PhoneNumberUtil.getCutPhoneNumber(person.getHomePhone()));
		frm.setEmail(person.getEmail());
		frm.setMainDocuments(PersonHelper.getDocumentForProfile(person.getPersonDocuments()));
		frm.setAdditionalDocuments(BasketHelper.getUserDocuments());

		return mapping.findForward(FORWARD_START);
	}
}
