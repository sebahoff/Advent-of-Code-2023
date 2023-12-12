package day10;

import java.util.HashMap;
import java.util.Map;

public class Day10Thread extends Thread {
    private static final boolean print = false;

    @Override
    public void run() {

    }

    private static void print(String s) {
        if(print) {
            System.out.println(s);
        }
    }

    public static Map<String, Object> followPath(int startX, int startY, char c, String prevMove) {
        var map = new HashMap<String, Object>();
        map.put("x", startX);
        map.put("y", startY);
        map.put("finished", false);

//        System.out.println(startX + ", " + startY + ", " + c + ", prev: " + prevMove);

        switch (c) {
            case 'L':
                if (prevMove.equals("-x")) {
                    print("L top");
                    map.put("y", startY - 1);
                    map.put("move", "-y");
                } else {
                    print("L bottom");
                    map.put("x", startX + 1);
                    map.put("move", "+x");
                }
                break;
            case 'J':
                if (prevMove.equals("+y")) {
                    print("J top");
                    map.put("x", startX - 1);
                    map.put("move", "-x");
                } else {
                    print("J bottom");
                    map.put("y", startY - 1);
                    map.put("move", "-y");
                }
                break;
            case 'F':
                if (prevMove.equals("-y")) {
                    print("F top");
                    map.put("x", startX + 1);
                    map.put("move", "+x");
                } else {
                    print("F bottom");
                    map.put("y", startY + 1);
                    map.put("move", "+y");
                }
                break;
            case '7':
                if (prevMove.equals("+x")) {
                    print("7 top");
                    map.put("y", startY + 1);
                    map.put("move", "+y");
                } else {
                    print("7 bottom");
                    map.put("x", startX - 1);
                    map.put("move", "-x");
                }
                break;
            case '|':
                if (prevMove.equals("-y")) {
                    print("| top");
                    map.put("y", startY - 1);
                    map.put("move", "-y");
                } else {
                    print("| bottom");
                    map.put("y", startY + 1);
                    map.put("move", "+y");
                }
                break;
            case '-':
                if (prevMove.equals("-x")) {
                    print("- top");
                    map.put("x", startX - 1);
                    map.put("move", "-x");
                } else {
                    print("- bottom");
                    map.put("x", startX + 1);
                    map.put("move", "+x");
                }
                break;
            case '.':
            case 'S':
                print("S or .");
                map.put("finished", true);
        }

        return map;
    }
}
