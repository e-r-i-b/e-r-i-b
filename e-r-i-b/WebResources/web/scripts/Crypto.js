
var rsCryptoPluginName;
var LOADER_NOT_EXIST = -10;
var CRYPTO_NOT_EXIST = -11;
var ACTION_USER_CANCEL = 1086;

function createRSCrypto()
{
	if(rsCryptoPluginName == null || rsCryptoPluginName == '')
		throw "Не установлен крипто плагин";

	return eval("new " + rsCryptoPluginName  + "Crypto()");
}

function setRSCryptoName(pluginName)
{
	rsCryptoPluginName = pluginName;
}

// ctor
function RSCrypto()
{
}

/*
	Подписать текст
	text - текст для подписи
	cert - сертификат (RSCertificate) которым надо подписать

	возвращает строку содержащую подпись
*/
RSCrypto.prototype.sign = function(text, cert){}

/*
	Поиск сертификата по его номеру
	certId - номер сертификата

	возвращает сертификат
*/
RSCrypto.prototype.getCertificate = function(certId){}

/*
	Создание запроса на сертификат
	fullName  - имя персоны, на которого будет выдан сертификат

	возвращает масссив
		result['request'] - запрос
		result['fileName'] - имя файла запроса
*/
RSCrypto.prototype.CreateCertRequest = function(fullName){}

/*
	инсталяция сертификата
	answer - ответ на запрос
	answerFile - имя файла ответа
*/
RSCrypto.prototype.InstallCertAnswer = function(answer, answerFile){}

/*
	печать сертификата
*/
RSCrypto.prototype.PrintCertificate = function(id){}

/*
	печать запроса на сертификат
*/
RSCrypto.prototype.PrintCertDemand = function(id){}

/*
	просмотр сертификата
*/
RSCrypto.prototype.PreviewCertificate = function(id){}

/*
	просмотр запроса на сертификата
*/
RSCrypto.prototype.PreviewCertDemand = function(id){}

//ctor
function RSCertificate(certId, owner, startDateTime, endDateTime)
{
	this.id           = certId;
	this.owner         = owner;
	this.startDateTime = startDateTime;
	this.endDateTime   = endDateTime;
}

RSCertificate.prototype.toString = function()
{
	return this.id;
}

//ctor
function RSCryptoException(number, message)
{
	this.number = number;
	this.message = message;
}