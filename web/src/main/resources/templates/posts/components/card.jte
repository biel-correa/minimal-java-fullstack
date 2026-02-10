@param models.Post post

<div class="post" id="post-${post.getId().toString()}" style="position: relative; padding-right: 80px;">
    <div style="position: absolute; top: 0; right: 0; display: flex; gap: 5px;">
        <a href="/${post.getId().toString()}/edit"
           style="background-color: #007bff; padding: 8px 12px; border: none; border-radius: 4px; cursor: pointer; color: white; text-decoration: none; display: inline-block;"
           title="Edit post">
            ✏️
        </a>
        <button hx-delete="/${post.getId().toString()}"
                hx-target="#post-${post.getId().toString()}"
                hx-swap="outerHTML"
                hx-confirm="Are you sure you want to delete this post?"
                style="background-color: #dc3545; padding: 8px 12px; border: none; border-radius: 4px; cursor: pointer; color: white;"
                title="Delete post">
            🗑️
        </button>
    </div>
    <div class="post-title">${post.getTitle()}</div>
    <div class="post-content">${post.getContent()}</div>
</div>