package de.fhdo.master.mi.sms.project.mamphi.model;

import de.fhdo.master.mi.sms.project.mamphi.utils.UITranslation;

public enum ConsentInformedStatus {

    YES (UITranslation.YES),
    NO (UITranslation.NO);

    private final String translation;

    ConsentInformedStatus(String translation) {
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }
}
