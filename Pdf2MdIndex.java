package dev.benkyou.utilities;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.SimpleBookmark;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.*;

public class Pdf2MdIndex {

    public static String getDesktopPath() {
        return FileSystemView.getFileSystemView().getHomeDirectory().getPath();
    }

    public static void main(String[] args) throws Exception {
        String inputPath = getDesktopPath() + File.separator + "sicp.pdf";
        String outputPath = getDesktopPath() + File.separator + "sicp-index.md";

        PdfReader reader = new PdfReader(inputPath);
        List<HashMap<String, Object>> bookmarkList = SimpleBookmark.getBookmark(reader);
        PrintWriter output = new PrintWriter(outputPath);
        bookmarkList.forEach(bookmark -> writeBookmark(bookmark, output, 0));
        output.close();
    }

    public static void writeBookmark(Map<String, Object> bookmark, PrintWriter output, int depth) {
//        String named;
//        if (((String) bookmark.get("Named")).startsWith("chapter*")) {
//            named = "chapter.0";
//        } else {
//            named = (String) bookmark.get("Named");
//        }
        StringBuilder mdIndex = new StringBuilder()
//                .append(" ".repeat(depth * 2))
                .append("#".repeat(depth + 1))
                .append(" ")
//                .append(named)
                .append(" ")
                .append(bookmark.get("Title"));
        System.out.println(mdIndex);
        output.println(mdIndex);
        List<Map<String, Object>> kids = (List<Map<String, Object>>) bookmark.get("Kids");
        if (kids == null) {
            return;
        }
        kids.forEach(e -> writeBookmark(e, output, depth + 1));
    }

}
