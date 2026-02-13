@template.base(title = "Create Post", content = @`
    <h1>Create New Post</h1>
    <form method="post" action="/create" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label for="content">Content:</label>
            <textarea id="content" name="content" required></textarea>
        </div>
        <div class="form-group">
            <label for="thumbnail">Thumbnail:</label>
            <input type="file" id="thumbnail" name="thumbnail" accept="image/*">
        </div>
        <button type="submit">Create Post</button>
    </form>
`)
