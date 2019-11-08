<#include "base.ftl"/>

<#macro content>
    <img src="${post.getPhotopath()}" width="200"/>
    <p>${post.getName()}</p>
    <p>${post.getText()}</p>
    <p>${post.getPublication().toString()}</p>
</#macro>
<#macro title>
    <title>Post</title>
</#macro>
<@display_page />