package com.rssl.phizic.web.common.socialApi.ima;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.ima.GetIMAccountAbstractOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.ima.IMAccountInfoAction;
import com.rssl.phizic.web.common.client.ima.IMAccountInfoForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Детальная информация по ОМС
 * @author Dorzhinov
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowImaInfoMobileAction extends IMAccountInfoAction
{
    private static final String FORWARD_SAVE_NAME = "SaveName";
    
    protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("saveName", "saveIMAccountName");
        return map;
    }

    protected void updateFormData(ViewEntityOperation operation, EditFormBase formBase) throws BusinessException, BusinessLogicException
    {
        ShowImaInfoMobileForm form = (ShowImaInfoMobileForm) formBase;
        GetIMAccountAbstractOperation imaOperation = (GetIMAccountAbstractOperation) operation;

        IMAccountLink imAccountlink = imaOperation.getEntity();
        form.setImAccountLink(imAccountlink);

        if (imaOperation.isUseStoredResource())
            saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) imAccountlink.getImAccount()));
    }

    protected MapValuesSource getSaveImaNameFieldValuesSource(IMAccountInfoForm form)
    {
        ShowImaInfoMobileForm frm = (ShowImaInfoMobileForm) form;
        Map<String,Object> filter = new HashMap<String,Object>();
        filter.put("imAccountName", frm.getImAccountName());
        return new MapValuesSource(filter);
    }

    protected ActionForward forwardSaveImaName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return mapping.findForward(FORWARD_SAVE_NAME);
    }
}
