package com.rssl.phizic.web.client.header;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.skins.ChangePersonalSkinOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncHeaderSkinsAction extends OperationalActionBase
{
	protected static final String FORWARD_ACOUNTS = "Accounts";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected boolean isAjax()
	{
		return true;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncHeaderSkinsForm frm = (AsyncHeaderSkinsForm) form;
		return  updateSkins(frm,mapping,request);
	}

	private ActionForward updateSkins(AsyncHeaderSkinsForm form,ActionMapping mapping,HttpServletRequest request) throws BusinessException, BusinessLogicException
	{
			ChangePersonalSkinOperation operation = createOperation(ChangePersonalSkinOperation.class);
			//id скина с формы прийходит только в случаи нажатия на него пользователя
			//при перерисовки страницы для отображения preview скина id берм уже из header-ов.
			Long previewSkinIdForm = form.getPreviewSkin();
		    if (previewSkinIdForm!=null)
				operation.initialize(previewSkinIdForm);
			else
		        operation.initialize(getSkinIdFromUrl(request.getHeader("Referer")));

			form.setSkins(operation.getSkins());
			form.setCurrentSkin(operation.getCurrentSkin());
			if (previewSkinIdForm != null)
			{
				//значит клиент выбрал скин,
				return new ActionForward(mapping.findForward(FORWARD_ACOUNTS).getPath()+"?previewSkin="+form.getPreviewSkin());
			}
			return mapping.findForward(FORWARD_START);
	}

	private Long getSkinIdFromUrl(String urlStr)
	{
		String[] splitUrl = urlStr.split("previewSkin");
		if (splitUrl.length > 1)
		{
			String params = splitUrl[1];
			int idx = StringUtils.indexOf(params,"=");
			int nextParamIdx = StringUtils.indexOf(params,"&");
			nextParamIdx = nextParamIdx == -1 ? params.length() : nextParamIdx;
			return Long.valueOf(StringUtils.substring(params,idx+1,nextParamIdx));
		}
	 	return null;
	}
}
