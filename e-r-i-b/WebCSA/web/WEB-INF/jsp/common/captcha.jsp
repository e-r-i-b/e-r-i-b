<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<tiles:useAttribute name="id"/>
<div id="${id}" class="reg_captcha visibled captcha-block" style="display:block">
    <div class="clear"></div>
    <div id="captchaImage" style="margin: 0 auto; width: 170px; height: 55px;">
        <tiles:useAttribute name="show"/>
        <c:if test="${show}">
            <tiles:useAttribute name="url"/>
            <img src="/${csa:loginContextName()}/${url}?=<%=java.lang.Math.round(java.lang.Math.random() * 100000)%>" width="100%" height="100%"/>
        </c:if>
    </div>
    <div class="update-captcha">
        <a href="#" tabindex="3">обновить код</a>
    </div>
    <!-- пол€ дл€ капчи -->
    <input id="ccode" class="customPlaceholder codeCaptcha" type="text" name="field(ccode)" maxlength="10" title="¬ведите код с картинки" tabindex="2"/>
    <input id="captchaCode" type="hidden" name="field(captchaCode)"/>
    <script type="text/javascript">
        <%-- список соответсви€ символа и кода. --%>
        (function (){

            $(".captcha-block").filter("#${id}").each(function(){
                $(this).find("#ccode").keyup(function(){
                    var code = $(this).val().toLowerCase(), convertedCode = "";

                    for (var i = 0; i < code.length; i++)
                        convertedCode += (code.charCodeAt(i) + '_');

                    $(this).closest(".captcha-block").find("#captchaCode").val(convertedCode);
                });

                $(this).find(".update-captcha a").click(function(){
                    $(".captcha-block").filter("#${id}")
                            .find("#captchaImage").html("<img src=\"/${csa:loginContextName()}/${url}?=" + Math.random() + "\" width=\"100%\" height=\"100%\">");
                    return false;
                });
            });
        })();
    </script>
</div>