package thebergers.adventofcode2019.hullpainter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hull {

	private List<Panel> panels;
	
	public Hull() {
		this.panels = new ArrayList<>();
	}
	
	public Integer countPaintedPanels() {
		return 0;
	}
	
	public void addPanel(Integer x, Integer y) {
		
	}
	
	public Optional<Panel> findPanel(Integer x, Integer y) {
		Integer index = panels.indexOf(new Panel(x, y));
		if (index >= 0) {
			return Optional.of(panels.get(index));
		}
		return Optional.empty();
	}
	
	public void paintPanel(Integer x, Integer y, Colour colour) {
		
	}
}
