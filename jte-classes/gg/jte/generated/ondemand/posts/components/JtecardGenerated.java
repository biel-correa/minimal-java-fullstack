package gg.jte.generated.ondemand.posts.components;
@SuppressWarnings("unchecked")
public final class JtecardGenerated {
	public static final String JTE_NAME = "posts/components/card.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,2,2,2,2,2,4,4,4,4,9,9,9,9,10,10,10,10,18,18,18,19,19,19,20,20,20,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, models.Post post) {
		jteOutput.writeContent("\n<div class=\"post\" id=\"post-");
		jteOutput.setContext("div", "id");
		jteOutput.writeUserContent(post.getId().toString());
		jteOutput.setContext("div", null);
		jteOutput.writeContent("\" style=\"position: relative; padding-right: 80px;\">\n    <div style=\"position: absolute; top: 0; right: 0; display: flex; gap: 5px;\">\n        <a href=\"/");
		jteOutput.setContext("a", "href");
		jteOutput.writeUserContent(post.getId().toString());
		jteOutput.setContext("a", null);
		jteOutput.writeContent("/edit\"\n           style=\"background-color: #007bff; padding: 8px 12px; border: none; border-radius: 4px; cursor: pointer; color: white; text-decoration: none; display: inline-block;\"\n           title=\"Edit post\">\n            ✏️\n        </a>\n        <button hx-delete=\"/");
		jteOutput.setContext("button", "hx-delete");
		jteOutput.writeUserContent(post.getId().toString());
		jteOutput.setContext("button", null);
		jteOutput.writeContent("\"\n                hx-target=\"#post-");
		jteOutput.setContext("button", "hx-target");
		jteOutput.writeUserContent(post.getId().toString());
		jteOutput.setContext("button", null);
		jteOutput.writeContent("\"\n                hx-swap=\"outerHTML\"\n                hx-confirm=\"Are you sure you want to delete this post?\"\n                style=\"background-color: #dc3545; padding: 8px 12px; border: none; border-radius: 4px; cursor: pointer; color: white;\"\n                title=\"Delete post\">\n            🗑️\n        </button>\n    </div>\n    <div class=\"post-title\">");
		jteOutput.setContext("div", null);
		jteOutput.writeUserContent(post.getTitle());
		jteOutput.writeContent("</div>\n    <div class=\"post-content\">");
		jteOutput.setContext("div", null);
		jteOutput.writeUserContent(post.getContent());
		jteOutput.writeContent("</div>\n</div>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		models.Post post = (models.Post)params.get("post");
		render(jteOutput, jteHtmlInterceptor, post);
	}
}
