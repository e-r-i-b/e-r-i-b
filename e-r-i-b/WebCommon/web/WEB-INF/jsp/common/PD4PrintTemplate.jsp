<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<HTML>
<HEAD>
<script language="JavaScript" src="${initParam.resourcesRealPath}/scripts/Utils.js"></script>
    <c:if test="${phiz:isPhizIC()}">
        <%@ include file="/WEB-INF/jsp/common/script-vaultonline.jsp"  %>
    </c:if>
<META content="text/html; charset=windows-1251" http-equiv=Content-Type>
<style type="text/css">
   table {border-collapse:collapse;table-layout:fixed;}
   td {font-size:0.1pt}
  .bigTab {border:0.5pt solid black;}
  .bd_right {border-right:0.5pt solid black}
  .bd_bottom {border-bottom:0.5pt solid black}
  .logo {font-family:Times New Roman;font-size:6pt;}
  .big_font {font-family:Times New Roman;font-size:10pt;}
  .data {font-family:Times New Roman;font-size:7pt;vertical-align:bottom;font-weight: bold}
  .digit {font-family:Arial;font-size:8pt;text-align:center;
          border:0.5pt solid black}
  .label {font-family:Times New Roman;font-size:7pt;}
  .subscript {font-family:Times New Roman;font-size:5pt;vertical-align:top;
              text-align:center}
  .small_font {font-family:Times New Roman;font-size:5pt;}

 </style>
</HEAD>
<BODY onload="javascript:startFill(window.opener.document);">
<c:if test="${phiz:isPhizIC()}">
    <tiles:insert definition="googleTagManager"/>
