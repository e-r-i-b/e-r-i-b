<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:insert page="/WEB-INF/jsp/common/authBlock.jsp" flush="false">
    <tiles:put name="id" value="login-form"/>
    <tiles:put name="display" value="block"/>
    <tiles:put name="data" type="string">
        <form action="${csa:calculateActionURL(pageContext, '/index.do')}">
            <div class="login">
                <h2>
                    <span class="auth-title">ќбратите внимание!</span>
                </h2>
                <div class="description-block">
                    ƒанна€ ссылка предназначена только дл€ оплаты товаров и услуг с других сайтов.
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



