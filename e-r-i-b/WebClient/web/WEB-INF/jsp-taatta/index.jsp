<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert definition="paymentMain">
<tiles:put name="mainmenu" value="Payments"/>
<tiles:put name="needSave" type="string" value="false"/>
<!-- ��������� -->
<tiles:put name="pageTitle" type="string">�������</tiles:put>

<!--������� �������� ���� ��������-->
<tiles:put name="data" type="string">
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<table cellpadding="0" cellspacing="0" class="MaxSize">
<tr>
<td height="100%">
<div class="MaxSize">
<table cellspacing="0" cellpadding="0" width="100%">
<tr>
	<td colspan="2" class='listPayPart'>�������</td>
</tr>
<tr>
	<td>
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
						<tr>
							<td class="listPayImg" align="center">
								<phiz:link action="/private/templates"
								           serviceId="ClientTemplatesManagement">
									<img src="${imagePath}/templatesPayments.gif"
									     border="0"/>
								</phiz:link>
							</td>
							<td valign="top" width="300px;">
								<phiz:link action="/private/templates"
								           serviceId="ClientTemplatesManagement">
									<span class="listPayTitle">������� ��������</span>
								</phiz:link>
							</td>
						</tr>
					</table>
				</td>

				<td>&nbsp;
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td colspan="2" class='listPayPart'><br>�������</td>
</tr>
<tr>
<td>
<table cellspacing="0" cellpadding="0">
<tr>
	<td>
		<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			<tr>
				<td class="listPayImg" align="center">
					<phiz:link action="/private/payments/payment"
					           serviceId="InternalPayment">
                        <phiz:param name="form" value="InternalPayment"/>
						<img src="${imagePath}/InternalPayment.gif"
						     border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;">
					<phiz:link action="/private/payments/payment"
					           serviceId="InternalPayment">
                        <phiz:param name="form" value="InternalPayment"/>
						<span class="listPayTitle">������� ����� �������</span>
					</phiz:link>
					<br>
					������� �������� ������� ����� ������ �������<br>
					<phiz:menuLink action="/private/payments/payments" param="name=InternalPayment"
					               serviceId="InternalPayment" id="m4" align="right">������� ��������
					</phiz:menuLink>
				</td>
			</tr>
		</table>
	</td>
	<td>
		<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			<tr>
				<td class="listPayImg" align="center">
					<phiz:link action="/private/payments/payment"
					           serviceId="RurPayment">
                        <phiz:param name="form" value="RurPayment"/>
						<img src="${imagePath}/RurPayment.gif"
						     border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;">
					<phiz:link action="/private/payments/payment"
					           serviceId="RurPayment">
                        <phiz:param name="form" value="RurPayment"/>
						<span class="listPayTitle">�������� �������</span>
					</phiz:link>
					<br>
					������������ �������� ������� � ������ ����� �� ���� ����������� ��� ������������ ����<br>
					<phiz:menuLink action="/private/payments/payments" param="name=RurPayment"
					               serviceId="RurPayment" id="m12" align="right">������� ��������
					</phiz:menuLink>
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			<tr>
				<td class="listPayImg" align="center" valign="middle">
					<phiz:link action="/private/payments/payment"
					           serviceId="CurrencyPayment">
                        <phiz:param name="form" value="CurrencyPayment"/>
						<img src="${imagePath}/CurrencyPayment.gif"
						     border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;">
					<phiz:link action="/private/payments/payment"
					           serviceId="CurrencyPayment">
                        <phiz:param name="form" value="CurrencyPayment"/>
						<span class="listPayTitle">������� ����������� ������</span>
					</phiz:link>
					<br>
					������� �������� ������� � ������ ����� � ����������� ������<br><br>
					<phiz:menuLink action="/private/payments/payments" param="name=CurrencyPayment"
					               serviceId="CurrencyPayment" id="m1" align="right">������� ��������
					</phiz:menuLink>
				</td>
			</tr>
		</table>
	</td>
	<td>
		<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			<tr>
				<td class="listPayImg" align="center" valign="middle">
					<phiz:link action="/private/payments/payment"
					           serviceId="TaxPayment">
                        <phiz:param name="form" value="TaxPayment"/>
						<img src="${imagePath}/TaxPayment.gif" border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;">
					<phiz:link action="/private/payments/payment"
					           serviceId="TaxPayment">
                       <phiz:param name="form" value="TaxPayment"/>
						<span class="listPayTitle">��������� ������</span>
					</phiz:link>
					<br>
					������������ �������� ������� � ������ ����� � ���� ������ �������.<br><br>
					<phiz:menuLink action="/private/payments/payments" param="name=TaxPayment"
					               serviceId="TaxPayment" id="m3" align="right">������� ��������
					</phiz:menuLink>
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
					<phiz:link action="/private/payments/payment"
					           serviceId="CardReplenishmentPayment">
                        <phiz:param name="form" value="CardReplenishmentPayment"/>
						<img src="${imagePath}/CardReplenishmentPayment.gif" border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;">
					<phiz:link action="/private/payments/payment"
					           serviceId="CardReplenishmentPayment">
                        <phiz:param name="form" value="CardReplenishmentPayment"/>
						<span class="listPayTitle">���������� ����������� �����</span>
					</phiz:link>
					<br>
						������� �������� ������� � ������ ����� ��� ���������� ����������� �����.
					<br>
					<phiz:menuLink action="/private/payments/payments" param="name=CardReplenishmentPayment"
					               serviceId="CardReplenishmentPayment" id="m666" align="right">������� ��������
					</phiz:menuLink>
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
</td>
</tr>
<tr>
	<td colspan="2" class='listPayPart'><br>����� ������</td>
