package gg.jte.generated.ondemand;
import java.util.List;
import models.Post;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,3,3,3,3,5,5,5,5,7,7,8,8,9,9,10,10,11,11,13,13,14,14,14,15,15,15,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, List<Post> posts) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.JtebaseGenerated.render(jteOutput, jteHtmlInterceptor, "Posts", new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <h1>Posts</h1>\n    ");
				if (posts != null && !posts.isEmpty()) {
					jteOutput.writeContent("\n        ");
					for (var post : posts) {
						jteOutput.writeContent("\n            ");
						gg.jte.generated.ondemand.posts.components.JtecardGenerated.render(jteOutput, jteHtmlInterceptor, post);
						jteOutput.writeContent("\n        ");
					}
					jteOutput.writeContent("\n    ");
				} else {
					jteOutput.writeContent("\n        <p>No posts available.</p>\n    ");
				}
				jteOutput.writeContent("\n");
			}
		});
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		List<Post> posts = (List<Post>)params.get("posts");
		render(jteOutput, jteHtmlInterceptor, posts);
	}
}
