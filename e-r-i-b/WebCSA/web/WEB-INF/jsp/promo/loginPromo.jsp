<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="logic" uri="http://jakarta.apache.org/struts/tags-logic" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<c:set var="form" value="${csa:currentForm(pageContext)}"/>

    <tiles:insert definition="loginMain" flush="false">
        <tiles:put name="data" type="string">
            <tiles:put name="showHelp" value="false"/>
            <tiles:put name="showRegistration" value="false"/>
            <tiles:put name="showSecurityBlock" value="false"/>
            <html:form action="/loginPromo">
                <div class="loginPromo">
                    <div id="content">
                        <%--Блок с фоновым рисунком и регистрацией--%>
                        <div class="sliderBlock">
                            <%@include file="/WEB-INF/jsp/promo/promoRegistration.jsp"%>
                        </div>

                    </div>
                </div>
            </html:form>
        </tiles:put>

        <tiles:put name="scripts">
            <c:if test="${not empty form.tempCookie}">
                <script type="text/javascript">
                    function init(){
                        showOrHideAjaxPreloader(true);
                        var expDate = new Date();
                        <%-- Время жизни куки 12 часов --%>
                        expDate.setTime(expDate.getTime() + 1000 * 60 * 60 * 12);
                        document.cookie = "${csa:escapeForJS(form.tempCookie, false)}" + "; expires=" + expDate.toUTCString();
                        window.location = "${csa:calculateActionURL(pageContext, '/index')}";
                    }
                    init();
                </script>
            </c:if>

            <script type="text/javascript">
                function initBAW(obj) {
                    if (obj.value == 'BAW')
                    {
                        $.data($('#loginForm')[0], 'jForm').clearVSPValidator();
                        $('#CodeVSP').parent().hide();
                    }
                    else
                    {
                        $('#CodeVSP').parent().show();
                        $.data($('#loginForm')[0], 'jForm').fillVSPValidator();
                    }
                }

                $(document).ready(function(){
                    try {
                        initBAW(document.getElementById('ChannelId'));
                    }
                    catch (e) { }
                });

                function submitData()
                {
                    showOrHideAjaxPreloader(true);
                    var channelField = document.getElementById("ChannelId");
                    var bankField = document.getElementById("Bank");
                    var osbField = document.getElementById("CodeOSB");
                    var vspField = document.getElementById("CodeVSP");
                    var promoIdField = document.getElementById("CodeID");
                    var form = document.createElement("form");
                    form.setAttribute("method", "post");
                    form.setAttribute("action", "${csa:calculateActionURL(pageContext, '/loginPromo')}");
                    var hiddenField = document.createElement("input");
                    hiddenField.setAttribute("type", "hidden");
                    hiddenField.setAttribute("name", "operation");
                    hiddenField.setAttribute("value", "button.loginPromo");
                    form.appendChild(hiddenField);
                    form.appendChild(channelField);
                    form.appendChild(bankField);
                    form.appendChild(osbField);
                    form.appendChild(vspField);
                    form.appendChild(promoIdField);
                    document.body.appendChild(form);
                    form.submit();
                }
            </script>
        </tiles:put>
    </tiles:insert>