</tr>
<tr>
	<td>
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
						<tr>
							<td class="listPayImg" align="center">
								<phiz:link action="/private/payments/payment"
								           serviceId="ConvertCurrencyPayment">
                                    <phiz:param name="form" value="ConvertCurrencyPayment"/>
									<img src="${imagePath}/PCPayment.gif"
									     border="0"/>
								</phiz:link>
							</td>
							<td valign="top" width="300px;">
								<phiz:link action="/private/payments/payment"
								           serviceId="ConvertCurrencyPayment">
                                    <phiz:param name="form" value="ConvertCurrencyPayment"/>
									<span class="listPayTitle">��������� ����������� ������</span>
								</phiz:link>
								<br>
								��������� ����������� ������
								<br><br>
								<phiz:menuLink action="/private/payments/payments"
								               param="name=ConvertCurrencyPayment"
								               serviceId="ConvertCurrencyPayment" id="m11" align="right">
									������� ��������
								</phiz:menuLink>
							</td>
						</tr>
					</table>
				</td>
				<td>&nbsp;
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<table cellpadding="0" cellspacing="0">
			<tbody><tr>
				<td> 
<tr>
	<td colspan="2" class='listPayPart'><br>������ ��������</td>
</tr>
<tr>
	<td align="center">
		<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			<tr>
				<td class="listPayImg" align="center" rowspan="2">
					<phiz:link action="/private/payments/common" serviceId="PaymentList">
                        <phiz:param name="status" value="all"/>
						<img src="${imagePath}/AllPayments.gif" border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;">
					<phiz:link action="/private/payments/common" serviceId="PaymentList">
                        <phiz:param name="status" value="all"/>
						<span class="listPayTitle">��� �������</span>
					</phiz:link>
				</td>
				<td>&nbsp;
				</td>
			</tr>
		</table>
	</td>
	<td>
		<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			<tr>
				<td class="listPayImg" align="center" rowspan="2">
					<phiz:link action="/private/payments/common" serviceId="PaymentList">
                        <phiz:param name="status" value="uncompleted"/>
						<img src="${imagePath}/UncompletedPayments.gif" border="0"/>
					</phiz:link>
				</td>
				<td valign="top" width="300px;">
					<phiz:link action="/private/payments/common" serviceId="PaymentList">
                        <phiz:param name="status" value="uncompleted"/>
						<span class="listPayTitle">������������� �������</span>
					</phiz:link>
				</td>
				<td>&nbsp;
				</td>
			</tr>
		</table>
	</td>
</tr>
				</td>
			</tr>
		</tbody>
			<tr>
				<td colspan="2" class='listPayPart'><br>�����������</td>
			</tr>
			<tr>
			  <td>
			   <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			    <tr>
			               <td class="listPayImg" align="center" rowspan="2">
			                  <phiz:link action="/private/receivers/list" serviceId="PaymentReceiverList">
			                     <img src="${imagePath}/HandBooksPh.gif" border="0"/>
			                  </phiz:link>
			               </td>
			               <td valign="top" width="300px;">
			                   <phiz:link action="/private/receivers/list" serviceId="PaymentReceiverList">
									<span class="listPayTitle">���������� �����������</span>
								</phiz:link>
			               </td>
				    </tr>
				</table>
			  </td>
			  <td>
			           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			           <tr>
			              <td class="listPayImg" align="center" rowspan="2">
			                 <phiz:link action="/private/dictionary/banks/national" serviceId="BankList">
			                    <img src="${imagePath}/HandBooksBanks.gif" border="0"/>
			                 </phiz:link>
			              </td>
			              <td valign="top" width="300px;">
				             <phiz:link action="/private/dictionary/banks/national" serviceId="BankList">
								<span class="listPayTitle">���������� ������</span>
							</phiz:link>
			              </td>
			           </tr>
			           </table>
			  </td>
			 </tr>
                         <tr>
			  <td>
			           <table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
			           <tr>
			              <td class="listPayImg" align="center" rowspan="2">
			                 <phiz:link action="/private/dictionary/KBK" serviceId="KBKList">
			                    <img src="${imagePath}/HandBooksBanks.gif" border="0"/>
			                 </phiz:link>
			              </td>
			              <td valign="top" width="300px;">
				             <phiz:link action="/private/dictionary/KBK" serviceId="KBKList">
								<span class="listPayTitle">���������� ���</span>
							</phiz:link>
			              </td>
			           </tr>
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
