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
	server->NFun(func, result);
	return S_OK;
}

STDMETHODIMP CServer::GParamString(BSTR name, BSTR* value)
{
	server->GParamString(name,value);
	return S_OK;
}

STDMETHODIMP CServer::SÐaram(BSTR name, BSTR value)
{	
	server->SÐaram(name,value);
	return S_OK;
};



STDMETHODIMP  CServer::Clear(void)
{
	server->Clear();
	return S_OK;
};
