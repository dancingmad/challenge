package com.irongrp.challenge;

import com.irongrp.challenge.model.Pack;
import com.irongrp.challenge.model.PackageCombination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/******************************************
 * Takes packages in the format with a max of 15 items per package
 * [maxWeight] : ([index,weight,€value) ...
 * e.g. 20 : (1,10.0,€50) (2,5.5,€20) (3,8.0,30€)
 * Calculates the max value item combination that fits the max weight
 */
public class Packager {


    public static String execute(String file) throws Exception {
        Packager packager = new Packager();
        return PackageParser.readLines(file).stream()
                .map(packager::calculatePackages)
                .collect(Collectors.joining("\n"));
    }

    private String calculatePackages(String line) {
        List<Pack> packageList = PackageParser.getPackagesFromLine(line);
        long maxWeight = PackageParser.getMaxWeightFromLine(line);
        List<PackageCombination> packageCombinations = createPackageCombinations(packageList,
                maxWeight,
                new PackageCombination());
        Optional<PackageCombination> combination = packageCombinations.stream()
                .max(PackageCombination::compareTo);

        if (!combination.isPresent()) {
            return "-";
        }
        return formatResult(combination.get());
    }

    private String formatResult(PackageCombination combination) {
        return combination.getPackages().stream()
                .map(Pack::getIndex)
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    private List<PackageCombination> createPackageCombinations(List<Pack> packages,
                                                        long maxWeight,
                                                              PackageCombination currentCombination) {
        List<PackageCombination> packageCombinations = new ArrayList<>();
        for(int i=0;i<packages.size();i++) {
            Pack pack = packages.get(i);
            // we skip entries that would exceed the weight
            boolean exceedsWeight = currentCombination.getTotalWeight().add(pack.getWeight())
                    .doubleValue() > maxWeight;
            if (exceedsWeight) {
                continue;
            }

            boolean lastElement = packages.size() == i+1;
            if (lastElement) {
                currentCombination.add(pack);
                break;
            }
            PackageCombination passedOnCombination = currentCombination.copy();
            passedOnCombination.add(pack);
            packageCombinations.addAll(
                    createPackageCombinations(packages.subList(i + 1, packages.size()),
                            maxWeight,
                            passedOnCombination));
        }
        if (!currentCombination.getPackages().isEmpty()) {
            packageCombinations.add(currentCombination);
        }
        return packageCombinations;
    }


}
