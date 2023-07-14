<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<tiles:insert definition="front" flush="false">
    <tiles:put name="data" type="string">
        <div class="noAccess">
            <tiles:insert definition="errorBlock" flush="false">
                <tiles:put name="regionSelector" value="errors"/>
                <tiles:put name="isDisplayed" value="${true}"/>
                <tiles:put name="data">
                    <bean:message bundle="businessEnvironmentBundle" key="form.login.fail.message" arg0="${csa:getBusinessEnvironmentMainURL()}"/>
                </tiles:put>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>