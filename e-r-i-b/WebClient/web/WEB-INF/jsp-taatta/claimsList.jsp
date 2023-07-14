<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="claimList">
  <!-- ��������� -->
  <tiles:put name="pageTitle" type="string">������</tiles:put>
  <tiles:put name="data" type="string">
  <tiles:importAttribute/>
  <c:set var="globalImagePath" value="${globalUrl}/images"/>
  <c:set var="imagePath" value="${skinUrl}/images"/>

  <table cellpadding="0" cellspacing="0" class="MaxSize">
  <tr>
    <td height="100%">
    <table cellspacing="0" cellpadding="0" width="100%">
    <tr><td colspan="2" class='listPayPart'>������</td></tr>
    <tr>
       <td>
       <table cellspacing="0" cellpadding="0" border="0">
	   <tr>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="DepositOpeningClaim">
                     <phiz:param name="form" value="DepositOpeningClaim"/>
                    <img src="${imagePath}/DepositOpeningClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="DepositOpeningClaim">
                     <phiz:param name="form" value="DepositOpeningClaim"/>
                    <span class="listPayTitle">�������� �����/������</span>
                 </phiz:link><br>
                     ������ � ���� ������ �� �������� �������� ����� ��� �������� ������.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=DepositOpeningClaim" serviceId="DepositOpeningClaim" id="m1" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="DepositClosingClaim">
                     <phiz:param name="form" value="DepositClosingClaim"/>
                    <img src="${imagePath}/DepositClosingClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="DepositClosingClaim">
                     <phiz:param name="form" value="DepositClosingClaim"/>
                    <span class="listPayTitle">�������� �����/������</span>
                 </phiz:link><br>
                    ������ � ���� ������ �� �������� �������� ����� ��� ��������� �������� �������� ������.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=DepositClosingClaim" serviceId="DepositClosingClaim" id="m2" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
     </tr>
	 <tr>
        <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="DepositReplenishmentClaim">
                     <phiz:param name="form" value="DepositReplenishmentClaim"/>
                    <img src="${imagePath}/DepositReplenishmentClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="DepositReplenishmentClaim">
                     <phiz:param name="form" value="DepositReplenishmentClaim"/>
                    <span class="listPayTitle">��������� �����</span>
                 </phiz:link><br>
                    ������� �������� ������� � ������ ����� ��� ���������� �������� ������.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=DepositReplenishmentClaim" serviceId="DepositReplenishmentClaim" id="m4" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
       
       <td>
           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
           <tr>
              <td class="listPayImg" align="center" rowspan="2">
                 <phiz:link action="/private/claims/claim" serviceId="InternalTransferClaim">
                     <phiz:param name="form" value="InternalTransferClaim"/>
                    <img src="${imagePath}/DepositReplenishmentClaim.gif" border="0"/>
                 </phiz:link>
              </td>
              <td valign="top" width="300px;">
                 <phiz:link action="/private/claims/claim" serviceId="InternalTransferClaim">
                     <phiz:param name="form" value="InternalTransferClaim"/>
                    <span class="listPayTitle">������� �������� �� ������</span>
                 </phiz:link><br>
                    ������� �������� ������� �� ������ �� ��� ����.<br>
              </td>
           </tr>
	       <tr><td><phiz:menuLink action="/private/claims/claims" param="name=InternalTransferClaim" serviceId="InternalTransferClaim" id="m5" align="right">������� ��������</phiz:menuLink></td></tr>
           </table>
       </td>
        </tr>
        </table>
        </td>
    </tr>
    </table>
    </td>
    </tr>
</table>

  </tiles:put>
</tiles:insert>