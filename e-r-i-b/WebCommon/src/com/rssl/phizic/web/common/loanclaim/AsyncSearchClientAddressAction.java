package com.rssl.phizic.web.common.loanclaim;

import com.rssl.phizic.business.*;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.operations.loanclaim.*;
import com.rssl.phizic.web.common.asyncsearch.*;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.*;

/**
 * ������������ ������ �� ��������� ������ ��� ������ ������ �������� ���������� ������������
 *
 * @author Balovtsev
 * @since 27.05.2014
 */
public class AsyncSearchClientAddressAction extends AsyncSearchActionBase
{
    @Override
    protected AsyncSearchClientAddressOperation createSearchOperation() throws BusinessException
    {
	    Application currentApplication = ApplicationInfo.getCurrentApplication();

	    if (currentApplication == Application.PhizIA)
	    {
		    return createOperation(AsyncSearchClientAddressOperation.class, "LoanClaimEmployeeService");
	    }
	    return createOperation(AsyncSearchClientAddressOperation.class, "ExtendedLoanClaim");
    }

    @Override
    protected Map<String, Object> getQueryParametersMap(AsyncSearchForm frm) throws BusinessException
    {
        Map<String, Object> values = new HashMap<String, Object>();
        for (String key : frm.getFields().keySet())
        {
            values.put(key, getValue(key, frm.getField(key)));
        }

        ClientAddressRequestType requestType = ClientAddressRequestType.getTypeByValue(frm.getPageType());
        if (requestType == ClientAddressRequestType.AREA     ||
            requestType == ClientAddressRequestType.CITY     ||
            requestType == ClientAddressRequestType.LOCALITY ||
            requestType == ClientAddressRequestType.STREET)
        {
            if (!values.containsKey(AsyncSearchClientAddressForm.FIELD_REGION))
            {
                values.put(AsyncSearchClientAddressForm.FIELD_REGION, null);
            }

            if (requestType == ClientAddressRequestType.AREA)
            {
                if (!values.containsKey(AsyncSearchClientAddressForm.FIELD_TYPEOFAREA))
                {
                    values.put(AsyncSearchClientAddressForm.FIELD_TYPEOFAREA, null);
                }

                return values;
            }
        }

        if (requestType == ClientAddressRequestType.CITY     ||
            requestType == ClientAddressRequestType.LOCALITY ||
            requestType == ClientAddressRequestType.STREET)
        {
	        /*
	         ���� ������������ �� ������ �����, �� � �������� ������� areaIsNull ����������� null,
	         ����� 1 (���� ��������� �������� ���������� � ��������� area)
	         */
            if (!values.containsKey(AsyncSearchClientAddressForm.FIELD_AREA))
            {
	            values.put(AsyncSearchClientAddressForm.FIELD_AREA + "IsNull", null);
                values.put(AsyncSearchClientAddressForm.FIELD_AREA, Collections.singletonList(""));
            }
	        else
	            values.put(AsyncSearchClientAddressForm.FIELD_AREA + "IsNull", 1);

            if (requestType == ClientAddressRequestType.CITY)
            {
                if (!values.containsKey(AsyncSearchClientAddressForm.FIELD_TYPEOFCITY))
                {
                    values.put(AsyncSearchClientAddressForm.FIELD_TYPEOFCITY, null);
                }

                return values;
            }
        }

        if (requestType == ClientAddressRequestType.LOCALITY ||
            requestType == ClientAddressRequestType.STREET)
        {
	        /*
	         ���� ������������ �� ������ �����, �� � �������� ������� cityIsNull ����������� null,
	         ����� 1 (���� ��������� ������� ���������� � ��������� city)
	         */
            if (!values.containsKey(AsyncSearchClientAddressForm.FIELD_CITY))
            {
	            values.put(AsyncSearchClientAddressForm.FIELD_CITY + "IsNull", null);
	            values.put(AsyncSearchClientAddressForm.FIELD_CITY, Collections.singletonList(""));
            }
	        else
	            values.put(AsyncSearchClientAddressForm.FIELD_CITY + "IsNull", 1);

            if (requestType == ClientAddressRequestType.LOCALITY)
            {
                if (!values.containsKey(AsyncSearchClientAddressForm.FIELD_TYPEOFLOCALITY))
                {
                    values.put(AsyncSearchClientAddressForm.FIELD_TYPEOFLOCALITY, null);
                }

                return values;
            }
        }

        if (requestType == ClientAddressRequestType.STREET)
        {
	        /*
	         ���� ������������ �� ������ ���������� �����, �� � �������� ������� settlementIsNull ����������� null,
	         ����� 1 (���� ��������� ���������� ������� ���������� � ��������� settlement)
	         */
            if (!values.containsKey(AsyncSearchClientAddressForm.FIELD_SETTLEMENT))
            {
	            values.put(AsyncSearchClientAddressForm.FIELD_SETTLEMENT + "IsNull", null);
	            values.put(AsyncSearchClientAddressForm.FIELD_SETTLEMENT, Collections.singletonList(""));
            }
	        else
	            values.put(AsyncSearchClientAddressForm.FIELD_SETTLEMENT + "IsNull", 1);

            if (!values.containsKey(AsyncSearchClientAddressForm.FIELD_TYPEOFSTREET))
            {
                values.put(AsyncSearchClientAddressForm.FIELD_TYPEOFSTREET, null);
            }
        }

        return values;
    }

	private Object getValue(String key, Object value)
	{
		/*
		� ������ ������ ������, ������... ���������� �������� ���������� ���� ���
		��� ��� ������ � ����� ���������� ��� ����, ������� ���������� �������� � ������, ����������� ��������
		��� ���������� ������� �� �����: �����, �����, ���������� ����� ������ �� ������� ����� ����������� � ���������
		 */
		if ("area".equals(key) || "city".equals(key) || "settlement".equals(key))
		{
			String[] codes = StringUtils.split((String) value, ',');
			List<BigInteger> numCodes = new ArrayList<BigInteger>(codes.length);
			for (String code : codes)
			{
				numCodes.add(new BigInteger(code));
			}
			return numCodes;
		}
		return value;
	}

	@Override
    protected List<String> search(AsyncSearchForm frm) throws BusinessException
    {
        ClientAddressRequestType requestType = ClientAddressRequestType.getTypeByValue(frm.getPageType());

        if (requestType == ClientAddressRequestType.UNKNOWN)
        {
            return null;
        }
        else
        {
            AsyncSearchClientAddressOperation operation = createSearchOperation();
            operation.setQueryName(requestType.name());

            return operation.search(getQueryParametersMap(frm));
        }
    }
}
