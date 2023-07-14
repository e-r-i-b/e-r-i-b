package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 01.12.2005
 * @ $Author: barinov $
 * @ $Revision: 35594 $
 */

public class ValidatorsTest extends BusinessTestCaseBase
{

	protected void setUp() throws Exception
	{
		super.setUp();
		initializeRsV51Gate();
	}

	public void testUserAccountValidator() throws Exception
	{
		// UserAccount
		UserAccountValidator userAccountValidator = new UserAccountValidator();
		PersonData data = createTestClientContext();
		List<AccountLink> accountLinks = data.getAccounts();
		if( accountLinks.size() > 0 )
		{
			AccountLink accountLink = accountLinks.get(0);
			String accountNumber = accountLink.getNumber();
			assertTrue(userAccountValidator.validate(accountNumber));
		}

		assertFalse(userAccountValidator.validate("40820840500000000003"));
	}

	public void testDateValidator() throws Exception
	{
		// Date
		DateFieldValidator dateValidator=new DateFieldValidator();
		assertTrue(dateValidator.validate("31.12.2005"));
		assertFalse(dateValidator.validate("32.12.2005"));
		assertFalse(dateValidator.validate("1.0.2005"));
		assertFalse(dateValidator.validate("12.2005"));
		assertFalse(dateValidator.validate("qwerty"));
	}

	public void testMoneyValidator() throws Exception
	{
		// Money
		MoneyFieldValidator moneyValidator=new MoneyFieldValidator();

		assertTrue(moneyValidator.validate("0"));
		assertTrue(moneyValidator.validate("1"));
		assertTrue(moneyValidator.validate("12"));
		assertTrue(moneyValidator.validate("12."));
		assertTrue(moneyValidator.validate("12.13"));
		assertTrue(moneyValidator.validate("12,13"));
		assertFalse(moneyValidator.validate("-12.13"));
		assertFalse(moneyValidator.validate("w3.13"));
		assertFalse(moneyValidator.validate("3.13w"));
		assertFalse(moneyValidator.validate("1.999"));
	}

	public void testNumericRangeValidator() throws Exception
	{
		// NumericRange
		NumericRangeValidator numericRangeValidator=new NumericRangeValidator();
		numericRangeValidator
		    .setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0.01");
		numericRangeValidator
		    .setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9.98");

		assertTrue(numericRangeValidator.validate("0.01"));
		assertTrue(numericRangeValidator.validate("9.98"));
		assertTrue(numericRangeValidator.validate("5.00"));
		assertFalse(numericRangeValidator.validate("sdsdsd"));
		assertFalse(numericRangeValidator.validate("0.00"));
		assertFalse(numericRangeValidator.validate("9.99"));
		assertFalse(numericRangeValidator.validate("-9.99"));
		assertFalse(numericRangeValidator.validate("99.99"));
	}

	public void testRegexpValidator() throws Exception
	{
		// Regexp
		RegexpFieldValidator regexpValidator = new RegexpFieldValidator();
		regexpValidator.setParameter(RegexpFieldValidator.PARAMETER_REGEXP,"^\\d{7}$");

		assertTrue(regexpValidator.validate("6744444"));
		assertFalse(regexpValidator.validate(" 6744444"));
		assertFalse(regexpValidator.validate("26744444"));
		assertFalse(regexpValidator.validate("44444"));
		assertFalse(regexpValidator.validate("6 7 "));
		assertFalse(regexpValidator.validate("rr"));

		regexpValidator.setParameter(RegexpFieldValidator.PARAMETER_REGEXP,"[1-9]|1[012]");
		assertTrue(regexpValidator.validate("1"));
		assertTrue(regexpValidator.validate("9"));
		assertTrue(regexpValidator.validate("10"));
		assertTrue(regexpValidator.validate("11"));
		assertTrue(regexpValidator.validate("12"));
		assertFalse(regexpValidator.validate("0"));
		assertFalse(regexpValidator.validate("13"));
		assertFalse(regexpValidator.validate("20"));
		assertFalse(regexpValidator.validate("100"));
		assertFalse(regexpValidator.validate("qw"));

		regexpValidator.setParameter(RegexpFieldValidator.PARAMETER_REGEXP,"[-\\d]");

		assertTrue(regexpValidator.validate("-"));
		assertTrue(regexpValidator.validate("0"));
		assertTrue(regexpValidator.validate("1"));
		assertTrue(regexpValidator.validate("9"));
		assertFalse(regexpValidator.validate("+"));
		assertFalse(regexpValidator.validate("10"));
		assertFalse(regexpValidator.validate("q"));

		regexpValidator.setParameter(RegexpFieldValidator.PARAMETER_REGEXP, "[^<>]*");

		assertFalse(regexpValidator.validate("some < string"));
		assertFalse(regexpValidator.validate("some > string"));
		assertTrue(regexpValidator.validate("some string"));
	}

