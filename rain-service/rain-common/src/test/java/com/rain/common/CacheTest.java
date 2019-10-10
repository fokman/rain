package com.rain.common;

import com.rain.common.cache.CacheService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CacheTest {

    public static void main(String[] args) {

        CacheService cache = CacheService.getInstance();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        do {
            try {
                System.out.print("> ");
                System.out.flush();

                String line = in.readLine().trim();
                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit"))
                    break;

                String[] cmds = line.split(" ");
                if ("get".equalsIgnoreCase(cmds[0])) {
                    Object obj = cache.get(cmds[1]);
                    System.out.printf("[%s]<=%s\n", cmds[1], obj);
                } else if ("set".equalsIgnoreCase(cmds[0])) {
                    cache.set(cmds[1], cmds[2]);
                    System.out.printf("[%s]<=%s\n", cmds[1], cmds[2]);
                } else if ("del".equalsIgnoreCase(cmds[0])) {
                    cache.del(cmds[1]);
                    System.out.printf("[%s]=>null\n", cmds[1]);
                } else if ("clear".equalsIgnoreCase(cmds[0])) {
                    cache.clear();
                    System.out.printf("Cache [%s] clear.\n", cmds[1]);
                } else if ("help".equalsIgnoreCase(cmds[0])) {
                    printHelp();
                } else if ("static".equalsIgnoreCase(cmds[0])) {
                    System.out.printf("Static: [%s]", cache.getCacheStatic().toString());
                } else {
                    System.out.println("Unknown command.");
                    printHelp();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Wrong arguments.");
                printHelp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);

        cache.close();

        System.exit(0);
    }

    private static void printHelp() {
        System.out.println("Usage: [cmd] region key [value]");
        System.out.println("cmd: get/set/del/clear/quit/exit/help");
    }

}
