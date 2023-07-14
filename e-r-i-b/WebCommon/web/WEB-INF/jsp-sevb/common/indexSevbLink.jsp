<%--
  Created by IntelliJ IDEA.
  User: osminin
  Date: 11.02.2009
  Time: 14:14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<tiles:importAttribute/>
<c:set var="i" value="${i+1}" scope="request"/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<c:if test="${empty listPayTitle and empty description}">
	<c:set var="descriptions" value="${phiz:getPaymentDescription(serviceId, appointment)}"/>
	<c:set var="listPayTitle" value="${descriptions.description}"/>
	<c:set var="description" value="${descriptions.detailedDescription}"/>
</c:if>

<div style="width:375px;height:100px;float:left;padding:2px;">
<table cellspacing="3" cellpadding="3" class="paymentFon listPayTab">
	<tr>
		<td class="listPayImg" align="center">

			<phiz:link action="${action}"
			           serviceId="${serviceId}" param="${params}">
                <c:choose>
                    <c:when test="${notForm}">
                        <phiz:param name="appointment" value="${appointment}"/>
                    </c:when>
                    <c:otherwise>
                        <phiz:param name="form" value="${serviceId}"/>
                    </c:otherwise>
                </c:choose>
				<c:choose>
					<c:when test="${not empty image}">
						<c:set var="imagePath" value="${imagePath}/${image}.gif"/>
					</c:when>
					<c:when test="${not empty appointment}">
						<c:set var="imagePath" value="${imagePath}/iconPmntList_${appointment}.gif"/>
					</c:when>
					<c:otherwise>
						<c:set var="imagePath" value="${imagePath}/${serviceId}.gif"/>
					</c:otherwise>
				</c:choose>

				<img src="${imagePath}" border="0"/>
			</phiz:link>
		</td>
		<td valign="top" width="300px;">
			<phiz:link action="${action}" serviceId="${serviceId}" param="${params}">
                <c:choose>
				<c:when test="${notForm}">
                    <phiz:param name="appointment" value="${appointment}"/>
                </c:when>
                <c:otherwise>
                    <phiz:param name="form" value="${serviceId}"/>
                </c:otherwise>
                </c:choose>
				<span class="listPayTitle">${listPayTitle}</span>
			</phiz:link>
			<br>${description}<br>
			<c:if test="${not empty actionHistoryLink}">
				<phiz:menuLink action="${actionHistoryLink}" param="name=${serviceId}&appointment=${appointment}${params}"
				               serviceId="${serviceId}" id="m${i}" align="right">История операций
				</phiz:menuLink>
			</c:if>
		</td>
	</tr>
</table>
</div>