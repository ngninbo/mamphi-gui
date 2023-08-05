package de.fhdo.master.mi.sms.project.mamphi.model;

import de.fhdo.master.mi.sms.project.mamphi.utils.TrialStatements;

public enum Consent {

	LATE (TrialStatements.SELECT_LATE_INFORMED_CONSENT),
	MISSING(TrialStatements.SELECT_MISSING_CONSENT),
	NOT_COMPLETED(TrialStatements.SELECT_INCOMPLETE_CONSENT);

	private final String query;

	Consent(String query) {
		this.query = query;
	}

	public String getQuery() {
		return query;
	}
}
