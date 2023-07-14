package com.rssl.phizic.job.fpp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Данные для ФПП-файла
 * @author Rtischeva
 * @ created 09.06.14
 * @ $Author$
 * @ $Revision$
 */
class FPPFileData
{
	private String tb;

	private List<FPPFileTransactionData> transactions;

	FPPFileData(String tb)
	{
		this.tb = tb;
		transactions = new ArrayList<FPPFileTransactionData>();
	}

	public String getTb()
	{
		return tb;
	}

	public BigDecimal getRubTransactionAmount()
	{
		BigDecimal totalTransactionAmount = new BigDecimal(0);
		for (FPPFileTransactionData transactionData : transactions)
		{
			totalTransactionAmount = totalTransactionAmount.add(transactionData.getChargeAmount());
		}
		return totalTransactionAmount;
	}

	public List<FPPFileTransactionData> getTransactions()
	{
		return transactions;
	}

	public void addTransaction(FPPFileTransactionData transactionData)
	{
		transactions.add(transactionData);
	}
}
