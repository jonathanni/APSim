package com.prodp.apsim;

import javax.vecmath.Point3i;

public class APRegion {
	private Point3i p1, p2;
	
	private boolean physics, ballBounce, buoyancy, fireRemove, steamChange;

	public APRegion(Point3i p1, Point3i p2) {
		setPoint1(p1);
		setPoint2(p2);
	}

	public Point3i getPoint1() {
		return p1;
	}

	public void setPoint1(Point3i p1) {
		this.p1 = p1;
	}

	public Point3i getPoint2() {
		return p2;
	}

	public void setPoint2(Point3i p2) {
		this.p2 = p2;
	}

	public boolean isPhysics() {
		return physics;
	}

	public void setPhysics(boolean physics) {
		this.physics = physics;
	}

	public boolean isBallBounce() {
		return ballBounce;
	}

	public void setBallBounce(boolean ballBounce) {
		this.ballBounce = ballBounce;
	}

	public boolean isBuoyancy() {
		return buoyancy;
	}

	public void setBuoyancy(boolean buoyancy) {
		this.buoyancy = buoyancy;
	}

	public boolean isFireRemove() {
		return fireRemove;
	}

	public void setFireRemove(boolean fireRemove) {
		this.fireRemove = fireRemove;
	}

	public boolean isSteamChange() {
		return steamChange;
	}

	public void setSteamChange(boolean steamChange) {
		this.steamChange = steamChange;
	}
}
