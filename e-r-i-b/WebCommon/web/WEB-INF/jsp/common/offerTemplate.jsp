<%@ page import="com.rssl.phizic.web.PD4Form" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<bean:define id="letter" name="PD4Form" property="isLetter"/>

<tiles:insert definition="confirmCard">
<tiles:put name="pageTitle" type="string">
	<c:choose>
		<c:when test='${letter}'>
			Инкассовое поручение для печати
		</c:when>
		<c:otherwise>
			Платежное поручение для печати
		</c:otherwise>
	</c:choose>
</tiles:put>
<tiles:put name="data" type="string">

<script>
	function getElementValue(doc,id)
	{
		el = doc.getElementById(id);
		if(el == null)return "";
		else return el.value;
	}
	function setPaymentValue(id,value)
	{
		var el;
		el = document.getElementById(id);
		if(el != null )el.innerHTML = value;
	}
	function addPaymentValue(id,value)
	{
		var el;
		el = document.getElementById(id);
		if(el != null )el.innerHTML = el.innerHTML + " "+ value;
	}
	function formatKop(value)
	{
		kop="00";
		if( (value == "")||(value == null) )kop ="00"
		if(value.length==1)kop="0" + value;
		if(value.length==2)kop=value;
		return kop;
	}
//заполнение формы
	function startFill(doc)
	{
		// general fields
		value = getElementValue(doc,"field(ground)");
		addPaymentValue("ground",value);

		value = getElementValue(doc,"field(amount)");
		value1 = getElementValue(doc,"field(amountKop)");
		setPaymentValue("amount",value+"."+formatKop(value1));

		value = getElementValue(doc,"field(docKind)");
		setPaymentValue("docKind",value);

		value = getElementValue(doc,"field(docNumber)");
		addPaymentValue("docNumber",value);
		//payer info
		value = getElementValue(doc,"field(payerDescription)");
		setPaymentValue("payerDescription",value);

		value = getElementValue(doc,"field(payerAccount)");
		setPaymentValue("payerAccount",value);

		value = getElementValue(doc,"field(payerINN)");
		if(value != "0")
			addPaymentValue("payerINN",value);

		value = getElementValue(doc,"field(payerKPP)");
		if(value != "0")
			addPaymentValue("payerKPP",value);
		//payer bank
		value = getElementValue(doc,"field(payerBankName)");
		setPaymentValue("payerBankName",value);

		value = getElementValue(doc,"field(payerCorAccount)");
		setPaymentValue("payerCorAccount",value);

		value = getElementValue(doc,"field(payerBIC)");
		setPaymentValue("payerBIC",value);
		//reciver info
		value = getElementValue(doc,"field(receiverName)");
		setPaymentValue("receiverName",value);

		value = getElementValue(doc,"field(receiverAccount)");
		setPaymentValue("receiverAccount",value);

		value = getElementValue(doc,"field(receiverINN)");
		addPaymentValue("receiverINN",value);

		value = getElementValue(doc,"field(receiverKPP)");
		addPaymentValue("receiverKPP",value);
		//reciver bank info
		value = getElementValue(doc,"field(receiverBankName)");
		setPaymentValue("receiverBankName",value);

		value = getElementValue(doc,"field(receiverCorAccount)");
		setPaymentValue("receiverCorAccount",value);

		value = getElementValue(doc,"field(receiverBIC)");
		setPaymentValue("receiverBIC",value);

		value = getElementValue(doc,"field(docDate)");
		setPaymentValue("docDate",value);

		el = doc.getElementById("field(tax)");
		if(el.checked)
		{
			value = getElementValue(doc,"field(KBK)");
			setPaymentValue("kbk",value);

			value = getElementValue(doc,"field(receiverOKATO)");
			setPaymentValue("okato",value);

			value = getElementValue(doc,"field(taxPeriod1)")+"."+getElementValue(doc,"field(taxPeriod2)")+"."+getElementValue(doc,"field(taxPeriod3)");
			if(value.length > 2)setPaymentValue("taxPeriod",value);

			value = getElementValue(doc,"field(taxFundamental)");
			setPaymentValue("taxFund",value);

			value = getElementValue(doc,"field(taxNumber)");
			setPaymentValue("taxNumber",value);

			value = getElementValue(doc,"field(taxData)");
			setPaymentValue("taxDate",value);

			value = getElementValue(doc,"field(taxType)");
			setPaymentValue("taxType",value);

			value = getElementValue(doc,"field(taxStatus)");
			setPaymentValue("taxStatus",value);
		}
}
function init(){
	startFill(window.opener.document);
}
addEventListenerEx(window, 'load', init, false);
</script>

   <table cellspacing="0" cellpadding="0" border="0"
    style="margin-left:5mm;margin-top:5mm;table-layout:fixed;width:180mm;border-collapse:collapse;">
  <col  style="width:20mm"/>
  <col  style="width:15mm"/>
  <col  style="width:10mm"/>
  <col  style="width:5mm"/>
  <col  style="width:10mm"/>
  <col  style="width:15mm"/>
  <col  style="width:10mm"/>
  <col  style="width:5mm"/>
  <col  style="width:10mm"/>
  <col  style="width:10mm"/>
  <col  style="width:5mm"/>
  <col  style="width:5mm"/>
  <col  style="width:5mm"/>
  <col  style="width:10mm"/>
  <col  style="width:10mm"/>
  <col  style="width:10mm"/>
  <col  style="width:5mm"/>
  <col  style="width:5mm"/>
  <col  style="width:5mm"/>
  <col  style="width:3mm"/>
  <col  style="width:7mm"/>

	<tr style="height:10mm">
      <td colspan="2" align="center" class=ul valign="bottom">

      </td>
      <td colspan="2"></td>
      <td colspan="3" align="center" class=ul valign="bottom">

      </td>
      <td colspan="11"></td>
      <td colspan="3" align="center"  valign="middle" class=bd
		  style="border-top:1 solid black;border-bottom:1 solid black;border-left:1 solid black;border-right:1 solid black;">
		   	<c:choose>
				<c:when test='${letter}'>
					0401071
				</c:when>
				<c:otherwise>
					0401060
				</c:otherwise>
			</c:choose>
	  </td>
    </tr>
    <tr style="height:6mm">
      <td colspan="2"  align="center" valign="top" style="border-top:1 solid black;font-size:8pt;">Поступ. в банк плат.</td>
      <td colspan="2"></td>
      <td colspan="3" align="center" valign="top" style="font-size:8pt;border-top:1 solid black;">Списано со сч. плат.</td>
      <td colspan="14"></td>
    </tr>
    <tr style="height:7mm">
      <td colspan="5" >
		  	<c:choose>
				<c:when test='${letter}'>
					ИНКАССОВОЕ ПОРУЧЕНИЕ
				</c:when>
				<c:otherwise>
					ПЛАТЕЖНОЕ ПОРУЧЕНИЕ
				</c:otherwise>
			</c:choose>
	  </td>
      <td id="docNumber" colspan="2" ><span  >N </span>
		<!--номер документа-->
      </td>
      <td id="docDate" colspan="5" align="center" class=ul valign="bottom">
		  <!--дата документа-->
      </td>
      <td></td>
      <td id="docKind" colspan="4" align="center" class=ul valign="bottom">
		  <!--вид документа-->
      </td>
      <td colspan="3"></td>
      <td id="taxStatus" class=bd align="center"  valign="middle"
		  style="border-top:1 solid black;border-bottom:1 solid black;border-left:1 solid black;border-right:1 solid black;">

      </td>
    </tr>
    <tr style="height:7mm">
      <td colspan="7"></td>
      <td colspan="5" align="center" valign="top" style="border-top:1 solid black;font-size:8pt;">Дата</td>
      <td></td>
      <td colspan="4" align="center" valign="top" style="border-top:1 solid black;font-size:8pt;">Вид платежа</td>
      <td colspan="4"></td>
    </tr>
    <tr style="height:15mm">
       <td valign="top" style="border-right:1px solid black">Сумма прописью</td>
       <td colspan="20" valign="top">&nbsp;
		   <!--сумма прописью-->
			<bean:write name="PD4Form" property="summa"/>
       </td>
    </tr>
    <tr style="height:5mm">
       <td id="payerINN" colspan="4" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black">
		   ИНН&nbsp;
		   <!--ИНН плательщика-->
	   </td>
       <td id="payerKPP" colspan="5" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black">
		   &nbsp;КПП&nbsp;
		   <!--КПП плательщика-->
       </td>
       <td colspan="2" style="border-top:1px solid black;border-right:1px solid black;border-bottom:1px solid black;">&nbsp;Сумма</td>
       <td id="amount" colspan="10" style="border-top:1px solid black;">&nbsp;
		   <!--Сумма-->
       </td>
    </tr>
    <tr style="height:10mm">
       <td id="payerDescription" colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
	   		<!--Имя плательщика-->
       </td>
       <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
       <td colspan="10"></td>
    </tr>
    <tr style="height:10mm">
       <td colspan="2" style="border-right:1px solid black" valign="top">&nbsp;Сч. N</td>
       <td id="payerAccount" colspan="10" style="border-top:1px solid black;" valign="top">&nbsp;
			<!--Счет плательщика-->
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Плательщик</td>
       <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
       <td colspan="10"></td>
    </tr>
    <tr style="height:5mm">
       <td id="payerBankName" colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
		    <!--Название банка плательщика-->
       </td>
       <td colspan="2" style="border-right:1px solid black;border-bottom:1px solid black">&nbsp;БИК</td>
       <td id="payerBIC" colspan="10" >&nbsp;
		     <!--БИК банка плательщика-->
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
       <td id="payerCorAccount" colspan="10" >&nbsp;
		     <!--Кор.Счет банка плательщика-->
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Банк плательщика</td>
       <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
       <td colspan="10" class=ul></td>
    </tr>
    <tr style="height:5mm">
       <td id="receiverBankName" colspan="9" rowspan="2" style="border-right:1px solid black" valign="top">
			<!--Название банка получателя-->
       </td>
       <td colspan="2" style="border-right:1px solid black;border-bottom:1px solid black;">&nbsp;БИК</td>
       <td id="receiverBIC" colspan="10" style="border-top:1px solid black;">&nbsp;
		   <!--БИК банка получателя-->
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="2" style="border-right:1px solid black">&nbsp;Сч. N</td>
       <td id="receiverCorAccount" colspan="10" >&nbsp;
		   <!--Кор.Счет банка получателя-->
       </td>
    </tr>
    <tr style="height:5mm">
       <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Банк получателя</td>
       <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
       <td colspan="10"></td>
    </tr>
    <tr style="height:5mm">
       <td id="receiverINN" colspan="4" style="border-bottom:1px solid black;border-right:1px solid black">
		   ИНН&nbsp;
		   <!--ИНН получателя-->
       </td>
       <td id="receiverKPP" colspan="5" style="border-bottom:1px solid black;border-right:1px solid black">
		   &nbsp;КПП&nbsp;
		   <!--КПП получателя-->
       </td>
       <td colspan="2" style="border-right:1px solid black;border-bottom:1px solid black;">&nbsp;Сч. N</td>
       <td id="receiverAccount" colspan="10">&nbsp;
		   <!--Счет получателя-->
       </td>
    </tr>
    <tr style="height:10mm">
      <td id="receiverName" colspan="9" rowspan="3" style="border-right:1px solid black" valign="top">
		  <!--КПП получателя-->
      </td>
      <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black"></td>
      <td colspan="10" class=ul></td>
    </tr>
    <tr style="height:5mm">
        <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">Вид оп.</td>
        <td colspan="3" style="border-top:1px solid black; border-right:1px solid black">&nbsp;01 </td>
        <td colspan="2" rowspan="3" style="border-top:1px solid black;border-bottom:1px solid black;border-right:1px solid black">
			<c:choose>
				<c:when test='${letter}'>
				   <table width="100%" style="border-collapse:collapse;"  cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td style="border-bottom:1px solid black">
								Очер.плат.
							</td>
						</tr>
						<tr>
							<td>
								Рез. поле.
							</td>
						</tr>
					</table>
				</c:when>
				<c:otherwise>
				   <table width="100%" style="border-collapse:collapse;"  cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td style="border-bottom:1px solid black;">
								Срок плат.
							</td>
						</tr>
						<tr>
							<td style="border-bottom:1px solid black">
								Очер.плат.
							</td>
						</tr>
						<tr>
							<td>
								Рез. поле.
							</td>
						</tr>
					</table>
				</c:otherwise>
			</c:choose>
		</td>
        <td colspan="5" style="border-top:1px solid black;">&nbsp;

        </td>
    </tr>
    <tr style="height:5mm">
        <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">Наз. пл.</td>
        <td colspan="3" style="border-right:1px solid black">&nbsp;</td>
