@import models.Post
@param Post post

@template.base(title = "Edit Post", content = @`
    <h1>Edit Post</h1>
    <form method="post" action="/${post.getId().toString()}/update">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${post.getTitle()}" required>
        </div>
        <div class="form-group">
            <label for="content">Content:</label>
            <textarea id="content" name="content" required>${post.getContent()}</textarea>
        </div>
        <button type="submit">Update Post</button>
        <a href="/" style="margin-left: 10px;">Cancel</a>
    </form>
`)

