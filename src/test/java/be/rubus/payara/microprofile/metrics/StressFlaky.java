package be.rubus.payara.microprofile.metrics;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StressFlaky {

    private static List<UserFlakyThread> users = new ArrayList<>();

    public static void main(String[] args) throws MalformedURLException {
        System.setProperty("atbash.utils.cdi.check", "false");

        runProgram();

    }

    private static void runProgram() {
        Scanner in = new Scanner(System.in);

        while (true) {

            String input = in.nextLine();
            if ("q".equalsIgnoreCase(input)) {
                scaleThreads(0);
                break;
            }
            Integer count = Integer.valueOf(input);
            if (count > 0 && count < 101) {
                scaleThreads(count);
            }
        }
    }

    private static void scaleThreads(Integer count) {
        if (count > users.size()) {
            for (int i = users.size(); i < count; i++) {
                UserFlakyThread userFlakyThread = new UserFlakyThread();
                users.add(userFlakyThread);
                new Thread(userFlakyThread).start();
            }
        }
        if (count < users.size()) {
            for (int i = count; i < users.size(); i++) {
                users.get(i).stopThread();
            }

        }
    }
}