	public void testRequiredFieldValidator() throws Exception
	{
		// Required
		RequiredFieldValidator requiredValidator=new RequiredFieldValidator();
		assertTrue(requiredValidator.validate("sdfsdf"));
		assertFalse(requiredValidator.validate(""));
		assertFalse(requiredValidator.validate(null));

	}

	public void testDefaultIntegerValidator() throws Exception
	{
		IntegerType    integerType    = new IntegerType();
		FieldValidator fieldValidator = integerType.getDefaultValidators().get(0);

		assertTrue( fieldValidator.validate("") );
		assertTrue( fieldValidator.validate("100"));
		assertTrue( fieldValidator.validate("-100"));
		assertTrue( fieldValidator.validate("0"));
		assertTrue( fieldValidator.validate("-0"));
		assertFalse( fieldValidator.validate("-"));
		assertFalse( fieldValidator.validate("0.0"));
		assertFalse( fieldValidator.validate("-0.0"));
	}

	public void testFormValidators() throws Exception
	{
        PersonData data = createTestClientContext();

        // AccountAmountValidator
	    AccountAmountValidator accountAmountValidator = new AccountAmountValidator();

	    accountAmountValidator.setBinding(AccountAmountValidator.FIELD_ACCOUNT, AccountAmountValidator.FIELD_ACCOUNT);
	    accountAmountValidator.setBinding(AccountAmountValidator.FIELD_AMOUNT, AccountAmountValidator.FIELD_AMOUNT);

	    Map<String, Object> values = new HashMap<String, Object>();

        Account accountWithRestMoreThan = findAccountWithRestMoreThan(data.getAccounts(), 100.0);
		assertNotNull("Не найден счет с положиьтельным балансом у тестового пользователя!", accountWithRestMoreThan);

		values.put(AccountAmountValidator.FIELD_ACCOUNT, accountWithRestMoreThan.getNumber());
	    values.put(AccountAmountValidator.FIELD_AMOUNT,   new BigDecimal("100.00"));

	    assertTrue(accountAmountValidator.validate(values));

	    values.put(AccountAmountValidator.FIELD_AMOUNT, new BigDecimal("99999999999999999999999999999.00"));

	    assertFalse(accountAmountValidator.validate(values));

	    // AccountsNotEqualValidator
	    AccountsNotEqualValidator accountsNotEqualValidator = new AccountsNotEqualValidator();
	    accountsNotEqualValidator.setBinding(AccountsNotEqualValidator.FIELD_FROM_ACCOUNT, AccountsNotEqualValidator.FIELD_FROM_ACCOUNT);
	    accountsNotEqualValidator.setBinding(AccountsNotEqualValidator.FIELD_TO_ACCOUNT, AccountsNotEqualValidator.FIELD_TO_ACCOUNT);

	    values.clear();
	    values.put(AccountsNotEqualValidator.FIELD_FROM_ACCOUNT, "42305810100000100000");
	    values.put(AccountsNotEqualValidator.FIELD_TO_ACCOUNT, "42305810100000100000");
	    assertFalse( accountsNotEqualValidator.validate(values) );

	    values.put(AccountsNotEqualValidator.FIELD_TO_ACCOUNT, "42301810500000100002");
	    assertTrue( accountsNotEqualValidator.validate(values) );

	    values.put(AccountsNotEqualValidator.FIELD_FROM_ACCOUNT, "");
	    assertTrue( accountsNotEqualValidator.validate(values) );

	    values.put(AccountsNotEqualValidator.FIELD_TO_ACCOUNT, "");
	    assertTrue( accountsNotEqualValidator.validate(values) );

	    //CompareValidator
	    CompareValidator compareValidator = new CompareValidator();
	    compareValidator.setBinding(CompareValidator.FIELD_O1, CompareValidator.FIELD_O1);
	    compareValidator.setBinding(CompareValidator.FIELD_O2, CompareValidator.FIELD_O2);

	    values.clear();
	    values.put(CompareValidator.FIELD_O1, 1);
	    values.put(CompareValidator.FIELD_O2, 2);

	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
	    assertTrue(compareValidator.validate(values));

	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    assertTrue(compareValidator.validate(values));

	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.EQUAL);
	    assertFalse(compareValidator.validate(values));

	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.NOT_EQUAL);
	    assertTrue(compareValidator.validate(values));

	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.GREATE);
	    assertFalse(compareValidator.validate(values));

	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.GREATE_EQUAL);
	    assertFalse(compareValidator.validate(values));

	}

    public void testCardNumberChecksumFieldValidator() throws Exception
    {
	    CardNumberChecksumFieldValidator validator = new CardNumberChecksumFieldValidator();
	    String number = "4276838065318778";
	    boolean isValid = validator.validate(number);
	    assertTrue(isValid);

	    number = "5276838065318778";
	    isValid = validator.validate(number);
	    assertFalse(isValid);

	    number = "375123400255623";
	    isValid = validator.validate(number);
	    assertTrue(isValid);

	    number = "375123400255624";
	    isValid = validator.validate(number);
	    assertFalse(isValid);

	    number = "639072129003750";
	    isValid = validator.validate(number);
	    assertTrue(isValid);
    }
}