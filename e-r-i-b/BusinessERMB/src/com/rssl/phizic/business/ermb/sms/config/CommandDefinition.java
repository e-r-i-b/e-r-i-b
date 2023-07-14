package com.rssl.phizic.business.ermb.sms.config;

import com.rssl.phizic.business.ermb.sms.parser.*;

import java.util.Comparator;

/**
 * @author Erkin
 * @ created 27.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ���-�������
 */
public enum CommandDefinition
{
	BALANCE
	{
		public CommandParser createParser()
		{
			return new BalanceCommandParser();
		}
	},

	HISTORY
	{
		public CommandParser createParser()
		{
			return new HistoryCommandParser();
		}
	},

	TRANSFER
	{
		public CommandParser createParser()
		{
			return new TransferCommandParser();
		}
	},

	PRODUCT_INFO
	{
		public CommandParser createParser()
		{
			return new ProductInfoCommandParser();
		}
	},

	CURRENCY_RATE
	{
		public CommandParser createParser()
		{
			return new CurrencyRateCommandParser();
		}
	},

	TARIFF
	{
		public CommandParser createParser()
		{
			return new TariffCommandParser();
		}
	},

	QUICK_SERVICES
	{
		public CommandParser createParser()
		{
			return new QuickServicesCommandParser();
		}
	},

	CONFIRM
	{
		public CommandParser createParser()
		{
			return new ConfirmCommandParser();
		}
	},

	TEMPLATE
	{
		public CommandParser createParser()
		{
			return new TemplateCommandParser();
		}
	},

	PRODUCT_BLOCK
	{
		public CommandParser createParser()
		{
			return new ProductBlockCommandParser();
		}
	},

	RECHARGE_OWN_PHONE
	{
		public CommandParser createParser()
		{
			return new RechargeOwnPhoneCommandParser();
		}
	},

	RECHARGE_OTHER_PHONE
	{
		public CommandParser createParser()
		{
			return new RechargeOtherPhoneCommandParser();
		}
	},

	SERVICE_PAYMENT
	{
		public CommandParser createParser()
		{
			return new ServicePaymentCommandParser();
		}
	},

	TEMPLATE_PAYMENT
	{
		public CommandParser createParser()
		{
			return new TemplatePaymentCommandParser();
		}
	},

	LOAN_PAYMENT
	{
		public CommandParser createParser()
		{
			return new LoanPaymentCommandParser();
		}
	},

	SBER_THANKS
	{
		public CommandParser createParser()
		{
			return new SberThanksCommandParser();
		}
	},

	CREATE_AUTO_PAYMENT
	{
		public CommandParser createParser()
		{
			return new CreateAutoPaymentCommandParser();
		}
	},

	REFUSE_AUTO_PAYMENT
	{
		public CommandParser createParser()
		{
			return new RefuseAutoPaymentCommandParser();
		}
	},

	CARD_ISSUE
	{
		public CommandParser createParser()
		{
			return new CardIssueCommandParser();
		}
	},

	;

	///////////////////////////////////////////////////////////////////////////

	private boolean enabled;

	private Dictionary keywords;

	private int parseOrder;

	/**
	 * @return ����������� ���-�������
	 */
	public String getCodename()
	{
		return name();
	}

	/**
	 * @return true = ������� �������� ��� �������������
	 */
	public boolean isEnabled()
	{
		return enabled;
	}

	void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	/**
	 * @return �������� ����� (������)
	 */
	public Dictionary getKeywords()
	{
		return keywords;
	}

	void setKeywords(Dictionary keywords)
	{
		this.keywords = keywords;
	}

	/**
	 * @return ��������� ������� ���-������� (��� ������ ��������, ��� ���� ���������)
	 */
	public int getParseOrder()
	{
		return parseOrder;
	}

	void setParseOrder(int parseOrder)
	{
		this.parseOrder = parseOrder;
	}

	/**
	 * @return ������� ������� (never null)
	 */
	public abstract CommandParser createParser();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ��������� �� � ������������
	 * @param codename - �����������
	 * @return ��������� ���-������� ��� null, ���� ������ �� �������
	 */
	public static CommandDefinition fromCodename(String codename)
	{
		for (CommandDefinition definition : values()) {
			if (codename.equalsIgnoreCase(definition.getCodename()))
				return definition;
		}

		return null;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ��� ���������� ��������� � ������� �������� ����������
	 */
	public static final Comparator<CommandDefinition> PARSE_ORDER_COMPARATOR = new Comparator<CommandDefinition>()
	{
		public int compare(CommandDefinition c1, CommandDefinition c2)
		{
			return c1.getParseOrder() - c2.getParseOrder();
		}
	};
}
