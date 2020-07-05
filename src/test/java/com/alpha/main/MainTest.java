package com.alpha.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainTest {
    private static final String resources = new File("").getAbsolutePath()+"/src/test/resources";

    @Test
    public void testIllegalArguments() throws IOException {

    }

    @Test
    public void testMain() throws IOException{
        String balance = resources+"/default_balance.csv";
        String requests = resources+"/default_requests.csv";
        Main.runConsoleVersion(new String[] {balance, requests});
        File expectedUpdatedBalance = new File(resources + "/expected/expectedUpdatedBalance.csv");
        File expectedReports = new File(resources + "/expected/expectedReports.csv");
        File updatedBalance = new File(resources + "/updatedBalance.csv");
        File reports = new File(resources + "/reports.csv");
        Assertions.assertEquals(
                Files.readAllLines(expectedUpdatedBalance.toPath()),
                Files.readAllLines(updatedBalance.toPath())
        );
        Assertions.assertEquals(
                Files.readAllLines(expectedReports.toPath()),
                Files.readAllLines(reports.toPath())
        );
    }
}
