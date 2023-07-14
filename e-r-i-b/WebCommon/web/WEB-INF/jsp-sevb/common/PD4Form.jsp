<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<body onLoad="chooseDocument();" language="JavaScript">

<tiles:insert definition="paymentCurrent">
	<tiles:put name="mainmenu" value="PD4"/>
	<tiles:put name="submenu" type="string" value="FILLFORM"/>
	<!-- заголовок -->
	<tiles:put name="pageTitle" type="string" value="Печать платежного документа"/>
    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
	<!-- меню -->
	<tiles:put name="menu" type="string">
		 <tiles:insert definition="clientButton" flush="false" operation="PaymentOrderOperation">
		        <tiles:put name="commandTextKey" value="button.Clear"/>
		        <tiles:put name="commandHelpKey" value="button.Clear"/>
		        <tiles:put name="bundle" value="commonBundle"/>
		        <tiles:put name="onclick" value="clearForm(document.forms.item(0))"/>
	     </tiles:insert>
		 <tiles:insert definition="clientButton" flush="false" operation="PaymentOrderOperation">
		        <tiles:put name="commandTextKey" value="button.next"/>
			    <tiles:put name="commandHelpKey" value="button.next"/>
		        <tiles:put name="bundle" value="commonBundle"/>
		        <tiles:put name="onclick" value="generateForm()"/>
	     </tiles:insert>
	</tiles:put>
	<!-- собственно данные -->
	<tiles:put name="data" type="string">
