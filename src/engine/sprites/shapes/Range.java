package ro7.engine.sprites.shapes;

import cs195n.Vec2f;

public class Range {

	private Vec2f min;
	private Vec2f max;

	public Range(Vec2f min, Vec2f max) {
		this.min = min;
		this.max = max;
	}

	public boolean overlaps(Range range2) {
		return this.min.mag2() <= range2.max.mag2() && range2.min.mag2() <= this.max.mag2();
	}

}
