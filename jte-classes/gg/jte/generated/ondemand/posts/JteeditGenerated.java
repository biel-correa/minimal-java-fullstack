package gg.jte.generated.ondemand.posts;
import models.Post;
@SuppressWarnings("unchecked")
public final class JteeditGenerated {
	public static final String JTE_NAME = "posts/edit.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,1,1,1,3,3,3,3,5,5,5,5,8,8,8,8,8,8,8,8,8,12,12,12,17,17,17,19,19,19,1,1,1,1};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Post post) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.JtebaseGenerated.render(jteOutput, jteHtmlInterceptor, "Edit Post", new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    <h1>Edit Post</h1>\n    <form method=\"post\" action=\"/");
				jteOutput.setContext("form", "action");
				jteOutput.writeUserContent(post.getId().toString());
				jteOutput.setContext("form", null);
				jteOutput.writeContent("/update\">\n        <div class=\"form-group\">\n            <label for=\"title\">Title:</label>\n            <input type=\"text\" id=\"title\" name=\"title\"");
				var __jte_html_attribute_0 = post.getTitle();
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" value=\"");
					jteOutput.setContext("input", "value");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("input", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(" required>\n        </div>\n        <div class=\"form-group\">\n            <label for=\"content\">Content:</label>\n            <textarea id=\"content\" name=\"content\" required>");
				jteOutput.setContext("textarea", null);
				jteOutput.writeUserContent(post.getContent());
				jteOutput.writeContent("</textarea>\n        </div>\n        <button type=\"submit\">Update Post</button>\n        <a href=\"/\" style=\"margin-left: 10px;\">Cancel</a>\n    </form>\n");
			}
		});
		jteOutput.writeContent("\n\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Post post = (Post)params.get("post");
		render(jteOutput, jteHtmlInterceptor, post);
	}
}
