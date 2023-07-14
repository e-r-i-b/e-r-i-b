package com.rssl.phizic.web.common.socialApi.ima;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.ima.GetIMAccountAbstractOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.ima.IMAbstractFilter;
import com.rssl.phizic.web.common.client.ima.IMAccountGeneralAction;
import com.rssl.phizic.web.common.socialApi.common.FilterFormBase;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Выписка по ОМС
 * @author Dorzhinov
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowImaAbstractMobileAction extends IMAccountGeneralAction
{
    protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
    {
        ShowImaAbstractMobileForm form = (ShowImaAbstractMobileForm) frm;
        GetIMAccountAbstractOperation abstractOperation = (GetIMAccountAbstractOperation)operation;

        IMAccountLink imAccountlink = abstractOperation.getEntity();
        form.setImAccountAbstract(getAbstract(abstractOperation, form));

        if (abstractOperation.isUseStoredResource())
            saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) imAccountlink.getImAccount()));

        if (abstractOperation.isBackError())
            saveMessage(currentRequest(), BACK_ERROR_IMACCOUNT_MESSAGE);
    }

    protected IMAbstractFilter getFilter(Map<String, Object> source)
    {
        throw new UnsupportedOperationException();
    }

    //формируем поля фильтрации для валидации
    private MapValuesSource getMapSource(FilterFormBase frm)
    {
        //формируем поля фильтрации для валидации
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put(FilterFormBase.FROM_DATE_NAME, frm.getFrom());
        filter.put(FilterFormBase.TO_DATE_NAME, frm.getTo());
        filter.put(FilterFormBase.COUNT_NAME, frm.getCount());
        return new MapValuesSource(filter);
    }

    private IMAccountAbstract getAbstract(GetIMAccountAbstractOperation operation, ShowImaAbstractMobileForm form) throws BusinessException, BusinessLogicException
    {
        IMAccountLink link = operation.getEntity();
        
        if(!operation.isFullAbstractCreated())
            return operation.getIMAccountAbstractMap(MAX_COUNT).get(link);

        FieldValuesSource valuesSource = getMapSource(form);
        Form filterForm = FilterFormBase.FILTER_DATE_COUNT_FORM;
        FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, filterForm);
        if (processor.process())
        {
            Map<String, Object> result = processor.getResult();
            Date from = (Date) result.get(FilterFormBase.FROM_DATE_NAME);
            Date to = (Date) result.get(FilterFormBase.TO_DATE_NAME);
            Long count = (Long) result.get(FilterFormBase.COUNT_NAME);

            if (from != null && to != null)
            {
                return createAbstractMap(operation, DateHelper.toCalendar(from), DateHelper.toCalendar(to)).get(link);
            }
            else
            {
                return operation.getIMAccountAbstractMap((count == null || count > MAX_COUNT) ? MAX_COUNT : count).get(link);
            }
        }
        else
        {
            saveErrors(currentRequest(), processor.getErrors());
            return operation.getIMAccountAbstractMap(MAX_COUNT).get(link);
        }
    }
}
