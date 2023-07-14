<%--
  Created by IntelliJ IDEA.
  User: Gainanov
  Date: 22.07.2008
  Time: 13:42:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/receivers/viewGorod" onsubmit="return setEmptyAction(event);">

	<c:set var="form" value="${ShowGorodCardInfoForm}"/>

	<script type="text/javascript">


    </script>
	<tiles:insert definition="personEdit">
		<tiles:put name="submenu" type="string" value="PaymentReceiversJ"/>
        <tiles:put name="pageTitle" type="string" value="Информация по карте Город"/>

		<tiles:put name="menu" type="string">
			<c:if test="${form.validCard}">
				<tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.save"/>
					<tiles:put name="commandHelpKey" value="button.save"/>
					<tiles:put name="bundle" value="personsBundle"/>
					<tiles:put name="image" value=""/>
					<tiles:put name="postbackNavigation" value="true"/>
                    <tiles:put name="viewType" value="blueBorder"/>
				</tiles:insert>
			</c:if>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.close.gorod"/>
				<tiles:put name="commandHelpKey" value="button.close.gorod.help"/>
				<tiles:put name="bundle"         value="personsBundle"/>
				<tiles:put name="image"          value=""/>
				<tiles:put name="action"  value="persons/receivers/list.do?kind=J&person=${form.activePerson.id}"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="data">

					<td height="100%">
                        <tiles:insert definition="tableTemplate" flush="false">
                            <tiles:put name="data">
						<c:choose>
							<c:when test="${form.validCard}">
									<tr>
										<td>
											<b>Номер карты Город (ПАН)</b>
										</td>
									</tr>
									<tr>
										<td>
											<c:out value="${form.card.id}"/>
										</td>
									</tr>
									<tr>
										<td>
											<b>Данные о владельце</b>
										</td>
									</tr>
									<tr>
										<td>
											<c:out value="${form.client.fullName}"/>
										</td>
									</tr>
									<tr>
										<td>
											<b>Адрес</b>
										</td>
									</tr>
									<tr>
										<td>
											<c:out value="${form.client.realAddress}"/>
										</td>
									</tr>
							</c:when>
							<c:otherwise>
									<tr>
										<td>
											<b>Владелец карты не совпадает с текущим клиентом</b>
										</td>
									</tr>
									<tr>
										<td>
											<b>Владелец карты:</b>&nbsp;
											<c:out value="${form.client.fullName}"/>
										</td>
									</tr>
									<tr>
										<td>
											<b>Клиент</b>&nbsp;
											<c:out value="${form.activePerson.fullName}"/>
										</td>
									</tr>
									<tr>
										<td>
											<b>Данные сохранены не будут</b>
										</td>
									</tr>
							</c:otherwise>
						</c:choose>
                            </tiles:put>
                        </tiles:insert>
					</td>
				</tiles:put>
			</tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
