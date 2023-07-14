<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
   td {font-size:1pt;}
  .bigTab {border:1pt solid black;}
  .bd_right {border-right:1pt solid black}
  .bd_bottom {border-bottom:0.25mm solid black}
  .logo {font-family:Times New Roman;font-size:6pt;}
  .big_font {font-family:Times New Roman;font-size:10pt;}
  .data {font-family:Times New Roman;font-size:7pt;vertical-align:bottom;font-weight: bold}
  .digit {font-family:Arial;font-size:8pt;text-align:center;
          border:0.25mm solid black}
  .label {font-family:Times New Roman;font-size:7pt;}
  .subscript {font-family:Times New Roman;font-size:5pt;vertical-align:top;text-align:center}
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
	function getElementValue(doc,id)
	{
		el = doc.getElementById(id);
		if(el == null)return "";
		else return el.value;
	}
//заполнение формы
	function startFill(doc)
	{
		var el,el1,isTax=false,date,res,val;

		value = getElementValue(doc,"field(ground)");
		setPaymentValue("paymentReciver",value);
		setPaymentValue("paymentReciver1",value);

		//заполнение налоговых полей

		{
			value = getElementValue(doc,"field(receiverNameShort)");
			setPaymentValue("receiverNameShort",value);
			setPaymentValue("receiverNameShort1",value);

			value = getElementValue(doc,"field(fine)");
			value1 = getElementValue(doc,"field(fineKop)");
			if(value!="")
			{
				val = value + "." + formatKop(value1);
				setPaymentValue("fine",val);
				setPaymentValue("fine1",val);
			}

			value = getElementValue(doc,"field(penalty)");
			value1 = getElementValue(doc,"field(penaltyKop)");
			if(value!="")
			{
				val = value + "." + formatKop(value1);
				setPaymentValue("penalty",val);
				setPaymentValue("penalty1",val);
			}

			value = getElementValue(doc,"field(taxPeriod1)");
			setPaymentValue("taxPeriod1",value);
			setPaymentValue("taxPeriod11",value);

			value = getElementValue(doc,"field(taxPeriod2)");
			setPaymentValue("taxPeriod2",value);
			setPaymentValue("taxPeriod21",value);

			value = getElementValue(doc,"field(taxPeriod3)");
			setPaymentValue("taxPeriod3",value);
			setPaymentValue("taxPeriod31",value);

			value = getElementValue(doc,"field(amount)");
			value1 = getElementValue(doc,"field(amountKop)");
			val = value + "." + formatKop(value1);
			setPaymentValue("amount",val);
			setPaymentValue("amount1",val);

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

			value = getElementValue(doc,"field(KBK)");
			fillNumericValues("KBK",value);
			fillNumericValues("KBK2",value);

			value = getElementValue(doc,"field(receiverAccount)");
			fillNumericValues("receiverAccount",value);
			fillNumericValues("receiverAccount2",value);

			value = getElementValue(doc,"field(receiverKPP)");
			fillNumericValues("receiverKPP",value);
			fillNumericValues("receiverKPP1",value);

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
			fillNumericValues("payerAccount",value);
			fillNumericValues("payerAccount2",value);

			value = getElementValue(doc,"field(receiverOKATO)");
			fillNumericValues("receiverOKATO",value);
			fillNumericValues("receiverOKATO2",value);

			value = getElementValue(doc,"field(payerINN)");
			fillNumericValues("payerINN",value);
			fillNumericValues("payerINN2",value);

			value = getElementValue(doc,"field(docDate)");
			try{
				date = Str2Date(value);
				if(date != null)
				{
					res = date.getDate();
					res = res + " ";
					res = res + monthToStringOnly(value);
					res = res + " ";
					if(date.getYear()<100)res = res + (date.getYear()+1900);
					else res += date.getYear();
				}
				setPaymentValue("docDate",res);
				setPaymentValue("docDate1",res);
			}
			catch(er)
			{

			}

			res = 0;
			res = calcMoney(doc,"amount");
			res += calcMoney(doc,"fine");
			res += calcMoney(doc,"penalty");

			setPaymentValue("sum",formatMoney(res/100));
			setPaymentValue("sum1",formatMoney(res/100));
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
	</script>
 <table cellspacing="0" cellpadding="0" border="1" UNSELECTABLE="on"
     class="bigTab" style="margin-left:5mm;margin-top:5mm;">
  <col UNSELECTABLE="on" style="width:169mm"/>
  <!-- первая половина -->
  <tr>
   <td>
      <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
      style="">
      <col UNSELECTABLE="on" style="width:47mm"/>
      <col UNSELECTABLE="on" style="width:1mm"/>
      <col UNSELECTABLE="on" style="width:118mm"/>
      <col UNSELECTABLE="on" style="width:3mm"/>
      <!--1-->
      <tr style="height:6mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>
         <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:30mm"/>
           <col UNSELECTABLE="on" style="width:60mm"/>
           <col UNSELECTABLE="on" style="width:28mm"/>
           <tr style="height:6mm">
              <td class="logo"><img style="height:6mm" src="${globalImagePath}/LogoSBRFsm.jpg"></img></td>
              <td>&nbsp;</td>
              <td class="label">Форма № ПД-4сб (налог)</td>
           </tr>
         </table>
        </td>
        <td>&nbsp;</td>
      </tr>
      <!--2-->
     <tr style="height:4mm">
        <td class="bd_right big_font" valign="top" align="center">Извещение</td>
        <td>&nbsp;</td>
        <td colspan="2">
          <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:77mm"/>
           <col UNSELECTABLE="on" style="width:9mm"/>
           <col UNSELECTABLE="on" style="width:32mm"/>
           <tr style="height:4mm" valign="bottom">
             <td class="bd_bottom data" id="receiverName" ></td>
             <td class="label" align="center">КПП</td>
             <td valign="bottom">
	       <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                      style="width:32mm;">
               <tr style="height:4mm">
                 <td class="digit" id="receiverKPP0"></td>
                 <td class="digit" id="receiverKPP1"></td>
                 <td class="digit" id="receiverKPP2"></td>
        	 	 <td class="digit" id="receiverKPP3"></td>
	         	 <td class="digit" id="receiverKPP4"></td>
	         	 <td class="digit" id="receiverKPP5"></td>
        	 	 <td class="digit" id="receiverKPP6"></td>
            	 <td class="digit" id="receiverKPP7"></td>
         	 	 <td class="digit" id="receiverKPP8"></td>
               </tr>
              </table>
             </td>
           </tr>
         </table>
        </td>
     </tr>
     <!--3-->
      <tr style="height:8mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="2" >
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="table-layout:fixed;width:118mm;border-collapse:collapse;">
           <col UNSELECTABLE="on" style="width:36mm"/>
           <col UNSELECTABLE="on" style="width:41mm"/>
           <col UNSELECTABLE="on" style="width:40mm"/>
           <tr style="height:8mm">
            <td style="height:8mm">
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="width:35mm;">
               <tr style="height:1mm"><td colspan="10" style="font-size:1pt">&nbsp;</td></tr>
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
               <tr style="height:3mm">
                 <td colspan="10" class="subscript">ИНН получателя платежа*</td>
               </tr>
              </table>
           </td>
           <td valign="top">
             <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="width:40mm;border-collapse:collapse;">
               <tr style="height:2mm"><td class="subscript">(наименование получателя платежа)</td></tr>
               <tr style="height:3mm"><td class="bd_bottom data" id="receiverNameShort"></td></tr>
               <tr style="height:3mm"><td class="subscript">и его сокращенное наименование</td></tr>
             </table>
           </td>
           <td style="height:8mm" align="right">
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                style="width:40mm;border-collapse:collapse;">
               <tr style="height:1mm"><td colspan="11" style="font-size:1pt">&nbsp;</td></tr>
               <tr style="height:4mm">
      		  <td class="digit" id="receiverOKATO0"></td>
	          <td class="digit" id="receiverOKATO1"></td>
        	  <td class="digit" id="receiverOKATO2"></td>
	          <td class="digit" id="receiverOKATO3"></td>
        	  <td class="digit" id="receiverOKATO4"></td>
	          <td class="digit" id="receiverOKATO5"></td>
        	  <td class="digit" id="receiverOKATO6"></td>
	          <td class="digit" id="receiverOKATO7"></td>
        	  <td class="digit" id="receiverOKATO8"></td>
	          <td class="digit" id="receiverOKATO9"></td>
        	  <td class="digit" id="receiverOKATO10"></td>
                </tr>
                <tr style="height:3mm">
                  <td colspan="11" class="subscript">(Код ОКАТО)</td>
                </tr>
              </table>
           </td>
         </tr>
        </table>
       </td>
     </tr>
     <!--4-->
     <tr style="height:6mm">
     <td class="bd_right">&nbsp;</td>
     <td>&nbsp;</td>
     <td colspan="2">
       <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:71mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:42mm"/>
           <tr style="height:4mm">
           <td>
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:71mm;">
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
         	   <td class="digit" id="receiverAccount9" ></td>
                   <td class="digit" id="receiverAccount10" ></td>
                   <td class="digit" id="receiverAccount11" ></td>
                   <td class="digit" id="receiverAccount12" ></td>
                   <td class="digit" id="receiverAccount13" ></td>
              	   <td class="digit" id="receiverAccount14" ></td>
	           <td class="digit" id="receiverAccount15" ></td>
	           <td class="digit" id="receiverAccount16" ></td>
        	   <td class="digit" id="receiverAccount17" ></td>
            	   <td class="digit" id="receiverAccount18"></td>
         	   <td class="digit" id="receiverAccount19"></td>
                  </tr>
                 </table>
               </td>
               <td class="label" align="center">в</td>
               <td class="bd_bottom data" id="receiverBankName" align="center"></td>
            </tr>
            <tr style="height:2mm">
                <td class="subscript">(номер счета получателя платежа)</td>
                <td style="font-size:1pt">&nbsp;</td>
                <td class="subscript">(наименование банка)</td>
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
           <col UNSELECTABLE="on" style="width:7mm"/>
           <col UNSELECTABLE="on" style="width:32mm"/>
           <col UNSELECTABLE="on" style="width:12mm"/>
           <col UNSELECTABLE="on" style="width:67mm"/>
           <tr style="height:4mm">
             <td class="label" valign="bottom">БИК:</td>
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
             <td class="label" valign="bottom" align="center">Кор./сч.:</td>
             <td>
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:67mm;border-collapse:collapse;">
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
                   <td class="digit" id="receiverCorAccount10" ></td>
                   <td class="digit" id="receiverCorAccount11"></td>
                   <td class="digit" id="receiverCorAccount12"></td>
                   <td class="digit" id="receiverCorAccount13" ></td>
              	   <td class="digit" id="receiverCorAccount14"></td>
	           <td class="digit" id="receiverCorAccount15"></td>
	           <td class="digit" id="receiverCorAccount16"></td>
        	   <td class="digit" id="receiverCorAccount17"></td>
            	   <td class="digit" id="receiverCorAccount18" ></td>
         	   <td class="digit" id="receiverCorAccount19"></td>
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
        <td style="height:6mm">
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:49.8mm"/>
		   <col UNSELECTABLE="on" style="width:1mm"/>
		   <col UNSELECTABLE="on" style="width:67mm"/>
           <tr style="height:4mm">
             <td class="bd_bottom data" id="paymentReciver" name="receiverName"></td>
			 <td >&nbsp</td>
			 <td  valign="bottom">
                 <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:67mm;border-collapse:collapse;">
                  <tr style="height:3.6mm">
                   <td class="digit" id="KBK0" name="KBK0"></td>
                   <td class="digit" id="KBK1" name="KBK1"></td>
                   <td class="digit" id="KBK2" name="KBK2"></td>
              	   <td class="digit" id="KBK3" name="KBK3"></td>
	           	   <td class="digit" id="KBK4" name="KBK4"></td>
	               <td class="digit" id="KBK5" name="KBK5"></td>
        	       <td class="digit" id="KBK6" name="KBK6"></td>
				   <td class="digit" id="KBK7" name="KBK7"></td>
				   <td class="digit" id="KBK8" name="KBK8"></td>
				   <td class="digit" id="KBK9" name="KBK9"></td>
				   <td class="digit" id="KBK10" name="KBK10"></td>
                   <td class="digit" id="KBK11" name="KBK11"></td>
                   <td class="digit" id="KBK12" name="KBK12"></td>
              	   <td class="digit" id="KBK13" name="KBK13"></td>
	           	   <td class="digit" id="KBK14" name="KBK14"></td>
	               <td class="digit" id="KBK15" name="KBK15"></td>
        	       <td class="digit" id="KBK16" name="KBK16"></td>
				   <td class="digit" id="KBK17" name="KBK17"></td>
				   <td class="digit" id="KBK18" name="KBK18"></td>
				   <td class="digit" id="KBK19" name="KBK19"></td>
				  </tr>
                 </table>
             </td>
           </tr>
           <tr style="height:2mm">
             <td class="subscript">(наименование платежа)</td>
			 <td  class="subscript">&nbsp</td>
			 <td class="subscript">(код бюджетной классификации)</td>
           </tr>
           </table>
        </td>
        <td>&nbsp;</td>
   </tr>
   <!-- 7 -->
   <tr style="height:4mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td style="height:4mm">
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:28mm"/>
           <col UNSELECTABLE="on" style="width:90mm"/>
           <tr style="height:4mm">
             <td class="label" valign="bottom" >Плательщик (Ф.И.О.)</td>
             <td class="bd_bottom data" id="payerName" name="payerName"></td>
           </tr>
         </table>
        </td>
        <td>&nbsp;</td>
   </tr>
  <!-- 8 -->
   <tr style="height:4mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:26mm"/>
           <col UNSELECTABLE="on" style="width:92mm"/>
           <tr style="height:4mm">
             <td class="label" valign="bottom">Адрес плательщика:</td>
             <td class="bd_bottom data" id="payerAddress1" name="payerAddress1"></td>
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
           <col UNSELECTABLE="on" style="width:22mm"/>
           <col UNSELECTABLE="on" style="width:38mm"/>
           <col UNSELECTABLE="on" style="width:23mm"/>
           <col UNSELECTABLE="on" style="width:36mm"/>
           <tr>
             <td class="label">ИНН плательщика</td>
             <td valign="bottom">
                 <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:38mm;">
                  <tr style="height:3.5mm">
                   <td class="digit" id="payerINN0" name="payerINN0"></td>
                   <td class="digit" id="payerINN1" name="payerINN1"></td>
                   <td class="digit" id="payerINN2" name="payerINN2"></td>
                   <td class="digit" id="payerINN3" name="payerINN3"></td>
              	   <td class="digit" id="payerINN4" name="payerINN4"></td>
	               <td class="digit" id="payerINN5" name="payerINN5"></td>
	               <td class="digit" id="payerINN6" name="payerINN6"></td>
        	       <td class="digit" id="payerINN7" name="payerINN7"></td>
            	   <td class="digit" id="payerINN8" name="payerINN8"></td>
         	       <td class="digit" id="payerINN9" name="payerINN9"></td>
                   <td class="digit" id="payerINN10" name="payerINN10"></td>
                   <td class="digit" id="payerINN11" name="payerINN11"></td>
                  </tr>
                 </table>
             </td>
             <td class="label" align="center">№ л/с плательщика</td>
             <td valign="bottom">
                 <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:36mm;">
                  <tr style="height:3.5mm">
                   <td class="digit" id="payerAccount0" name="payerAccount0"></td>
                   <td class="digit" id="payerAccount1" name="payerAccount1"></td>
                   <td class="digit" id="payerAccount2" name="payerAccount2"></td>
                   <td class="digit" id="payerAccount3" name="payerAccount3"></td>
              	   <td class="digit" id="payerAccount4" name="payerAccount4"></td>
	               <td class="digit" id="payerAccount5" name="payerAccount5"></td>
	               <td class="digit" id="payerAccount6" name="payerAccount6"></td>
        	       <td class="digit" id="payerAccount7" name="payerAccount7"></td>
            	   <td class="digit" id="payerAccount8" name="payerAccount8"></td>
         	   	   <td class="digit" id="payerAccount9" name="payerAccount9"></td>
                   <td class="digit" id="payerAccount10" name="payerAccount10"></td>
                   <td class="digit" id="payerAccount11" name="payerAccount11"></td>
                  </tr>
                 </table>
             </td>
           </tr>
           </table>
        </td>
   </tr>
   <!--10-->
   <tr style="height:3.5mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="2">
          <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="table-layout:fixed;width:118mm;border-collapse:collapse;">
           <col UNSELECTABLE="on" style="width:23mm"/>
           <col UNSELECTABLE="on" style="width:33mm"/>
           <col UNSELECTABLE="on" style="width:29mm"/>
           <col UNSELECTABLE="on" style="width:33mm"/>
           <tr style="height:3.5mm">
             <td class="label">Платеж по сроку:</td>
             <td class="bd_bottom data"><span id="taxPeriod1" name="taxPeriod1"></span>.<span id="taxPeriod2" name="taxPeriod2"></span>.<span id="taxPeriod3" name="taxPeriod3"></span></td>
             <td class="label">&nbsp;Сумма налога (сбора):</td>
             <td class="bd_bottom data" id="amount" name="amount"></td>
           </tr>
           </table>
        </td>
   </tr>
   <!--11-->
   <tr style="height:3.5mm">
    <td class="bd_right big_font" align="center" >Кассир</td>
    <td>&nbsp;</td>
    <td colspan="2">
      <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:8mm"/>
           <col UNSELECTABLE="on" style="width:19mm"/>
           <col UNSELECTABLE="on" style="width:12mm"/>
           <col UNSELECTABLE="on" style="width:19mm"/>
           <col UNSELECTABLE="on" style="width:22mm"/>
           <col UNSELECTABLE="on" style="width:38mm"/>
           <tr style="height:3.5mm">
             <td class="label">Пеня:</td>
             <td class="bd_bottom data" id="fine" name="fine">0</td>
             <td class="label">&nbsp;Штраф:</td>
             <td class="bd_bottom data"  id="penalty" name="penalty">0</td>
             <td class="label" >&nbsp;Итого к уплате:</td>
             <td class="bd_bottom data"  id="sum"></td>
           </tr>
           </table>
    </td>
   </tr>
   <!--12-->
   <tr style="height:4mm">
    <td class="bd_right">&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2">
       <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
        <col UNSELECTABLE="on" style="width:27mm"/>
        <col UNSELECTABLE="on" style="width:38mm"/>
        <col UNSELECTABLE="on" style="width:7mm"/>
        <col UNSELECTABLE="on" style="width:46mm"/>
        <tr style="height:4mm">
          <td class="label">Плательщик (подпись):</td>
          <td class="data">&nbsp;</td>
          <td class="label">Дата:</td>
          <td class="data" id="docDate" name="docDate"></td>
        </tr>
       </table>
    </td>
   </tr>
    <!--13-->
   <tr style="height:3mm">
    <td class="bd_right">&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2" class="small_font" valign="top">* или иной государственный орган исполнительной власти
    </td>
   </tr>
      </table>
   </td>
  </tr>
 <!-- вторая половина-->
  <tr >
   <td style="height:73mm">
     <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
      <col UNSELECTABLE="on" style="width:47mm"/>
      <col UNSELECTABLE="on" style="width:1mm"/>
      <col UNSELECTABLE="on" style="width:118mm"/>
      <col UNSELECTABLE="on" style="width:3mm"/>
      <!--1-->
      <tr style="height:10mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="2">
          <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:77mm"/>
           <col UNSELECTABLE="on" style="width:9mm"/>
           <col UNSELECTABLE="on" style="width:32mm"/>
           <tr style="height:10mm" valign="bottom">
             <td class="bd_bottom data" id="receiverName1"></td>
             <td class="label" align="center">КПП</td>
             <td valign="bottom">
	       <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="width:32mm;">
               <tr style="height:4mm">
                 <td class="digit" id="receiverKPP10" name="receiverKPP10"></td>
                 <td class="digit" id="receiverKPP11" name="receiverKPP11"></td>
                 <td class="digit" id="receiverKPP12" name="receiverKPP12"></td>
        	 	 <td class="digit" id="receiverKPP13" name="receiverKPP13"></td>
	         	 <td class="digit" id="receiverKPP14" name="receiverKPP14"></td>
	         	 <td class="digit" id="receiverKPP15" name="receiverKPP15"></td>
        	 	 <td class="digit" id="receiverKPP16" name="receiverKPP16"></td>
            	 <td class="digit" id="receiverKPP17" name="receiverKPP17"></td>
         	 	 <td class="digit" id="receiverKPP18" name="receiverKPP18"></td>
               </tr>
              </table>
             </td>
           </tr>
         </table>
        </td>
      </tr>
     <!--2-->
 <tr style="height:9mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="2" >
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="table-layout:fixed;width:118mm;border-collapse:collapse;">
           <col UNSELECTABLE="on" style="width:36mm"/>
           <col UNSELECTABLE="on" style="width:41mm"/>
           <col UNSELECTABLE="on" style="width:40mm"/>
           <tr style="height:9mm">
            <td style="height:9mm">
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="width:35mm;">
               <tr style="height:2mm"><td colspan="10" style="font-size:1pt">&nbsp;</td></tr>
               <tr style="height:4mm">
                 <td class="digit" id="receiverINN10" name="receiverINN10"></td>
                 <td class="digit" id="receiverINN11" name="receiverINN11"></td>
                 <td class="digit" id="receiverINN12" name="receiverINN12"></td>
                 <td class="digit" id="receiverINN13" name="receiverINN13"></td>
        	 	 <td class="digit" id="receiverINN14" name="receiverINN14"></td>
	         	 <td class="digit" id="receiverINN15" name="receiverINN15"></td>
	         	 <td class="digit" id="receiverINN16" name="receiverINN16"></td>
        	 	 <td class="digit" id="receiverINN17" name="receiverINN17"></td>
            	 <td class="digit" id="receiverINN18" name="receiverINN18"></td>
         	 	 <td class="digit" id="receiverINN19" name="receiverINN19"></td>
               </tr>
               <tr style="height:3mm">
                 <td colspan="10" class="subscript">ИНН получателя платежа*</td>
               </tr>
              </table>
           </td>
           <td valign="top">
             <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="width:40mm;">
               <tr style="height:2mm"><td class="subscript">(наименование получателя платежа)</td></tr>
               <tr style="height:4mm"><td class="bd_bottom data" id="receiverNameShort1" name="receiverNameShort1"></td></tr>
               <tr style="height:3mm"><td class="subscript">и его сокращенное наименование</td></tr>
             </table>
           </td>
           <td style="height:9mm" align="right">
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                style="width:40mm;">
               <tr style="height:2mm"><td colspan="11" style="font-size:1pt">&nbsp;</td></tr>
               <tr style="height:4mm">
      		  <td class="digit" id="receiverOKATO20" name="receiverOKATO20"></td>
	          <td class="digit" id="receiverOKATO21" name="receiverOKATO21"></td>
        	  <td class="digit" id="receiverOKATO22" name="receiverOKATO22"></td>
	          <td class="digit" id="receiverOKATO23" name="receiverOKATO23"></td>
        	  <td class="digit" id="receiverOKATO24" name="receiverOKATO24"></td>
	          <td class="digit" id="receiverOKATO25" name="receiverOKATO25"></td>
        	  <td class="digit" id="receiverOKATO26" name="receiverOKATO26"></td>
	          <td class="digit" id="receiverOKATO27" name="receiverOKATO27"></td>
        	  <td class="digit" id="receiverOKATO28" name="receiverOKATO28"></td>
	          <td class="digit" id="receiverOKATO29" name="receiverOKATO29"></td>
        	  <td class="digit" id="receiverOKATO210" name="receiverOKATO210"></td>
                </tr>
                <tr style="height:3mm">
                  <td colspan="11" class="subscript">(Код ОКАТО)</td>
                </tr>
              </table>
           </td>
         </tr>
        </table>
       </td>
     </tr>
     <!--3-->
    <tr style="height:7mm">
     <td class="bd_right">&nbsp;</td>
     <td>&nbsp;</td>
     <td colspan="2">
       <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="table-layout:fixed;width:118mm;border-collapse:collapse;">
           <col UNSELECTABLE="on" style="width:71mm"/>
           <col UNSELECTABLE="on" style="width:5mm"/>
           <col UNSELECTABLE="on" style="width:42mm"/>
           <tr style="height:4mm">
            <td>
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:71mm;">
                  <tr style="height:4mm">
                   <td class="digit" id="receiverAccount20" name="receiverAccount20"></td>
                   <td class="digit" id="receiverAccount21" name="receiverAccount21"></td>
                   <td class="digit" id="receiverAccount22" name="receiverAccount22"></td>
                   <td class="digit" id="receiverAccount23" name="receiverAccount23"></td>
              	   <td class="digit" id="receiverAccount24" name="receiverAccount24"></td>
	           	   <td class="digit" id="receiverAccount25" name="receiverAccount25"></td>
	               <td class="digit" id="receiverAccount26" name="receiverAccount26"></td>
        	       <td class="digit" id="receiverAccount27" name="receiverAccount27"></td>
            	   <td class="digit" id="receiverAccount28" name="receiverAccount28"></td>
         	       <td class="digit" id="receiverAccount29" name="receiverAccount29"></td>
                   <td class="digit" id="receiverAccount210" name="receiverAccount210"></td>
                   <td class="digit" id="receiverAccount211" name="receiverAccount211"></td>
                   <td class="digit" id="receiverAccount212" name="receiverAccount212"></td>
                   <td class="digit" id="receiverAccount213" name="receiverAccount213"></td>
              	   <td class="digit" id="receiverAccount214" name="receiverAccount214"></td>
	               <td class="digit" id="receiverAccount215" name="receiverAccount215"></td>
	               <td class="digit" id="receiverAccount216" name="receiverAccount216"></td>
        	       <td class="digit" id="receiverAccount217" name="receiverAccount217"></td>
            	   <td class="digit" id="receiverAccount218" name="receiverAccount218"></td>
         	       <td class="digit" id="receiverAccount219" name="receiverAccount219"></td>
                  </tr>
                 </table>
               </td>
               <td class="label" align="center">в</td>
               <td class="bd_bottom data" id="receiverBankName1" name="receiverBankName1"  align="center"></td>
            </tr>
            <tr style="height:3mm">
                <td class="subscript">(номер счета получателя платежа)</td>
                <td style="font-size:1pt">&nbsp;</td>
                <td class="subscript">(наименование банка)</td>
            </tr>
        </table>
     </td>
     </tr>
     <!--4-->
     <tr style="height:4mm">
     <td class="bd_right">&nbsp;</td>
     <td>&nbsp;</td>
     <td colspan="2">
       <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:7mm"/>
           <col UNSELECTABLE="on" style="width:32mm"/>
           <col UNSELECTABLE="on" style="width:12mm"/>
           <col UNSELECTABLE="on" style="width:67mm"/>
           <tr style="height:4mm">
             <td class="label" valign="bottom">БИК:</td>
             <td style="height:4mm">
                 <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:32mm;">
                  <tr style="height:4mm">
                   <td class="digit" id="receiverBIC10" name="receiverBIC10"></td>
                   <td class="digit" id="receiverBIC11" name="receiverBIC11"></td>
                   <td class="digit" id="receiverBIC12" name="receiverBIC12"></td>
              	   <td class="digit" id="receiverBIC13" name="receiverBIC13"></td>
	           	   <td class="digit" id="receiverBIC14" name="receiverBIC14"></td>
	           	   <td class="digit" id="receiverBIC15" name="receiverBIC15"></td>
        	   	   <td class="digit" id="receiverBIC16" name="receiverBIC16"></td>
            	   <td class="digit" id="receiverBIC17" name="receiverBIC17"></td>
         	   	   <td class="digit" id="receiverBIC18" name="receiverBIC18"></td>
                  </tr>
                 </table>
             </td>
             <td class="label" valign="bottom" align="center">Кор./сч.:</td>
             <td style="height:4mm">
              <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:67mm;">
                  <tr style="height:4mm">
                   <td class="digit" id="receiverCorAccount20" name="receiverCorAccount20"></td>
                   <td class="digit" id="receiverCorAccount21" name="receiverCorAccount21"></td>
                   <td class="digit" id="receiverCorAccount22" name="receiverCorAccount22"></td>
                   <td class="digit" id="receiverCorAccount23" name="receiverCorAccount23"></td>
              	   <td class="digit" id="receiverCorAccount24" name="receiverCorAccount24"></td>
	              <td class="digit" id="receiverCorAccount25" name="receiverCorAccount25"></td>
	               <td class="digit" id="receiverCorAccount26" name="receiverCorAccount26"></td>
        	       <td class="digit" id="receiverCorAccount27" name="receiverCorAccount27"></td>
            	   <td class="digit" id="receiverCorAccount28" name="receiverCorAccount28"></td>
         	       <td class="digit" id="receiverCorAccount29" name="receiverCorAccount29"></td>
                   <td class="digit" id="receiverCorAccount210" name="receiverCorAccount210"></td>
                   <td class="digit" id="receiverCorAccount211" name="receiverCorAccount211"></td>
                   <td class="digit" id="receiverCorAccount212" name="receiverCorAccount212"></td>
                   <td class="digit" id="receiverCorAccount213" name="receiverCorAccount213"></td>
              	   <td class="digit" id="receiverCorAccount214" name="receiverCorAccount214"></td>
	               <td class="digit" id="receiverCorAccount215" name="receiverCorAccount215"></td>
	               <td class="digit" id="receiverCorAccount216" name="receiverCorAccount216"></td>
        	   	   <td class="digit" id="receiverCorAccount217" name="receiverCorAccount217"></td>
            	   <td class="digit" id="receiverCorAccount218" name="receiverCorAccount218"></td>
         	   	   <td class="digit" id="receiverCorAccount219" name="receiverCorAccount219"></td>
                  </tr>
                 </table>
               </td>
           </tr>
       </table>
     </td>
    </tr>

   <!--5-->
      <tr style="height:9mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
               style="table-layout:fixed;width:118mm;border-collapse:collapse;">
           <col UNSELECTABLE="on" style="width:49.8mm"/>
		   <col UNSELECTABLE="on" style="width:1mm"/>
		   <col UNSELECTABLE="on" style="width:67mm"/>
           <tr style="height:6mm">
             <td class="bd_bottom data" id="paymentReciver1" name="receiverName1"></td>
			 <td >&nbsp</td>
			 <td align="center" valign="bottom">
                 <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:67mm;">
                  <tr style="height:4mm">
                   <td class="digit" id="KBK20" name="KBK20"></td>
                   <td class="digit" id="KBK21" name="KBK21"></td>
                   <td class="digit" id="KBK22" name="KBK22"></td>
              	   <td class="digit" id="KBK23" name="KBK23"></td>
	           	   <td class="digit" id="KBK24" name="KBK24"></td>
	               <td class="digit" id="KBK25" name="KBK25"></td>
        	       <td class="digit" id="KBK26" name="KBK26"></td>
				   <td class="digit" id="KBK27" name="KBK27"></td>
				   <td class="digit" id="KBK28" name="KBK28"></td>
				   <td class="digit" id="KBK29" name="KBK29"></td>
				   <td class="digit" id="KBK210" name="KBK210"></td>
                   <td class="digit" id="KBK211" name="KBK211"></td>
                   <td class="digit" id="KBK212" name="KBK212"></td>
              	   <td class="digit" id="KBK213" name="KBK213"></td>
	           	   <td class="digit" id="KBK214" name="KBK214"></td>
	               <td class="digit" id="KBK215" name="KBK215"></td>
        	       <td class="digit" id="KBK216" name="KBK216"></td>
				   <td class="digit" id="KBK217" name="KBK217"></td>
				   <td class="digit" id="KBK218" name="KBK218"></td>
				   <td class="digit" id="KBK219" name="KBK219"></td>
            	  </tr>
                 </table>
             </td>
           </tr>
           <tr style="height:3mm">
             <td class="subscript">(наименование платежа)</td>
			 <td class="subscript">&nbsp</td>
			 <td class="subscript">(код бюджетной классификации)</td>
           </tr>
           </table>
        </td>
        <td>&nbsp;</td>
   </tr>
   <!-- 6 -->
    <tr style="height:4mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td>
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:28mm"/>
           <col UNSELECTABLE="on" style="width:90mm"/>
           <tr style="height:4mm">
             <td class="label" valign="bottom">Плательщик (Ф.И.О.)</td>
             <td class="bd_bottom data" id="payerName1" name="payerName1"></td>
           </tr>
         </table>
        </td>
        <td>&nbsp;</td>
   </tr>
  <!-- 7 -->
   <tr style="height:5mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td style="height:5mm">
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:26mm"/>
           <col UNSELECTABLE="on" style="width:92mm"/>
           <tr style="height:5mm">
             <td class="label" valign="bottom">Адрес плательщика:</td>
             <td class="bd_bottom data"  id="payerAddress" name="payerAddress"></td>
           </tr>
           </table>
        </td>
        <td>&nbsp;</td>
   </tr>
   <!--8-->
      <tr style="height:5mm">
        <td class="bd_right">&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="2">
           <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:22mm"/>
           <col UNSELECTABLE="on" style="width:38mm"/>
           <col UNSELECTABLE="on" style="width:23mm"/>
           <col UNSELECTABLE="on" style="width:36mm"/>
           <tr style="height:5mm">
             <td class="label">ИНН плательщика</td>
             <td valign="bottom">
                 <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:38mm;">
                  <tr style="height:4mm">
                   <td class="digit" id="payerINN20" name="payerINN20"></td>
                   <td class="digit" id="payerINN21" name="payerINN21"></td>
                   <td class="digit" id="payerINN22" name="payerINN22"></td>
                   <td class="digit" id="payerINN23" name="payerINN23"></td>
              	   <td class="digit" id="payerINN24" name="payerINN24"></td>
	               <td class="digit" id="payerINN25" name="payerINN25"></td>
	               <td class="digit" id="payerINN26" name="payerINN26"></td>
        	       <td class="digit" id="payerINN27" name="payerINN27"></td>
            	   <td class="digit" id="payerINN28" name="payerINN28"></td>
         	   	   <td class="digit" id="payerINN29" name="payerINN29"></td>
                   <td class="digit" id="payerINN210" name="payerINN210"></td>
                   <td class="digit" id="payerINN211" name="payerINN211"></td>
                  </tr>
                 </table>
             </td>
             <td class="label" align="center">№ л/с плательщика</td>
             <td valign="bottom">
                 <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on"
                  style="width:36mm;">
                  <tr style="height:4mm">
                   <td class="digit"  id="payerAccount20" name="payerAccount20"></td>
                   <td class="digit" id="payerAccount21" name="payerAccount21"></td>
                   <td class="digit" id="payerAccount22" name="payerAccount22"></td>
                   <td class="digit" id="payerAccount23" name="payerAccount23"></td>
              	   <td class="digit" id="payerAccount24" name="payerAccount24"></td>
	           	   <td class="digit" id="payerAccount25" name="payerAccount25"></td>
	           	   <td class="digit" id="payerAccount26" name="payerAccount26"></td>
        	   	   <td class="digit" id="payerAccount27" name="payerAccount27"></td>
            	   <td class="digit" id="payerAccount28" name="payerAccount28"></td>
         	   	   <td class="digit" id="payerAccount29" name="payerAccount29"></td>
                   <td class="digit" id="payerAccount210" name="payerAccount210"></td>
                   <td class="digit" id="payerAccount211" name="payerAccount211"></td>
                  </tr>
                 </table>
             </td>
           </tr>
           </table>
        </td>
   </tr>
   <!--9-->
      <tr style="height:4mm">
        <td class="bd_right big_font" align="center">Квитанция</td>
        <td>&nbsp;</td>
        <td colspan="2">
          <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:23mm"/>
           <col UNSELECTABLE="on" style="width:33mm"/>
           <col UNSELECTABLE="on" style="width:29mm"/>
           <col UNSELECTABLE="on" style="width:33mm"/>
           <tr style="height:4mm">
             <td class="label">Платеж по сроку:</td>
             <td class="bd_bottom data"><span id="taxPeriod11" name="taxPeriod11"></span>.<span id="taxPeriod21" name="taxPeriod21"></span>.<span id="taxPeriod31" name="taxPeriod31"></span></td>
             <td class="label">&nbsp;Сумма налога (сбора):</td>
             <td class="bd_bottom data"  id="amount1" name="amount1"></td>
           </tr>
           </table>
        </td>
   </tr>
   <!--10-->
   <tr style="height:5mm">
    <td class="bd_right">&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2">
      <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
           <col UNSELECTABLE="on" style="width:8mm"/>
           <col UNSELECTABLE="on" style="width:19mm"/>
           <col UNSELECTABLE="on" style="width:12mm"/>
           <col UNSELECTABLE="on" style="width:19mm"/>
           <col UNSELECTABLE="on" style="width:22mm"/>
           <col UNSELECTABLE="on" style="width:38mm"/>
           <tr style="height:5mm">
             <td class="label">Пеня:</td>
             <td class="bd_bottom data" id="fine1" name="fine1">0</td>
             <td class="label">&nbsp;Штраф:</td>
             <td class="bd_bottom data" id="penalty1" name="penalty1">0</td>
             <td class="label">&nbsp;Итого к уплате:</td>
             <td class="bd_bottom data"  id="sum1"></td>
           </tr>
           </table>
    </td>
   </tr>
   <!--11-->
    <tr style="height:5mm">
    <td class="bd_right big_font" align="center" valign="top">Кассир</td>
    <td>&nbsp;</td>
    <td colspan="2">
       <table cellspacing="0" cellpadding="0" border="0" UNSELECTABLE="on">
        <col UNSELECTABLE="on" style="width:27mm"/>
        <col UNSELECTABLE="on" style="width:38mm"/>
        <col UNSELECTABLE="on" style="width:7mm"/>
        <col UNSELECTABLE="on" style="width:46mm"/>
        <tr>
          <td class="label">Плательщик (подпись):</td>
          <td class="data">&nbsp;</td>
          <td class="label">Дата:</td>
          <td class="data" id="docDate1" ></td>
        </tr>
       </table>
    </td>
   </tr>
    <!--12-->
   <tr style="height:6mm">
    <td class="bd_right">&nbsp;</td>
    <td>&nbsp;</td>
    <td colspan="2" class="small_font" >* или иной государственный орган исполнительной власти
    </td>
   </tr>
  </table>
 </td>
 </tr>
</table>
</BODY>
</HTML>