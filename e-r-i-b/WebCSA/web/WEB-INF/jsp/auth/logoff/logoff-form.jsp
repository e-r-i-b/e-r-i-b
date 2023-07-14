<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<tiles:insert page="/WEB-INF/jsp/common/authBlock.jsp" flush="false">
    <tiles:put name="id" value="login-form"/>
    <tiles:put name="display" value="true"/> 
    <tiles:put name="data" type="string">
        <form action="${csa:calculateActionURL(pageContext, '/logoff.do')}">
            <div class="login">
                <h2>
                    <tiles:insert definition="authIcon" flush="false"/>
                    <span class="auth-title">Выход из системы</span>
                </h2>
                <div style="text-align: center;">
                    <span class="auth-title"><bean:message bundle="commonBundle" key="message.session.end"/></span>
                </div>
            </div>
            <div class="buttonsArea">
                <div id="loginButton" class="commandButton" onclick="location.replace('${csa:calculateActionURL(pageContext, '/index.do')}');">
                    <div class="buttonCarroty">
                        <div class="left-corner"></div>
                        <div class="text">
                            <span><bean:message bundle="commonBundle" key="title.login"/></span>
                        </div>
                        <div class="right-corner"></div>
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </form>
    </tiles:put>
</tiles:insert>
