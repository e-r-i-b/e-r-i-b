<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib prefix="html"  uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean"  uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="sl"    uri="http://struts.application-servers.com/layout" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz"  uri="http://rssl.com/tags" %>

<c:set var="imagePath" value="${skinUrl}/images"/>

<html:form action="/dictionaries/synchronization/information">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="dictionaries">
        <tiles:put name="submenu" type="string" value="DictionariesSynchronizationInformation"/>
        <tiles:put name="pageTitle"><bean:message bundle="synchronizationInformationBundle" key="form.page.title"/></tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="id" value="DictionariesSynchronizationInformation"/>
                <tiles:put name="data">
                    <script type="text/javascript">
                        doOnLoad(function(){
                            $('.plusImage').click(
                                function()
                                {
                                    var div = $(this).parent().find('div');
                                    if(div.is(":visible"))
                                    {
                                        div.hide();
                                        $(this).attr("src",'${imagePath}/iconSm_triangleDown.gif');
                                    }
                                    else
                                    {
                                        div.show();
                                        $(this).attr("src",'${imagePath}/iconSm_triangleUp.gif');
                                    }
                                }
                            );
                        });
                    </script>
                    <tr class="tblInfHeader">
                        <th class="titleTable">
                            <bean:message bundle="synchronizationInformationBundle" key="form.page.table.column.nodeName"/>
                        </th>
                        <th class="titleTable">
                            <bean:message bundle="synchronizationInformationBundle" key="form.page.table.column.state"/>
                        </th>
                        <th class="titleTable">
                            <bean:message bundle="synchronizationInformationBundle" key="form.page.table.column.lastUpdateDate"/>
                        </th>
                        <th class="titleTable Width120">
                            <bean:message bundle="synchronizationInformationBundle" key="form.page.table.column.errorDetail"/>
                        </th>
                    </tr>
                    <c:forEach var="information" items="${form.data}" varStatus="lineInfo">
                        <tr class="ListLine${(lineInfo.count + 1) % 2}">
                            <td class="listItem">
                                <c:out value="${information.nodeName}"/>
                            </td>
                            <td class="listItem">
                                <c:choose>
                                    <c:when test="${not empty information.state}">
                                        <tiles:insert definition="roundedPlate" flush="false">
                                            <tiles:put name="data">
                                                <bean:message bundle="synchronizationInformationBundle" key="form.page.table.column.state.${information.state}"/>
                                            </tiles:put>
                                            <c:choose>
                                                <c:when test="${information.state eq 'PROCESS'}"><tiles:put name="color" value="gray"/></c:when>
                                                <c:when test="${information.state eq 'ERROR'}"><tiles:put name="color" value="red"/></c:when>
                                                <c:otherwise><tiles:put name="color" value="green"/></c:otherwise>
                                            </c:choose>
                                        </tiles:insert>
                                    </c:when>
                                    <c:otherwise>
                                        &mdash;
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="listItem">
                                <c:choose>
                                    <c:when test="${not empty information.lastUpdateDate}">
                                        <bean:write name="information" property="lastUpdateDate.time" format="dd.MM.yyyy HH:mm:ss"/>
                                    </c:when>
                                    <c:otherwise>
                                        &mdash;
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="listItem">
                                <c:choose>
                                    <c:when test="${not empty information.errorDetail}">
                                        <img class="plusImage pointer" src='${imagePath}/iconSm_triangleDown.gif' border='0'/>
                                        <div class="displayNone">
                                            <pre><c:out value="${information.errorDetail}"/></pre>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        &mdash;
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    <tiles:put name="buttons">
                        <tiles:insert definition="commandButton" operation="DictionaryInformationOperation" flush="false">
                            <tiles:put name="commandKey"     value="button.startSynchronizationInCurrentBlock"/>
                            <tiles:put name="commandTextKey" value="button.startSynchronizationInCurrentBlock"/>
                            <tiles:put name="commandHelpKey" value="button.startSynchronizationInCurrentBlock.help"/>
                            <tiles:put name="bundle"         value="synchronizationInformationBundle"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" operation="DictionaryInformationOperation" flush="false">
                            <tiles:put name="commandKey"     value="button.sendSynchronizationNotification"/>
                            <tiles:put name="commandTextKey" value="button.sendSynchronizationNotification"/>
                            <tiles:put name="commandHelpKey" value="button.sendSynchronizationNotification.help"/>
                            <tiles:put name="bundle"         value="synchronizationInformationBundle"/>
                        </tiles:insert>
                    </tiles:put>
                    <tiles:put name="isEmpty" value="false"/>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>