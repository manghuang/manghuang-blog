package com.whw.manghuangblog.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MakedownUtils {

    public static String makedownToHtml(String makedown){
        Parser parser = Parser.builder().build();
        Node parse = parser.parse(makedown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(parse);
    }
}
