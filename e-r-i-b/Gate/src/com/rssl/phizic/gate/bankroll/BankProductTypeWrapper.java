package com.rssl.phizic.gate.bankroll;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ќбЄртка дл€ соответсви€ между гейтовыми классами и типами банковских продуктов
 * 
 * @author egorova
 * @ created 04.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class BankProductTypeWrapper
{
	private static final Map<Class, BankProductType> bankProductType = new HashMap<Class, BankProductType>();

	static
	{
		bankProductType.put(Account.class, BankProductType.Deposit);
		bankProductType.put(Card.class, BankProductType.Card);
		bankProductType.put(Loan.class, BankProductType.Credit);
		bankProductType.put(IMAccount.class, BankProductType.IMA);
		bankProductType.put(DepoAccount.class, BankProductType.DepoAcc);
		bankProductType.put(LongOffer.class, BankProductType.LongOrd);
	}

	/**
	 * ѕолучение списка всех банковских типов продуктов
	 * @return ћассив всех типов банковских продуктов
	 */
	public static List<Class> getProductTypeList()
	{
		return new ArrayList(bankProductType.keySet());
	}

	/**
	 * ѕолучение списка типов банковских продуктов
	 * @param clazz - классы банковских продуктов, по которым необходимо получить информацию
	 * @return ћассив типов банковских продуктов
	 */
	public static BankProductType[] getBankProductTypes(List<Class> clazz)
	{
		if (clazz == null || clazz.isEmpty())
			return null;
		BankProductType[] bankProducts = new BankProductType[clazz.size()];
		for (int i=0; i<clazz.size(); i++)
		{
			bankProducts[i] = getBankProductType(clazz.get(i));
		}
		return bankProducts;
	}

	/**
	 * ѕолучение типа продукта по классу
	 * @param clazz класс
	 * @return тип продукта
	 */
	public static BankProductType getBankProductType(Class clazz)
	{
		if (clazz == null)
			return null;
		return bankProductType.get(clazz);
	}
}
