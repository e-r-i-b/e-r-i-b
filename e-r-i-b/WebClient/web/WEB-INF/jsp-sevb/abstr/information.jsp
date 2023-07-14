<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
	.docTableBorder{border-top:1px solid #000000;border-left:1px solid #000000;}
	.docTdBorder{border-bottom:1px solid #000000;border-right:1px solid #000000;}
	.docTdBorderFirst{border-top:1px solid #000000;border-bottom:1px solid #000000;border-right:1px solid #000000;}
	.docTdBorderFirstAngle{border-top:1px solid #000000;border-bottom:1px solid #000000;border-right:1px solid #000000;border-left:1px solid #000000;}
	.docTdBorderSecond{border-bottom:1px solid #000000;border-right:1px solid #000000;border-left:1px solid #000000;}
	.textPadding{padding-left:4;padding-right:4;}
	.font10{font-family:Times New Roman;font-size:10pt;}
	.font8{font-family:Times New Roman;font-size:8pt;}
</style>

<c:set var="abstactMap" value="${form.accountAbstract}"/>
<c:set var="user" value="${form.user}"/>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:choose>
	<c:when test="${empty abstactMap}">
			<table width="100%">
				<tr>
					<td align="center" class="messageTab"><br>
						��&nbsp;�������&nbsp;��&nbsp;������&nbsp;�����,<br>
						����������������&nbsp;���������&nbsp;�������!
					</td>
				</tr>
			</table>
	</c:when>
	<c:otherwise>

		<c:forEach items="${abstactMap}" var="listElement">
			<c:set var="accountLink" value="${listElement.key}"/>
			<c:set var="account" value="${accountLink.value}"/>
			<c:set var="accountInfo" value="${accountLink.accountInfo}"/>
			<c:set var="abstract" value="${listElement.value}"/>

			<table style="width:170mm;font-family:Times New Roman;font-size:11pt;">
			<tr>
				<td>
					<table width="100%">
						<tr>
							<td><img src="${globalImagePath}/miniLogoSbrf.jpg"/>
							</td>
							<td align="left" width="30%">
								<input value="�������� ������" style="width:100%;text-align:left;font-size: 11px;" type="Text" readonly="true" class="insertInput"/>
							</td>
							<td width="70%" align="right">
								<nobr>�.� 297</nobr>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center">
					<br/>
				</td>
			</tr>
			<tr>
				<td align="center">
					������������� � ${accountLink.office.code.fields[branch]}/${accountLink.office.code.fields[office]}
					<br/>
				</td>
			</tr>
			<tr>
				<td align="center"><br/>
					������� � ��������� ������(��)
				</td>
			</tr>
			<tr>
				<td align="center">
					<input value="${accountLink.fullname}" type="Text" readonly="true" class="insertInput" style="width:90%;text-align:center;font-family:Times New Roman;font-size:8pt;textPadding-top:0;textSpacing-top:0;"/>
				</td>
			</tr>
			<tr>
				<td align="center" valign="top" >
					<i>(����������� �.�.�. ���������)</i>
				</td>
			</tr>
			<tr>
				<td align="center">
					<br/>�� ������ �
					${phiz:formatDateWithStringMonth(abstract.fromDate)}
					 �. ��
					 ${phiz:formatDateWithStringMonth(abstract.toDate)}
					 �.
				</td>
			</tr>
			<tr>
				<xsl:variable name="curCode" select="currencyCode"/>
				<td align="left" style="padding-left:20;">
					<br/>���� �${account.number} �� ������: &lt;&lt;${account.description}&gt;&gt; � ${account.currency.code} <br/>
				</td>
			</tr>
			<tr>
				<td>
					<br/>
				</td>
			</tr>
			<tr>
				<td>
					<font style="font-family:Times New Roman;font-size:14pt;">1.</font> ���������� � ������� ������
				</td>
			</tr>
			<tr>
				<td width="100%">
					<table class="docTdBorder docTableBorder" style="border-collapse:collapse;font-family:Times New Roman;font-size:11pt;">
						<tr>
							<td style="width:56mm" class="docTdBorder docTableBorder">���� ��������� �������� ��������� (�����������������) �����
							</td>
							<td style="width:56mm" class="docTdBorder docTableBorder">���������� ���� �� ��������� ��������� (�����������������) ����� �������� ������
							</td>
							<td style="width:56mm" class="docTdBorder docTableBorder">������� ���������� ������ �� ������, ����������� � ������� �������� �����
							</td>
						</tr>
						<tr>
							<td class="docTdBorder docTableBorder" align="center">
								<c:choose>
									<c:when test="${empty accountInfo.closeDate}">
										�� �������������
									</c:when>
									<c:otherwise>
										${phiz:formatDateWithStringMonth(accountInfo.closeDate)} �.
									</c:otherwise>
								</c:choose>
							</td>
							<td class="docTdBorder docTableBorder" align="center">
								<c:if test="${accountInfo.closeDate!=null}">
									${phiz:calculatePeriodLeft(accountInfo.closeDate).valueInDays}
								</c:if>
							</td>
							<td align="center" class="docTdBorder docTableBorder">
								<bean:write name="account" property="interestRate" format="0.00"/>&nbsp;
							</td>
						</tr>
					</table><br/>
				</td>
			</tr>
			<tr>
				<td>
					<font style="font-family:Times New Roman;font-size:14pt;">2.</font> �������� � �������� ������ (�����������, ���� ����� ��� ������ �� ��������� ������)
				</td>
			</tr>
			<tr>
				<td width="100%">
					<table width="100%" class="docTdBorder docTableBorder" style="border-collapse:collapse;font-family:Times New Roman;font-size:11pt;">
						<tr>
							<td  width="50%" class="docTdBorder docTableBorder">���� ��������
							</td>
							<td  width="50%" class="docTdBorder docTableBorder">����������� �����
							</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${!empty abstract.closedDate}">
									<td class="docTdBorder docTableBorder">
										<bean:write name='abstract' property="closedDate.time" format="dd.MM.yyyy"/>&nbsp;
									</td>
									<td class="docTdBorder docTableBorder">
										<c:if test="${!empty abstract.closedSum}">
											<bean:write name="abstract" property="closedSum.decimal" format="0.00"/>
										</c:if>&nbsp;
									</td>
								</c:when>
								<c:otherwise>
									<td class="docTdBorder docTableBorder"><br/>
									</td>
									<td class="docTdBorder docTableBorder"><br/>
									</td>
								</c:otherwise>
							</c:choose>
						</tr>
					</table><br/>
				</td>
			</tr>
			<tr>
				<td>
					<font style="font-family:Times New Roman;font-size:14pt;">3.</font> �������� � ������� ��������� ������
				</td>
			</tr>

			<tr>
				<td width="100%">
					<table width="100%" class="docTdBorder docTableBorder" style="border-collapse:collapse;font-family:Times New Roman;font-size:11pt;">
					<tr style="height:10mm">
						<td class="docTdBorder docTableBorder" align="center">���� ��������</td>
						<td class="docTdBorder docTableBorder" align="center">������������ ��������</td>
						<td class="docTdBorder docTableBorder" align="center">����� ��������</td>
						<td class="docTdBorder docTableBorder" align="center">������� ������</td>
					</tr>
					<c:set var="lineNumber" value="0"/>

					<c:forEach items="${abstract.transactions}" var="transaction">
						<c:set var="lineNumber" value="${lineNumber+1}"/>
						<tr>
							<td class="docTdBorder docTableBorder" align="center">
								<c:if test="${!empty transaction.date}">
									<bean:write name='transaction' property="date.time" format="dd.MM.yyyy"/>
								</c:if>&nbsp;
							</td>
							<td class="docTdBorder docTableBorder" align="center">
								<c:if test="${!empty transaction.description}">
									${transaction.description}
								</c:if>&nbsp;
							</td>
							<td class="docTdBorder docTableBorder" align="center">
								<c:choose>
									<c:when test="${!empty transaction.debitSum and !empty transaction.creditSum}">
										<c:if test="${transaction.debitSum.decimal == 0 and transaction.creditSum.decimal == 0}">
											 0.00
										</c:if>
										<c:if test="${transaction.debitSum.decimal > 0}">
											<bean:write name="transaction" property="debitSum.decimal" format="0.00"/>
										</c:if>
										<c:if test="${transaction.creditSum.decimal > 0}">
											<bean:write name="transaction" property="creditSum.decimal" format="0.00"/>
										</c:if>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
							<td class="docTdBorder docTableBorder" align="center">
								<c:if test="${!empty transaction.balance}">
									<bean:write name="transaction" property="balance.decimal" format="0.00"/>
								</c:if>&nbsp;
							</td>
						</tr>
					</c:forEach>
						<c:if test="${lineNumber eq 0}">
							<tr>
								<td class="docTdBorder docTableBorder">&nbsp;</td>
								<td class="docTdBorder docTableBorder">&nbsp;</td>
								<td class="docTdBorder docTableBorder">&nbsp;</td>
								<td class="docTdBorder docTableBorder">&nbsp;</td>
							</tr>
						</c:if>
					</table><br/>
				</td>
			</tr>
			<tr>
				<td>
					<font style="font-family:Times New Roman;font-size:14pt;">4.</font> ���������� � ������� ������������(����������� ��� �� ����������)
				</td>
			</tr>
			<tr>
				<td width="100%">
					<table  width="100%" class="docTdBorder docTableBorder" style="border-collapse:collapse;font-family:Times New Roman;font-size:11pt;">
						<tr>
							<td width="33%" class="docTdBorder docTableBorder">�.�.�. ����, �� �������� ��������� ������������
							</td>
							<td width="33%" class="docTdBorder docTableBorder">���� ��������� �������� ������������
							</td>
							<td width="33%" class="docTdBorder docTableBorder">���������� ���� �� ��������� ����� �������� ������������
							</td>
						</tr>
						<c:set var="lineNumber" value="0"/>
						<c:forEach items="${abstract.trusteesDocuments}" var="trusteesDocument">
							<c:set var="lineNumber" value="${lineNumber+1}"/>
							<tr>
								<td class="docTdBorder docTableBorder" align="center">
									<c:if test="${!empty trusteesDocument.name}">
										${trusteesDocument.name}
									</c:if>
								</td>
								<td class="docTdBorder docTableBorder" align="center">
									<c:if test="${!empty trusteesDocument.endingDate}">
										<bean:write name='trusteesDocument' property="endingDate.time" format="dd.MM.yyyy"/>
									</c:if>
								</td>
								<td class="docTdBorder docTableBorder" align="center">
									<c:if test="${!empty trusteesDocument.endingDate}">
										${phiz:calculatePeriodLeft(trusteesDocument.endingDate).valueInDays}
									</c:if>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${lineNumber eq 0}">
							<tr>
								<td class="docTdBorder docTableBorder">&nbsp;</td>
								<td class="docTdBorder docTableBorder">&nbsp;</td>
								<td class="docTdBorder docTableBorder">&nbsp;</td>
							</tr>
						</c:if>
					</table><br/>
				</td>
			</tr>
			<tr>
				<td>
					<font style="font-family:Times New Roman;font-size:14pt;">5.</font> �������������� ����������
				</td>
			</tr>
			<tr>
				<td width="100%">
					<table  width="100%" class="docTdBorder docTableBorder" style="border-collapse:collapse;font-family:Times New Roman;font-size:11pt;">
							<tr>
								<td width="100%" class="docTdBorder docTableBorder">
									<c:if test="${!empty abstract.additionalInformation}">
										${abstract.additionalInformation}
									</c:if>&nbsp;
								</td>
							</tr>
					</table><br/>
				</td>
			</tr>
			<tr>
				<td>
					<br/>
				</td>
			</tr>
			<tr>
				<td>
					������� ���������� �� �������
				</td>
			</tr>
			<tr>
				<td width="100%" align="center">
					<input value="${user.fullName}" type="Text" readonly="true" class="insertInput" style="width:90%;font-family:Times New Roman;font-size:11pt;"/>
					<br/>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" style="font-family:Times New Roman;font-size:11pt;">
						<tr>
							<td  width="33%">
								<br/>${phiz:formatDateWithStringMonth(phiz:currentDate())} �.
							</td>
							<td width="33%">
							</td>
							<td width="33%">
							</td>
						</tr>
					</table><br/>
				</td>
			</tr>

			</table>

			<br style="page-break-after:always;">
		</c:forEach>
	</c:otherwise>
</c:choose>