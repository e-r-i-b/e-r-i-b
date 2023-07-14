package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gololobov
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class MerchantCategoryCodeReplicaDestinations extends QueryReplicaDestinationBase<MerchantCategoryCode>
{
	//ћапа с MCC-кодами из базы
	private Map<Long,MerchantCategoryCode> mccCodesMap = null;

	public MerchantCategoryCodeReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.finances.getAllMccCodes", true);
	}

	public void initialize(GateFactory factory) throws GateException
	{
		if (this.mccCodesMap == null)
			this.mccCodesMap = this.loadMerchantCagoryCodes();
	}

	/**
	 * «агрузка MCC-кодов и базы
	 * @return - Map<Long, MerchantCategoryCode> мапа с MCC-кодами (key - код MCC, value - MerchantCategoryCode)
	 * @throws GateException
	 */
	private Map<Long, MerchantCategoryCode> loadMerchantCagoryCodes() throws GateException
	{
		//«агружаем все MCC-коды
		try
		{
			List<MerchantCategoryCode> mccCodeslist = HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<List<MerchantCategoryCode>>()
				{
					public List<MerchantCategoryCode> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.finances.getAllMccCodes");
						return query.list();
					}
				}
			);

			Map<Long, MerchantCategoryCode> mccMap = new HashMap<Long, MerchantCategoryCode>(mccCodeslist.size());
			for (MerchantCategoryCode mcc : mccCodeslist)
				mccMap.put(mcc.getCode(), mcc);
			return mccMap;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ƒобавление новог MCC-кода
	 * @param newValue - нова€ запись MCC-кода
	 * @throws GateException
	 */
	public void add(MerchantCategoryCode newValue) throws GateException
	{
		MerchantCategoryCode oldMerchantCategoryCode = this.mccCodesMap.get(newValue.getCode());
		//≈сли в базе уже есть такой MCC-код то только обновл€ть
		if (oldMerchantCategoryCode != null)
			update(oldMerchantCategoryCode, newValue);
		else
			super.add(newValue);
	}

	/**
	 * ”даление старого MCC-кода
	 * @param oldValue - стара€ запись MCC-кода
	 * @throws GateException
	 */
	public void remove(MerchantCategoryCode oldValue) throws GateException
	{
		//не удал€ть MCC-коды
	}
}
