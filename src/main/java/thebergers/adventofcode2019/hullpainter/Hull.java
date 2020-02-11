package thebergers.adventofcode2019.hullpainter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hull {

	private List<Panel> panels;

	public Hull() {
		this.panels = new ArrayList<>();
	}
	
	public long countPaintedPanels() {
		return panels
			.stream()
			.filter(panel -> panel.isPainted())
			.count();
	}

	public Panel addPanel(Integer x, Integer y) {
		Panel panel = new Panel(x, y);
		panels.add(panel);
		return panel;
	}
	
	public Optional<Panel> findPanel(Integer x, Integer y) {
		return panels
			.stream()
			.filter(panel -> panel.getX().equals(x) && panel.getY().equals(y))
			.findFirst();
	}

	public void paintPanel(Panel panel, Colour colour) {
		Optional<Panel> panelOpt = findPanel(panel.getX(), panel.getY());
		if (!panelOpt.isPresent()) {
			throw new IllegalArgumentException(String.format("Panel %s not found on the Hull!", panel));
		}
		Panel toPaint = panelOpt.get();
		toPaint.setColour(colour);
		toPaint.setPainted(true);
	}

	public Panel goToPanel(int x, int y) {
		Optional<Panel> panelOpt = findPanel(x, y);
		if (panelOpt.isPresent()) {
			return panelOpt.get();
		}
		return addPanel(x, y);
	}
}
