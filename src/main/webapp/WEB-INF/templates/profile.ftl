<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>

</head>
<body>
<#if error_wrong_user?has_content>
    <p>
        ${error_wrong_user}
    </p>
<#else>
    <img src="${curr_user.getPhotoPath()}" width="100"/>
    <p>
        ${curr_user.getNick()}
    </p>
    <p>
        ${curr_user.getEmail()}
    </p>
    <#if viewBirth?has_content>
        <p>
            ${curr_user.getBirth_date()}
        </p>
    </#if>
    <p>
        ${timeOnBoard}
    </p>
    <#if owner?has_content>
        <input type="submit" value="logout"
               onclick="window.location='/logout';" />
        <form action="/changeProfile/" method="get">
            <p><input type="hidden" name="user_id" id="user_id" value="${curr_user.getId()}"/></p>
            <p><input type="submit" value="Edit"/></p>
        </form>
        <#else>
    </#if>
</#if>
</body>
</html>