package com.rssl.phizic.web.limits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.link.*;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vagin
 * @ created 30.05.2012
 * @ $Author$
 * @ $Revision$
 * Форма для настройки групп риска перевода внешнему получателю.
 */
public class EditLimitPaymentLinkForm extends EditFormBase
{
	public static final String PHIZICAL_EXTERNAL_ACCOUNT_PAYMENT = "phizicalExternalAccountPayment";
	public static final String PHIZICAL_EXTERNAL_CARD_PAYMENT = "phizicalExternalCardPayment";
	public static final String PHIZICAL_INTERNAL_PAYMENT = "phizicalInternalPayment";
	public static final String INTERNAL_SOCIAL_PAYMENT = "internalSocialPayment";
	public static final String CONVERSION_OPERATION = "conversionOperation";
	public static final String JURIDICAL_PAYMENT = "juridicalPayment";

	public static final Map<Class, String> PAYMENT_TO_NAME = new HashMap<Class, String>();

	static
	{
		PAYMENT_TO_NAME.put(PhysicalExternalAccountLimitPaymentLink.class, PHIZICAL_EXTERNAL_ACCOUNT_PAYMENT);
		PAYMENT_TO_NAME.put(PhysicalExternalCardLimitPaymentLink.class, PHIZICAL_EXTERNAL_CARD_PAYMENT);
		PAYMENT_TO_NAME.put(PhysicalInternalLimitPaymentLink.class, PHIZICAL_INTERNAL_PAYMENT);
		PAYMENT_TO_NAME.put(InternalSocialLimitPaymentLink.class, INTERNAL_SOCIAL_PAYMENT);
		PAYMENT_TO_NAME.put(ConversionLimitPaymentLink.class, CONVERSION_OPERATION);
		PAYMENT_TO_NAME.put(JuridicalExternalLimitPaymentLink.class, JURIDICAL_PAYMENT);
	}

	private List<LimitPaymentsLink> limitPaymentsLinks;
	private List<GroupRisk> groupRisks;

	public List<LimitPaymentsLink> getLimitPaymentsLinks()
	{
		return limitPaymentsLinks;
	}

	public void setLimitPaymentsLinks(List<LimitPaymentsLink> limitPaymentsLinks)
	{
		this.limitPaymentsLinks = limitPaymentsLinks;
	}

	public List<GroupRisk> getGroupRisks()
	{
		return groupRisks;
	}

	public void setGroupRisks(List<GroupRisk> groupRisks)
	{
		this.groupRisks = groupRisks;
	}

	public static final Form EDIT_LIMIT_PAYMENT_LINK_FORM = createForm();

	private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

        FieldBuilder fieldBuilder;

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName(PHIZICAL_EXTERNAL_ACCOUNT_PAYMENT);
        fieldBuilder.setDescription("На счет в другом банке");
	    fieldBuilder.setType(LongType.INSTANCE.getName());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName(PHIZICAL_EXTERNAL_CARD_PAYMENT);
        fieldBuilder.setDescription("На карту в другом банке");
	    fieldBuilder.setType(LongType.INSTANCE.getName());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName(PHIZICAL_INTERNAL_PAYMENT);
        fieldBuilder.setDescription("На карту в Сбербанке, На счет в Сбербанке, На карту в Сбербанке по номеру телефона");
	    fieldBuilder.setType(LongType.INSTANCE.getName());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName(INTERNAL_SOCIAL_PAYMENT);
        fieldBuilder.setDescription("Вклад – соц карта");
	    fieldBuilder.setType(LongType.INSTANCE.getName());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName(CONVERSION_OPERATION);
        fieldBuilder.setDescription("Конверсионные операции на свои\\чужие счета");
	    fieldBuilder.setType(LongType.INSTANCE.getName());
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName(JURIDICAL_PAYMENT);
        fieldBuilder.setDescription("Перевод юр. лицу по свободным реквизитам");
	    fieldBuilder.setType(LongType.INSTANCE.getName());
	    formBuilder.addField(fieldBuilder.build());

	    return formBuilder.build();
    }
}