<html:form action="/private/PD4" styleId="PD4Form" onsubmit="return setEmptyAction(event)" >
	<script type="text/javascript" language="JavaScript">
	   var errorsForm="";//буфер сообщений об ошибках
	function clearForm(theForm)
	{
		if (confirm("Вы действительно хотите очистить форму?"))
		{
		  var i, len = theForm.elements.length;
		  var elem,s;

		  for ( i =0; i < len; i++)
		  {
			elem = theForm.elements[i];
			switch ( elem.type )
			{
			  case 'text':
						s = elem.value="";
			}
		  }
		  return true;
		}
		return false;
	}
	//позволяет ввод исключительно чисел
	function enterNumericFld(event, el)
	{
	   if (navigator.appName=="Netscape") return;
	   var key = event.keyCode;
	   var range;
	   var i1,i2;

	   // клавиши требующие стандартной обработки
	   if ( ((key >= KEY_END) && (key <= KEY_DOWN)) || (key==KEY_TAB) || (key==KEY_ENTER)) return;

	   if ((!isDigit(key)) && (key !=KEY_BACKSPACE) &&(key != KEY_DELETE))
	   {
		   cancelEvent(event);
		}
	   return false;
 	}
	//заполнение банковских полей
	var bankOwner=""
	function setBankInfo(bankInfo)
    {
        var bankName = bankInfo["name"];
        if (bankName.length > 50)
        {
            bankName= bankName.substring(0,50);
        }
		if(bankOwner==1)
		{
			setElement('field(receiverBankName)',bankName);
      		setElement('field(receiverBIC)',bankInfo["BIC"]);
	  		setElement('field(receiverCorAccount)',bankInfo["account"]);
		}
		if(bankOwner==0)
		{
			setElement('field(payerBankName)',bankName);
      		setElement('field(payerBIC)',bankInfo["BIC"]);
	  		setElement('field(payerCorAccount)',bankInfo["account"]);
		}
	}
	function fillBankInfo(bankNameField,BICField,num)
	{
		bankOwner = num;
        var params = "";
        if (bankNameField == null) bankNameField = "bankName";
        if (BICField == null)      BICField = "receiverBIC";
        params = addParamToList(params, bankNameField, "fltrName");
        params = addParamToList(params, BICField, "fltrBIC");
        if (params.length > 0) params = "&amp;" + params;
        window.open("${phiz:calculateActionURL(pageContext, '/private/banks.do')}?action=getBankInfo" + params,
			'Banks', "resizable=1,menubar=0,toolbar=0,scrollbars=1");
	}

    function addParamToList(params, name, addName)
    {
    var val = document.getElementById(name).value;
    if (val.length > 0)
    {
        if (params.length > 0) params += "&";
        params += (addName == null ? name : addName);
        params += "=" + trim(val);
    }
    return params;
    }

	//заполнение налогового периода
	function setTaxPeriod(taxInfo)
    {
      setElement('field(taxPeriod1)',taxInfo["taxPeriod"]);
	  setElement('field(taxPeriod2)',taxInfo["month"]);
	}
	function setTaxStatus(taxInfo)
    {
      setElement('field(taxStatus)',taxInfo["taxStatus"]);
    }
	function setTaxType(taxInfo)
    {
      setElement('field(taxType)',taxInfo["taxType"]);
    }
	function setTaxFund(taxInfo)
    {
      setElement('field(taxFundamental)',taxInfo["taxFund"]);
    }
	function displayPeriodList(event,action)
	{
        var height = 570;
		win=window.open("${phiz:calculateActionURL(pageContext, '/private/PD4.do')}?action="+action,"","scrollbars=0,resizable=0,menubar=0,toolbar=0,status=0");
        if(action=='taxstatus')
            height=145;
        if(action=='taxtype')
            height=375;
        if(action=='taxfund')
            height=430;
        if(action=='periodfill')
            height=265;
		win.resizeTo(500,height);
		win.moveTo(300,300);
	}
	//показ и мокрытие налоговых полей
	function chooseTax(event)
	{
		var theTag = document.getElementById("field(tax)");
		var el,docName;
		el = document.getElementById("field(docType)");
		if(  el != null )
		{
			docName = el.value;
		}
		if(theTag.checked)
		{
			showField("payerKPP");
			showField("kbk");
			showField("period");
			showField("payerINN");
			showField("taxHeader");
			if(docName!="pd" && docName!="pdNalog")
			{
				showField("taxNumber");
				showField("taxType");
				showField("taxStatus");
				hideField("payerKPPAster");
				hideField("payerINNAster");
			}
			else
			{
				el.options.value="pdNalog";
				showField("payerKPPAster");
				showField("payerINNAster");
				showField("penalty");
				showField("shortName");
			}
		}
		else
		{
			hideField("taxHeader");
			hideField("taxStatus");
			if(docName=="pdNalog")
			{
				hideField("payerKPP");
				hideField("payerINN");
				el.options.value="pd";
			}
			hideField("payerKPPAster");
			hideField("payerINNAster");
			hideField("shortName");
			hideField("kbk");
			hideField("period");
			hideField("penalty");
			hideField("taxNumber");
			hideField("taxType");
		}
	}
	//работа с ошибками
	function setErr(id,msg)
	{
		if(id==null)return;
		name = idToName(id);
		if(name != null)
		{
		  errorsForm = errorsForm + "Поле '"+ name +"': "+ msg+"\n";
		}
		return false;
	}
	function printErr()
	{
		if(errorsForm!=null)
			alert(errorsForm);
	}
	function emptyErr()
	{
		errorsForm="";
	}
	//-------------------------проверки----------------------------
	function StrCheck(sPattern, sCheck)
	{
		var i,ch;
		for (i = 0;  i < sCheck.length;  i++)
		{
			ch = sCheck.charAt(i);
			if(sPattern.indexOf(ch) == -1) return false;

		}
		return true;
	}
	function idToName(id)
	{
		switch(id)
		{
			case "field(docDate)": return "Дата";
			case "field(ground)": return "Назначение платежа";
			case "field(docKind)": return "Вид платежа";
			case "field(docNumber)": return "Номер документа";
			case "field(amount)": return "Сумма";
			case "field(amountKop)": return "Сумма";
			case "field(payerDescription)": return "Наименование плательщика";
			case "field(payerName)": return "ФИО плательщика";
			case "field(payerAddress)": return "Адрес плательщика";
		    case "field(payerAccount)": return "№ лицевого счета(код) плательщика";
			case "field(payerINN)": return "ИНН плательщика";
			case "field(payerKPP)": return "КПП плательщика";
			case "field(payerBankName)": return "Банк плательщика";
			case "field(payerBIC)": return "БИК банка плательщика";
			case "field(payerCorAccount)": return "Корр. счет банка плательщика";
			case "field(receiverName)": return "Наименование получателя";
			case "field(receiverAccount)": return "Счет получателя";
			case "field(receiverINN)": return "ИНН получателя";
			case "field(receiverKPP)": return "КПП получателя";
			case "field(receiverBankName)": return "Банк получателя";
			case "field(receiverBIC)": return "БИК банка получателя";
			case "field(receiverCorAccount)": return "Корр. счет банка получателя";
			case "field(receiverNameShort)": return "Сокращенное наименование";
			case "field(receiverOKATO)": return "Код ОКАТО";
			case "field(KBK)": return "КБК";
			case "field(fine)": return "Пеня";
			case "field(penalty)": return "Штраф";
			case "field(taxPeriod1)": return "Платеж по сроку";
			case "field(taxPeriod2)": return "Платеж по сроку";
			case "field(taxPeriod3)": return "Платеж по сроку";
			case "field(taxNumber)": return "Номер налогового документа";
			case "field(taxType)": return "Тип платежа";
			case "field(taxData)": return "Номер налогового документа";
			case "field(taxFundamental)": return "Основание платежа";
			case "field(taxStatus)": return "Статус составителя";
		}
		return null;
	}
	function StrToDate ( sDate )
	{
	 var dp,mp,dt,yLen;
	 var day, month;
	 var sErr;
	 sErr="wrong date";
	 if ( (dp=sDate.indexOf(".")) < 0 )
		   throw sErr ;
	 else
		   if ( (mp=sDate.indexOf(".",dp+1)) < 0 )
				   throw sErr;
		   else
			  {
			   yLen=sDate.length-mp-1; // год 2 или 4 цифры
			   if ( (yLen !=2 ) && ( yLen!=4))
				 throw  sErr;
		   else
			   {
				   day=parseInt(sDate.substr(0,dp),10);
				   month=parseInt(sDate.substr(dp+1,mp-dp-1),10);
				   if ( (day >31) || (month > 12))
						 throw  sErr;
				   else
				   {
					  dt = new Date( month + "/" + day + "/" + sDate.substr(mp+1));
					  if ( dt.getDate()!=day )
								throw  sErr;
					  else
						   return dt;
				   }
			   }
			  }
	}
	function CDate ( sDate )
	{
	 var dat,e;
		if ( sDate. length == 0 )
		{
		   return false;
		}
		else
		{
		   dat=sDate.replace(/\s*/,"");
		   if ( !StrCheck("0123456789.", dat))
		   {
			 return false;
		   }
		   else
		   {
			  try
			  {
					StrToDate(dat);
			  }
			  catch (e)
			  {
				return false;
			  }
		   }
		}
		return true;
	}

	function SC_checkFields( theForm)
	{
	  var i, len = theForm.elements.length;
	  var elem,s;

	  for ( i =0; i < len; i++)
	  {
		elem = theForm.elements[i];
		switch ( elem.type )
		{
		  case 'textarea':
		  case 'text':
		  case 'hidden':
					s = elem.value.toUpperCase();
					if ( s.indexOf( '/SCRIPT' ) >= 0 )
					{
						return setErr(elem.id,"содержит недопустимый текст.");
					}
		}
	  }
	  return true;
	}
