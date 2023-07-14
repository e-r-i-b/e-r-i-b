// Server.cpp : Implementation of CServer

#include "stdafx.h"
#include "Server.h"
#include "comutil.h"
#include ".\server.h"
#include "comutil.h"
#include <string>
#include <time.h>
#include <iostream>


// CServer


STDMETHODIMP CServer::NFun(LONG func, LONG* result)
{
	Clear();
	if (func == 5004)
	{
		Sleep(4000);
		double random = double(rand()) / double(32767);
		if (random > 0.885)
		{
			num = 0;
			*result = 1;
			return S_OK;
		}
		else
		{
			const char* pStr = card_number.c_str(); 
			WCHAR wBuff[40]; 
			BSTR bsTr; 

			MultiByteToWideChar(CP_ACP, 0, pStr, -1, wBuff, 40); 
			bsTr = SysAllocString(wBuff); 
			SÐaram(SysAllocString( OLESTR("CardName" ) ), SysAllocString( OLESTR( "MyCard" ) ));
			SÐaram(SysAllocString( OLESTR( "CardType" ) ), SysAllocString( OLESTR( "1" ) ));
			SÐaram(SysAllocString( OLESTR( "TrxDate" ) ), SysAllocString( OLESTR( "17.07.2012" ) ));
			SÐaram(SysAllocString( OLESTR( "TrxTime" ) ), SysAllocString( OLESTR( "13:08:44" ) ));
			SÐaram(SysAllocString( OLESTR( "TermNum" ) ), SysAllocString( OLESTR( "123445" ) ));
			SÐaram(SysAllocString( OLESTR( "ClientCard" ) ), bsTr);
			SÐaram(SysAllocString( OLESTR( "ClientExpiryDate" ) ), SysAllocString( OLESTR( "02/14" ) ));

			*result = 0;
			return S_OK;
		}
	}
	*result = 1;
	return S_OK;
}

STDMETHODIMP CServer::GParamString(BSTR name, BSTR* value)
{
	std::string nameForSeach = static_cast<CHAR*>(CW2A(name));
	for (td_Map ::iterator  it=params.begin();it!=params.end();it++)
	{
		if ((*it).first == nameForSeach)
		{
			*value =  (*it).second;
			return S_OK;
		}
	}

	*value = SysAllocString( OLESTR( "" ) );
	return S_OK;
}

STDMETHODIMP CServer::SÐaram(BSTR name, BSTR value)
{	
	std::string nameForSave = static_cast<CHAR*>(CW2A(name));
	params[nameForSave] = value;
	return S_OK;
};



STDMETHODIMP  CServer::Clear(void)
{
	params.clear();
	return S_OK;
};
