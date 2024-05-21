package temalab.common.map_generation;

import temalab.model.Field;
import temalab.model.Position;

import java.util.Map;

public interface MapGeneratorStrategy {
	Map<Position, Field> generateMap(int mapSize);
}
