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
        ${curr_user.getName()}
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
</#if>
<input type="submit" value="logout"
       onclick="window.location='/logout';" />
</body>
</html>