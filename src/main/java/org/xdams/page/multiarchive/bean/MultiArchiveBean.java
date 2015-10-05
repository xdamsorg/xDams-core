package org.xdams.page.multiarchive.bean;

import org.xdams.user.bean.Archive;

public class MultiArchiveBean {
	private Archive archive;

	private int recordFound;

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}

	public int getRecordFound() {
		return recordFound;
	}

	public void setRecordFound(int recordFound) {
		this.recordFound = recordFound;
	}
}
