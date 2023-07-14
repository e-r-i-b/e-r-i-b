
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="i" value="${i+1}" scope="request"/>

				<div STYLE="WIDTH:315PX; HEIGHT: 110PX; FLOAT:left">
					<table cellspacing="0" cellpadding="2" class="paymentFon listPayTab">
						<tr>
							<td colspan="3" class="borderB"><img src="${globalImagePath}/1x1.gif" alt="" width="1" height="1" border="0"></td>
						</tr>
						<tr>
							<td class="borderT">&nbsp;</td>
							<td align="center" class="titleLeftMenu borderT orangeLine" width="100%" style="color:#903E98;">
								<phiz:link action="${action}"
					                                    serviceId="${serviceId}">
                                     <phiz:param name="form" value="${serviceId}${params}"/>
									<span class="listPayTitle">${listPayTitle}</span>
								</phiz:link></td>
							<td class="borderT">&nbsp;</td>
						</tr>
						<tr>
							<td class="listPayImg" align="center">
								<phiz:link action="${action}"
					                                    serviceId="${serviceId}">
                                    <phiz:param name="form" value="${serviceId}${params}"/>
									<img src="${image}" border="0"/>
								</phiz:link>
							</td>
							<c:if test="${not empty actionHistoryLink}">
							<td valign="top" width="300px;"> ${description}<br>
								<phiz:menuLink action="${actionHistoryLink}" param="name=${serviceId}${params}"
					                    serviceId="${serviceId}" id="id${i}" align="right">История операций
								</phiz:menuLink>
							</td>
							</c:if>
						</tr>
					</table>
				</div>
