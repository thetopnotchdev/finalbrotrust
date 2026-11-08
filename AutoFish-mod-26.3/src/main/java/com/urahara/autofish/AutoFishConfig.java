package com.urahara.autofish;

/**
 * Shared, mutable runtime settings. A single static instance so the tick
 * logic, HUD overlay, and settings screen all read/write the same state.
 */
public class AutoFishConfig {

	public static boolean enabled = false;

	// how sharp the bobber's downward dip must be to count as a bite
	public static double biteThreshold = -0.02;

	// ticks to ignore right after casting (lets the initial splash settle)
	public static int minSettleTicks = 15;

	// ticks to wait after reeling in before casting again
	public static int recastDelayTicks = 10;

	// after the fish bites, wait a random number of ticks in [min, max] before
	// reeling in. Max is capped below the ~20 tick bite window.
	public static final int MAX_REACT_LIMIT = 18;
	public static int reactMinTicks = 3;
	public static int reactMaxTicks = 12;
}
