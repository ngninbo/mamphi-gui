package de.fhdo.master.mi.sms.project.mamphi.utils;

import de.fhdo.master.mi.sms.project.mamphi.model.Centre;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation.GERMANY;

public class TrialUtils {

    public static Map<Boolean, List<Centre>> partition(List<Centre> centerList) {

        return centerList.stream()
                .collect(Collectors.partitioningBy(center -> GERMANY.equals(center.getCountry())));
    }

    public static Map<String, List<Centre>> grouping(List<Centre> centerList) {

        return centerList.stream()
                .collect(Collectors.groupingBy(Centre::getCountry));
    }
}
