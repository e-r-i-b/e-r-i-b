<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/policy/clientTemplate" onsubmit="return setEmptyAction(event);">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="configEdit">
   	<tiles:put name="submenu" type="string" value="ClientTemplate"/>
    <tiles:put name="pageTitle" type="string"><bean:message bundle="policyClientBundle" key="form.title"/></tiles:put>
<tiles:put name="data" type="string">

    <tiles:importAttribute/>
    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

	<%@include file="/WEB-INF/jsp/persons/editAccessScript.jsp"%>
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="userOperations"/>
        <tiles:put name="buttons">
             <tiles:insert definition="commandButton" flush="false" operation="EditPolicyTemplateOperation">
                <tiles:put name="commandKey"         value="button.save"/>
                <tiles:put name="commandHelpKey"     value="button.save.help"/>
                <tiles:put name="bundle"             value="commonBundle"/>
                <tiles:put name="isDefault"            value="true"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false" operation="EditPolicyTemplateOperation">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"         value="commonBundle"/>
                <tiles:put name="onclick"        value="resetForm(event)"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="settingsBeforeInf">
            <img src="${imagePath}/info.gif" alt="" border="0">&nbsp;<bean:message bundle="policyClientBundle" key="form.schemes.policy.hint"/>
        </tiles:put>
		<tiles:put name="data">
		<tr>
            <c:set var="width" value="100%"/>

			<c:set var="subform" value="${form.simpleAccess}"/>
			<c:set var="policy" value="${subform.policy}"/>
			<c:if test="${!empty policy}">
                <c:set var="width" value="50%"/>
                <td>
                     <%@ include file="editAccess.jsp"%>
                </td>
			</c:if>
			<c:set var="subform" value="${form.secureAccess}"/>
			<c:set var="policy" value="${subform.policy}"/>
			<c:if test="${!empty policy}">
                <td width="${width}">
                    <%@ include file="editAccess.jsp"%>
                </td>
			</c:if>
		</tr>
        <tr style="height:30px">
            <td>
                <img src="${imagePath}/info.gif" alt="" border="0">&nbsp;
                <bean:message bundle="policyClientBundle" key="form.schemes.access.hint"/>
            </td>
        </tr>
        <tr>
            <td>
                <table cellpadding="0" cellspacing="0" class="standartTable">
                    <tr class="tblInfHeader">
                        <th class="titleTable"><bean:message bundle="policyClientBundle" key="form.schemes.access.TB"/></th>
                        <th class="titleTable" width="320px"><bean:message bundle="policyClientBundle" key="form.schemes.access.SBOL"/></th>
                        <th class="titleTable" width="320px"><bean:message bundle="policyClientBundle" key="form.schemes.access.UDBO"/></th>
                    </tr>
                    <%int lineNumber = 0;%>
                    <c:forEach items="${form.terbanks}" var="dep">
                        <% lineNumber++; %>
                        <tr class="ListLine<%=lineNumber%2%>">
                            <td><c:out value="${dep.name}"/></td>
                            <td align="center">
                                <html:select property="schemesSBOL(${dep.region})" style="font-size:12px;height:17px">
                                    <html:option value="" style="color:gray">
                                        <bean:message key="label.noSchemes" bundle="personsBundle"/>
                                    </html:option>
                                    <c:forEach var="helper" items="${form.helpers}">
                                        <c:set var="helperCategory" value="${helper.category}"/>
                                        <c:set var="accessSchemes" value="${helper.schemes}"/>
                                        <c:forEach var="scheme" items="${accessSchemes}">
                                            <html:option value="${scheme.id}" style="color:black;">
                                                <c:out value="${scheme.name}"/>
                                            </html:option>
                                        </c:forEach>
                                    </c:forEach>
                                </html:select>
                            </td>
                            <td align="center">
                              <html:select property="schemesUDBO(${dep.region})" style="font-size:12px;height:17px">
                                <html:option value="" style="color:gray">
                                    <bean:message key="label.noSchemes" bundle="personsBundle"/>
                                </html:option>
                                <c:forEach var="helper" items="${form.helpers}">
                                    <c:set var="helperCategory" value="${helper.category}"/>
                                    <c:set var="accessSchemes" value="${helper.schemes}"/>
                                    <c:forEach var="scheme" items="${accessSchemes}">
                                        <html:option value="${scheme.id}" style="color:black;">
                                            <c:out value="${scheme.name}"/>
                                        </html:option>
                                    </c:forEach>
                                </c:forEach>
                              </html:select>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <div class="clear"></div>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="policyClientBundle" key="form.schemes.access.CARD"/>
                    </tiles:put>
                    <tiles:put name="needMargin" value="true"/>
                    <tiles:put name="data">
                        <html:select property="schemeCARD">
                            <html:option value="" style="color:gray">
                                <bean:message key="label.noSchemes" bundle="personsBundle"/>
                            </html:option>
                            <c:forEach var="helper" items="${form.helpers}">
                                <c:set var="helperCategory" value="${helper.category}"/>
                                <c:set var="accessSchemes" value="${helper.schemes}"/>
                                <c:forEach var="scheme" items="${accessSchemes}">
                                    <html:option value="${scheme.id}" style="color:black;">
                                        <c:out value="${scheme.name}"/>
                                    </html:option>
                                </c:forEach>
                            </c:forEach>
                        </html:select>
                    </tiles:put>
                </tiles:insert>

                <div class="clear"></div>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="policyClientBundle" key="form.schemes.access.CARD_TEMPORARY"/>
                    </tiles:put>
                    <tiles:put name="needMargin" value="true"/>
                    <tiles:put name="data">
                        <html:select property="schemeCARD_TEMPORARY">
                            <html:option value="" style="color:gray">
                                <bean:message key="label.noSchemes" bundle="personsBundle"/>
                            </html:option>
                            <c:forEach var="helper" items="${form.helpers}">
                                <c:set var="helperCategory" value="${helper.category}"/>
                                <c:set var="accessSchemes" value="${helper.schemes}"/>
                                <c:forEach var="scheme" items="${accessSchemes}">
                                    <html:option value="${scheme.id}" style="color:black;">
                                        <c:out value="${scheme.name}"/>
                                    </html:option>
                                </c:forEach>
                            </c:forEach>
                        </html:select>
                    </tiles:put>
                </tiles:insert>

                <div class="clear"></div>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="policyClientBundle" key="form.schemes.access.UDBO_TEMPORARY"/>
                    </tiles:put>
                    <tiles:put name="needMargin" value="true"/>
                    <tiles:put name="data">
                        <html:select property="schemeUDBO_TEMPORARY">
                            <html:option value="" style="color:gray">
                                <bean:message key="label.noSchemes" bundle="personsBundle"/>
                            </html:option>
                            <c:forEach var="helper" items="${form.helpers}">
                                <c:set var="helperCategory" value="${helper.category}"/>
                                <c:set var="accessSchemes" value="${helper.schemes}"/>
                                <c:forEach var="scheme" items="${accessSchemes}">
                                    <html:option value="${scheme.id}" style="color:black;">
                                        <c:out value="${scheme.name}"/>
                                    </html:option>
                                </c:forEach>
                            </c:forEach>
                        </html:select>
                    </tiles:put>
                </tiles:insert>

                <div class="clear"></div>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="title">
                        <bean:message bundle="policyClientBundle" key="form.schemes.access.GUEST"/>
                    </tiles:put>
                    <tiles:put name="needMargin" value="true"/>
                    <tiles:put name="data">
                        <html:select property="schemeGUEST">
                            <html:option value="" style="color:gray">
                                <bean:message key="label.noSchemes" bundle="personsBundle"/>
                            </html:option>
                            <c:forEach var="helper" items="${form.helpers}">
                                <c:set var="helperCategory" value="${helper.category}"/>
                                <c:set var="accessSchemes" value="${helper.schemes}"/>
                                <c:forEach var="scheme" items="${accessSchemes}">
                                    <html:option value="${scheme.id}" style="color:black;">
                                        <c:out value="${scheme.name}"/>
                                    </html:option>
                                </c:forEach>
                            </c:forEach>
                        </html:select>
                    </tiles:put>
                </tiles:insert>
            </td>
        </tr>
	</tiles:put>
	</tiles:insert>
		
</tiles:put>
</tiles:insert>
</html:form>