<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/private/mobilebank/main" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>
            <tiles:insert definition="mobilebank">
                <tiles:put name="menu" type="string"/>
                <tiles:put name="data" type="string">
                    <tiles:insert definition="mainWorkspace" flush="false">
                        <tiles:put name="title" value="Мобильный банк"/>
                        <tiles:put name="data">
                            <div id="mobilebank">
                                <bean:message arg0="${form.fo}"
                                              key="message.ermb.in.other.tb"
                                              bundle="mobilebankBundle"/>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
</html:form>