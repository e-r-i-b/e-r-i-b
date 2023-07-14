<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/skins/sbrf/images"/>
<c:set var="form" value="${csa:currentForm(pageContext)}"/>
<c:set var="loginUrl" value="${csa:calculateActionURL(pageContext, '/loginPromo')}"/>


<tiles:put name="loginFormHeader">

    <span class="b-ie">
        <i class="bl"></i><i class="br"></i><i class="tl"></i><i class="tr"></i><i class="l"></i><i class="r"></i><i class="t"></i><i class="b"></i>
    </span><!-- // b-ie -->

    <div class="auth_header invoice_promo">
        Программа продвижения
    </div>
    <div class="auth_body">

        <div class="b-logo">
            <img class="logo_img" width="143" height="41" src="${skinUrl}/skins/sbrf/images/csa/loginPage/b-logo_img.png" alt=""/>
        </div><!-- // g-logo -->

        <form autocomplete="off" id="loginForm"  action="${loginUrl}" class="b-form auth_form">

            <div onclick="return{valid:{required: true}}" class="b-field">
                <div class="field_inner">
                    <select id="ChannelId" name="field(channelId)" class="field_select" type="text" onchange="initBAW(this);">
                        <option value="">Выберите канал</option>
                        <c:forEach items="${form.promoChannels}" var="channel">
                            <c:set var="selected" value=""/>
                            <c:if test="${channel==form.fields['channelId']}">
                                <c:set var="selected">selected</c:set>
                            </c:if>
                            <option value="${channel}" ${selected}>${channel.description}</option>
                        </c:forEach>
                    </select>
                </div>
            </div><!-- // b-field -->

            <div onclick="return{valid:{required: true}}" class="b-field">
                <div class="field_inner">
                    <select id="Bank" name="field(tb)" class="field_select" type="text">
                        <option value="">Выберите тербанк</option>
                        <c:forEach items="${form.promoTbList}" var="terbank">
                            <c:set var="tbCode" value="${terbank.code}"/>
                            <c:set var="selected" value=""/>
                            <c:if test="${tbCode.region==form.fields['tb']}">
                                <c:set var="selected">selected</c:set>
                            </c:if>
                            <option value="${tbCode.region}" ${selected}>${terbank.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div><!-- // b-field -->

            <div onclick="return{valid:{required: true, minLen: 1, maxLen: 4}}" class="b-field">
                <div class="field_inner">
                    <input type="text" id="CodeOSB" name="field(osb)" class="field_input" value="${form.fields['osb']}"/>
                    <c:set var="styleCode">
                    <c:if test="${not empty form.fields['osb']}">style="display:none;"</c:if>
                    </c:set>
                    <label for="CodeOSB" class="field_title" ${styleCode}>Код ОСБ</label>
                </div>
            </div><!-- // b-field -->

            <div onclick="return{valid:{required: true, minLen: 1, maxLen: 7}}" class="b-field">
                <div class="field_inner">
                    <input type="text" id="CodeVSP" name="field(vsp)" class="field_input" value="${form.fields['vsp']}"/>
                    <c:set var="styleCode">
                    <c:if test="${not empty form.fields['vsp']}">style="display:none;"</c:if>
                    </c:set>
                    <label for="CodeVSP" class="field_title" ${styleCode}>Код ВСП</label>
                </div>
            </div><!-- // b-field -->

            <div onclick="return{valid:{required: true, minLen: 3, maxLen: 100}}" class="b-field">
                <div class="field_inner">
                    <input type="text" id="CodeID" name="field(promoId)" class="field_input">
                    <label for="CodeID" class="field_title">Идентификатор</label>
                </div>
            </div><!-- // b-field -->

            <div class="form_note">
                Проверьте все поля. В&nbsp;случае ошибки привлеченные клиенты будут засчитаны другому промоутеру.
            </div>

            <div class="form_submit" onclick="validateAndSubmit()" id="buttonSubmit">

                <div class="b-btn btn-large btn-yellow">
                    <div class="btn_text">Начать смену</div>
                    <i class="btn_curve"></i>
                    <button class="btn_hidden" type="button"></button>
                </div><!-- // b-btn -->

            </div>
        </form><!-- // b-form -->

    </div>
</tiles:put>

<tiles:put name="showLoginAndPasswordForm" value="false"/>

