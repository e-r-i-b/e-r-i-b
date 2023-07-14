package com.rssl.phizic.business.documents.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.GroupRiskRank;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

/**
 * Handler запрещает выполнять платеж, если истинны все условия:
 * - клиент работает по light-схеме
 * - перевод вовне (обеспечивается тем, что данный handler подключен только к платежам вовне в state-machine)
 * - не оплата собственного телефона
 * - не оплата низкорисковому поставщику
 * - не оплата выскорисковому поставщику с признаком "Мобильная связь" или "Интернет-кошелек" при наличии соответствующих полей у ПУ.
 * - платеж не по шаблону или платеж по шаблону, но понадобилось подтвердить платеж одноразовым паролем (изменение суммы больше допустимой кратности, изменение ключевых полей и т.д.)
 * @author Dorzhinov
 * @ created 27.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class OutsidePaymentFromTemplateHandler extends BusinessDocumentHandlerBase
{
    public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
    {
        if (!(document instanceof AbstractPaymentDocument))
            throw new DocumentException("Ожидается " + AbstractPaymentDocument.class.getSimpleName());

	    //не light схема
	    if (!MobileApiUtil.isLightScheme())
			return;

	    if (document instanceof JurPayment)
	    {
		    JurPayment jurPayment = (JurPayment) document;

		    //разрешаем оплату собственного телефона
		    if (jurPayment.isSelfMobileNumberPayment())
				return;

		    ServiceProviderBase provider = jurPayment.getServiceProvider();
		    if (provider != null)
		    {
				GroupRisk groupRisk = provider.getGroupRisk();
			    //разрешаем платежи ПУ из низкорисковой группы
				if (groupRisk != null && groupRisk.getRank() == GroupRiskRank.LOW)
					return;
				//разрешаем платежи ПУ из высокорисковой группы если ПУ с бизнес-признаком "Мобильная связь" или "Интернет кошелек"
				// и есть поля с признаками "сотовая связь" или "интернет кошелек"
				else if (groupRisk != null)
				{
					for (FieldDescription fd : provider.getFieldDescriptions())
					{
						if (fd.getBusinessSubType() != null)
							return;
					}
				}
		    }
	    }
	    if (document instanceof RurPayment)
	    {
		    //перевод частному лицу тоже доступен по light-схеме, начиная с 6.00. Но заполнение полей для перевода только из справочника ранее введенных(доверенных получателей).
		    RurPayment rurPayment = (RurPayment) document;
		    if (MobileApiUtil.isMobileApiGE(MobileAPIVersions.V6_00) && RurPayment.PHIZ_RECEIVER_TYPE_VALUE.equals(rurPayment.getReceiverType()) && rurPayment.getDictFieldId() != null)
			    return;
	    }

	    //оплата не по шаблону или по шаблону, но потребовалось подтверждение по otp
        AbstractPaymentDocument payment = (AbstractPaymentDocument) document;
        if (!payment.isByTemplate() || payment.isPaymentFromTemplateNeedConfirm())
            throw new DocumentLogicException("В данной версии системы Вы можете только выполнять операции по шаблону или переводить деньги между своими счетами.");
    }
}
