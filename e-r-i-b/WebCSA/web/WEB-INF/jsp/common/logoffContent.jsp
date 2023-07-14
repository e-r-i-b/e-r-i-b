<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<tiles:insert definition="front" flush="false">
    <tiles:put name="data" type="string">
        <div id="content">
            <%-- Блок с фоновым рисунком и регистрацией--%>
            <div class="sliderBlock" id="authBlock">
                <tiles:insert page="/WEB-INF/jsp/auth/logoff/logoffForm.jsp" flush="false">
                    <tiles:putList name="formItems">
                        <tiles:add value="/WEB-INF/jsp/auth/logoff/logoff-form.jsp"/>
                    </tiles:putList>
                </tiles:insert>
            </div>
            </div>
        <div class="clear"></div>
    </tiles:put>
</tiles:insert>
