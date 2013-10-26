package ro7.engine.world.entities;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public abstract class GroupEntity extends Entity {
	
	protected int groupIndex;

	protected GroupEntity(GameWorld world, Vec2f position, CollidingShape shape, Map<String, String> properties) {
		super(world, position, shape);
		this.groupIndex = Integer.parseInt(properties.get("groupIndex"));
	}
	
	protected GroupEntity(GameWorld world, Vec2f position, int groupIndex) {
		super(world, position, null);
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
