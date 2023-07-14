package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
/**
 * @author tisov
 * @ created 29.05.15
 * @ $Author$
 * @ $Revision$
 * Экшн получения списка карт по номеру телефона через хранимую процедуру из отчётной базы МБК
 */
public class GetCardNumbersByPhoneNumberViaReportDBJDBCAction extends GetCardNumbersByPhoneJDBCActionBase
{

	public GetCardNumbersByPhoneNumberViaReportDBJDBCAction(String phone)
	{
		super(phone);
	}

	@Override
	protected String getProcedureName()
	{
		return "mb_GetCardNumberByPhone";

	}

	@Override
	protected void getCardNumbersFromResultSet(ResultSet rs, Set<String> cardNumbers) throws SQLException, GateException
	{
		if(!rs.next())
			return;

		String cards = rs.getString(1);
		List<String> cardNums = null;

		try
		{
			cardNums = XmlHelper.getSimpleListElementValue(XmlHelper.parse(cards).getDocumentElement(), "CardNumber");
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		for(String cardNum : cardNums)
			cardNumbers.add(cardNum);
	}

}
