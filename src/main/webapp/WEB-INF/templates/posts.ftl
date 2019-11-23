<#ftl encoding='UTF-8'>
<#include "base.ftl"/>

<#macro content>
    <div id="posts">
        <#if posts?has_content>
            <#list posts as post>
                <div id="post_${post.getId()}">
                    <img src="${post.getPhotoPath()}" width="100"/>
                    <h1>${post.getName()}</h1>
                    <p>${post.getText()}</p>
                    <p>${post.getCategory().toString()}</p>
                    <a href="/showPost?id=${post.getId()}">
                        Просмотреть запись</a>
                </div>
                <form action="/changePost/" method="get">
                    <p><input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/></p>
                    <p><input type="submit" value="Редактировать"/></p>
                </form>
                <form action="/posts" method="post">
                    <p><input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/></p>
                    <p><input type="submit" value="Удалить"/></p>
                </form>
            </#list>
        <#else>
            <p>No posts avaliable</p>
        </#if>
    </div>
</#macro>
<#macro title>
    <title>Posts</title>
</#macro>
<@display_page />