<!--    <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">Очер. плат.</td>   -->
        <td colspan="5">&nbsp; </td>
    </tr>
    <tr style="height:5mm">
        <td colspan="9" style="border-bottom:1px solid black;border-right:1px solid black">Получатель</td>
        <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Код</td>
        <td colspan="3" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;</td>
<!--    <td colspan="2" style="border-bottom:1px solid black;border-right:1px solid black">&nbsp;Рез. поле.</td> -->
        <td colspan="5" style="border-bottom:1px solid black">&nbsp;</td>
    </tr>
    <tr style="height:5mm" >
        <td id="kbk" style="border-bottom:1px solid black;border-right:1px solid black" colspan="3" align="center">

        </td>
        <td id="okato" style="border-bottom:1px solid black;border-right:1px solid black" colspan="3" align="center">

        </td>
        <td id="taxFund" style="border-bottom:1px solid black;border-right:1px solid black" align="center">

        </td>
        <td id="taxPeriod" style="border-bottom:1px solid black;border-right:1px solid black" colspan="3" align="center">

        </td>
        <td id="taxNumber" style="border-bottom:1px solid black;border-right:1px solid black" colspan="5" align="center">

        </td>
        <td id="taxDate" style="border-bottom:1px solid black;border-right:1px solid black" colspan="4" align="center">

        </td>
        <td id="taxType" style="border-bottom:1px solid black;" colspan="2" align="center">

        </td>
    </tr>
    <tr style="height:25mm">
        <td id="ground" colspan="21" valign="top" align="left">
			<!--Назначение платежа-->

        </td>
   </tr>
   <tr style="height:10mm">
         <td  colspan="21" style="border-bottom:1px solid black;">
			 Назначение платежа
		 </td>
   </tr>
   <tr style="height:15mm">
        <td colspan="5">&nbsp;</td>
        <td colspan="7" align="center" valign="top" style="border-bottom:1px solid black">Подписи</td>
        <td colspan="9" align="center" valign="top">Отметки банка</td>
   </tr>
   <tr style="height:15mm">
        <td colspan="2">&nbsp;</td>
        <td align="center" valign="top">М.П.</td>
        <td colspan="2">&nbsp;</td>
        <td colspan="7" style="border-bottom:1px solid black">&nbsp;</td>
        <td colspan="9" align="center"></td>
   </tr>
   </table>
   <c:if test='${letter}'>
   <br style="page-break-after:always;">
   <table border="0" cellspacing="0" cellpadding="0" style="margin-left:5mm;margin-top:5mm;table-layout:fixed;width:180mm;">
      <col  style="width:10mm"/>
	  <col  style="width:15mm"/>
	  <col  style="width:25mm"/>
	  <col  style="width:30mm"/>
	  <col  style="width:30mm"/>
	  <col  style="width:15mm"/>
	  <col  style="width:55mm"/>
	  <tr  style="height:15mm">
	  	<td style="border-top:1px solid black;border-right:1px solid black;">N ч.<br/>плат.
		</td>
	  	<td style="border-top:1px solid black;border-right:1px solid black;">N плат. ордера
		</td>
	  	<td style="border-top:1px solid black;border-right:1px solid black;">Дата плат. ордера
		</td>
	  	<td style="border-top:1px solid black;border-right:1px solid black;">Сумма частичного платежа
		</td>
	  	<td style="border-top:1px solid black;border-right:1px solid black;">Сумма остатка платежа
		</td>
	  	<td style="border-top:1px solid black;">Под-<br/>пись
		</td>
	  	<td align="center">Дата помещения в<br/>картотеку
		</td>
	  </tr>
	  <tr  style="height:45mm">
	  	<td style="border-top:1px solid black;border-right:1px solid black;">&nbsp;
		</td>
	  	<td style="border-top:1px solid black;border-right:1px solid black;">&nbsp;
		</td>
	  	<td style="border-top:1px solid black;border-right:1px solid black;">&nbsp;
		</td>
	  	<td style="border-top:1px solid black;border-right:1px solid black;">&nbsp;
		</td>
	  	<td style="border-top:1px solid black;border-right:1px solid black;">&nbsp;
		</td>
	  	<td style="border-top:1px solid black;">&nbsp;
		</td>
	  	<td align="center">Отметки банка<br/>плательщика
		</td>
	  </tr>
   </table>
	</c:if>

</tiles:put>
</tiles:insert>
