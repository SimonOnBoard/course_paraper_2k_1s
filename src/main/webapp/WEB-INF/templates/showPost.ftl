<#ftl encoding='UTF-8'>
<#include "base.ftl"/>

<#macro content>
    <img src="${post.getPhotoPath()}" width="200"/>
    <p>${post.getName()}</p>
    <p>${post.getText()}</p>
    <p>${post.getPublication().toString()}</p>
    <#list comments as comment>
        <div id="post_${comment.getKey().getId()}">
            <#if comment.getValue().getPhotoPath()??>
                <img src="${comment.getValue().getPhotoPath()}" width="30"/>
            <#else>
                No photo((
            </#if>

            <a href="/home?id=${comment.getKey().getOwnerId()}">
                ${comment.getValue().getNick()}
            </a>
            <p>
                ${comment.getKey().getText()}
            </p>
            <p>On data : ${comment.getKey().getDate().toString()}</p>
        </div>
    </#list>
    <form method="post" action="/comment">
        <p><input type="hidden" name="post_id" value="${post.getId()}"/></p>
        <p><textarea rows="10" cols="45" name="text"></textarea></p>
        <p><input type="submit" value="Отправить"></p>
    </form>
</#macro>
<#macro title>
    <title>Post</title>
</#macro>
<@display_page />