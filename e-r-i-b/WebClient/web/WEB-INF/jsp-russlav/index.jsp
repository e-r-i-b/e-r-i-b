<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="paymentMain">  
  <!-- ��������� -->
  <tiles:put name="pageTitle" type="string">�������</tiles:put>
  <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>
  <!--������� �������� ���� ��������-->
  <tiles:put name="data" type="string">
  <table cellpadding="0" cellspacing="0" class="MaxSize">
  <tr>
    <td height="100%">
    <div class="MaxSize">
    <table cellspacing="0" cellpadding="0" width="100%">
    <tr><td colspan="2" class='listPayPart'>������� �������</td></tr>
    <tr>
       <td>
       <table cellspacing="0" cellpadding="0">
	   <tr>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/payment" serviceId="InternalPayment">
                    <phiz:param name="form" value="InternalPayment"/>
                    <img src="${imagePath}/InternalPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="InternalPayment">
                     <phiz:param name="form" value="InternalPayment"/>
                    <span class="listPayTitle">������� �� ������ � �������</span>
                 </phiz:link><br>
                    ������� �������� ������� ����� ������ �������.<br><br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=InternalPayment" serviceId="InternalPayment" id="m1" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
       <td width="50%">
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
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
                    ������� � ������� ����������� ������ �� �����.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=PurchaseSaleCurrencyPayment" serviceId="PurchaseSaleCurrencyPayment" id="m2" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
        </td>
     </tr>
     <tr>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/payment" serviceId="RurPayment">
                     <phiz:param name="form" value="RurPayment"/>
                    <img src="${imagePath}/RurPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="RurPayment">
                    <phiz:param name="form" value="RurPayment"/>
                    <span class="listPayTitle">������� ������ ��</span>
                 </phiz:link><br>
                    ������� �������� ������� � ������ �����<br>
	                �� ���� ����������� ���� ��� ������������ ����.
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=RurPayment" serviceId="RurPayment" id="m4" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
	     <td>
	       <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
	       <tr>
	           <td class="listPayImg" align="center" rowspan="2">
	             <phiz:link action="/private/payments/payment" serviceId="TaxPayment">
                     <phiz:param name="form" value="TaxPayment"/>
	                 <img src="${imagePath}/TaxPayment.gif" border="0"/>
	             </phiz:link>
	           </td>
	           <td valign="top" width="300px;">
	             <phiz:link action="/private/payments/payment" serviceId="TaxPayment">
                     <phiz:param name="form" value="TaxPayment"/>
	                 <span class="listPayTitle">������ �������</span>
	             </phiz:link><br>
	                 ������������ �������� ������� � ������<br>����� � ���� ������ ������� � ������.<br>
	           </td>
	       </tr>
		   <tr><td><phiz:menuLink action="/private/payments/payments" param="name=TaxPayment" serviceId="TaxPayment" id="m5" align="right">������� ��������</phiz:menuLink></td></tr>
	       </table>
	   </td>
     </tr>
	 <tr>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/payment" serviceId="CurrencyPayment">
                     <phiz:param name="form" value="CurrencyPayment"/>
                    <img src="${imagePath}/CurrencyPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="CurrencyPayment">
                    <phiz:param name="form" value="CurrencyPayment"/>
                    <span class="listPayTitle">������� ����������� ������</span>
                 </phiz:link><br>
	                ������������ �������� ������� � ������<br>
	                �����  �� ���� ���������� � ����������� ������.
                   <br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=CurrencyPayment" serviceId="CurrencyPayment" id="m6" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
		 <td>
	      <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
	      <tr>
	         <td class="listPayImg" align="center" rowspan="2">
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
	                ������� ������� �������� ������� �<br>
		            ������ ����� � ������ ���������� ���<br>
		            ��� �������� ����� ����������.
	         </td>
	      </tr>
		  <tr><td><phiz:menuLink action="/private/payments/payments" param="name=ContactPayment" serviceId="ContactPayment" id="m7" align="right">������� ��������</phiz:menuLink></td></tr>
	      </table>
	   </td>
     </tr>
     </table>
     </td>
    </tr>
	
    <tr><td colspan="2" class='listPayPart'><br><bean:message key="label.payments.services" bundle="commonBundle"/></td></tr>
    <tr>
        <td>
        <table cellspacing="0" cellpadding="0" rowspan="2">
        <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/payment" serviceId="GKHPayment">
                     <phiz:param name="form" value="GKHPayment"/>
                    <img src="${imagePath}/GKHPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/payment" serviceId="GKHPayment">
                     <phiz:param name="form" value="GKHPayment"/>
                    <span class="listPayTitle">������ ����� ���</span>
                 </phiz:link><br>
                    ������������ �������� ������� � ������ �����
	                ��� ������ �������-������������ �����  ��
	                ����� ������� ���������� ���������.
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GKHPayment" serviceId="GKHPayment" id="m8" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="GoodsAndServicesPayment"/>
                      <phiz:param name="appointment" value="cellular-communication"/>
                     <img src="${imagePath}/MobilePayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="GoodsAndServicesPayment"/>
                      <phiz:param name="appointment" value="cellular-communication"/>
                     <span class="listPayTitle">������ ������� �����</span>
                  </phiz:link><br>
                     ������������ �������� ������� � ������ �������� ������ �����  ������� �����.<br><br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m9" align="right">������� ��������</phiz:menuLink></td></tr>
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
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="GoodsAndServicesPayment"/>
                      <phiz:param name="appointment" value="credit-repayment"/>
                     <img src="${imagePath}/CreditRepaymentaymanet.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="MobilePGoodsAndServicesPayment"/>
                      <phiz:param name="appointment" value="credit-repaymentayment"/>
                     <span class="listPayTitle">��������� �������</span>
                  </phiz:link><br>
                     ������������ �������� ������� � ������ ����� ��� ��������� ������� � ��������� �� ����. <br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m10" align="right">������� ��������</phiz:menuLink></td></tr>
            </table>
        </td>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="inet-connection"/>
                    <img src="${imagePath}/InternetPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="inet-connection"/>
                    <span class="listPayTitle">��������</span>
                 </phiz:link><br>
                    ������������ �������� ������� � ������ ����� ��� ������ ����� ��������-�����������.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m11" align="right">������� ��������</phiz:menuLink></td></tr>
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
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="satellite-connection"/>
                    <img src="${imagePath}/SatelliteConnection.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="satellite-connection"/>
                    <span class="listPayTitle">����������� �����/��</span>
                 </phiz:link><br>
                    ������������ �������� ������� ��� ���������� �����  ���������� ����������� ����� � ��.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m12" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="ip-telephony"/>
                     <img src="${imagePath}/IPTelephony.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="ip-telephony"/>
                     <span class="listPayTitle">IP-���������</span>
                  </phiz:link><br>
                     ������������ �������� ������� � ������ ����� ��� ������ �����  ���������� IP-���������.<br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m13" align="right">������� ��������</phiz:menuLink></td></tr>
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
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="telephony"/>
                    <img src="${imagePath}/Telephony.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="telephony"/>
                    <span class="listPayTitle">������ ���������</span>
                 </phiz:link><br>
                    ������������ �������� ������� � ������ ����� ��� ������ �����  ���������� ���������� �����.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m14" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="game-portals"/>
                     <img src="${imagePath}/GamePortals.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="game-portals"/>
                     <span class="listPayTitle">������� �������</span>
                  </phiz:link><br>
                     ������������ �������� ������� � ������ ����� ��� ���������� ������ ������� ��������.<br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m15" align="right">������� ��������</phiz:menuLink></td></tr>
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
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment"  serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="inet-shops"/>
                    <img src="${imagePath}/InetShops.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="inet-shops"/>
                    <span class="listPayTitle">��������-��������</span>
                 </phiz:link><br>
                    ������������ �������� ������� � ������ ����� ��� ������ ������� � �����, ���������� � ��������-���������.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m16" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="payment-system"/>
                     <img src="${imagePath}/PaymentSystem.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="payment-system"/>
                     <span class="listPayTitle">��������� �������</span>
                  </phiz:link><br>
                     ������������ �������� ������� � ������ ����� ��� ���������� ����� (������������ ��������) ��������� ������.<br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m17" align="right">������� ��������</phiz:menuLink></td></tr>
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
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="air-ticket"/>
                    <img src="${imagePath}/AirTicket.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                    <phiz:param name="form" value="GoodsAndServicesPayment"/>
                    <phiz:param name="appointment" value="air-ticket"/>
                    <span class="listPayTitle">����������</span>
                 </phiz:link><br>
                    ������������ �������� ������� � ������ ����� ��� ������ ���������� �����������.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m18" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="travel-agency"/>
                     <img src="${imagePath}/TravelAgency.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="travel-agency"/>
                     <span class="listPayTitle">����������</span>
                  </phiz:link><br>
                    ������������ �������� ������� � ������ ����� ��� ������ �������������� ������.<br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m19" align="right">������� ��������</phiz:menuLink></td></tr>
            </table>
        </td>
        </tr>
	    <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="hotel-payment"/>
                    <img src="${imagePath}/HotelPayment.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="hotel-payment"/>
                    <span class="listPayTitle">���������</span>
                 </phiz:link><br>
                    ������������ �������� ������� � ������ ����� ��� ������ ���������������� ������ � ���������.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m20" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                      <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="policy-payment"/>
                     <img src="${imagePath}/PolicyPayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="policy-payment"/>
                     <span class="listPayTitle">��������� �����</span>
                  </phiz:link><br>
                     ������������ �������� ������� � ������ ����� ��� ������ ���������� ������.<br><br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m21" align="right">������� ��������</phiz:menuLink></td></tr>
            </table>
        </td>
        </tr>
	    <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="realty-operation"/>
                    <img src="${imagePath}/RealtyOperation.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="realty-operation"/>
                    <span class="listPayTitle">������������</span>
                 </phiz:link><br>
                    ������������ �������� ������� � ������ ����� ��� ������ �������� � �������������.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m22" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
        <td>
            <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
            <tr>
               <td class="listPayImg" align="center" rowspan="2">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="other-payment"/>
                     <img src="${imagePath}/OtherPayment.gif" border="0"/>
                  </phiz:link>
               </td>
               <td valign="top" width="300px;">
                  <phiz:link action="/private/payments/forms/GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment">
                     <phiz:param name="form" value="GoodsAndServicesPayment"/>
                     <phiz:param name="appointment" value="other-payment"/>
                     <span class="listPayTitle">������</span>
                  </phiz:link><br>
                     ������������ �������� ������� � ������ ����� ��� ������ ������ ������� � �����. <br>
               </td>
            </tr>
	        <tr><td><phiz:menuLink action="/private/payments/payments" param="name=GoodsAndServicesPayment" serviceId="GoodsAndServicesPayment" id="m23" align="right">������� ��������</phiz:menuLink></td></tr>
            </table>
        </td>
        </tr>
        </table>
        </td>
    </tr>
    </table>
    </div>
   </td>
</tr>
</table>

 </tiles:put>
</tiles:insert>
