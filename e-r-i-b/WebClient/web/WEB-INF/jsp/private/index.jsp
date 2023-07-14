<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="paymentMain">
  <tiles:put name="mainmenu" value="Payments"/>
  <!-- ��������� -->
  <tiles:put name="pageTitle" type="string">�������</tiles:put>
  <!--������� �������� ���� ��������-->
  <tiles:put name="data" type="string">
  <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
    <table cellspacing="0" cellpadding="0" width="100%">
    <tr><td colspan="2" class='listPayPart'>�������</td></tr>
    <tr>
       <td>
       <table cellspacing="0" cellpadding="0">
        <tr>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center">
                 <phiz:link action="/private/payments/payment" serviceId="CurrencyPayment">
                     <phiz:param name="form" value="CurrencyPayment"/>
                    <img src="${imagePath}/InternalPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="CurrencyPayment">
                     <phiz:param name="form" value="CurrencyPayment"/>
                    <span class="listPayTitle">������� ����������� ������</span>
                 </phiz:link><br>
                    ������� ����������� ������.<br>
                <phiz:menuLink action="/private/payments/payments" param="name=CurrencyPayment" serviceId="CurrencyPayment" id="m1" align="right">������� ��������</phiz:menuLink>
              </td>
           </tr>
           </table>
       </td>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center">
                 <phiz:link action="/private/payments/payment" serviceId="GKHPayment">
                     <phiz:param name="form" value="GKHPayment"/>
                    <img src="${imagePath}/InternalPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="GKHPayment">
                     <phiz:param name="form" value="GKHPayment"/>
                    <span class="listPayTitle">������ ����� ���</span>
                 </phiz:link><br>
                    ������ ����� ��� � ������ �����.<br>
                <phiz:menuLink action="/private/payments/payments" param="name=GKHPayment" serviceId="GKHPayment" id="m2" align="right">������� ��������</phiz:menuLink>
              </td>
           </tr>
           </table>
       </td>
     </tr>
        <tr>
	        <td>
	            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
	            <tr>
	               <td class="listPayImg" align="center">
	                  <phiz:link action="/private/payments/payment" serviceId="TaxPayment">
                          <phiz:param name="form" value="TaxPayment"/>
	                     <img src="${imagePath}/RurPayment.gif" border="0"/>
	                  </phiz:link>
	               </td>
	               <td valign="top" width="300px;">
	                  <phiz:link action="/private/payments/payment" serviceId="TaxPayment">
                          <phiz:param name="form" value="TaxPayment"/>
	                     <span class="listPayTitle">������ �������</span>
	                  </phiz:link><br>
	                     ������ �������.<br>
	                 <phiz:menuLink action="/private/payments/payments" param="name=TaxPayment" serviceId="TaxPayment" id="m2" align="right">������� ��������</phiz:menuLink>
	               </td>
	            </tr>
	            </table>
	        </td>
	        <td>
	          <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
	            <tr>
	               <td class="listPayImg" align="center">
		               <phiz:link action="/private/payments/payment" serviceId="ContactPayment">
                           <phiz:param name="form" value="ContactPayment"/>
	                      <img src="${imagePath}/RurPaymentPh.gif" border="0"/>
	                  </phiz:link>
	               </td>
	               <td valign="top" width="300px;">
		               <phiz:link action="/private/payments/payment" serviceId="ContactPayment">
                        <phiz:param name="form" value="ContactPayment"/>
	                     <span class="listPayTitle">������� �� ���� CONTACT</span>
	                  </phiz:link><br>
	                     ������������ �������� ������� � ������ ����� � ������ ����������� ���� ��� �������� ����� ����������<br>
		               <phiz:menuLink action="/private/payments/payments" param="name=ContactPayment" serviceId="ContactPayment" id="m4" align="right">������� ��������</phiz:menuLink>
	               </td>
	            </tr>
	          </table>
	        </td>
      </tr>
       </table>
      </td>
    </tr>
	
