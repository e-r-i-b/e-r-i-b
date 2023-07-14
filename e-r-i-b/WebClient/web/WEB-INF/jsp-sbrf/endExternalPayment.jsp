<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:form action="/external/payments/system/end">
    <tiles:insert definition="fnsMain">
        <tiles:put name="data" type="string">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="data">
                    <div align="center" class="title">
                        <bean:message bundle="commonBundle" key="description.continueVisit"/>
                    </div>
                    <div class="buttonsArea">

                       <c:set var="logoffUrl" value="${phiz:calculateActionURL(pageContext,'/async/logoff')}"/>

                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.exit"/>
                            <tiles:put name="commandHelpKey" value="button.exit"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                            <tiles:put name="action" value="/logoff.do"/>
                        </tiles:insert>
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.continueVisit"/>
                            <tiles:put name="commandHelpKey" value="button.continueVisit"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="isDefault" value="true"/>
                        </tiles:insert>
                    </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>