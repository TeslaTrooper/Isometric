package panels.editor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import core.PicLoader;

/**
 * Diese Klasse hat die Aufgabe eines "Werkzeugkoffers". Objekte dieser Klasse werden
 * als {@link JPanel} auf dem Bildschirm dargestellt. In diesem Panel befinden sich
 * weitere Panels, welche für eine weitere Kategorisierung der Spielweltobjekte 
 * zuständig sind. Dazu zählen das {@link LandscapeObjectsPanel} sowie das {@link ItemsPanel}.
 * @author Stefan
 *
 */
@SuppressWarnings("serial")
public class ConstructionToolsPanel extends JPanel implements ActionListener {
	
	private int width = 300;
	private int height = 300;
	
	private int gamePanel_width;
	private int gamePanel_height;
	
	private JButton btnLandscape, btnTools, btnTerrain, btnMinimize;
	
	private LandscapeObjectsPanel landscapeObjectsPanel;
	private ItemsPanel itemsPanel;
	private TerrainPanel terrainPanel;
	
	public boolean[] panelStates = new boolean[3];
	
	public ConstructionToolsPanel(int w, int h, PicLoader picLoader) {
		this.setLayout(null);
		this.setBackground(Color.orange);
		this.setSize(width, height);
		this.setLocation(0, h-(this.height));
		this.gamePanel_width = w;
		this.gamePanel_height = h;
		
		btnLandscape = new JButton("Landschaft");
		btnLandscape.setBounds(5, 5, 100, 20);
		btnLandscape.addActionListener(this);
		this.add(btnLandscape);
		
		btnTools = new JButton("Items");
		btnTools.setBounds(110, 5, 70, 20);
		btnTools.addActionListener(this);
		this.add(btnTools);
		
		btnTerrain = new JButton("Terrain");
		btnTerrain.setBounds(185, 5, 75, 20);
		btnTerrain.addActionListener(this);
		this.add(btnTerrain);
		
		btnMinimize = new JButton("-");
		btnMinimize.setBounds(width-(5+40), 5, 40, 20);
		btnMinimize.addActionListener(this);
		this.add(btnMinimize);
		
		landscapeObjectsPanel = new LandscapeObjectsPanel(width, height, picLoader);
		landscapeObjectsPanel.setVisible(false);
		this.add(landscapeObjectsPanel);
		
		itemsPanel = new ItemsPanel(width-10, height-25);
		itemsPanel.setVisible(true);
		this.add(itemsPanel);
		
		terrainPanel = new TerrainPanel(width, height);
		terrainPanel.setVisible(false);
		this.add(terrainPanel);
	}
	
	/**
	 * Über diese Methode kann das {@link LandscapeObjectsPanel} ermittelt werden.
	 * @return Gibt das {@link LandscapeObjectsPanel} zurück.
	 */
	public LandscapeObjectsPanel getLandscapeObjectsPanel() {
		return this.landscapeObjectsPanel;
	}
	
	public TerrainPanel getTerrainPanel() {
		return this.terrainPanel;
	}
	
	private void resetPanelStates() {
		for(int i = 0; i<panelStates.length; i++) {
			panelStates[i] = false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLandscape) {
			landscapeObjectsPanel.setVisible(true);
			itemsPanel.setVisible(false);
			terrainPanel.setVisible(false);
			
			resetPanelStates();
			panelStates[0] = true;
		} else if(e.getSource() == btnTools) {
			landscapeObjectsPanel.setVisible(false);
			itemsPanel.setVisible(true);
			terrainPanel.setVisible(false);
			
			resetPanelStates();
			panelStates[1] = true;
		} else if(e.getSource() == btnTerrain) {
			landscapeObjectsPanel.setVisible(false);
			itemsPanel.setVisible(false);
			terrainPanel.setVisible(true);
			
			resetPanelStates();
			panelStates[2] = true;
		} else if(e.getSource() == btnMinimize) {
			if(btnMinimize.getText().contentEquals("-")) {
				this.setSize(40, 20);
				this.setLocation(gamePanel_width-this.getWidth(), 0);
				btnMinimize.setLocation(0, 0);
				btnLandscape.setVisible(false);
				btnMinimize.setText("+");
			} else {
				this.setSize(width, height);
				this.setLocation(0, gamePanel_height-(this.height));
				btnMinimize.setLocation(width-(5+40), 5);
				btnLandscape.setVisible(true);
				btnMinimize.setText("-");
			}
		}
	}
	
	

}
