package thebergers.adventofcode2019.hullpainter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Hull {

	private static final Logger LOG = LoggerFactory.getLogger(Hull.class);

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

	public Panel addPanel(Integer x, Integer y, Colour colour) {
		Panel panel = new Panel(x, y);
		panel.setColour(colour);
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

	public List<String> visualise() {
		Integer minX = panels.stream().min(Comparator.comparing(Panel::getX)).get().getX();
		Integer maxX = panels.stream().max(Comparator.comparing(Panel::getX)).get().getX();
		Integer minY = panels.stream().min(Comparator.comparing(Panel::getY)).get().getY();
		Integer maxY = panels.stream().max(Comparator.comparing(Panel::getY)).get().getY();
		panels.stream().forEach(panel -> LOG.info("{}", panel));
		//LOG.info("minX: {}, maxX: {}, minY: {}, maxY: {}", minX, maxX, minY, maxY);
		panels = fillBlanks(panels, minX, maxX, minY, maxY);
		return panels
			.stream()
			.collect(groupingBy(Panel::getY))
			.entrySet()
			.stream()
			.sorted(comparingByKey(Comparator.reverseOrder()))
			.collect(toMap(e -> e.getKey(),e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new))
			.entrySet()
			.stream()
			.map(e -> visualiseRow(e.getValue()))
			.collect(Collectors.toList());
	}

	private String visualiseRow(List<Panel> panels) {
		return panels
			.stream()
			.sorted(Comparator.comparing(Panel::getX))
			.map(Panel::visualise)
			.collect(Collectors.joining());
	}

	private List<Panel> fillBlanks(List<Panel> panels, Integer minX, Integer maxX, Integer minY, Integer maxY) {
		for (Integer x = minX; x <= maxX;x++) {
			for (Integer y = minY; y <= maxY;y++) {
				Panel panelToFind = new Panel(x, y);
				if (!panels.contains(panelToFind)) {
					panels.add(panelToFind);
				}
			}
		}
		return panels;
	}
}
