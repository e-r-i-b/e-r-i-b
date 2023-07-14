// SBRF.cpp : Implementation of DLL Exports.
//
// Note: COM+ 1.0 Information:
//      Please remember to run Microsoft Transaction Explorer to install the component(s).
//      Registration is not done by default. 

#include "stdafx.h"
#include "resource.h"
#include "compreg.h"

// The module attribute causes DllMain, DllRegisterServer and DllUnregisterServer to be automatically implemented for you
[ module(dll, uuid = "{EA8C9799-D0AF-4FD1-92E7-EC4CACD1C535}", 
		 name = "SBRF", 
		 helpstring = "SBRF 1.0 Type Library",
		 resource_name = "IDR_SBRF", 
		 custom = { "a817e7a1-43fa-11d0-9e44-00aa00b6770a", "{D9EB58F1-AFDF-45F5-886D-4528B692400F}"}) ]
class CSBRFModule
{
public:
// Override CAtlDllModuleT members
};
		 
