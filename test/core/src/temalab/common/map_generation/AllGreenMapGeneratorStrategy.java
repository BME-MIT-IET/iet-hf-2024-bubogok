package temalab.common.map_generation;

import temalab.model.Field;
import temalab.model.Position;

import java.util.HashMap;
import java.util.Map;

public class AllGreenMapGeneratorStrategy implements MapGeneratorStrategy {

	@Override
	public Map<Position, Field> generateMap(int mapSize) {
		Map<Position, Field> fields = new HashMap<>();
		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				var temPos = new Position(i, j);
				fields.put(temPos, new Field(temPos, Field.Type.GRASS));
			}
		}
		return fields;
	}
}
