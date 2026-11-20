package com.apartment.util;

import java.util.List;

/**
 * Utility class to print formatted tabular outputs to the console.
 */
public class TableFormatter {

    public static void printRow(int[] widths, String... columns) {
        StringBuilder sb = new StringBuilder("| ");
        for (int i = 0; i < widths.length; i++) {
            String col = (i < columns.length && columns[i] != null) ? columns[i] : "";
            if (col.length() > widths[i]) {
                col = col.substring(0, widths[i] - 3) + "...";
            }
            sb.append(String.format("%-" + widths[i] + "s | ", col));
        }
        System.out.println(sb.toString());
    }

    public static void printSeparator(int[] widths) {
        StringBuilder sb = new StringBuilder("+-");
        for (int w : widths) {
            for (int i = 0; i < w; i++) sb.append("-");
            sb.append("-+-");
        }
        sb.setLength(sb.length() - 1);
        System.out.println(sb.toString());
    }

    public static void printHeader(String title) {
        System.out.println("\n" + "=".repeat(75));
        System.out.println("  " + title);
        System.out.println("=".repeat(75));
    }
}
