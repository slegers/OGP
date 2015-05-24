package worms.model;

import static org.junit.Assert.*;

import org.junit.Test;

import worms.model.Worm;
import worms.util.Util;

public class WormTest {
	
	
	private static final double EPS = Util.DEFAULT_EPSILON;
	
	@Test
	public void testMoveHorizontal() {
		Worm worm = new Worm(0, 0, 0, 1, "Ed O'Sullivan");
		worm.move(5);
		assertEquals(worm.getXCoordinate(), 5, EPS);
		worm.setOrientation(Math.PI);
		worm.move(10);
		assertEquals(worm.getXCoordinate(), -5, EPS);
	}
	
	@Test
	public void testMoveVertical() {
		Worm worm = new Worm(0, 0, Math.PI / 2, 1, "Ed O'Sullivan");
		worm.move(5);
		assertEquals(worm.getYCoordinate(), 5, EPS);
		worm.setOrientation(Math.PI * 3 / 2);
		worm.move(10);
		assertEquals(worm.getYCoordinate(), -5, EPS);
	}
	
	@Test
	public void testSetOrientation() {
		Worm worm = new Worm(0, 0, Math.PI / 2, 1, "Ed O'Sullivan");
		assertEquals(worm.getOrientation(), Math.PI / 2, EPS);
		worm.setOrientation(2);
		assertEquals(worm.getOrientation(), 2, EPS);
	}
	
	@Test(expected=AssertionError.class)
	public void testSetOrientationException1() {
		Worm worm = new Worm(0, 0, Math.PI / 2, 1, "Ed O'Sullivan");
		worm.setOrientation(-1);
	}
	
	@Test(expected=AssertionError.class)
	public void testSetOrientationException2() {
		Worm worm = new Worm(0, 0, Math.PI / 2, 1, "Ed O'Sullivan");
		worm.setOrientation(Math.PI * 2);
	}
	
	@Test
	public void testIsValidOrientationCaseTrue() {
		assertTrue(Worm.isValidOrientation(0));
		assertTrue(Worm.isValidOrientation(Math.PI));
		assertTrue(Worm.isValidOrientation(Math.PI * 2 - 1));	
	}
	
	@Test
	public void testIsValidOrientationCaseFalse() {
		assertFalse(Worm.isValidOrientation(-1));
		assertFalse(Worm.isValidOrientation(-Math.PI));		
		assertFalse(Worm.isValidOrientation(Math.PI * 2));
		assertFalse(Worm.isValidOrientation(Math.PI * 3));
	}
	
	@Test 
	public void canTurnCaseTrue() {
		Worm worm = new Worm(0, 0, 0, 1, "Ed O'Sullivan");
		assertTrue(worm.canTurn(Math.PI));
		assertTrue(worm.canTurn(-Math.PI));
		assertTrue(worm.canTurn(2 * Math.PI));
	}
	
	@Test
	public void canTurnCaseFalse() {
		Worm worm = new Worm(0, 0, Math.PI / 2, 1, "Ed O'Sullivan");
		while(worm.canMove(1)) {
			worm.move(1);
		}		
		assertFalse(worm.canTurn(Math.PI));
		assertFalse(worm.canTurn(1));
	}
	
	@Test
	public void testTurnCommonCase() {
		Worm worm = new Worm(0, 0, 0, 1, "Ed O'Sullivan");
		worm.turn(Math.PI);
		assertEquals(worm.getOrientation(), Math.PI, EPS);
	}
	
	@Test
	public void testTurnCasePast2PiArg() {
		Worm worm = new Worm(0, 0, 0, 1, "Ed O'Sullivan");
		worm.turn(3 * Math.PI);
		assertEquals(worm.getOrientation(), Math.PI, EPS);
	}
	
	@Test
	public void testTurnCaseNegativeArg() {
		Worm worm = new Worm(0, 0, 0, 1, "Ed O'Sullivan");
		worm.turn(-Math.PI);
		assertEquals(worm.getOrientation(), 3/2*Math.PI, EPS);
	}
	
	@Test
	public void testSetRadius() {
		Worm worm = new Worm(0, 0, 0, 1, "Ed O'Sullivan");
		assertEquals(worm.getRadius(), 1, EPS);
		worm.setRadius(15);
		assertEquals(worm.getRadius(), 15, EPS);
		worm.setRadius(worm.getMinimalRadius());
		assertEquals(worm.getRadius(), worm.getMinimalRadius(), EPS);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetRadiusException() {
		Worm worm = new Worm(0, 0, 0, 1, "Ed O'Sullivan");
		double minimalRadius = worm.getMinimalRadius();
		worm.setRadius(minimalRadius - 0.001);
	}
	
	@Test
	public void testIsValidRadiusCaseTrue() {
		Worm worm = new Worm(0,0,0,1,"Eddy");
		assertTrue(Worm.isValidRadius(worm.getMinimalRadius(), worm));
		assertTrue(Worm.isValidRadius(20, worm));
	}
	
	@Test
	public void testIsValidRadiusCaseFalse() {
		Worm worm = new Worm(0,0,0,1,"Eddy");
		assertFalse(Worm.isValidRadius(worm.getMinimalRadius() - 0.001, worm));
		assertFalse(Worm.isValidRadius(-1, worm));
	}
	
	@Test
	public void testSetName() {
		Worm worm = new Worm(0, 0, 0, 1, "Ed O'Sullivan");
		worm.setName("James");
		assertEquals(worm.getName(), "James");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNameCaseNoCapital() {
		Worm worm = new Worm(0, 0, 0, 1, "Ed O'Sullivan");
		worm.setName("higgins");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNameCaseIllegalChar() {
		Worm worm = new Worm(0, 0, 0, 1, "Ed O'Sullivan");
		worm.setName("Dr. Dre");
	}
	@Test
	public void testJump(){
		Worm worm = new Worm(0, 0, Math.PI / 2, 1, "Ed O'Sullivan");
		worm.jump();
		assertEquals(worm.getYCoordinate(), 0, EPS);
		assertEquals(worm.getXCoordinate(), (Math.pow((((5.0 * worm.getActionPoints())
				+ (worm.getMass() * 9.80665)) / worm.getMass() * 0.5), 2) 
				 * Math.sin(2.0 * worm.getOrientation())) / 9.80665, EPS);
	}
	

}
