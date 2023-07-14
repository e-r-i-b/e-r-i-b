<%--
  Created by IntelliJ IDEA.
  User: lepihina
  Date: 09.11.2011
  Time: 13:35:12
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<c:set var="imagePathGlobal" value="${globalUrl}/commonSkin/images"/>


<div class="title">Обратите внимание</div>
<div class="provHeader">
    По указанным реквизитам найдено несколько получателей. Для продолжения операции выберите нужного получателя перевода.
</div>
<div>
    <div class="filterInfo">
        <span class="bold">Номер счета: </span><span class="filterInfoData">123456789465132</span>
        <span class="bold">ИНН: </span><span class="filterInfoData">12346 456 45 12345</span>
        <span class="bold">БИК: </span><span class="filterInfoData">1234567894</span>
    </div>
    <div class="triangle bottomFilterInfo"></div>
</div>

<script type="text/javascript">

function showOtherRegions(id)
{
    var elem = ensureElement('otherRegions'+id);

    if(elem != null)
	    elem.style.display = "";
}

function hideOtherRegions(id)
{
    var elem = ensureElement('otherRegions'+id);

    if(elem != null)
	    elem.style.display = "none";
}

</script>

<div class="regions">
    <c:set var="id" value="0"/>

    <div class="regionWithProviders">
        <%--название области/края--%>
        <div class="titleRegion">
            <span class="bold">Московская область</span>
        </div>

        <div>
            <tiles:insert definition="providerTemplate" flush="false">
                <tiles:put name="data" value=""/>
                <tiles:put name="id" value="${id}"/>
            </tiles:insert>
            <c:set var="id" value="${id+1}"/>
            <tiles:insert definition="providerTemplate" flush="false">
                <tiles:put name="data" value=""/>
                <tiles:put name="id" value="${id}"/>
            </tiles:insert>
            <c:set var="id" value="${id+1}"/>
            <tiles:insert definition="providerTemplate" flush="false">
                <tiles:put name="data" value=""/>
                <tiles:put name="id" value="${id}"/>
            </tiles:insert>
            <c:set var="id" value="${id+1}"/>
            <tiles:insert definition="providerTemplate" flush="false">
                <tiles:put name="data" value=""/>
                <tiles:put name="id" value="${id}"/>
            </tiles:insert>
            <c:set var="id" value="${id+1}"/>
        </div>
    </div>

    <%--название области/края--%>
    <div class="regionWithProviders">
        <div class="titleRegion">
            <span class="bold">Краснодарский край</span>
        </div>

        <div>
            <tiles:insert definition="providerTemplate" flush="false">
                <tiles:put name="data" value=""/>
                <tiles:put name="id" value="${id}"/>
            </tiles:insert>
            <c:set var="id" value="${id+1}"/>
        </div>
    </div>

    <%--название области/края--%>
    <div class="regionWithProviders">
        <div class="titleRegion">
            <span class="bold">Тверская область</span>
        </div>

        <div>
            <tiles:insert definition="providerTemplate" flush="false">
                <tiles:put name="data" value=""/>
                <tiles:put name="id" value="${id}"/>
            </tiles:insert>
            <c:set var="id" value="${id+1}"/>
            <tiles:insert definition="providerTemplate" flush="false">
                <tiles:put name="data" value=""/>
                <tiles:put name="id" value="${id}"/>
            </tiles:insert>
            <c:set var="id" value="${id+1}"/>
        </div>
    </div>
</div>
<div class="buttonsArea">
    <div class="inactivePaginLeftArrow"></div>
    <div class="inactivePaginRightArrow"></div>
</div>

