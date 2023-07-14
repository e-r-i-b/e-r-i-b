package com.rssl.phizic.web.skins;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.ApplicationType;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.logging.operations.CompositeLogParametersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.skins.ChangeSkinOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author egorova
 * @ created 19.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrenSkinsAction extends OperationalActionBase
{
	public static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		return map;
	}

	private ChangeSkinOperation createChangeCurrentSkinOperation() throws BusinessLogicException, BusinessException
	{
		ChangeSkinOperation operation = createOperation("ChangeCurrentSkins");
		operation.initialize();
		return operation;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CurrenSkinsForm frm = (CurrenSkinsForm) form;
		ChangeSkinOperation operation = createChangeCurrentSkinOperation();
		updateForm(frm, operation);

		return mapping.findForward(FORWARD_START);
	}

	private void updateForm(CurrenSkinsForm frm, ChangeSkinOperation operation) throws BusinessException
	{
		List<Skin> employeeSkins = operation.getEmployeeSkins();
		List<Skin> clientSkins = operation.getClientSkins();

		for (Skin employeeSkin : employeeSkins)
		{
			if (employeeSkin.isAdminDefaultSkin())
			{
				frm.setField("adminSkin", employeeSkin.getId());
				addLogParameters(new BeanLogParemetersReader("“екущий скин приложени€ сотрудника банка", employeeSkin));
				break;
			}
		}

		for (Skin clientSkin : clientSkins)
		{
			if (clientSkin.isClientDefaultSkin() && clientSkin.isCommon())
			{
				frm.setField("clientSkin", clientSkin.getId());
				addLogParameters(new BeanLogParemetersReader("“екущий скин клиентского приложени€", clientSkin));
				break;
			}
		}

		frm.setSystemSkins(employeeSkins);
		frm.setUsersSkins(clientSkins);
		String globalUrl = operation.getGlobalUrl().getUrl();
		frm.setField("globalUrl", globalUrl);

		frm.setChangeAdminSkinAllowed(operation.doesChangeAdminSkinAllowed());

		addLogParameters(new SimpleLogParametersReader(" аталог общих стилей", globalUrl));
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		CurrenSkinsForm frm = (CurrenSkinsForm) form;
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		Form editForm = CurrenSkinsForm.FORM;
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, editForm);

		if (formProcessor.process())
		{
			ChangeSkinOperation operation = createChangeCurrentSkinOperation();

			addLogParameters(new CompositeLogParametersReader(
					new BeanLogParemetersReader("ѕервоначальный скин клиентского приложени€", operation.getCurrentApplicationSkin(ApplicationType.Client)),
					new BeanLogParemetersReader("ѕервоначальный скин приложени€ сотрудника банка", operation.getCurrentApplicationSkin(ApplicationType.Admin)),
					new SimpleLogParametersReader("ѕервоначальный каталог общих стилей", operation.getGlobalUrl())
			));

			operation.changeCurrentSkins(new Long(frm.getField("adminSkin").toString()), new Long(frm.getField("clientSkin").toString()));
			SkinUrlValidator urlValidator = new SkinUrlValidator();
			String newGlobalUrl = "";
			if (urlValidator.validate((String) frm.getField("globalUrl")))
				newGlobalUrl += "http://";
			newGlobalUrl += (String) frm.getField("globalUrl");
			operation.changeGlobalUrl(newGlobalUrl);

			addLogParameters(new CompositeLogParametersReader(
					new BeanLogParemetersReader("“екущий скин клиентского приложени€", operation.getCurrentApplicationSkin(ApplicationType.Client)),
					new BeanLogParemetersReader("“екущий скин приложени€ сотрудника банка", operation.getCurrentApplicationSkin(ApplicationType.Admin)),
					new SimpleLogParametersReader("“екущий каталог общих стилей", newGlobalUrl)
			));
			
			saveMessage(request, getResourceMessage("skinsBundle", "message.all-data-saved"));
			return start(mapping, frm, request, response);
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
			return start(mapping, frm, request, response);
		}
	}
}
