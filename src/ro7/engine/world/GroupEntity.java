package ro7.engine.world;

import cs195n.Vec2f;

public abstract class GroupEntity extends Entity {
	
	protected int groupIndex;

	protected GroupEntity(GameWorld world, Vec2f position, int groupIndex) {
		super(world, position);
		this.groupIndex = groupIndex;
	}
	
	public boolean collidable(GroupEntity other) {
		if (this.groupIndex != other.groupIndex) {
			return true;
		}
		if (this.groupIndex > 0 && other.groupIndex > 0) {
			return true;
		}
		return false;
	}

}
