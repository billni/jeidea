package com.bossteach.messagemanager.ci;

import java.util.List;

import com.bossteach.model.Visitor;

public interface VisitorManagerCI {
	/**
	 * add visitor
	 * @param visitor
	 */
	public void createVisitor(Visitor visitor);
	
	/**
	 * find visitor
	 */	
	public List<Visitor> findVisitor(String mail);
}
