package com.prodp.apsim.lang;

public class Object {
	private Container parent;
	private Object prev, next;

	public Container getParent() {
		return parent;
	}

	public Object setParent(Container parent) {
		this.parent = parent;
		return this;
	}

	public Object getPrevious() {
		return prev;
	}

	public Object setPrevious(Object prev) {
		this.prev = prev;
		return this;
	}

	public Object getNext() {
		return next;
	}

	public Object setNext(Object next) {
		this.next = next;
		return this;
	}
}
