package dk.obhnothing.utilities;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class PrettyPrinter
{
    private static final String colorStrings[] = {
        "\u001B[0m",
        "\u001B[30m",
        "\u001B[31m",
        "\u001B[32m",
        "\u001B[33m",
        "\u001B[34m",
        "\u001B[35m",
        "\u001B[36m",
        "\u001B[37m",
    };

    public enum ANSIColorCode {
        ANSI_RESET,
        ANSI_BLACK,
        ANSI_RED,
        ANSI_GREEN,
        ANSI_YELLOW,
        ANSI_BLUE,
        ANSI_PURPLE,
        ANSI_CYAN,
        ANSI_WHITE,
    }

    private static Map<ANSIColorCode, String> ANSIColorMap;

    private static void Init()
    {
        if (ANSIColorMap != null)
            return;
        ANSIColorMap = new HashMap<>();
        for (int i = 0; i < colorStrings.length; i++) {
            ANSIColorMap.put(ANSIColorCode.values()[i], colorStrings[i]);
        }
    }

    public static void withColor(String str, ANSIColorCode col)
    {
        if (ANSIColorMap == null)
            Init();
        System.out.print(ANSIColorMap.get(col) + str + ANSIColorMap.get(ANSIColorCode.ANSI_RESET));
    }

    public static void arr2d(int[][] arr, Predicate<Integer> highlightCond)
    {
        if (ANSIColorMap == null)
            Init();
        int maxelem = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr.length; j++)
                maxelem = (arr[i][j] > maxelem) ? arr[i][j] : maxelem;
        int digits = (int)Math.log10(Math.abs(maxelem == 0 ? 1 : maxelem)) + 1;
        System.out.printf("[");
        for (int i = 0; i < arr.length; i++) {
            if (i > 0)
                System.out.printf(" ");
            for (int j = 0; j < arr[0].length; j++) {
                int element = arr[i][j];
                if (highlightCond.test(element))
                    System.out.printf("%s", ANSIColorCode.ANSI_RED);

                String eleStr = String.valueOf(element);
                eleStr = " ".repeat(digits - eleStr.length()) + eleStr;
                System.out.printf("%s", eleStr);

                if (highlightCond.test(element))
                    System.out.printf("%s", ANSIColorCode.ANSI_RESET);
                if (j < arr[0].length - 1)
                    System.out.printf(",");
            }
            if (i < arr.length - 1)
                System.out.printf("%n");
            else
                System.out.printf("]");
        }
    }

    public static void PrintObjectTable(List<?> emps)
    {
        if (ANSIColorMap == null)
            Init();
        if (emps == null || emps.size() < 1)
            return;
        Class<?> cl = emps.get(0).getClass();
        String nameFull = cl.getName().substring(cl.getName().lastIndexOf(".") + 1, cl.getName().length());
        String nameSpace = cl.getName().substring(0, cl.getName().lastIndexOf("."));
        System.out.println();
        PrettyPrinter.withColor("*** Printing " + nameFull + " table (from: " + nameSpace + " )"    + " ***", PrettyPrinter.ANSIColorCode.ANSI_YELLOW);
        System.out.println();

        Field fields[] = cl.getFields();
        if (fields.length < 1) {
            System.out.println("this class has no public fields...");
        }
        for (int i = 0; i < fields.length; i++) {
            PrettyPrinter.withColor(fields[i].getName() + "\t", PrettyPrinter.ANSIColorCode.ANSI_BLUE);
        }
        for (Object e : emps) {
            System.out.println();
            for (int i = 0; i < fields.length; i++) {
                try {
                    if (fields[i].get(e) == null)
                        continue;
                    String fValStr = fields[i].get(e).toString();
                    int maxLen = fields[i].getName().length();
                    int nPads = maxLen - fValStr.length();
                    String padStr = " ".repeat(Math.max(nPads, 0));
                    System.out.print(fValStr.substring(0, Math.min(fValStr.length(), maxLen)) + padStr + "\t");
                } catch (org.hibernate.LazyInitializationException | IllegalArgumentException | IllegalAccessException e1) {
                }
            }
        }
        System.out.println();
    }
}
