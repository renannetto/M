package ro7.engine.sprites.shapes;


public class Range {

	private float min;
	private float max;

	public Range(float min, float max) {
		this.min = min;
		this.max = max;
	}

	public boolean overlaps(Range range2) {
		return this.min <= range2.max && range2.min <= this.max;
	}

}
