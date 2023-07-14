<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 11.09.2008
  Time: 15:02:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="imagePath" value="${skinUrl}/images"/>
<div class="lmRegion">
<table cellpadding="0" cellspacing="0" width="100%" class="leftMenu">
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td><img src="${imagePath}/lm_headerLeftCorner.gif" alt=""  width="10" height="11" border="0"></td>
					<td width="100%" class="lmBgHeader"></td>
					<td><img src="${imagePath}/lm_headerRightCorner.gif" alt="" width="11" height="11" border="0"></td>
				</tr>
			</table>
		</td>
	</tr>

	<tr>
		<td>
        <div class="lmInformationArea" id="lmInformationArea">
			<table cellpadding="0" cellspacing="0">
			<!-- Информационные блоки -->
			<c:if test="${phiz:writeCard() == 'true'}">
			<tr>
				<td>
					<tiles:insert definition="infoBlock" flush="false">
				        <tiles:put name="text" value="Информация пользователю"/>
				        <tiles:put name="image" value="iconBig_users.gif"/>
				        <tiles:put name="data">
					    <table cellpadding="0" cellspacing="4" width="100%">
						<tr><td class="infoTitle">Активная карта ключей</td></tr>
							<tr>
								<td>
									<c:set var="card" value="${phiz:activeCard()}"/>
									<c:choose>
										<c:when test="${card != null}">
											<c:set var="date" value="${card.activationDate}"/>
											<c:set var="style" value="menuInsertText backTransparent"/>
											<c:if test="${card.validPasswordsCount < 5}">
												<c:set var="style" value="attention"/>
											</c:if>
								        <span>Номер:&nbsp;${card.number}&nbsp;</span><br/>
										<span>Дата активации:&nbsp;<%out.println(String.format("%1$td.%1$tm.%1$tY", new Object[]{pageContext.getAttribute("date")}));%></span><br/>
										<span>Количество ключей:&nbsp;${card.passwordsCount}/${card.validPasswordsCount}</span><br/>
										</c:when>
										<c:otherwise>
									    <span class="attention">
										   У вас нет активной карты ключей
									    </span>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						  </table>
			              </tiles:put>
		            </tiles:insert>
				</td>
			</tr>
			</c:if>
			<c:if test="${phiz:impliesService('CurrenciesRateInfo')}">
                <c:set var="tarifPlanCodeType" value="${phiz:getActivePersonTarifPlanCode()}"/>
                <c:set var="department" value="${phiz:getDepartmentForCurrentClient()}"/>
                <tr>
                    <td>
                    <tiles:insert definition="infoBlock" flush="false">
                        <tiles:put name="text" value="Курсы валют"/>
                        <tiles:put name="image" value="iconBig_current.gif"/>
                        <tiles:put name="data">
                            <table cellpadding="0" cellspacing="0" width="100%" class="currencyTbl">
                            <tr>
                                <td>Покупка</td>
                                <td>&nbsp;</td>
                                <td>Продажа</td>
                            </tr>
                            <tr>
                                <td>
                                    <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('RUB','USD', 'BUY', department, tarifPlanCodeType), 2)}"/>
                                    <c:choose>
                                        <c:when test="${rate != ''}">${rate}</c:when>
                                        <c:otherwise> &mdash; </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="colorText">USD</td>
                                <td>
                                    <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('USD','RUB', 'SALE', department, tarifPlanCodeType), 2)}"/>
                                    <c:choose>
                                        <c:when test="${rate != ''}">${rate}</c:when>
                                        <c:otherwise> &mdash; </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('RUB','EUR', 'BUY', department, tarifPlanCodeType), 2)}"/>
                                    <c:choose>
                                        <c:when test="${rate != ''}">${rate}</c:when>
                                        <c:otherwise> &mdash; </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="colorText">EUR</td>
                                <td>
                                    <c:set var="rate" value="${phiz:getFormattedCurrencyRate(phiz:getRateByDepartment('EUR','RUB', 'SALE', department, tarifPlanCodeType), 2)}"/>
                                    <c:choose>
                                        <c:when test="${rate != ''}">${rate}</c:when>
                                        <c:otherwise> &mdash; </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </table>
                        </tiles:put>
                    </tiles:insert>
                </td>
            </tr>
        </c:if>
		</table>
	</div>
		</td>
	</tr>
<!-- Левое меню -->
	<tr>
		<td class="subMenu"><br>
			<c:if test="${leftMenu != ''}">
				<%@ include file="leftMenu.jsp"%>				
			</c:if>
		</td>
	</tr>
	<tr>
		<td>
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td><img src="${imagePath}/lm_bottomLeftCorner.gif" alt="" width="10" height="22" border="0"></td>
					<td width="100%" class="lmBgBottom"></td>
					<td><img src="${imagePath}/lm_bottomRightCorner.gif" alt="" width="11" height="22" border="0"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>