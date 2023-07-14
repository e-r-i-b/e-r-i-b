package com.rssl.phizic.business.forms;

import com.rssl.common.forms.*;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.DocumentMetadatalessExtendedMetadataLoaderBase;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Moshenko
 * Date: 25.09.2012
 * Time: 12:27:19
 */
public class CardOfferMetadataLoader  extends DocumentMetadatalessExtendedMetadataLoaderBase
{
	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException, BusinessLogicException
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		//В админки перезагрузку методаты не выполняем
		//BUG089292: АРМ. Аудит. Ошибка при просмотре заявки на предодобренную кредитную карту.
		if (applicationInfo.getApplication() == Application.PhizIA)
			return metadata;

		String offrId = fieldSource.getValue("offerId");
		OfferId offerId = OfferId.fromString(offrId);
		String loan = fieldSource.getValue("loan");
		boolean isChangeLimit = "true".equals(fieldSource.getValue("changeLimit"));
		String  cardTypeCode = StringHelper.isEmpty(fieldSource.getValue("cardTypeCode")) ? "" : fieldSource.getValue("cardTypeCode");

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		LoanCardOffer offer = personData.findLoanCardOfferById(offerId);
		if (offer == null)
			return metadata;

		String vsp = (offer.getVsp() == null) ? "" : String.valueOf(offer.getVsp());
		String osb = String.valueOf(offer.getOsb());
		String tb  = String.valueOf(offer.getTerbank());

		if (StringHelper.isEmpty(cardTypeCode) && !StringHelper.isEmpty(loan))
		{
			Long loanId;
			if (isChangeLimit)
				loanId = PersonContext.getPersonDataProvider().getPersonData().findConditionIdByOfferId(offerId);
			else
				loanId = Long.valueOf(loan);
			ConditionProductByOffer conditionProductByOffer = PersonContext.getPersonDataProvider().getPersonData().findConditionProductByOfferIdAndConditionId(offerId ,loanId);
			cardTypeCode = String.valueOf(conditionProductByOffer.getCardTypeCode());
		}

		Form form = metadata.getForm();
		List<Field> fields = form.getFields();
		List<Field> newFields = new ArrayList<Field>(fields);

	    for(Field field : fields)
	    {
		    if ("vspOffer".equals(field.getName()))
		    {
				copyFields(newFields, field, StringHelper.appendLeadingZeros(vsp, 5));
		    }
		    else  if ("osbOffer".equals(field.getName()))
		    {
			    copyFields(newFields, field, StringHelper.appendLeadingZeros(osb, 4));
		    }
		    else  if ("tbOffer".equals(field.getName()))
		    {
				copyFields(newFields, field, StringHelper.removeLeadingZeros(tb));
		    }
		    else if ("cardTypeCode".equals(field.getName()))
		    {
			   copyFields(newFields, field, cardTypeCode);
		    }
	    }
		ExtendedMetadata newMetadata = new ExtendedMetadata();
		newMetadata.setOriginal(metadata);

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(newFields);

		formBuilder.setName(form.getName());
		formBuilder.setDetailedDescription(form.getDetailedDescription());
		formBuilder.setDescription(form.getDescription());
		formBuilder.setConfirmDescription(form.getConfirmDescription());
		formBuilder.setFormValidators(form.getFormValidators());

		newMetadata.addAllDictionaries(metadata.getDictionaries());
		newMetadata.setExtendedForm(formBuilder.build());

		return newMetadata;
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;  
	}

	private void copyFields(List<Field> toFields, Field oldField, Object value)
	{
		toFields.remove(oldField);
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(oldField.getName());
		fieldBuilder.setSource(oldField.getSource());
		fieldBuilder.setDescription(oldField.getDescription());
		List<FieldValidator> validators = oldField.getValidators();
		fieldBuilder.addValidators(validators.toArray(new FieldValidator[validators.size()]));
		fieldBuilder.setValueExpression(new ConstantExpression(value));
		fieldBuilder.setInitalValueExpression(new ConstantExpression(value));
		toFields.add(fieldBuilder.build());
	}
}
