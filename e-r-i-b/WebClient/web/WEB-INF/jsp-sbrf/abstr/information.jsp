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

<c:set var="abstractMap" value="${form.accountAbstract}"/>
<c:set var="user" value="${form.user}"/>
<c:choose>
	<c:when test="${empty abstractMap}">
			<table width="100%">
				<tr>
					<td align="center" class="messageTab"><br>
						Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;счета,<br>
						соответствующего&nbsp;заданному&nbsp;фильтру!
					</td>
				</tr>
			</table>
	</c:when>
	<c:otherwise>

		<c:forEach items="${abstractMap}" var="listElement">
			<c:set var="accountLink" value="${listElement.key}"/>
			<c:set var="account"     value="${accountLink.value}"/>
			<c:set var="resourceAbstract" value="${listElement.value}"/>

			<tiles:insert definition="tableTemplate" flush="false">
              <tiles:put name="text" value="Справка о состоянии вклада"/>
              <tiles:put name="cleanText" value="true"/>
              <tiles:put name="data" type="string">
              <tr class="noBorder">
                    <td>
                        <table class="font10">
                        <tr>
                            <td align="left" width="30%">
                                <nobr> ${accountLink.office.name}</nobr>
                            </td>
                            <td align="right" width="70%">
                                <nobr>Ф.№ 297</nobr>
                            </td>
                        </tr>
                        </table>
                    </td>
                </tr>               
                <tr class="noBorder">
                    <td align="center">
                        <br/>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td align="center">
                        Подразделение № ${accountLink.office.code.fields[branch]}/${accountLink.office.code.fields[office]}
                        <br/>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td align="center"><br/>
                        СПРАВКА О СОСТОЯНИИ ВКЛАДА(ОВ)
                    </td>
                </tr>
                <tr class="noBorder">
                    <td align="center">
                        <input value="${accountLink.accountClient.fullName}" type="Text" readonly="true" class="insertInput" style="width:90%;text-align:center;font-family:Times New Roman;font-size:8pt;textPadding-top:0;textSpacing-top:0;"/>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td align="center" valign="top" >
                        <i>(указывается Ф.И.О. вкладчика)</i>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td align="center">
                        <br/>за период с
                        ${phiz:formatDateWithStringMonth(resourceAbstract.fromDate)}
                         г. по
                         ${phiz:formatDateWithStringMonth(resourceAbstract.toDate)}
                         г.
                    </td>
                </tr>
                <tr class="noBorder">
                    <xsl:variable name="curCode" select="currencyCode"/>
                    <td align="left" style="padding-left:20;">
                        <br/>СЧЕТ №${phiz:getFormattedAccountNumber(accountLink.number)} по вкладу: &lt;&lt;${accountLink.description}&gt;&gt; в ${accountLink.currency.code} <br/>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td>
                        <br/>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td><br/>
                        <font style="font-family:Times New Roman;font-size:14pt;">1.</font> Информация о статусе вклада
                    </td>
                </tr>
                <tr class="noBorder">
                    <td width="100%" class="needBorder">
                        <table cellpadding="0" cellspacing="0">
                            <tr class="tblInfHeader">
                                <td style="width:56mm">Дата окончания хранения основного (пролонгированного) срока
                                </td>
                                <td style="width:56mm">Количество дней до истечения основного (пролонгированного) срока хранения вклада
                                </td>
                                <td style="width:56mm">Годовая процентная ставка по вкладу, действующая в течении текущего срока
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <c:choose>
                                        <c:when test="${empty account.closeDate}">
                                            до востребования
                                        </c:when>
                                        <c:otherwise>
                                            ${phiz:formatDateWithStringMonth(account.closeDate)} г.
                                        </c:otherwise>
                                    </c:choose>&nbsp;
                                </td>
                                <td class="docTdBorder docTableBorder" align="center">
                                        ${periodInfo.valueInDays}&nbsp;
                                </td>
                                <td align="center" class="docTdBorder docTableBorder">
                                    <bean:write name="account" property="interestRate" format="0.00"/>&nbsp;
                                </td>
                            </tr>
                        </table><br/>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td>
                        <br/><font style="font-family:Times New Roman;font-size:14pt;">2.</font> Сведения о закрытии вклада (указываются, если вклад был закрыт за указанный период)
                    </td>
                </tr>
                <tr class="noBorder">
                    <td width="100%" class="needBorder">
                        <table cellpadding="0" cellspacing="0">
                            <tr class="tblInfHeader">
                                <td  width="50%">Дата закрытия</td>
                                <td  width="50%">Выплаченная сумма</td>
                            </tr>
                            <tr>
                                <c:choose>
                                    <c:when test="${!empty resourceAbstract.closedDate}">
                                        <td class="labelAll">
                                            <bean:write name='resourceAbstract' property="closedDate.time" format="dd.MM.yyyy"/>&nbsp;
                                        </td>
                                        <td>
                                            <c:if test="${!empty resourceAbstract.closedSum}">
                                                <bean:write name="resourceAbstract" property="closedSum.decimal" format="0.00"/>
                                            </c:if>&nbsp;
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><br/></td>
                                        <td><br/></td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </table><br/>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td>
                        <br/><font style="font-family:Times New Roman;font-size:14pt;">3.</font> Сведения о текущем состоянии вклада
                    </td>
                </tr>

                <tr class="noBorder">
                    <td width="100%" class="needBorder">
                        <table cellpadding="0" cellspacing="0">
                        <tr class="tblInfHeader">
                            <td>Дата операции</td>
                            <td>Наименование операции</td>
                            <td>Сумма операции</td>
                            <td>Остаток вклада</td>
                        </tr>
                        <c:set var="lineNumber" value="0"/>

                        <c:forEach items="${resourceAbstract.transactions}" var="transaction">
                            <c:set var="lineNumber" value="${lineNumber+1}"/>
                            <tr>
                                <td align="center">
                                    <c:if test="${!empty transaction.date}">
                                        <bean:write name='transaction' property="date.time" format="dd.MM.yyyy"/>
                                    </c:if>&nbsp;
                                </td>
                                <td align="center">
                                    <c:if test="${!empty transaction.description}">
                                        ${transaction.description}
                                    </c:if>&nbsp;
                                </td>
                                <td align="center">
                                    <c:set var="debit"
                                           value="${(transaction.debitSum!=null and transaction.debitSum.asCents!=0) ? transaction.debitSum.decimal : null}"/>
                                    <c:set var="credit"
                                           value="${(transaction.creditSum!=null and transaction.creditSum.asCents!=0) ? transaction.creditSum.decimal : null}"/>
                                    <c:choose>
                                        <c:when test="${!empty debit and !empty credit}">
                                            <bean:write name="debit" format="0.00"/>
                                            &nbsp;
                                            <bean:write name="credit" format="0.00"/>
                                        </c:when>
                                        <c:when test="${!empty debit}">
                                            <bean:write name="debit" format="0.00"/>
                                        </c:when>
                                        <c:when test="${!empty credit}">
                                            <bean:write name="credit" format="0.00"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber value="0" pattern="0.00"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td align="center">
                                    <c:if test="${!empty transaction.balance}">
                                        <bean:write name="transaction" property="balance.decimal" format="0.00"/>
                                    </c:if>&nbsp;
                                </td>
                            </tr>
                        </c:forEach>
                            <c:if test="${lineNumber eq 0}">
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                            </c:if>
                        </table><br/>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td>
                        <br/><font style="font-family:Times New Roman;font-size:14pt;">4.</font> Информация о наличии доверенности(указываются при ее оформлении)
                    </td>
                </tr>
                <tr class="noBorder">
                    <td width="100%" class="needBorder">
                        <table cellpadding="0" cellspacing="0">
                            <tr class="tblInfHeader">
                                <td width="33%">Ф.И.О. лица, на которого оформлена доверенность
                                </td>
                                <td width="33%">Дата окончания действия доверенности
                                </td>
                                <td width="33%">Количество дней до истечения срока действия доверенности
                                </td>
                            </tr>
                            <c:set var="lineNumber" value="0"/>
                            <c:forEach items="${resourceAbstract.trusteesDocuments}" var="trusteesDocument">
                                <c:set var="lineNumber" value="${lineNumber+1}"/>
                                <tr>
                                    <td align="center">
                                        <c:if test="${!empty trusteesDocument.name}">
                                            ${trusteesDocument.name}
                                        </c:if>
                                    </td>
                                    <td align="center">
                                        <c:if test="${!empty trusteesDocument.endingDate}">
                                            <bean:write name='trusteesDocument' property="endingDate.time" format="dd.MM.yyyy"/>
                                        </c:if>
                                    </td>
                                    <td align="center">
                                        <c:if test="${!empty trusteesDocument.endingDate}">
                                            ${phiz:calculatePeriodLeft(trusteesDocument.endingDate).valueInDays}
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${lineNumber eq 0}">
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                            </c:if>
                        </table><br/>
                    </td>
                </tr>                
                <tr class="noBorder">
                    <td width="100%">
                        <table cellpadding="0" cellspacing="0">
                                <tr>
                                    <td width="100%">
                                        <c:if test="${!empty resourceAbstract.additionalInformation}">
                                            ${resourceAbstract.additionalInformation}
                                        </c:if>&nbsp;
                                    </td>
                                </tr>
                        </table><br/>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td>
                        <br/>
                    </td>
                </tr>
                <tr class="noBorder">
                    <td>
                        <table cellspacing="0" cellpadding="0">
                        <tr>
                            <td class="labelAll">
                                Выписка составлена по запросу
                            </td>
                            <td>${user.fullName}</td>
                        </tr>
                        </table>
                    </td>
                </tr>
                <%--<tr class="noBorder">--%>
                    <%--<td width="100%" align="center">--%>
                        <%--<input value="${user.fullName}" type="Text" readonly="true" class="insertInput" style="width:90%;font-family:Times New Roman;font-size:11pt;"/>--%>
                        <%--<br/>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <tr class="noBorder">
                    <td>
                        <table width="100%">
                            <tr>
                                <td  width="33%">
                                    <c:set var="curDate" value="${phiz:currentDate()}"/>
                                    <br/>${phiz:formatDateWithStringMonth(curDate)} г.
                                </td>
                                <td width="33%">
                                </td>
                                <td width="33%">
                                </td>
                            </tr>
                        </table><br/>
                    </td>
                </tr>
            </tiles:put>
        </tiles:insert>
		</c:forEach>

</c:otherwise>

</c:choose>