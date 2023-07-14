package com.rssl.phizic.business.promocodes;

import java.math.BigInteger;

/**
 * @author gladishev
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodesHelper
{
	private static final BigInteger CONST = new BigInteger(Long.toString(58493173L));
	/**
	 * ��������� ���������
	 *
	 * �����-��� ����������� �� ���������� ���������:
	 *  1.	����� ������������� ��������� � ����.
	 *  2.	�������� ������������� �� ��������� 58493173 � �������� ������������� ��������� 1 (��1).
	 *  3.	����� ������� 8 �������� �������������� ���������� 1 (��1), �������� 93847261
	 *  4.	������������ ������ ����������� ������ (��1) = 9 � ������� ������ ����� ���� �������������� ���������� (��1), �������� 9 = 9 � 0
	 *  5.	�������� ������ ����������� ������ (��1) ����� 4 � 5 ������� �������������� ���������� 1 (��1) � �������� ������������� ��������� 2 (��2), ��������, 938407261
	 *  6.	��� �������������� ���������� 2 (��2) ������������ check digit, ��� ��� �������� ��� ������ ����� (��).  ��������, 4.
	 *  7.	�������� �� ����� 5 � 6 ������� �������������� ���������� 2 (��2), �������� 9384047261
	 *  8.	����������  ������������� ����� �������� �����-�����, ������� ������������ ��������.
	 *
	 * @param documentId - id ���������
	 * @return �����-���
	 */
	public static String generatePromoCode(Long documentId)
	{
		//2. �������� ������������� �� ��������� 58493173 � �������� ������������� ��������� 1 (��1).
        BigInteger pr1BigInt = new BigInteger(documentId.toString()).multiply(CONST);

        //3. ����� ������� 8 �������� �������������� ���������� 1 (��1), �������� 93847261
        String pr1Tmp = pr1BigInt.toString();
        String pr1 = pr1Tmp.substring(pr1Tmp.length()-8);

        //4. ������������ ������ ����������� ������ (��1) = 9 � ������� ������ ����� ���� �������������� ���������� (��1), �������� 9 = 9 � 0
        String kr1 = Integer.toString(9 - getDigitsSum(pr1)%10);

        //5. �������� ������ ����������� ������ (��1) ����� 4 � 5 ������� �������������� ���������� 1 (��1) � �������� ������������� ��������� 2 (��2), ��������, 938407261
        StringBuilder pr2Tmp = new StringBuilder(pr1);
        pr2Tmp.insert(4, kr1);
        String pr2 = pr2Tmp.toString();

        //6. ��� �������������� ���������� 2 (��2) ������������ check digit, ��� ��� �������� ��� ������ ����� (��).  ��������, 4.
        int cd = calculateCheckDigit(pr2);

        //7. �������� �� ����� 5 � 6 ������� �������������� ���������� 2 (��2), �������� 9384047261
        StringBuilder result = new StringBuilder(pr2);
        result.insert(5, cd);

        //8. ����������  ������������� ����� �������� �����-�����, ������� ������������ ��������.
        return result.toString();
	}

	private static int getDigitsSum(String numberStr)
    {
        int result = 0;
        for (Character ch : numberStr.toCharArray())
        {
            result += Integer.parseInt(ch.toString());
        }

        return result;
    }

    /**
     * 9     3     8     4     0     x     7     2     6     1     is  pr2

      *2          *2          *2          *2          *2

      18          16          0           14          12
                                (x �������� �����)
     1 + 8 + 3 + 1 + 6 + 4 + 0 +   0  +  1 + 4 + 2 + 1 + 2 + 1 = 34, �.�. result=6 (40-34)
     */
    private static int calculateCheckDigit(String pr2)
    {
        if (pr2==null || pr2.length()!=9)
            throw new RuntimeException("����������� ����� ��2");

        StringBuilder pr2WithZero = new StringBuilder(pr2);
        pr2WithZero.insert(5, '0');
        int sum = 0;
        int i=0;
        int tmpInt;
        for (Character ch : pr2WithZero.toString().toCharArray())
        {
            tmpInt= Integer.parseInt(ch.toString());
            if (i++%2==0)
            {
                //��������� �����, ������� �� �������� 0,2,4...
                tmpInt = tmpInt*2;
                if (tmpInt >= 10)
                    tmpInt -=9; //��������� ����� ����� ���� �� ������ 18, ������� ����� ���� 2-x �������� ����� = ���� ����� - 9
            }

            sum += tmpInt;
        }

        return sum%10 == 0 ? 0 : 10 - sum%10;
    }
}
