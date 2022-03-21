module mamphiGuiVersion {
	exports de.fhdo.master.mi.sms.project.mamphi.gui.app;
	exports de.fhdo.master.mi.sms.project.mamphi.model;
	exports de.fhdo.master.mi.sms.project.mamphi.repository;
	exports de.fhdo.master.mi.sms.project.mamphi.service;
	exports de.fhdo.master.mi.sms.project.mamphi.utils;

	requires java.sql;
	requires javafx.base;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires java.base;
	requires java.desktop;
}