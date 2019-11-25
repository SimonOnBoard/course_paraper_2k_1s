<#ftl encoding='UTF-8'>
<#include "base.ftl"/>
<#macro content>

    <div class="my-container">
        <div class="reg-container">

            <form method="post" enctype="multipart/form-data">

                <div id="namer">
                    <div id="namer-input" style="margin-bottom:2rem;">
                        <input type="text" id="name" name="name" placeholder="Name" required>
                    </div>


                    <div class="form-group">
                        <select class="form-control" id="categories" name="categories" style="height:1.8em;">
                            <#list categories as category>
                                <option value="${category}">${category}</option>
                            </#list>
                        </select>
                    </div>

                    <div id="namer-input">
                        <textarea class="comment-area" name=text rows="3" ></textarea>
                    </div>

                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" name="showAuth" value="0" style="height:auto;">
                        <label class="form-check-label" for="showAuth">Отображать имя автора</label>
                    </div>


                    <div id="namer-input" style="border-bottom:none;">
                        <input type="file" id="photo" name="photo" class="btn btn-danger">
                    </div>

                </div>

                <button type="submit" class="btn btn-danger btn-lg">Submit</button>
            </form>
        </div>
    </div>


</#macro>
<#macro title>
    <title>AddPostPage</title>
</#macro>
<@display_page />