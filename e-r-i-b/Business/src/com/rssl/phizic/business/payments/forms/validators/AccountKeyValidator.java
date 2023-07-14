package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * @author Gainanov
 * @ created 21.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccountKeyValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_BIC = "receiverBIC";
	public static final String FIELD_RECEIVER_ACCOUNT = "receiverAccount";
	private static final String COEFFICIENT = "7137137137137137137100000"; //онякедмхе 5 яхлбнкнб днкфмш ашрэ 0!!!
																		   //ме лемърэ!!! нрбюкхбюеряъ яебепмши!!!
																		   //еякх мюдн онлемърэ мю йюйсч-рн люяйс, опнйнмяскэрхпнбюрэяъ
																		   //я люйеднмяйхл х унанрнбшл!!!!!

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String bic = (String)retrieveFieldValue(FIELD_BIC, values);
		String account = (String)retrieveFieldValue(FIELD_RECEIVER_ACCOUNT, values);
		String relationNumber;
		if(bic== null || account == null || bic.equals("") || account.equals(""))
			return true;
		if(isRKC(bic))
			relationNumber = "0"+bic.substring(4,6);
		else
			relationNumber = bic.substring(6,9);
		String newAccount = account.substring(0,8) + "0" + account.substring(9,account.length());
		int sum = 0;
		for(int i = 0; i < newAccount.length(); i++)
		{
			int d = Integer.parseInt(newAccount.substring(i,i+1))*Integer.parseInt(COEFFICIENT.substring(i,i+1));
			sum+=d%10;
			//sum+=Integer.parseInt(d.toString().substring(d.toString().length()-1,d.toString().length()));
			if(i<3)
				sum+=Integer.parseInt( relationNumber.substring(i,i+1))*Integer.parseInt(COEFFICIENT.substring(i,i+1) )%10;
		}
		sum=sum%10;
		sum*=3;
		if(account.equals(new String(account.substring(0,8) + sum%10 + account.substring(9,account.length()))))
			return true;
		return false;
	}

	private boolean isRKC(String bic)
	{
		String lastSymbols = bic.substring(bic.length()-3,bic.length());
		if(lastSymbols.equals("000") || lastSymbols.equals("001") || lastSymbols.equals("002"))
			return true;
		return false;
	}
}
