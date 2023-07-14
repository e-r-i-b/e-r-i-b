package com.rssl.phizic.business.loans.dummy;

import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.GateFactory;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 04.09.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanOfficesReplicaSource implements ReplicaSource
{
	public void initialize(GateFactory factory) throws GateException
	{
	}

	public Iterator<LoanOffice> iterator() throws GateException
	{
		List<LoanOffice> offices = new ArrayList<LoanOffice>();

		offices.add(createOffice(new BigDecimal(1), "Головной офис Банка", null, null, null, true));
		offices.add(createOffice(new BigDecimal(2), "ДО Ступинский", null, null, null, false));
		offices.add(createOffice(new BigDecimal(3), "ДО Отделение \"Марксистское\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(4), "ОО \"Воронежский\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(5), "ОО Кондрово", null, null, null, false));
		offices.add(createOffice(new BigDecimal(6), "ККО \"Томский\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(7), "ОО \"Тульский\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(8), "ОО \"Рязанский\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(9), "ОО \"Липецкий\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(500), "Головной банк", null, null, null, false));
		offices.add(createOffice(new BigDecimal(700), "ОО Калужский", null, null, null, false));
		offices.add(createOffice(new BigDecimal(800), "ОО \"Ярославский\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(900), "Челябинский ККО", null, null, null, false));
		offices.add(createOffice(new BigDecimal(999), "ТО по системе \"Экспресс-кредиты\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1001), "ДО \"Тургеневское\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1002), "ДО \"Транснефть-Сухаревка\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1003), "ДО \"Багратионовское-Рубин\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1004), "ДО \"ВВЦ-Нефтемаслозавод\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1005), "ДО \"Конти\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1006), "ДО \"Молодежное-МАТИ\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1007), "ДО \"Нагорное-Галант\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1008), "ДО \"Подмосковное\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1009), "ДО \"Ступино\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1010), "ДО \"Таганское\"", null, null, null, false));
		offices.add(createOffice(new BigDecimal(1011), "ДО \"Мичуринское\"", null, null, null, false));

		return offices.iterator();
	}

    public void close()
    {}

	private LoanOffice createOffice(BigDecimal key, String name, String address, String telephone, String info, boolean main)
	{
		LoanOffice office = new LoanOffice();
		office.setSynchKey(key);
		office.setName(name);
		if (address != null) office.setAddress(address);
		if (telephone != null) office.setTelephone(telephone);
		if (info != null) office.setInfo(info);
		office.setMain(main);

		return office;
	}
}