</c:if>
<%--todo. Видимо будет падать вообще эта jsp. Если понадобиться перевести на tiles--%>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<script>
//заполнение формы
	function getElementValue(doc,id)
	{
		el = doc.getElementById(id);
		if(el == null)return "";
		else return el.value;
	}
	function startFill(doc)
	{
		var el,el1,isTax=false,date,res;

			value = getElementValue(doc,"field(ground)");
			setPaymentValue("paymentReciver",value);
			setPaymentValue("paymentReciver1",value);

			value = getElementValue(doc,"field(amount)");
			setPaymentValue("amount",value);
			setPaymentValue("amount1",value);

			value = getElementValue(doc,"field(amountKop)");
			setPaymentValue("amountFract",formatKop(value));
			setPaymentValue("amount1Fract",formatKop(value));

			value = getElementValue(doc,"field(payerName)");
			setPaymentValue("payerName",value);
			setPaymentValue("payerName1",value);

			value = getElementValue(doc,"field(payerAddress)");
			setPaymentValue("payerAddress",value);
			setPaymentValue("payerAddress1",value);

			value = getElementValue(doc,"field(receiverName)");
			setPaymentValue("receiverName",value);
			setPaymentValue("receiverName1",value);

			value = getElementValue(doc,"field(receiverBankName)");
			setPaymentValue("receiverBankName",value);
			setPaymentValue("receiverBankName1",value);

			value = getElementValue(doc,"field(receiverAccount)");
			fillNumericValues("receiverAccount",value);
			fillNumericValues("receiverAccount2",value);

			value = getElementValue(doc,"field(receiverINN)");
			fillNumericValues("receiverINN",value);
			fillNumericValues("receiverINN1",value);

			value = getElementValue(doc,"field(receiverCorAccount)");
			fillNumericValues("receiverCorAccount",value);
			fillNumericValues("receiverCorAccount2",value);

			value = getElementValue(doc,"field(receiverBIC)");
			fillNumericValues("receiverBIC",value);
			fillNumericValues("receiverBIC1",value);

			value = getElementValue(doc,"field(payerAccount)");
			setPaymentValue("payerAccount",value);
			setPaymentValue("payerAccount2",value);

			value = getElementValue(doc,"field(docDate)");
			try
			{
				date = Str2Date(value);
				if(date != null)
				{
					res = date.getDate();
					setPaymentValue("docDateD",res);
					setPaymentValue("docDateD1",res);

					res = monthToStringOnly(value);
					setPaymentValue("docDateM",res);
					setPaymentValue("docDateM1",res);

					if(date.getYear()>100)
					{
						res = date.getYear()-2000;
						setPaymentValue("docDateY",res);
						setPaymentValue("docDateY1",res);
					}
				}
			}
			catch(er)
			{

			}
	}
	function calcMoney(doc,id)
	{
		el = doc.getElementById("field("+id+")");
		el1 = doc.getElementById("field("+id+"Kop)");
		if(( el != null)&&(el.value != ""))
		{
			str = el.value  + formatKop(el1.value);
			return parseFloat(str);
		}
		return 0;
	}
	function formatKop(value)
	{
		kop="00";
		if( (value == "")||(value == null) )kop ="00"
		if(value.length==1)kop="0" + value;
		if(value.length==2)kop=value;
		return kop;
	}
	function setPaymentValue(id,value)
	{
		var el;
		el = document.getElementById(id);
		if(el != null )el.innerHTML = value;
	}
	function fillNumericValues(id,value)
	{
		var el,i;
		for(i=0;i<value.length;i++)
		{
			el = document.getElementById(id+i);
			if(el != null )el.innerHTML = value.charAt(i);
		}
	}
	function fillCashValues(id,value,valueKOP)
	{
		var elI,elF,i,str,fract,intg;
			elI = document.getElementById(id);
			elF = document.getElementById(id+"Fract");
			if((elI != null )&&(elF != null ))
			{
				str = value.toString();
				i = str.indexOf('.');
				if(i > 0)
				{
					intg = str.substring(0,i);
					if(str.length-i==2)
					{
						fract = str.substring(i+1,i+2)+"0";
					}
					else
					{
						fract = str.substring(i+1,i+3);
					}
					elI.innerHTML = intg;
					elF.innerHTML = fract;
				}
				else
				{
					elI.innerHTML = str;
					elF.innerHTML = "00";
				}
			}
	}
	</script>

 <table cellspacing="0" cellpadding="0" border="1" UNSELECTABLE="on"
         class="bigTab" style="margin-left:5mm;margin-top:5mm;">
  <col UNSELECTABLE="on" style="width:168mm"/>
  <!-- первая половина -->
  <tr >
   <td>
      <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
      <col UNSELECTABLE="on" style="width:47mm"/>
      <col UNSELECTABLE="on" style="width:4mm"/>
      <col UNSELECTABLE="on" style="width:112mm"/>
      <col UNSELECTABLE="on" style="width:5mm"/>
      <!--1-->
      <tr style="height:6mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td style="height:6mm">
         <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:29mm"/>
           <col UNSELECTABLE="on" style="width:65mm"/>
           <col UNSELECTABLE="on" style="width:18mm"/>
           <tr>
              <td class="logo"><img style="height:6mm" src="${globalImagePath}/LogoSBRFsm.jpg"></img></td>
              <td>&nbsp;</td>
              <td class="label">Форма № ПД-4</td>
           </tr>
         </table>
        </td>
        <td>&nbsp;</td>
      </tr>
      <!--2-->
     <tr style="height:4mm">
        <td class="bd_right big_font" align="center">Извещение</td>
        <td>&nbsp;</td>
        <td class="bd_bottom data" id="receiverName"></td>
        <td>&nbsp;</td>
      </tr>
      <!--3-->
      <tr style="height:2mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td class="subscript">(наименование получателя платежа)</td>
        <td>&nbsp;</td>
      </tr>
      <!--4-->
      <tr style="height:6mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:35mm"/>
           <col UNSELECTABLE="on" style="width:7mm"/>
           <col UNSELECTABLE="on" style="width:70mm"/>
           <tr style="height:6mm">
            <td>
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="width:35mm;">
               <tr style="height:4mm">
                 <td class="digit" id="receiverINN0"></td>
                 <td class="digit" id="receiverINN1"></td>
                 <td class="digit" id="receiverINN2"></td>
                 <td class="digit" id="receiverINN3"></td>
        	 	 <td class="digit" id="receiverINN4"></td>
	         	 <td class="digit" id="receiverINN5"></td>
	         	 <td class="digit" id="receiverINN6"></td>
        	 	 <td class="digit" id="receiverINN7"></td>
            	 <td class="digit" id="receiverINN8"></td>
         	 	 <td class="digit" id="receiverINN9"></td>
               </tr>
               <tr style="height:2mm">
                 <td colspan="10" class="subscript">(ИНН получателя платежа)</td>
               </tr>
              </table>
           </td>
           <td>&nbsp;</td>
           <td>
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                style="width:70mm;">
               <tr style="height:4mm">
      		  <td class="digit" id="receiverAccount0"></td>
	          <td class="digit" id="receiverAccount1"></td>
        	  <td class="digit" id="receiverAccount2"></td>
	          <td class="digit" id="receiverAccount3"></td>
        	  <td class="digit" id="receiverAccount4"></td>
	          <td class="digit" id="receiverAccount5"></td>
        	  <td class="digit" id="receiverAccount6"></td>
	          <td class="digit" id="receiverAccount7"></td>
        	  <td class="digit" id="receiverAccount8"></td>
	          <td class="digit" id="receiverAccount9"></td>
        	  <td class="digit" id="receiverAccount10"></td>
	          <td class="digit" id="receiverAccount11"></td>
        	  <td class="digit" id="receiverAccount12"></td>
	          <td class="digit" id="receiverAccount13"></td>
        	  <td class="digit" id="receiverAccount14"></td>
	          <td class="digit" id="receiverAccount15"></td>
        	  <td class="digit" id="receiverAccount16"></td>
	          <td class="digit" id="receiverAccount17"></td>
        	  <td class="digit" id="receiverAccount18"></td>
	          <td class="digit" id="receiverAccount19"></td>
                </tr>
                <tr style="height:2mm">
                  <td colspan="20" class="subscript">(номер счета получателя платежа)</td>
                </tr>
              </table>
           </td>
         </tr>
        </table>
       </td>
       <td>&nbsp;</td>
     </tr>
     <!--5-->
     <tr style="height:6mm">
     <td class="bd_right">&nbsp;</td>
     <td>&nbsp;</td>
     <td colspan="2">
       <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:2mm"/>
           <col UNSELECTABLE="on" style="width:67mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:6mm"/>
           <col UNSELECTABLE="on" style="width:32mm"/>
           <tr style="height:4mm">
             <td class="label" valign="bottom">в</td>
             <td class="bd_bottom data" id="receiverBankName"></td>
             <td>&nbsp;</td>
             <td class="label" valign="bottom">БИК</td>
             <td>
                 <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:32mm;">
                  <tr style="height:4mm">
                   <td class="digit" id="receiverBIC0"></td>
                   <td class="digit" id="receiverBIC1"></td>
                   <td class="digit" id="receiverBIC2"></td>
                   <td class="digit" id="receiverBIC3"></td>
              	   <td class="digit" id="receiverBIC4"></td>
	               <td class="digit" id="receiverBIC5"></td>
	               <td class="digit" id="receiverBIC6"></td>
        	       <td class="digit" id="receiverBIC7"></td>
            	   <td class="digit" id="receiverBIC8"></td>
                  </tr>
                 </table>
             </td>
           </tr>
           <tr style="height:2mm">
             <td>&nbsp;</td>
             <td class="subscript">(наименование банка получателя платежа)</td>
             <td colspan="3">&nbsp;</td>
           </tr>
       </table>
     </td>
    </tr>
    <!--6-->
    <tr style="height:4mm">
     <td class="bd_right">&nbsp;</td>
     <td>&nbsp;</td>
     <td colspan="2">
        <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:50mm"/>
           <col UNSELECTABLE="on" style="width:62mm"/>
          <tr>
            <td class="label" valign="bottom">Номер кор./сч. банка получателя платежа</td>
            <td>
               <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:62mm;">
                  <tr style="height:4mm">
                   <td class="digit" id="receiverCorAccount0"></td>
                   <td class="digit" id="receiverCorAccount1"></td>
                   <td class="digit" id="receiverCorAccount2"></td>
                   <td class="digit" id="receiverCorAccount3"></td>
              	   <td class="digit" id="receiverCorAccount4"></td>
	           	   <td class="digit" id="receiverCorAccount5"></td>
	               <td class="digit" id="receiverCorAccount6"></td>
        	       <td class="digit" id="receiverCorAccount7"></td>
            	   <td class="digit" id="receiverCorAccount8"></td>
         	       <td class="digit" id="receiverCorAccount9"></td>
                   <td class="digit" id="receiverCorAccount10"></td>
                   <td class="digit" id="receiverCorAccount11"></td>
                   <td class="digit" id="receiverCorAccount12"></td>
                   <td class="digit" id="receiverCorAccount13"></td>
              	   <td class="digit" id="receiverCorAccount14"></td>
	               <td class="digit" id="receiverCorAccount15"></td>
	               <td class="digit" id="receiverCorAccount16"></td>
        	       <td class="digit" id="receiverCorAccount17"></td>
            	   <td class="digit" id="receiverCorAccount18"></td>
         	       <td class="digit" id="receiverCorAccount19"></td>
                  </tr>
                 </table>
            </td>
          </tr>
        </table>
    </td>
   </tr>
   <!--7-->
   <tr style="height:5mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td style="height:5mm">
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:65mm"/>
           <col UNSELECTABLE="on" style="width:4mm"/>
           <col UNSELECTABLE="on" style="width:43mm"/>
           <tr style="height:3mm">
             <td class="bd_bottom data" id="paymentReciver"></td>
             <td>&nbsp;</td>
             <td class="bd_bottom data" id="payerAccount"></td>
           </tr>
           <tr style="height:2mm">
             <td class="subscript">(наименование платежа)</td>
             <td>&nbsp;</td>
             <td class="subscript">(номер лицевого счета (код) плательщика)</td>
           </tr>
           </table>
        </td>
        <td>&nbsp;</td>
   </tr>
   <!-- 8 -->
   <tr style="height:9mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:23mm"/>
           <col UNSELECTABLE="on" style="width:89mm"/>
           <tr style="height:5mm">
             <td class="label" valign="bottom">Ф.И.О. плательщика</td>
             <td class="bd_bottom data" id="payerName"></td>
           </tr>
           <tr style="height:4mm">
             <td class="label" valign="bottom">Адрес плательщика</td>
             <td class="bd_bottom data" id="payerAddress"></td>
           </tr>
           </table>
        </td>
        <td>&nbsp;</td>
   </tr>
