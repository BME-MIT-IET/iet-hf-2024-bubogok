package temalab.common.map_generation;

import temalab.model.Field;
import temalab.model.Position;
import temalab.util.SimplexNoise;

import java.util.HashMap;
import java.util.Map;

public class SimplexMapGeneratorStrategy implements MapGeneratorStrategy {
	@Override
	public Map<Position, Field> generateMap(int mapSize) {
		Map<Position, Field> fields = new HashMap<>();
		for (int i = 0; i < mapSize; i++) {
			for (int j = 0; j < mapSize; j++) {
				var temPos = new Position(i, j);
				var noiseProb = SimplexNoise.noise(i / 30.0, j / 30.0, 10);
				if (-1 < noiseProb && noiseProb <= 0) {
					fields.put(temPos, new Field(temPos, Field.Type.GRASS));
				} else if (0 < noiseProb && noiseProb <= 0.2) {
					fields.put(temPos, new Field(temPos, Field.Type.WATER));
				} else if (0.2 < noiseProb && noiseProb <= 0.4) {
					fields.put(temPos, new Field(temPos, Field.Type.FOREST));
				} else if (0.4 < noiseProb && noiseProb <= 0.6) {
					fields.put(temPos, new Field(temPos, Field.Type.BUILDING));
				} else if (0.6 < noiseProb && noiseProb <= 1) {
					fields.put(temPos, new Field(temPos, Field.Type.MARSH));
				}
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				fields.replace(new Position(i, j), new Field(new Position(i, j), Field.Type.GRASS));
			}
		}
		for (int i = mapSize - 1; i > mapSize - 3; i--) {
			for (int j = mapSize - 1; j > mapSize - 3; j--) {
				fields.replace(new Position(i, j), new Field(new Position(i, j), Field.Type.GRASS));
			}
		}
		return fields;
	}
}
