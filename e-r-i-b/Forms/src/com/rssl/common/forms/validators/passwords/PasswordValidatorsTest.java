package com.rssl.common.forms.validators.passwords;

import junit.framework.TestCase;

/**
 * @author Roshka
 * @ created 28.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class PasswordValidatorsTest extends TestCase
{
	private static final String TESTDATA = "sydcgywe yefufgburb fwrbfrbfryfguerghuetgbenvurhbygerghergbuerbgrugvbrvubvrvevub " +
			"byyg12132312314sydcgywe yefufgburb fwrbfrbfryfguerghuetgbenvurhbygerghergbuerbgrugvbrvubvrvevub " +
			"byyg12132312314sydcgywe yefufgburb fwrbfrbfryfguerghuetgbenvurhbygerghergbuerbgrugvbrvubvrvevub byyg12132312314";

	public void testRemoveLineTerminators() throws Exception
	{
		DiffrentSymbolsUsageValidator validator = new DiffrentSymbolsUsageValidator();

		validator.setParameter("minAmountOfDiffrentSymbols","3");

		assertTrue(validator.validate("ababd"));
		assertFalse(validator.validate("abab"));
	}

	public void testSubsequenceLengthValidator() throws Exception
	{
		SubsequenceLengthValidator validator = new SubsequenceLengthValidator();

		String sequence = "123456789";
		validator.setParameter("sequence", sequence);
		validator.setParameter("length", "3");

		assertTrue(validator.validate("dsdd132ssa"));
		assertFalse(validator.validate("AA321GHJH"));
		assertFalse(validator.validate("WW789FJGJGHFGHFJGHFJG"));
	}

	public void testReverting()
	{
		String s = "12345";

		String[] strings = SubsequenceLengthValidator.split(s,3);
		for (String string : strings)
			System.out.println(string);

		SubsequenceLengthValidator validator = new SubsequenceLengthValidator();

		String sequence = "123456789";
		validator.setParameter("sequence", sequence);

		System.out.println(sequence);
		String reverted = SubsequenceLengthValidator.revert(sequence);
		System.out.println(reverted);
		assertTrue(SubsequenceLengthValidator.revert(reverted).equals(sequence));
	}

	public void measureRevertSpeed()
	{
		SubsequenceLengthValidator.revert(TESTDATA);
	}
}