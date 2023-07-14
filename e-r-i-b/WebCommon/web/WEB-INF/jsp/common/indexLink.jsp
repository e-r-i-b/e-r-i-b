<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<tiles:importAttribute ignore="true"/>
<c:set var="i" value="${i+1}" scope="request"/>
	<c:set var="globalImagePath" value="${globalUrl}/images"/>
	<c:set var="imagePath" value="${skinUrl}/images"/>

<c:if test="${empty listPayTitle and empty description}">
	<c:set var="descriptions" value="${phiz:getPaymentDescription(serviceId, appointment)}"/>
	<c:set var="listPayTitle" value="${descriptions.description}"/>
	<c:set var="description" value="${descriptions.detailedDescription}"/>
</c:if>

<div class="pmntList">
<table cellpadding="0" cellspacing="0">
<tr>
	<td class="pmntListBgTopLeftCorner"></td>
	<td class="pmntListBgTop"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
	<td class="pmntListBgTopRightCorner"></td>
</tr>
<tr>
	<td class="pmntListBgLeftCorner">&nbsp;</td>
	<td class="pmntListBg" valign="top">
		<table cellspacing="3" cellpadding="3" width="100%" height="100%" class="pmntListInfArea">
		<tr>
			<td class="pmntListIcon" align="center" rowspan="2">

					    <phiz:link action="${action}"
						    serviceId="${serviceId}">
                            <c:if test="${not empty params}">
                                <c:forEach var="name" items="${params[0]}" varStatus="status">
                                    <c:set var="value" value="${params[1][status.index]}"/>
                                    <phiz:param name="${name}" value="${value}"/>
                                </c:forEach>
                            </c:if>
                            <c:choose>
                            <c:when test="${notForm}">
                            <phiz:param name="appointment" value="${appointment}"/>
                            </c:when>
                            <c:otherwise>
                                <phiz:param name="form" value="${serviceId}"/>
                                </c:otherwise>
                            </c:choose>
						<c:choose>
							<c:when test="${not empty image}"><c:set var="imagePath" value="${imagePath}/${image}"/></c:when>
							<c:when test="${not empty appointment}"><c:set var="imagePath" value="${imagePath}/iconPmntList_${appointment}.gif"/></c:when>
							<c:otherwise><c:set var="imagePath" value="${imagePath}/iconPmntList_${serviceId}.gif"/></c:otherwise>
						</c:choose>

						<img src="${imagePath}" border="0"/>
					</phiz:link>

			</td>
			<td valign="top" width="300px;">
					    <phiz:link action="${action}"
						    serviceId="${serviceId}">
                            <c:if test="${not empty params}">
                                <c:forEach var="name" items="${params[0]}" varStatus="status">
                                    <c:set var="value" value="${params[1][status.index]}"/>
                                    <phiz:param name="${name}" value="${value}"/>
                                </c:forEach>
                            </c:if>
                            <c:choose>
                            <c:when test="${notForm}">
                            <phiz:param name="appointment" value="${appointment}"/>
                            </c:when>
                            <c:otherwise>
                                <phiz:param name="form" value="${serviceId}"/>
                                </c:otherwise>
                            </c:choose>
							<span class="pmntListTitle">${listPayTitle}</span>
						</phiz:link>
				<br>${description}<br>
			</td>
		</tr>
		<tr>
			<td align="right">
				<c:if test="${not empty actionHistoryLink}">
                    <c:set var="imagePath" value="${skinUrl}/images"/>
                    <c:if test="${not empty serviceId}">
                        <c:set var="url" value="${phiz:calculateActionURL(pageContext,actionHistoryLink)}"/>
                        <c:set var="par" value=""/>
                        <c:if test="${not empty params}">
                            <c:forEach var="name" items="${params[0]}" varStatus="status">
                                <c:set var="value" value="${params[1][status.index]}"/>
                                <c:set var="par">${par}&${name}=${value}</c:set>
                            </c:forEach>
                        </c:if>
                        <a onmouseout="javascript:linkUnFocus(this,'${i}')"
                                onmouseover="javascript:linkFocus(this,'${i}')"
                                onclick="loadNewAction('','');window.location='${url}?name=${serviceId}${par}';"
                                href="#"
                                class="linkUnFocus">
                            <img src="${imagePath}/iconSm_circle.gif" id="img${i}" alt="" border="0">
                            <span class='menuInsertText' id="spanA${i}">История операций</span>
                        </a>
                    </c:if>
   			    </c:if>
			</td>
		</tr>
		</table>
	</td>
	<td class="pmntListBgRightCorner">&nbsp;</td>
</tr>
<tr>
	<td class="pmntListBgBtmLeftCorner"></td>
	<td class="pmntListBgBtm"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
	<td class="pmntListBgBtmRightCorner"></td>
</tr>
</table>
</div>
