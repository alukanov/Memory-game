package MemoryGameGrafic;

import java.io.Serializable;
import javax.swing.Icon;
import javax.swing.JButton;

public class Buttons extends JButton implements Serializable{

	Icon icon;

	public Buttons(Icon icon, String name) {
		super();
		this.icon = icon;
		this.setName(name);
	}

}
