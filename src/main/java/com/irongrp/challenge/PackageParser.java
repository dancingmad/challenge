package com.irongrp.challenge;

import com.irongrp.challenge.exception.PackagerException;
import com.irongrp.challenge.model.Pack;

import java.io.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PackageParser {

    private static Pattern REGEXP_LINE_INPUT = Pattern.compile("(\\d+)\\s*:\\s*(.*)");
    private static Pattern REGEXP_PACKAGE_INPUT = Pattern.compile("\\((\\d+),(\\d+\\.\\d+),€(\\d+)\\)");

    static List<String> readLines(String file) throws PackagerException {
        File inputFile = new File(file);
        if (!inputFile.exists()) {
            throw new RuntimeException("File not found:"+file);
        };
        try(BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            List<String> lines = reader.lines().collect(Collectors.toList());
            if (lines.isEmpty()) {
                throw new PackagerException("Empty file");
            }
            return lines;
        } catch(IOException e) {
            throw new PackagerException("Error reading package file",e);
        }
    }

    static long getMaxWeightFromLine(String line) {
        Matcher lineMatcher = REGEXP_LINE_INPUT.matcher(line);
        if (!lineMatcher.find()) {
            throw new PackagerException("Could not find weight in line "+line);
        }
        return Long.parseLong(lineMatcher.group(1));
    }

    static List<Pack> getPackagesFromLine(String line) {
        System.out.println("Line:"+line);
        Matcher lineMatcher = REGEXP_LINE_INPUT.matcher(line);
        if (!lineMatcher.find() || lineMatcher.groupCount() < 2) {
            throw new RuntimeException("Package Line does not match format 'xxx:yyyyy':"+line);
        }
        String[] packageInputs = lineMatcher.group(2).split(" ");
        if (packageInputs.length == 0) {
            throw new RuntimeException("No packages found for input:"+lineMatcher.group(2));
        }
        return Arrays.stream(packageInputs)
                .map(PackageParser::createPackage)
                .sorted(Comparator.comparingDouble(p -> p.getWeight().doubleValue() * -1))
                .collect(Collectors.toList());
    }

    private static Pack createPackage(String input) {
        Matcher inputMatcher = REGEXP_PACKAGE_INPUT.matcher(input);
        if (!inputMatcher.find() || inputMatcher.groupCount() < 3) {
            throw new RuntimeException("Package does not match format '(xx,xx.xx,€xx)':"+input);
        };
        return new Pack()
                .index(Long.parseLong(inputMatcher.group(1)))
                .weight(new BigDecimal(inputMatcher.group(2)))
                .value(Long.parseLong(inputMatcher.group(3)));
    }
}
