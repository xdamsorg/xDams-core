/*
 * Created on 4-mag-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.xdams.utility;

/**
 * @author diego
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Titles {

	private int perPage = 0;

	private int firstPage = 1;

	private int lastPage = 0;

	private int thisPage = 0;

	private int totPage = 0;

	private int totElements = 0;
	
	private int firstElement = 0;
	

	private String navString = "";

	public static void main(String[] args) {

		Titles title = new Titles();
		title.setPages(32, 313);
		System.out.println(title.getPageFromIndex(22));
		System.out.println(title.getLastPage());

	}

	public void setPages(int perPage, int totElements) {
		this.perPage = perPage;
		this.totElements = totElements;
		int totPage = totElements / perPage;
		int gap = totElements % perPage;
		if (gap > 0) {
			totPage++;
		}
		this.totPage = totPage;
		this.thisPage = 1;
		setNavString();
		setFirstElement();
	}

	public void setPages(int perPage, int totElements, int thisElement) {

		this.perPage = perPage;
		this.totElements = totElements;
		int totPage = totElements / perPage;

		int gap = totElements % perPage;
		if (gap > 0) {
			totPage++;
		}
		this.totPage = totPage;
		System.out.println("totPage " + totPage);
		if (thisElement > totElements)
			this.thisPage = -1;
		else
			this.thisPage = getPageFromIndex(thisElement);
		setNavString();
		setFirstElement();
	}

	private void setFirstElement(){
		int firstElement = 0;
		firstElement = ((thisPage-1)*perPage)+1;
		this.firstElement = firstElement;
	}
	
	private void setNavString() {
		String navString = "";

		if (getThisPage() > 1)
			navString += "true,true,";

		else
			navString += "false,false,";

		if (getThisPage() < getLastPage())
			navString += "true,true";
		else
			navString += "false,false";

		this.navString = navString;
	}

	public int getPageFromIndex(int elemento) {
		int pagina = elemento / perPage;
		int gap = elemento % perPage;
		if (gap > 0) {
			pagina++;
		}
		return pagina;
	}

	public int getThisPage() {
		return thisPage;
	}

	public int getLastPage() {
		return totPage;
	}

	public String getNavString() {
		return navString;
	}
	
	public int getFirstElement() {
		return firstElement;
	}
}