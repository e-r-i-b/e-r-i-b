<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/deposits/calculator" onsubmit="return setEmptyAction(event)">
	<tiles:insert definition="depositMain">
		<tiles:put name="submenu" type="string" value="depositCalculator"/>
        <tiles:put type="string" name="needSave" value="false"/>
		<!-- заголовок -->
		<tiles:put name="pageTitle" type="string">
			<c:out value="Калькулятор депозитных продуктов"/>
		</tiles:put>

		<!--меню-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.depositsList"/>
				<tiles:put name="commandHelpKey" value="button.depositsList"/>
				<tiles:put name="bundle" value="depositsBundle"/>
				<tiles:put name="action" value="/private/deposits.do"/>
			</tiles:insert>
        </tiles:put>

        <!-- фильтр  -->
     
        <!-- данные -->
        <tiles:put name="data" type="string">
	        <tiles:importAttribute/>
	    	<c:set var="globalImagePath" value="${globalUrl}/images"/>
			<c:set var="imagePath" value="${skinUrl}/images"/>
	    <table cellpadding="0" cellspacing="0" class="pmntGlobal">
			<tr>
				<td class="pmntBgBaseTopLeftCorner"></td>
				<td class="pmntBgBaseTop"><img src="${imagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
				<td class="pmntBgBaseTopRightCorner"></td>
			</tr>
			<tr>
				<td class="pmntBgBaseLeftCorner">&nbsp;</td>
				<td class="pmntBgBase">
					<table cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<td class="pmntTitleBg">
								<table cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td class="label120">
											<table cellpadding="0" cellspacing="0" class="pmntIcon" align="center">
												<tr>
													<td>
														<img src="${imagePath}/iconBig_calculator.gif" alt="" width="31" height="37" border="0">
													</td>
												</tr>
											</table>
										</td>
										<td class="pmntTitleText">
											<span>Калькулятор вкладов</span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
                        <tr class="pmntInfArea">
                            <td>
                                ${DepositCalculatorForm.html}
                            </td>
                        </tr>
	                    <tr>
							<td>
								<table cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td class="pmntInfAreaShadowLeftCorner"></td>
										<td class="pmntInfAreaShadowBg"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
										<td class="pmntInfAreaShadowRightCorner"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
				<td class="pmntBgBaseRightCorner">&nbsp;</td>
			</tr>
			<tr>
				<td class="pmntBgBaseBtmLeftCorner"></td>
				<td class="pmntBgBaseBtm"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
				<td class="pmntBgBaseBtmRightCorner"></td>
			</tr>
		</table>
        </tiles:put>
	</tiles:insert>
</html:form>

