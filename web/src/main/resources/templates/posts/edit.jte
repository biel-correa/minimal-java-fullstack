@import models.Post
@param Post post

@template.base(title = "Edit Post", content = @`
    <h1>Edit Post</h1>
    <form method="post" action="/${post.getId().toString()}/update" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${post.getTitle()}" required>
        </div>
        <div class="form-group">
            <label for="content">Content:</label>
            <textarea id="content" name="content" required>${post.getContent()}</textarea>
        </div>
        <div class="form-group">
            <label for="thumbnail">Thumbnail:</label>
            <input type="file" id="thumbnail" name="thumbnail" accept="image/*">
        </div>
        @if(post.getThumbnailPath() != null)
            <div class="form-group">
                <label>Current Thumbnail:</label>
                <img src="/uploads/${post.getThumbnailPath()}" alt="Current thumbnail" style="max-width: 200px; height: auto; display: block;">
            </div>
        @endif
        <button type="submit">Update Post</button>
        <a href="/" style="margin-left: 10px;">Cancel</a>
    </form>
`)
