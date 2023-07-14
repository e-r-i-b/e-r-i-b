<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>

<div class="greenTopRT r-top" onclick="${onclick}">
    <div class="greenTopRTL r-top-left">
        <div class="greenTopRTR r-top-right">
            <div class="greenTopRTC r-top-center">
                <div class="greenTopCBT"></div>
                <div class="greenTopTitle">
                    <span>${name}</span>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>