<!--9-->
   <tr style="height:4mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="2">
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:18mm"/>
           <col UNSELECTABLE="on" style="width:11mm"/>
           <col UNSELECTABLE="on" style="width:6mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:14mm"/>
           <col UNSELECTABLE="on" style="width:29mm"/>
           <col UNSELECTABLE="on" style="width:11mm"/>
           <col UNSELECTABLE="on" style="width:6mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:7mm"/>
           <tr>
             <td class="label">Сумма платежа</td>
             <td class="bd_bottom data" align="right" id="amount">&nbsp;</td>
             <td class="label">&nbsp;&nbsp;руб.</td>
             <td class="bd_bottom data" align="center" id="amountFract"></td>
             <td class="label">&nbsp;&nbsp;коп.</td>
             <td class="label">Сумма платы за услуги</td>
             <td class="bd_bottom data" align="right" id="servicepayment" >&nbsp;</td>
             <td class="label">&nbsp;&nbsp;руб.</td>
             <td class="bd_bottom data" align="center" id="servicepaymentFract"></td>
             <td class="label" >&nbsp;&nbsp;коп.</td>
           </tr>
           </table>
        </td>
   </tr>
   <!--10-->
   <tr style="height:4mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="2">
          <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:7mm"/>
           <col UNSELECTABLE="on" style="width:11mm"/>
           <col UNSELECTABLE="on" style="width:6mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:37mm"/>
           <col UNSELECTABLE="on" style="width:1mm"/>
           <col UNSELECTABLE="on" style="width:7mm"/>
           <col UNSELECTABLE="on" style="width:1mm"/>
           <col UNSELECTABLE="on" style="width:26mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:3mm"/>
           <col UNSELECTABLE="on" style="width:3mm"/>
           <tr style="height:4mm">
             <td class="label">Итого</td>
             <td class="bd_bottom data" align="right" id="sum">&nbsp;</td>
             <td class="label">&nbsp;&nbsp;руб.</td>
             <td class="bd_bottom data" align="center" id="sumFract"></td>
             <td class="label">&nbsp;&nbsp;коп.</td>
             <td class="label">"</td>
             <td class="bd_bottom data" align="center" id="docDateD"></td>
             <td class="label">"</td>
             <td class="bd_bottom data" align="center" id="docDateM"></td>
             <td class="label">200</td>
             <td class="bd_bottom data" align="center" id="docDateY"></td>
             <td class="label">г.</td>
           </tr>
           </table>
        </td>
   </tr>
   <!--11-->
   <tr style="height:4mm">
    <td class="bd_right big_font" align="center" >Кассир</td>
    <td>&nbsp;</td>
    <td colspan="2" class="small_font" >C условиями приема указанной в платежном документе суммы, в т.ч. суммой взимаемой оплаты за услуги</td>
   </tr>
   <!--12-->
   <tr style="height:6mm">
    <td class="bd_right">&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2" valign="top">
       <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
        <col UNSELECTABLE="on" style="width:50mm"/>
        <col UNSELECTABLE="on" style="width:26mm"/>
        <col UNSELECTABLE="on" style="width:35mm"/>
        <tr style="height:4mm">
          <td class="small_font" >банка, ознакомлен и согласен.</td>
          <td class="label">Подпись плательщика</td>
          <td class="bd_bottom">&nbsp;</td>
        </tr>
       </table>
    </td>
   </tr>
      </table>
   </td>
  </tr>
 <!-- вторая половина-->
  <tr>
   <td>
     <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
      <col UNSELECTABLE="on" style="width:47mm"/>
      <col UNSELECTABLE="on" style="width:4mm"/>
      <col UNSELECTABLE="on" style="width:112mm"/>
      <col UNSELECTABLE="on" style="width:5mm"/>
      <!--1-->
      <tr style="height:6mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td class="bd_bottom data" id="receiverName1"></td>
        <td>&nbsp;</td>
      </tr>
      <!--2-->
      <tr style="height:4mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td class="subscript">(наименование получателя платежа)</td>
        <td>&nbsp;</td>
      </tr>
      <!--3-->
      <tr style="height:6mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
             <col UNSELECTABLE="on" style="width:35mm"/>
           <col UNSELECTABLE="on" style="width:7mm"/>
           <col UNSELECTABLE="on" style="width:70mm"/>
           <tr style="height:6mm">
            <td style="height:6mm">
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="width:35mm;">
               <tr style="height:4mm">
                 <td class="digit" id="receiverINN10"></td>
                 <td class="digit" id="receiverINN11"></td>
                 <td class="digit" id="receiverINN12"></td>
                 <td class="digit" id="receiverINN13"></td>
        	 	 <td class="digit" id="receiverINN14"></td>
	         	 <td class="digit" id="receiverINN15"></td>
	         	 <td class="digit" id="receiverINN16"></td>
        	 	 <td class="digit" id="receiverINN17"></td>
            	 <td class="digit" id="receiverINN18"></td>
         	 	 <td class="digit" id="receiverINN19"></td>
               </tr>
               <tr style="height:2mm">
                 <td colspan="10" class="subscript">(ИНН получателя платежа)</td>
               </tr>
              </table>
           </td>
           <td>&nbsp;</td>
           <td>
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                style="width:70mm;">
               <tr style="height:4mm">
      		  <td class="digit" id="receiverAccount20"></td>
	          <td class="digit" id="receiverAccount21"></td>
        	  <td class="digit" id="receiverAccount22"></td>
	          <td class="digit" id="receiverAccount23"></td>
        	  <td class="digit" id="receiverAccount24"></td>
	          <td class="digit" id="receiverAccount25"></td>
        	  <td class="digit" id="receiverAccount26"></td>
	          <td class="digit" id="receiverAccount27"></td>
        	  <td class="digit" id="receiverAccount28"></td>
	          <td class="digit" id="receiverAccount29"></td>
        	  <td class="digit" id="receiverAccount210"></td>
	          <td class="digit" id="receiverAccount211"></td>
        	  <td class="digit" id="receiverAccount212"></td>
	          <td class="digit" id="receiverAccount213"></td>
        	  <td class="digit" id="receiverAccount214"></td>
	          <td class="digit" id="receiverAccount215"></td>
        	  <td class="digit" id="receiverAccount216"></td>
	          <td class="digit" id="receiverAccount217"></td>
        	  <td class="digit" id="receiverAccount218"></td>
	          <td class="digit" id="receiverAccount219"></td>
                </tr>
                <tr style="height:2mm">
                  <td colspan="20" class="subscript">(номер счета получателя платежа)</td>
                </tr>
              </table>
           </td>
         </tr>
        </table>
       </td>
       <td>&nbsp;</td>
     </tr>
     <!--4-->
     <tr style="height:6mm">
     <td class="bd_right">&nbsp;</td>
     <td>&nbsp;</td>
     <td colspan="2">
       <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:2mm"/>
           <col UNSELECTABLE="on" style="width:67mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:6mm"/>
           <col UNSELECTABLE="on" style="width:32mm"/>
           <tr style="height:4mm">
             <td class="label" valign="bottom">в</td>
             <td class="bd_bottom data" id="receiverBankName1"></td>
             <td>&nbsp;</td>
             <td class="label" valign="bottom">БИК</td>
             <td>
                 <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:32mm;">
                  <tr style="height:4mm">
                   <td class="digit" id="receiverBIC10"></td>
                   <td class="digit" id="receiverBIC11"></td>
                   <td class="digit" id="receiverBIC12"></td>
                   <td class="digit" id="receiverBIC13"></td>
              	   <td class="digit" id="receiverBIC14"></td>
	           	   <td class="digit" id="receiverBIC15"></td>
	               <td class="digit" id="receiverBIC16"></td>
        	       <td class="digit" id="receiverBIC17"></td>
            	   <td class="digit" id="receiverBIC18"></td>
                  </tr>
                 </table>
             </td>
           </tr>
           <tr style="height:2mm">
             <td>&nbsp;</td>
             <td class="subscript">(наименование банка получателя платежа)</td>
             <td colspan="3">&nbsp;</td>
           </tr>
       </table>
     </td>
    </tr>
    <!--5-->
    <tr style="height:4mm">
     <td class="bd_right">&nbsp;</td>
     <td>&nbsp;</td>
     <td colspan="2">
        <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:50mm"/>
           <col UNSELECTABLE="on" style="width:62mm"/>
          <tr>
            <td class="label" valign="bottom">Номер кор./сч. банка получателя платежа</td>
            <td>
               <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:62mm;">
                  <tr style="height:4mm">
                   <td class="digit" id="receiverCorAccount20"></td>
                   <td class="digit" id="receiverCorAccount21"></td>
                   <td class="digit" id="receiverCorAccount22"></td>
                   <td class="digit" id="receiverCorAccount23"></td>
              	   <td class="digit" id="receiverCorAccount24"></td>
	           	   <td class="digit" id="receiverCorAccount25"></td>
	               <td class="digit" id="receiverCorAccount26"></td>
        	       <td class="digit" id="receiverCorAccount27"></td>
            	   <td class="digit" id="receiverCorAccount28"></td>
         	       <td class="digit" id="receiverCorAccount29"></td>
                   <td class="digit" id="receiverCorAccount210"></td>
                   <td class="digit" id="receiverCorAccount211"></td>
                   <td class="digit" id="receiverCorAccount212"></td>
                   <td class="digit" id="receiverCorAccount213"></td>
              	   <td class="digit" id="receiverCorAccount214"></td>
	               <td class="digit" id="receiverCorAccount215"></td>
	               <td class="digit" id="receiverCorAccount216"></td>
        	       <td class="digit" id="receiverCorAccount217"></td>
            	   <td class="digit" id="receiverCorAccount218"></td>
         	       <td class="digit" id="receiverCorAccount219"></td>
                  </tr>
                 </table>
            </td>
          </tr>
        </table>
    </td>
   </tr>
   <!--6-->
   <tr style="height:6mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:65mm"/>
           <col UNSELECTABLE="on" style="width:4mm"/>
           <col UNSELECTABLE="on" style="width:43mm"/>
           <tr style="height:4mm">
             <td class="bd_bottom data" id="paymentReciver1"></td>
             <td>&nbsp;</td>
             <td class="bd_bottom data" id="payerAccount2"></td>
           </tr>
           <tr style="height:2mm">
             <td class="subscript">(наименование платежа)</td>
             <td>&nbsp;</td>
             <td class="subscript">(номер лицевого счета (код) плательщика)</td>
           </tr>
           </table>
        </td>
        <td>&nbsp;</td>
   </tr>
   <!-- 7 -->
   <tr style="height:12mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="table-layout:fixed;width:112mm;border-collapse:collapse;">
           <col UNSELECTABLE="on" style="width:23mm"/>
           <col UNSELECTABLE="on" style="width:89mm"/>
           <tr style="height:6mm">
             <td class="label" valign="bottom">Ф.И.О. плательщика</td>
             <td class="bd_bottom data" id="payerName1"></td>
           </tr>
           <tr style="height:5mm">
             <td class="label" valign="bottom">Адрес плательщика</td>
             <td class="bd_bottom data" id="payerAddress1"></td>
           </tr>
           </table>
        </td>
        <td>&nbsp;</td>
   </tr>
   <!--8-->
   <tr style="height:6mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="2">
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:18mm"/>
           <col UNSELECTABLE="on" style="width:11mm"/>
           <col UNSELECTABLE="on" style="width:6mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:14mm"/>
           <col UNSELECTABLE="on" style="width:29mm"/>
           <col UNSELECTABLE="on" style="width:11mm"/>
           <col UNSELECTABLE="on" style="width:6mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:7mm"/>
           <tr style="height:6mm">
             <td class="label" valign="bottom">Сумма платежа</td>
             <td class="bd_bottom data" align="right" id="amount1">&nbsp;</td>
             <td class="label" valign="bottom">&nbsp;&nbsp;руб.</td>
             <td class="bd_bottom data" align="center" id="amount1Fract"></td>
             <td class="label" valign="bottom">&nbsp;&nbsp;коп.</td>
             <td class="label" valign="bottom">Сумма платы за услуги</td>
             <td class="bd_bottom data" align="right" id="servicepayment1">&nbsp;</td>
             <td class="label" valign="bottom">&nbsp;&nbsp;руб.</td>
             <td class="bd_bottom data" align="center" id="servicepayment1Fract"></td>
             <td class="label" valign="bottom">&nbsp;&nbsp;коп.</td>
           </tr>
           </table>
        </td>
   </tr>
   <!--9-->
   <tr style="height:6mm">
        <td class="bd_right big_font" align="center">Квитанция</td>
        <td>&nbsp;</td>
        <td colspan="2">
          <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:7mm"/>
           <col UNSELECTABLE="on" style="width:11mm"/>
           <col UNSELECTABLE="on" style="width:6mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:37mm"/>
           <col UNSELECTABLE="on" style="width:1mm"/>
           <col UNSELECTABLE="on" style="width:7mm"/>
           <col UNSELECTABLE="on" style="width:1mm"/>
           <col UNSELECTABLE="on" style="width:26mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:3mm"/>
           <col UNSELECTABLE="on" style="width:3mm"/>
           <tr style="height:6mm">
             <td class="label" valign="bottom">Итого</td>
             <td class="bd_bottom data" align="right" id="sum1">&nbsp;</td>
             <td class="label" valign="bottom">&nbsp;&nbsp;руб.</td>
             <td class="bd_bottom data" align="center" id="sum1Fract"></td>
             <td class="label" valign="bottom">&nbsp;&nbsp;коп.</td>
             <td class="label">"</td>
             <td class="bd_bottom data" align="center" id="docDateD1"></td>
             <td class="label">"</td>
             <td class="bd_bottom data" align="center" id="docDateM1"></td>
             <td class="label" valign="bottom">200</td>
             <td class="bd_bottom data" align="center" id="docDateY1"></td>
             <td class="label" valign="bottom">г.</td>
           </tr>
           </table>
        </td>
   </tr>
   <!--10-->
   <tr style="height:6mm">
    <td class="bd_right big_font" align="center" >Кассир</td>
    <td>&nbsp;</td>
    <td colspan="2" class="small_font" >C условиями приема указанной в платежном документе суммы, в т.ч. суммой взимаемой оплаты за услуги</td>
</tr>
<!--11-->
<tr style="height:4mm">
    <td class="bd_right">&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2" class="small_font" >банка, ознакомлен и согласен.</td>
 </tr>
<!--12-->
<tr style="height:6mm">
    <td class="bd_right">&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2" valign="top">
    <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">

        <col UNSELECTABLE="on" style="width:50mm"/>
        <col UNSELECTABLE="on" style="width:25mm"/>
        <col UNSELECTABLE="on" style="width:36mm"/>
        <tr style="height:4mm">
          <td>&nbsp;</td>
          <td class="label">Подпись плательщика</td>
          <td class="bd_bottom">&nbsp;</td>
        </tr>
       </table>
    </td>
 </tr>

  </table>
 </td>
 </tr>
</table>
</BODY>
</HTML>