package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;

/**
 * ¬алидатор проверки создани€ P2P автоперевода частному лицу, использу€ свою карту зачислени€
 *
 * @author khudyakov
 * @ created 16.11.14
 * @ $Author$
 * @ $Revision$
 */
public class TransferToOwnCardP2PValidator extends FieldValidatorBase
{
	public static final String WARNING_MESSAGE_PARAMETER_NAME      = "¬ы пытаетесь создать автоплатеж дл€ перевода средств между своими картами. ѕожалуйста, вернитесь к созданию автоплатежа и выберите вкладку ЂЌа свою картуї.";

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();


	public boolean validate(String value) throws TemporalDocumentException
	{
		return validate(value, PersonHelper.getContextPerson().getLogin());
	}

	/**
	 * ѕоверить карту на приналдежность клиенту
	 * @param value номер карты
	 * @param login догин клиента
	 * @return true - не принадлежит
	 * @throws TemporalDocumentException
	 */
	public boolean validate(String value, Login login) throws TemporalDocumentException
	{
		try
		{
			CardLink cardLink = externalResourceService.findLinkByNumber(login, ResourceType.CARD, value);
			if (cardLink != null)
			{
				setMessage(WARNING_MESSAGE_PARAMETER_NAME);
				return false;
			}
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
		return true;
	}
}
