package gg.jte.generated.ondemand;
import gg.jte.Content;
@SuppressWarnings("unchecked")
public final class JtebaseGenerated {
	public static final String JTE_NAME = "base.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,2,10,10,10,10,97,97,97,104,104,104,2,3,3,3,3};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, String title, Content content) {
		jteOutput.writeContent("\n<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n    <title>");
		jteOutput.setContext("title", null);
		jteOutput.writeUserContent(title);
		jteOutput.writeContent("</title>\n    <style>\n        body {\n            font-family: Arial, sans-serif;\n            margin: 20px;\n            background-color: #f5f5f5;\n        }\n        .container {\n            max-width: 800px;\n            margin: 0 auto;\n            background-color: white;\n            padding: 20px;\n            border-radius: 8px;\n            box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n        }\n        h1 {\n            color: #333;\n        }\n        .post {\n            border-bottom: 1px solid #ddd;\n            padding: 15px 0;\n        }\n        .post:last-child {\n            border-bottom: none;\n        }\n        .post-title {\n            font-size: 18px;\n            font-weight: bold;\n            color: #0066cc;\n            margin: 10px 0;\n        }\n        .post-content {\n            color: #666;\n            line-height: 1.6;\n        }\n        .form-group {\n            margin-bottom: 15px;\n        }\n        label {\n            display: block;\n            margin-bottom: 5px;\n            color: #333;\n            font-weight: bold;\n        }\n        input[type=\"text\"],\n        textarea {\n            width: 100%;\n            padding: 8px;\n            border: 1px solid #ddd;\n            border-radius: 4px;\n            box-sizing: border-box;\n        }\n        textarea {\n            min-height: 150px;\n            resize: vertical;\n        }\n        button {\n            background-color: #0066cc;\n            color: white;\n            padding: 10px 20px;\n            border: none;\n            border-radius: 4px;\n            cursor: pointer;\n            font-size: 16px;\n        }\n        button:hover {\n            background-color: #0052a3;\n        }\n        .nav {\n            margin-bottom: 20px;\n        }\n        .nav a {\n            color: #0066cc;\n            text-decoration: none;\n            margin-right: 15px;\n        }\n        .nav a:hover {\n            text-decoration: underline;\n        }\n    </style>\n</head>\n<body>\n    <div class=\"container\">\n        <div class=\"nav\">\n            <a href=\"/\">Home</a>\n            <a href=\"/create\">Create Post</a>\n        </div>\n        ");
		jteOutput.setContext("div", null);
		jteOutput.writeUserContent(content);
		jteOutput.writeContent("\n    </div>\n\n    <script src=\"https://cdn.jsdelivr.net/npm/htmx.org@2.0.8/dist/htmx.min.js\"></script>\n</body>\n</html>\n\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		String title = (String)params.getOrDefault("title", "Posts");
		Content content = (Content)params.get("content");
		render(jteOutput, jteHtmlInterceptor, title, content);
	}
}