<!--
    <tr><td colspan="2" class='listPayPart'><br>������</td></tr>
    <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center">
                  <phiz:link action="/private/payments/payment"  serviceId="MobilePayment">
                    <phiz:param name="form" value="MobilePayment"/>
                     <img src="${imagePath}/MobilePayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/payment" serviceId="MobilePayment">
                    <phiz:param name="form" value="MobilePayment"/>
                     <span class="listPayTitle">������ ������� �����</span>
                  </phiz:link><br>
                     ������ ����� ��������� �����.<br><br>
                 <phiz:menuLink action="/private/payments/payments" serviceId="MobilePayment" id="m4" align="right">
                    <phiz:param name="name" value="MobilePayment"/>
                 ������� ��������</phiz:menuLink>
               </td>
            </tr>
            </table>
        </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center">
                  <phiz:link action="/private/payments/payment" serviceId="ElectricPayment">
                    <phiz:param name="form" value="ElectricPayment"/>
                     <img src="${imagePath}/ElectricPayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/payment" serviceId="ElectricPayment">
                        <phiz:param name="form" value="ElectricPayment"/>
                     <span class="listPayTitle">������ ��������������</span>
                  </phiz:link><br>
                     ����������� ������ ����� ��� ������������� �������� �� ��������������.<br>
                 <phiz:menuLink action="/private/payments/payments" param="name=ElectricPayment" serviceId="ElectricPayment" id="m5" align="right">������� ��������</phiz:menuLink>
               </td>
            </tr>
            </table>
        </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center">
                  <phiz:link action="/private/payments/payment" serviceId="MGTSPayment">
                     <phiz:param name="form" value="MGTSPayment"/>
                     <img src="${imagePath}/MgtsPaymentsm.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/payment" serviceId="MGTSPayment">
                     <phiz:param name="form" value="MGTSPayment"/>
                     <span class="listPayTitle">������ ����� ����. ����������� �����</span>
                  </phiz:link><br>
                     ������������ ����������� ����������� ����� �� �������� ������� ����.<br>
                 <phiz:menuLink action="/private/payments/payments" param="name=MGTSPayment" serviceId="MGTSPayment" id="m6" align="right">������� ��������</phiz:menuLink>
               </td>
            </tr>
            </table>
       </td>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center">
                 <phiz:link action="/private/payments/payment" serviceId="InternetPayment">
                    <phiz:param name="form" value="InternetPayment"/>
                    <img src="${imagePath}/InternetPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="InternetPayment">
                    <phiz:param name="form" value="InternetPayment"/>
                    <span class="listPayTitle">������ ��������</span>
                 </phiz:link><br>
                    ������ ����� ������� � ��������.<br><br>
                <phiz:menuLink action="/private/payments/payments" param="name=InternetPayment" serviceId="InternetPayment" id="m7" align="right">������� ��������</phiz:menuLink>
              </td>
           </tr>
           </table>
        </td>
        </tr>
        </table>
        </td>
    </tr>
    <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center">
                  <phiz:link action="/private/payments/payment" serviceId="TVPayment">
                     <phiz:param name="form" value="TVPayment"/>
                     <img src="${imagePath}/TVPayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/payment" serviceId="TVPayment">
                       <phiz:param name="form" value="TVPayment"/>
                     <span class="listPayTitle">������ ����� �����������</span>
                  </phiz:link><br>
                     ������ ����� �����������.<br><br>
                 <phiz:menuLink action="/private/payments/payments" param="name=TVPayment" serviceId="TVPayment" id="m8" align="right">������� ��������</phiz:menuLink>
               </td>
            </tr>
            </table>
       </td>
        <td>&nbsp;</td>
        </tr>
        </table>
        </td>
     </tr>
