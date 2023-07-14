package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.config.MobilePlatformEditOperation;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Ќастройки socialAPI в разрезе платформ: редактирование
 * @author sergunin
 * @ created 21.08.14
 * @ $Author$
 * @ $Revision$
 */

public class SocialPlatformConfigEditAction extends MobilePlatformConfigEditAction
{
    /**
     * —оздать и проинициализировать операцию (операци€ редактировани€).
     * @param frm форма
     * @return созданна€ операци€.
     */
    @Override protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
    {
        SocialPlatformConfigEditForm form = (SocialPlatformConfigEditForm) frm;
        MobilePlatformEditOperation operation = createOperation(MobilePlatformEditOperation.class);
        return initializeOperation(form, operation);
    }

    /**
     * ¬ернуть форму редактировани€.
     * ¬ большинстве случаев(до исправлени€ ENH00319) будет возвращатьс€ frm.EDIT_FORM
     * @param frm struts-форма
     * @return форма редактировани€
     */
    @Override protected Form getEditForm(EditFormBase frm)
    {
        return SocialPlatformConfigEditForm.EDIT_FORM;
    }
}
