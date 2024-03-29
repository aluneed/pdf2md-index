package dev.benkyou.utilities;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;

import java.io.*;
import java.util.*;

public class Pdf2MdIndex {

    public static boolean generateSectionCode = true;

    public static void main(String[] args) throws Exception {

        String inputPath = args[0];

        int firstChapterIndex = 1;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--firstChapter=")) {
                firstChapterIndex = Integer.parseInt(arg.substring(arg.lastIndexOf("=") + 1));
            } else if (arg.startsWith("--noSectionCode")) {
                generateSectionCode = false;
            }
        }

        String outputPath = inputPath.substring(0, inputPath.lastIndexOf(".")) + "-index.md";

        PdfReader reader = new PdfReader(inputPath);
        List<HashMap<String, Object>> bookmarkList = SimpleBookmark.getBookmark(reader);
        PrintWriter output = new PrintWriter(outputPath);

        for (int i = 0; i < bookmarkList.size(); i++) {
            writeBookmark(bookmarkList.get(i), output, 0, i - firstChapterIndex + 2, "");
        }
        output.close();
    }

    public static void writeBookmark(Map<String, Object> bookmark, PrintWriter output, int depth, int order, String prefix) {
        String newPrefix;
        if (!generateSectionCode) {
            newPrefix = "";
        } else if (prefix.isEmpty()) {
            if (order <= 0) {
                newPrefix = prefix + "0";
            } else {
                newPrefix = prefix + order;
            }
        } else {
            newPrefix = prefix + "." + order;
        }
        StringBuilder mdIndex = new StringBuilder()
//                .append(" ".repeat(depth * 2))
                .append("#".repeat(depth + 1))
                .append(" ")
//                .append(getName(bookmark))
                .append(newPrefix)
                .append(" ")
                .append(bookmark.get("Title"));
        System.out.println(mdIndex);
        output.println(mdIndex);
        List<Map<String, Object>> kids = (List<Map<String, Object>>) bookmark.get("Kids");
        if (kids == null) {
            return;
        }

        for (int i = 0; i < kids.size(); i++) {
            writeBookmark(kids.get(i), output, depth + 1, i + 1, newPrefix);
        }
    }

    public static String getName(Map<String, Object> bookmark) {
        if (((String) bookmark.get("Named")).startsWith("chapter*")) {
            return "chapter.0";
        } else {
            return (String) bookmark.get("Named");
        }
    }

}
