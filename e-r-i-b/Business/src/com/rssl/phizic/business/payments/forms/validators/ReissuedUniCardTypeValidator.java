package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.forms.types.UserResourceParser;
import com.rssl.phizic.business.resources.ReissuedCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import java.text.ParseException;


/**
 * ��������� ��� �������� ��� ����������� ����.
 * �� ����������� ����� �� ���������� ����������:
 * 1)	���������� ��������� ���� (CardType= CC)
 * 2)	���������� Instant Issue ����(Momentum). (UNICardType=7 ���  UNICardType=8 � UNIAcctType= F )
 * 3)	���������� ���� ���. (UNICardType=27)
 * 4)	���������� ���� ���100 (UNICardType=14 ��� UNICardType=15 ��� UNICardType=17 ��� UNICardType=18)
 *
 * @author basharin
 * @ created 27.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class ReissuedUniCardTypeValidator extends FieldValidatorBase
{
	private static final UserResourceParser resourceParser = new UserResourceParser();

	public ReissuedUniCardTypeValidator()
	{
		setMessage("���������� ������� ���� ���� ����� �������� ������ ����������.");
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		CardLink link = parseValue(value);
		return new ReissuedCardFilter().evaluate(link);
	}

	private CardLink parseValue(String value) throws TemporalDocumentException
	{
		ExternalResourceLink link = null;
		try
		{
			link = resourceParser.parse(value);
		}
		catch (ParseException e)
		{
			throw new TemporalDocumentException("������ �������� �������� " + value, e);
		}
		if (link instanceof CardLink)
			return (CardLink) link;
		throw new TemporalDocumentException("������ �������� ������ �� ��������");
	}
}
