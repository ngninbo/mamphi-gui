package de.fhdo.master.mi.sms.project.mamphi.service;

import de.fhdo.master.mi.sms.project.mamphi.model.Consent;
import de.fhdo.master.mi.sms.project.mamphi.model.ConsentInformedStatus;
import de.fhdo.master.mi.sms.project.mamphi.model.InformedConsent;

import java.util.List;

public interface InformedConsentService {

    void update(InformedConsent informedConsent);

    List<InformedConsent> findAllInformedConsent();
    List<InformedConsent> findAllInformedConsent(Consent consent);
    List<InformedConsent> findAllInformedConsent(ConsentInformedStatus status);
}