//проверить правильность заполнения налогового периода
	function CheckTaxPeriod ( s1, s2 )
	{

	 var patt,mn;

	 if(( s1 == "" )&&( s2 == "")) return true;
	 switch (s1)
	 {
		case "МС" :
		 patt=new Array("12","11","10","09","08","07","06","05","04","03","02","01");
		 break;
	   case "КВ" :
		 patt=new Array("04","03","02","01");
		 break;
	   case "ПЛ" :
		 patt=new Array("02","01");
		 break;
	   case "ГД" :
		 patt=new Array("00");
		 break;
	   default:return true;
	 }
	 for ( i=0;i<patt.length;i++)
	 {
	   if ( patt[i] == s2 ) return true;
	 }
	 return false;
	}
	//типы полей
	var TP_SUM2= 0; //сумма - только цифры и точка, 2 цифры в дробной части
	var TP_DATE= 2; //дата
	var TP_TEXT= 3; //произвольный текст
	var TP_NUMD= 6; //номер документа не 0 и не оканчивается на 000
	var TP_NUMB= 7; //только цифры
	var TP_TAXP= 8; //налоговый период
	var TP_ACCT=10;// счет получателя  0 или 000..0 - пусто
	var TP_TKBK=11;// KBK : первые 3 позиции не 0, длина 20, позиции с 14 по 17 0000,1000,2000,3000
	var TP_TXTN=13;// пустое если 0

	function fnCheckField (id, type, mandatory, minLen, maxLen, minVal, maxVal,add)  //функция проверки правильности задания поля
	{                         //длина и тип значения
	  var f, d, s,a,res=true;
	  f = document.getElementById(id);
	  if(f == null)
		  return true;

	  if(add!=null)a = document.getElementById(add);
	  if(mandatory == true )
	  {
		   res = checkMandatoryField(id);
		   if(!res)return false;
	  }
	  if( ( minLen != null) && (minLen == maxLen) )
	  {
		  if ( f.value.length != minLen)
		  {
			 res =  setErr(id,"длина поля должна быть равна " + minLen + " симв.");
			 if(!res)return false;
		  }
	  }
	  else
	  {
		  if ( minLen != null )
			  if ( f.value.length < minLen)
			  {
				  res = setErr(id,"длина поля не должна быть меньше " + minLen + " симв.");
			 	  if(!res)return false;
			  }
		  if ( maxLen != null )
			  if ( f.value.length > maxLen)
			  {
			  	  res = setErr(id,"длина поля не должна превышать " + maxLen + " симв.");
			 	  if(!res)return false;
			  }
	  }

	  switch ( type)
	  {
		 case TP_SUM2: // в поле только цифры и точка
			  if ( !StrCheck("0123456789.", f.value )) return ( setErr(id,"может содержать только цифры и точку."));
			  break;
		 case TP_TAXP:
			  if ( !CheckTaxPeriod(f.value,a.value )) return(setErr(id,"указан неверный период"));
			  break;
		 case TP_DATE: //задана корректная дата
			  if (!CDate(f.value )) return setErr(id,"указана неверная дата");
			  break;
		 case TP_NUMD:
			  if ( f.value.match(/^ *0* *$/)) return setErr (id, "номер документа не может состоять из одних нулей");
			  if ( f.value.match(/000 *$/)) return setErr (id, "номер документа не может оканчиваться на 000");
			  break;
		 case TP_NUMB:  // в поле только цифры
			  if ( !StrCheck("0123456789", f.value )) return (setErr(id,"может содержать только цифры."));
			  break;
		 case TP_TKBK:
			  if ( f.value.length != 20)
				 return this.setErr(id,"код КБК должен содержать 20 символов");
			  if ( f.value.substr(0,3)=="000")
				  return setErr(id,"необходимо указать код администратора получателя платежа (1-3 разряды кода КБК)");
			  d=f.value.substr(13,1);
			  if ( (d != "0") && (d != "1") && (d != "2") && (d != "3"))
				return setErr(id,"неверно заполнен 14 разряд в КБК. Допустимы 0, 1, 2 или 3");
			  break;
		 default:
	  }
	  if ( minVal != null ) if ( f.value < minVal) return setErr(id,"значение поля не должно быть меньше " + this.minVal);
	  if ( maxVal != null ) if ( f.value > maxVal) return setErr(id,"значение поля не должно превышать " + this.maxVal);
	 return true;
	}
	function checkMandatoryField(id)
	{
		el = document.getElementById(id);
		if(el != null)
		{
			if( (el.value == "") || (el.value == null) )
			{
				setErr(id,"обязательно для заполнения");
				return false;
			}
			return true;
		}
		return false;
	}
	function checkFill()
	{
		var res=true, el, isTax=false, type;

		//получение данных о платеже
		el = document.getElementById("field(docType)");
		type = el.value;
		if (type== "pdNalog") type="pd";
		el = document.getElementById("field(tax)");
		isTax = el.checked;

		emptyErr();
		//проверка на наличие /script
		if(!SC_checkFields(document.forms.item(0)))
		{
			printErr();
			return false;
		}
		//проверка общих полей, которые есть и обязательны в любых платежах
		if(!fnCheckField("field(docDate)",TP_DATE,true))res = false;
		if(!fnCheckField("field(ground)",TP_TEXT,true))res = false;
		if(!fnCheckField("field(amount)",TP_NUMB,true))res = false;
		if(!fnCheckField("field(amountKop)",TP_NUMB,false))res = false;
		if(!fnCheckField("field(receiverName)",TP_TEXT,true))res = false;
		if(!fnCheckField("field(receiverAccount)",TP_NUMB,true,20,25))res = false;
		if(!fnCheckField("field(receiverBankName)",TP_TEXT,true))res = false;
		if(!fnCheckField("field(receiverBIC)",TP_NUMB,true,9,9))res = false;
		if(!fnCheckField("field(receiverCorAccount)",TP_NUMB,false,0,20))res = false;

		if( type == "pd")
		{
			if(!fnCheckField("field(receiverINN)",TP_NUMB,true,10,13))res = false;
			if(!fnCheckField("field(payerName)",TP_TEXT,true))res = false;
			if(!fnCheckField("field(payerAddress)",TP_TEXT,true))res = false;
			if(!isTax)
			{
				if(!fnCheckField("field(payerAccount)",TP_NUMB,true))res = false;
								el = document.getElementById("field(docDate)");
				if(el!=null)
				{
					dd = el.value.substring(0, 2);
					mm = el.value.substring(3, 5);
					yy = el.value.substring(6, 10);
					if( (yy>2009) || (yy<2000) )
					{
						setErr("field(docDate)","год должен быть в диапазоне 2000 - 2009");
						res=false;
					}
				}
			}
			else
			{
				if(!fnCheckField("field(receiverKPP)",TP_NUMB,true,9,9))res = false;
				if(!fnCheckField("field(payerKPP)",TP_NUMB,true,9,9))res = false;
				if(!fnCheckField("field(payerAccount)",TP_NUMB,true,12,12))res = false;
				if(!fnCheckField("field(payerINN)",TP_NUMB,true,10,13))res = false;
				if(!fnCheckField("field(receiverNameShort)",TP_TEXT,false))res = false;
				if(!fnCheckField("field(receiverOKATO)",TP_NUMB,true,11,11))res = false;
				if(!fnCheckField("field(KBK)",TP_TKBK,true))res = false;
				if(!fnCheckField("field(fine)",TP_NUMB,false))res = false;
				if(!fnCheckField("field(penalty)",TP_NUMB,false))res = false;
				if(!fnCheckField("field(taxPeriod1)",TP_TAXP,false,null,null,null,null,"field(taxPeriod2)"))res = false;
			}
		}
		else
		{
			if(!fnCheckField("field(receiverINN)",TP_NUMB,false,0,13))res = false;
			if(!fnCheckField("field(docNumber)",TP_NUMD,true))res = false;
			if(!fnCheckField("field(payerDescription)",TP_TEXT,true))res = false;
			if(!fnCheckField("field(payerAccount)",TP_NUMB,true))res = false;
			if(!fnCheckField("field(payerINN)",TP_NUMB,false,0,13))res = false;
			if(!fnCheckField("field(payerKPP)",TP_NUMB,false,0,9))res = false;
			if(!fnCheckField("field(payerBankName)",TP_TEXT,true))res = false;
			if(!fnCheckField("field(payerBIC)",TP_NUMB,true,9,9))res = false;
			if(!fnCheckField("field(payerCorAccount)",TP_NUMB,false,0,20))res = false;
			if(!fnCheckField("field(receiverKPP)",TP_NUMB,false,0,9))res = false;
			setToNull("field(payerINN)");
			setToNull("field(payerKPP)");

			if(isTax)
			{
				if(!fnCheckField("field(taxNumber)",TP_NUMB,true))res = false;
				if(!fnCheckField("field(taxType)",TP_TEXT,true))res = false;
				if(!fnCheckField("field(taxData)",TP_DATE,true))res = false;
				if(!fnCheckField("field(taxFundamental)",TP_TEXT,true))res = false;
				if(!fnCheckField("field(receiverOKATO)",TP_NUMB,true,11,11))res = false;
				if(!fnCheckField("field(KBK)",TP_TKBK,true))res = false;
				if(!fnCheckField("field(taxPeriod1)",TP_TAXP,false,null,null,null,null,"field(taxPeriod2)"))res = false;
				if(!fnCheckField("field(taxStatus)",TP_NUMB,true,2,2))res = false;
			}
		}
		if(!res)printErr();

		return res;
	}
	function setToNull(id)
	{
		el = document.getElementById(id);
		if(el!=null)
		{
			if(el.value.length == 0)el.value = "0";
		}
	}
	//-------------------------проверки----------------------------
	function generateForm()
	{
		var win,res=true,el,action = "print",isTax=false,sum=0;

		el = document.getElementById("field(tax)");
		if(el!=null)isTax = el.checked;

		if( checkFill() )
		{

			el = document.getElementById("field(docType)");
			if(  el != null )
			{
				el1 = document.getElementById("field(amountKop)");
				if(el1.value.length == 0)el1.value = "00";

				docName = el.value;
				if(docName == "pd" || docName =="pdNalog")
				{
					if(isTax)action = "printNalog";
					else action = "print";
				}
				else
				{
					//формирование суммы для передачи в action для генерации суммы прописью
					el = document.getElementById("field(amount)");
					el1 = document.getElementById("field(amountKop)");
					kop = "00";
					rub = "0";
					if(el.value.length != 0)rub = el.value;
					if(el1.value.length == 1)kop="0"+el1.value;

					sum =el.value + '.' + kop;
				}
				if(docName == "payOffer"){action = "printPay";}
				if(docName == "letterOffer"){action = "printLetter";}
			}

			win = window.open("${phiz:calculateActionURL(pageContext, '/private/PD4.do')}?action="+action+"&summ="+sum);
			if(docName == "pd" || docName =="pdNalog")
			{
				win.resizeTo(800,700);
				win.moveTo(100,10);
			}
			else
			{
				win.resizeTo(1200,700);
				win.moveTo(0,0);
			}

		}
	}
	pdFieldsShow = new Array("payerDescription","payerName","payerAddress","payerBank",
						"payerBankName","payerBIKCor","receiverINNAster","receiverKPPAster");
	pdFieldsHide = new Array("payerDescription","payerKPP","payerBank","payerBankName","payerBIKCor","payerINN",
							"docKind","docNumber","payerKPP");
	payOfferFieldsShow = new Array("payerDescription","payerKPP","payerBank","payerBankName","payerBIKCor","payerINN",
							"docKind","docNumber");
	payOfferFieldsHide =  new Array("payerName","payerAddress","receiverINNAster","receiverKPPAster");
	letterOfferFieldsShow= new Array("payerDescription","payerKPP","payerBank","payerBankName","payerBIKCor","payerINN",
							"docKind","docNumber");
	letterOfferFieldsHide =  new Array("payerName","payerAddress","receiverINNAster","receiverKPPAster");
	function showFields(arrShow,arrHide)
	{
		for(i=0; i<arrShow.length; i++)
			showField(arrShow[i]);
		for(i=0; i<arrHide.length; i++)
			hideField(arrHide[i]);
	}
	function showField(id)
	{
		el = document.getElementById(id);
		if(el != null)
			el.style.display = "block";
	}
	function hideField(id)
	{
		el = document.getElementById(id);
		if(el != null)
			el.style.display = "none";
	}
	function chooseDocument()
	{
		var el = document.getElementById("field(docType)");
		var elCheckBox = document.getElementById("field(tax)");
		if(  el != null )
		{
			var docName = el.value;
			elCheckBox.checked = (docName=="pdNalog");
			chooseTax(null);
			switch(docName)
			{
				case "pd":{showFields(pdFieldsShow,pdFieldsHide);return;}
				case "pdNalog":{showFields(pdFieldsShow,pdFieldsHide);chooseTax(null);return;}
				case "payOffer":{showFields(payOfferFieldsShow,payOfferFieldsHide);return;}
				case "letterOffer":{showFields(letterOfferFieldsShow,letterOfferFieldsHide);return;}
			}
		}


	}
	</script>
	<table cellspacing="2" cellpadding="0" border=0 onkeypress="onEnterKey(event);" class="paymentFon">
    <tr style="display: block">
       <td align="right" valign="middle"><img src="${imagePath}/RurPayment.gif" border="0"/></td>
                <td>
                    <table class="MaxSize">
                    <tr>
                        <td align="center" style="border-bottom:1px solid silver"  class="paperTitle">
                            <table height="100%" width="420px" cellspacing="0" cellpadding="0">
                            <tr>
                                <td class="titleHelp">
                                    <span class="formTitle">Печать платежного документа</span>
                                    <br/>
                                    Используйте данную форму для печати платежных документов: ПД-4, ПД-4сб (налог),
									инкассовое поручение, платежное поручение
                                </td>
                            </tr>
                            </table>
                        </td>
                        <td width="100%">&nbsp;</td>
                    </tr>
                    </table>
                </td>
            </tr>
			<tr style="display: block">
				<td class="Width120 LabelAll" valign="top">Вид документа*</td>
				<td>
					<c:set  var="sel" value="pd"/>
					<bean:define id="type" name="PD4Form" property="type"/>
						 <c:choose>
								<c:when test="${type == 'letter'}">
									<c:set  var="sel" value="letterOffer"/>
								</c:when>
								<c:when test="${type == 'pay'}">
									<c:set  var="sel" value="payOffer"/>
								</c:when>
	 							<c:otherwise>
									<c:set  var="sel" value="pd"/>
								</c:otherwise>
						</c:choose>
					<html:select value="${sel}" property="field(docType)" size="1" styleId="field(docType)" onchange="chooseDocument();">
						<c:if test="${phiz:impliesOperation('PD4PrintOperation','PaymentDocumentPreparation')}">
							<html:option value="pd" >ПД-4</html:option>
							<html:option value="pdNalog">ПД-4 сб (налог)</html:option>
						</c:if>
						<c:if test="${phiz:impliesOperation('PaymentOrderOperation','PaymentDocumentPreparation')}">
	                        <html:option value="payOffer">Платежное поручение ф.0401060</html:option>
						</c:if>
						<c:if test="${phiz:impliesOperation('CollectionLetterOperation','PaymentDocumentPreparation')}">
	                      <html:option value="letterOffer">Инкассовое поручение ф.0401071</html:option>
						</c:if>
					</html:select>
					<span class="Width120 LabelAll">налоговый документ</span>
					<html:checkbox style="border:none" styleId="field(tax)" property="field(tax)" onclick="javascript:chooseTax(event);"/>

				</td>
			</tr>
			<tr style="display: block">
				<td class="Width120 LabelAll" valign="top">Дата<span class="asterisk">*</span></td>
				<td><html:text property="field(docDate)" size="10" styleId="field(docDate)" onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"/></td>
			</tr>
	        <tr id = "docKind" style="display:none">
				<td class="Width120 LabelAll" valign="top">Вид платежа <span class="asterisk">*</span></td>
				<td>
					<html:select property="field(docKind)" size="1" styleId="field(docKind)">
					 	<html:option value="телеграфом">телеграфом</html:option>
                      	<html:option value="почтой">почтой</html:option>
						<html:option value="электронно">электронно</html:option>
					</html:select>
				</td>
			</tr>
	        <tr id="docNumber" style="display:none">
				<td class="Width120 LabelAll" valign="top">Номер документа <span class="asterisk">*</span></td>
				<td><html:text property="field(docNumber)" size="10" maxlength="5" styleId="field(docNumber)"/></td>
			</tr>
			<tr style="display: block">
				<td colspan="2" style="text-align:left;" class="Width120 LabelAll">&nbsp;<b>Плательщик</b></td>
			</tr>
			<tr id="payerDescription"  style="display:none">
				<td class="Width120 LabelAll" valign="top">Наименование плательщика<span class="asterisk">*</span></td>
				<td><html:text property="field(payerDescription)"size="80" maxlength="40" styleId="field(payerDescription)"/></td>
			</tr>
			<tr id="payerName" style="display: block">
				<td class="Width120 LabelAll" valign="top">ФИО<span class="asterisk">*</span></td>
				<td><html:text property="field(payerName)"size="80" maxlength="40" styleId="field(payerName)"/></td>
			</tr>
            <tr id="payerAddress" style="display: block">
				<td class="Width120 LabelAll" valign="top">Адрес<span class="asterisk">*</span></td>
				<td><html:text property="field(payerAddress)"size="80" maxlength="40" styleId="field(payerAddress)"/></td>
			</tr>
	        <tr style="display: block">
				<td class="Width120 LabelAll" valign="top">№ лицевого счета(код) <span class="asterisk">*</span></td>
				<td><html:text property="field(payerAccount)"size="30" maxlength="25" styleId="field(payerAccount)"  onkeydown="enterNumericFld(event,this);"/></td>
			</tr>
		    <tr  id="payerINN" style="display:none">
				<td class="Width120 LabelAll" valign="top">
					<table cellspacing="0" cellpadding="0" border=0><tr><td>ИНН&nbsp;</td><td><span id="payerINNAster" class="asterisk">*</span></td></tr></table>
				</td>
				<td><html:text property="field(payerINN)"size="14" maxlength="12" styleId="field(payerINN)"  onkeydown="enterNumericFld(event,this);"/></td>
			</tr>
		    <tr  id="payerKPP" style="display:none">
				<td class="Width120 LabelAll" valign="top">
					<table cellspacing="0" cellpadding="0" border=0><tr><td>КПП&nbsp;</td><td><span id="payerKPPAster" class="asterisk">*</span></td></tr></table>
				</td>
				<td><html:text property="field(payerKPP)"size="11" maxlength="9" styleId="field(payerKPP)"  onkeydown="enterNumericFld(event,this);"/></td>
			</tr>
			<tr id="payerBank"  style="display:none">
				<td colspan="2" style="text-align:left;" class="Width120 LabelAll"><b>&nbsp;Банк плательщика</b></td>
			</tr>
			<tr id="payerBankName"  style="display:none">
				<td class="Width120 LabelAll">Наименование<span class="asterisk">*</span></td>
                <td>
                    <table class="MaxSize" cellspacing="0" cellspadding="0">
                    <tr>
                        <td>
				        <html:text property="field(payerBankName)" size="67" maxlength="54" styleId="field(payerBankName)" readonly="true"/>
				          <input type="button" class="buttWhite" style="height:18px;width:18;" onclick="javascript:fillBankInfo('field(payerBankName)','field(payerBIC)',0);" value="..."/>
                        </td>
                     </tr>
                    </table>
                </td>
			</tr>
			<tr id="payerBIKCor"  style="display:none">
				<td class="Width120 LabelAll">БИК <span class="asterisk">*</span></td>
				<td><html:text property="field(payerBIC)" maxlength="9" size="11" styleId="field(payerBIC)"  onkeydown="enterNumericFld(event,this);"/>
                    <span class="Width120 LabelAll">Корр. счет</span>
                    <html:text property="field(payerCorAccount)" maxlength="20" size="24" styleId="field(payerCorAccount)"  onkeydown="enterNumericFld(event,this);"/>
                </td>
			</tr>
			<tr style="display: block">
				<td colspan="2" style="text-align:left;" class="Width120 LabelAll">&nbsp;<b>Получатель платежа</b></td>
			</tr>
			<tr style="display: block">
				<td class="Width120 LabelAll">Наименование <span class="asterisk">*</span></td>
				<td>
                    <table class="MaxSize" cellspacing="0" cellspadding="0">
                    <tr>
                        <td>
                            <html:text property="field(receiverName)" size="80" maxlength="40" styleId="field(receiverName)"/>
                        </td>
                    </tr>
                    </table>
                </td>
			</tr>
			<tr style="display: block">
				<td class="Width120 LabelAll">Счет <span class="asterisk">*</span></td>
				<td><html:text property="field(receiverAccount)" maxlength="25" size="30" styleId="field(receiverAccount)"  onkeydown="enterNumericFld(event,this);"/></td>
			</tr>
			<tr style="display: block">
				<td class="Width120 LabelAll">
					<table cellspacing="0" cellpadding="0" border=0><tr><td>ИНН&nbsp;</td><td><span id="receiverINNAster" class="asterisk">*</span></td></tr></table>
				</td>
				<td><html:text property="field(receiverINN)" maxlength="10" size="12" styleId="field(receiverINN)"  onkeydown="enterNumericFld(event,this);"/></td>
			</tr>
			<tr id="receiverKPP" style="display: block">
				<td class="Width120 LabelAll">
					<table cellspacing="0" cellpadding="0" border=0><tr><td>КПП&nbsp;</td><td><span id="receiverKPPAster" class="asterisk">*</span></td></tr></table>
				</td>
				<td><html:text property="field(receiverKPP)" maxlength="9" size="11" styleId="field(receiverKPP)"  onkeydown="enterNumericFld(event,this);"/></td>
			</tr>
			<tr style="display: block">
				<td colspan="2" style="text-align:left;" class="Width120 LabelAll"><b>&nbsp;Банк получателя</b></td>
			</tr>
			<tr style="display: block">
				<td class="Width120 LabelAll">Наименование<span class="asterisk">*</span></td>
                <td>
                    <table class="MaxSize" cellspacing="0" cellspadding="0">
                    <tr>
                        <td>
				        <html:text property="field(receiverBankName)" size="80" maxlength="27" styleId="field(receiverBankName)"/>
				          <input type="button" class="buttWhite" style="height:18px;width:18;" onclick="javascript:fillBankInfo('field(receiverBankName)','field(receiverBIC)',1);" value="..."/>
                        </td>
                     </tr>
                    </table>
                </td>
			</tr>
			<tr style="display: block">
				<td class="Width120 LabelAll">БИК <span class="asterisk">*</span></td>
				<td><html:text property="field(receiverBIC)" maxlength="9" size="11" styleId="field(receiverBIC)"  onkeydown="enterNumericFld(event,this);"/>
                    <span class="Width120 LabelAll">Корр. счет</span>
                    <html:text property="field(receiverCorAccount)" maxlength="20" size="24" styleId="field(receiverCorAccount)"  onkeydown="enterNumericFld(event,this);"/>
                </td>
			</tr>
			<tr style="display: block">
				<td class="Width120 LabelAll" valign="top"><br/></td>
				<td></td>
			</tr>
			<tr style="display: block">
				<td class="Width120 LabelAll" valign="top">Назначение платежа <span class="asterisk">*</span></td>
				<td><html:text property="field(ground)" size="86" maxlength="48" styleId="field(ground)"/></td>
			</tr>
			<tr style="display: block">
				<td class="Width120 LabelAll" valign="top">Сумма <span class="asterisk">*</span></td>
				<td><html:text property="field(amount)"size="14" maxlength="12" styleId="field(amount)" onkeydown="enterNumericFld(event,this);"/>руб.
					<html:text property="field(amountKop)"size="2" maxlength="2" styleId="field(amountKop)" onkeydown="enterNumericFld(event,this);"/>коп.
				</td>
			</tr>
			<tr id="taxHeader"  style="display:none">
				<td colspan="2" style="text-align:left;" class="Width120 LabelAll">&nbsp;<b>Налоговые поля</b></td>
			</tr>
			<tr id="taxStatus" style="display:none">
				<td class="Width120 LabelAll">Статус составителя<span class="asterisk">*</span></td>
				<td><html:text property="field(taxStatus)" maxlength="2" size="4" styleId="field(taxStatus)"  onkeydown="enterNumericFld(event,this);"/>
					<input type="button" class="buttWhite" style="height:18px;width:18;" onclick="javascipt:displayPeriodList(event,'taxstatus')"  value="..."/>
				</td>
			</tr>
			<tr id="taxNumber" style="display:none">
				<td class="Width120 LabelAll">Налог.док-т: N<span class="asterisk">*</span></td>
				<td><html:text property="field(taxNumber)" maxlength="8" size="12" styleId="field(taxNumber)"  onkeydown="enterNumericFld(event,this);"/>
					<span class="Width120 LabelAll">Дата<span class="asterisk">*</span></span>
                    <html:text property="field(taxData)"  size="10" styleId="field(taxData)"  onkeydown="enterNumericTemplateFld(event,this,DATE_TEMPLATE);"/>
				</td>
			</tr>
			<tr id="taxType" style="display:none">
				<td class="Width120 LabelAll">Тип плат.<span class="asterisk">*</span></td>
				<td><html:text property="field(taxType)" maxlength="2" size="4" styleId="field(taxType)"/>
					<input type="button" class="buttWhite" style="height:18px;width:18;" onclick="javascipt:displayPeriodList(event,'taxtype')"  value="..."/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span class="Width120 LabelAll">Основание плат.<span class="asterisk">*</span></span>
                    <html:text property="field(taxFundamental)" maxlength="2" size="4" styleId="field(taxFundamental)"/>
					<input type="button" class="buttWhite" style="height:18px;width:18;" onclick="javascipt:displayPeriodList(event,'taxfund')"  value="..."/>
				</td>
			</tr>
			<tr id="shortName"  style="display:none">
				<td class="Width120 LabelAll">Сокращенное наименование</td>
				<td><html:text property="field(receiverNameShort)" size="24" maxlength="20" styleId="field(receiverNameShort)"/>
                </td>
			</tr>
            <tr id="kbk" style="display:none">
				<td class="Width120 LabelAll">КБК<span class="asterisk">*</span></td>
				<td><html:text property="field(KBK)" maxlength="20" size="24" styleId="field(KBK)"  onkeydown="enterNumericFld(event,this);"/>
					<span class="Width120 LabelAll">Код ОКАТО<span class="asterisk">*</span></span>
                    <html:text property="field(receiverOKATO)" maxlength="11" size="13" styleId="field(receiverOKATO)"  onkeydown="enterNumericFld(event,this);"/>
				</td>
			</tr>
			<tr id="penalty"  style="display:none">
				<td class="Width120 LabelAll">Пеня</td>
				<td><html:text property="field(fine)" size="12" maxlength="12" styleId="field(fine)" onkeydown="enterNumericFld(event,this);"/>руб.
					<html:text property="field(fine)" size="2" maxlength="2" styleId="field(fineKop)" onkeydown="enterNumericFld(event,this);"/>коп.
					<span class="Width120 LabelAll">Штраф</span>
                    <html:text property="field(penalty)" maxlength="12" size="12" styleId="field(penalty)" onkeydown="enterNumericFld(event,this);"/>руб.
					<html:text property="field(penalty)" maxlength="2" size="2" styleId="field(penaltyKop)"  onkeydown="enterNumericFld(event,this);"/>коп.
				</td>
			</tr>
			<tr id="period" style="display:none">
				<td class="Width120 LabelAll">Платеж по сроку</td>
				<td><html:text property="field(taxPeriod1)" maxlength="2" size="2" styleId="field(taxPeriod1)"/>.
					<html:text property="field(taxPeriod2)" maxlength="2" size="2" styleId="field(taxPeriod2)" onkeydown="enterNumericTemplateFld(event,this,'__');"/>.
					<html:text property="field(taxPeriod3)" maxlength="4" size="4" styleId="field(taxPeriod3)" onkeydown="enterNumericTemplateFld(event,this,'____');"/>
					<input type="button" class="buttWhite" style="height:18px;width:18;" onclick="javascipt:displayPeriodList(event,'periodfill')"  value="..."/>
				</td>
			</tr>
	</table>
	<br/>
	</html:form>
	</tiles:put>

</tiles:insert>

</body>


