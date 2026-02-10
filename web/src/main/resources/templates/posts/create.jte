@template.base(title = "Create Post", content = @`
    <h1>Create New Post</h1>
    <form method="post" action="/create">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label for="content">Content:</label>
            <textarea id="content" name="content" required></textarea>
        </div>
        <button type="submit">Create Post</button>
    </form>
`)