-->
     <tr><td colspan="2" class='listPayPart'><br>����� ������</td></tr>
     <tr>
       <td>
       <table cellspacing="0" cellpadding="0">
       <tr>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center">
                 <phiz:link action="/private/payments/payment" serviceId="PurchaseSaleCurrencyPayment">
                     <phiz:param name="form" value="PurchaseSaleCurrencyPayment"/>
                    <img src="${imagePath}/PCPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="PurchaseSaleCurrencyPayment">
                     <phiz:param name="form" value="PurchaseSaleCurrencyPayment"/>
                    <span class="listPayTitle">�������/������� ����������� ������</span>
                 </phiz:link><br>
                    �������/������� ����������� ������<br><br>
                <phiz:menuLink action="/private/payments/payments" param="name=PurchaseSaleCurrencyPayment" serviceId="PurchaseSaleCurrencyPayment" id="m9" align="right">������� ��������</phiz:menuLink>
              </td>
           </tr>
           </table>
        </td>
        </tr>
        </table>
        </td>
     </tr>
     <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
			<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center">
                  <phiz:link action="/private/payments/payment" serviceId="ConvertCurrencyPayment">
                      <phiz:param name="form" value="ConvertCurrencyPayment"/>
                     <img src="${imagePath}/CCPayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/payment" serviceId="ConvertCurrencyPayment">
                      <phiz:param name="form" value="ConvertCurrencyPayment"/>
                     <span class="listPayTitle">��������� ����������� ������</span>
                  </phiz:link><br>
                   ����� ����� ����������� ������ �� ������.<br><br>
                 <phiz:menuLink action="/private/payments/payments" param="name=ConvertCurrencyPayment" serviceId="ConvertCurrencyPayment" id="m11" align="right">������� ��������</phiz:menuLink>
               </td>
            </tr>
            </table>
        </td>
        </tr>
        </table>
        </td>
     </tr>
     <tr><td colspan="2" class='listPayPart'><br>������ ���������� ���������</td></tr>
     <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center">
                  <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                      <phiz:param name="form" value="ConvertCurrencyPayment"/>
                     <img src="${imagePath}/PD4.gif" border="0" width="54px" />
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                      <phiz:param name="form" value="ConvertCurrencyPayment"/>
                     <span class="listPayTitle">����� ��-4 � ��-4�� (�����)</span>
                  </phiz:link><br>
                   ���������� ��������� ���������.<br><br><br>
               </td>
            </tr>
            </table>
        </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center">
                  <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                      <phiz:param name="page" value="pay"/>
                      <phiz:param name="form" value="ConvertCurrencyPayment"/>
                     <img src="${imagePath}/PP.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                      <phiz:param name="page" value="pay"/>
                      <phiz:param name="form" value="ConvertCurrencyPayment"/>
                     <span class="listPayTitle">��������� ���������</span>
                  </phiz:link><br>
                   ���������� ��������� ���������.<br><br><br>
               </td>
            </tr>
            </table>
        </td>
        </tr>
        <tr>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center">
                  <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                      <phiz:param name="page" value="letter"/>
                      <phiz:param name="form" value="ConvertCurrencyPayment"/>
                     <img src="${imagePath}/PP2.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/PD4" serviceId="PaymentDocumentPreparation">
                      <phiz:param name="page" value="letter"/>
                      <phiz:param name="form" value="ConvertCurrencyPayment"/>
                     <span class="listPayTitle">���������� ���������</span>
                  </phiz:link><br>
                   ���������� ��������� ���������.<br><br><br>
               </td>
            </tr>
            </table>
        </td>
        <td>&nbsp;</td>
        </tr>
        </table>
        </td>
     </tr>
 <!--	
     <tr><td colspan="2" class='listPayPart'><br>�����������</td></tr>
     <tr>
        <td>
        <table cellspacing="0" cellpadding="0">
        <tr>
        <td>
        <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
           <td class="listPayImg" align="center">
               <phiz:link action="/private/payments/payment" serviceId="RurPayJur">
                      <phiz:param name="form" value="RurPayJur"/>
               <img src="${imagePath}/HandBooks.gif" border="0"/>
               </phiz:link>
           </td>
           <td valign="top" width="300px;">
           <phiz:menuLink serviceId="BankList" action="/private/dictionary/banks/national" id="m15">���������� ������</phiz:menuLink>
           </td>
           </tr>
        </table>
        </td>
        <td>
        <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
           <td class="listPayImg" align="center">
               <phiz:link action="/private/payments/payment" serviceId="RurPayJur">
                    <phiz:param name="form" value="RurPayJur"/>
               <img src="${imagePath}/RMBooks.gif" border="0"/>
               </phiz:link>
           </td>
           <td valign="top" width="300px;">
           <phiz:menuLink serviceId="PaymentReceiverManagement" id="m16" action="/private/receivers/list">���������� �����������</phiz:menuLink>
           </td>
           </tr>
        </table>
        </td>
        </tr>
        </table>
        </td>
     </tr>            
-->
     </table>
 </tiles:put>
</tiles:insert>
