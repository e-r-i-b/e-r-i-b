// Server.h : Declaration of the CServer

#pragma once
#include "resource.h"       // main symbols
#include <map>
#include <string>
#include <iostream>
#include <fstream>

// IServer
[
	object,
	uuid("5537E0C6-5683-49C0-A958-619EFF1301E6"),
	dual,	helpstring("IServer Interface"),
	pointer_default(unique)
]
__interface IServer : IDispatch
{
	[id(1), helpstring("method NFun")] HRESULT NFun(LONG func, [out,retval] LONG* result);
	[id(2), helpstring("method GParamString")] HRESULT GParamString(BSTR name, [out,retval] BSTR* value);
	[id(3), helpstring("method SÐaram")] HRESULT SÐaram(BSTR name, BSTR value);
	[id(4), helpstring("method Clear")] HRESULT Clear(void);
};



// CServer

[
	coclass,
	threading("apartment"),
	vi_progid("SBRFSRV.Server"),
	progid("SBRFSRV.Server.1"),
	version(1.0),
	uuid("2C9823BE-15A3-4867-9852-8E4670F4C325"),
	helpstring("Server Class")
]
class ATL_NO_VTABLE CServer : 
	public IServer
{
public:
	typedef std::map< std::string, BSTR >		td_Map;
	td_Map params;
	int num;
	std::string card_number;
	std::string line;
	std::ifstream file;
	

	CServer()
	{
		std::ifstream file;
		
		SetCurrentDirectory("D:\\");
		file.open("card_number.txt");
		
		if (!file)
		{
			card_number = "3008619728719560";
		}
		else
		{
			file>>card_number;
			file.close();
		}
		num = 0;
	}


	DECLARE_PROTECT_FINAL_CONSTRUCT()

	HRESULT FinalConstruct()
	{
		return S_OK;
	}
	
	void FinalRelease() 
	{
	}

public:

	STDMETHOD(NFun)(LONG func, LONG* result);
	STDMETHOD(GParamString)(BSTR name, BSTR* value);
	STDMETHOD(SÐaram)(BSTR name, BSTR value);
	STDMETHOD(Clear)(void);
};

