<#ftl encoding='UTF-8'>
<#include "base.ftl"/>

<#macro content>
    <script type="text/javascript">
        function f() {

            var formData = {
                'post_id': $('#post_id').val(),
                'text': $('#text').val()
            };
            console.log(formData);

            $.ajax({
                url: "/comment",
                type: "post",
                data: formData,
                success: function (data) {
                    var JSONObject = JSON.parse(data);
                    var div_r = document.createElement("div");
                    div_r.setAttribute("id", "comment_" + JSONObject.key.post_id);
                    var k = JSONObject.key.post_id;
                    $("#comments").append(div_r);
                    $("#comment_" + k).append("<img width=" + "\"30\"" +
                        " src=" + "\"" + JSONObject.value.photoPath + "\"" + " />");
                    $("#comment_" + k).append("<a href=" + "\"/home?id=" + JSONObject.key.ownerId + "\" " + ">" +
                        JSONObject.value.nick + "</a>");
                    $("#comment_" + k).append("<p>" + JSONObject.key.text + "</p>");
                    $("#comment_" + k).append("<p>" + "On data :" + JSONObject.key.time + "</p>");
                },
                error: function (err) {
                    alert("error msg")
                }
            });
        }
    </script>
    <img src="${post.getPhotoPath()}" width="200"/>
    <p>${post.getName()}</p>
    <p>${post.getText()}</p>
    <p>${post.getPublication().toString()}</p>
    <div id="comments">
        <#if comments?has_content>
        <#list comments as comment>
            <div id="comment_${comment.getKey().getId()}">
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
            <#else>
            <p>No comments here</p>
        </#if>
    </div>
    <form action="/comment">
        <p><input type="hidden" name="post_id" id="post_id" value="${post.getId()}"/></p>
        <p><textarea rows="10" cols="45" name="text" id="text"></textarea></p>
        <p><input type="button" value="Отправить" onclick="f()"/></p>
    </form>
</#macro>
<#macro title>
    <title>Post</title>
</#macro>
<@display_page />