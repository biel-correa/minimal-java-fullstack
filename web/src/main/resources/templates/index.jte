@import java.util.List
@import models.Post

@param List<Post> posts

@template.base(title = "Posts", content = @`
    <h1>Posts</h1>
    @if(posts != null && !posts.isEmpty())
        @for(var post : posts)
            @template.posts.components.card(post = post)
        @endfor
    @else
        <p>No posts available.</p>
    @endif
`)
