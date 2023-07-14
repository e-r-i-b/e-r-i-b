<%@ page import="com.rssl.phizic.utils.DateHelper" %>
<%@ page import="java.util.Date" %>
<%--
  User: Zhuravleva
  Date: 15.11.2006
  Time: 11:55:51
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<tiles:importAttribute name="headerGroup" ignore="true"/>
<tiles:importAttribute name="headerMenu" ignore="true"/>
<c:set var="writeCard" value="${phiz:writeCard()}"/>
<c:if test="${headerGroup == 'true'}">

	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>

	<table cellspacing="0" cellpadding="0" width="100%" class="headerStyle">
		<tr>
			<td width="300px">
				<img src="${globalImagePath}/LogoBankHeaderLeft.gif"
				     alt="" border="0">
			</td>
			<td align="center" class="titleHeader">
				Система удаленного управления счетом "Онлайн-банкинг"
			</td>
			<td width="200px" align="right">
				<img src="${globalImagePath}/LogoBankHeaderRight.gif"
				     alt="" border="0">
			</td>
		</tr>
	</table>
	<table cellspacing="0" cellpadding="0" width="100%" class="headerStyle">
		<%--<tr><td> <c:if test="${headerMenu == 'true'}">Сегодня г.</c:if></td></tr>--%>
		<tr>
			<td>
				<c:if test="${headerMenu == 'true'}">Cегодня: <span class="menuInsertText backTransparent">
     		       <%=DateHelper.toString(new Date())%></span>
				</c:if>
			</td>
		</tr>
		<c:if test="${writeCard == 'true'}">
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
				  <span class="${style}">Номер активной карты ключей:&nbsp;${card.number},&nbsp;
				дата активации:&nbsp;<%out.println(String.format("%1$td.%1$tm.%1$tY", new Object[]{pageContext.getAttribute("date")}) + ", ");%>
				количество ключей:&nbsp;${card.passwordsCount}/${card.validPasswordsCount}
				</span>
						</c:when>
						<c:otherwise>
                       <span class="attention">
	                       У вас нет активной карты ключей
	                   </span>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:if>
	</table>
</c:if>
