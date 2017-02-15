package MemoryGameGrafic;

import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class IconArray implements Serializable{

	private Icon[] icons;

	public IconArray(int iconArrayLength) {
		icons = new Icon[iconArrayLength];
		for (int i = 0; i < iconArrayLength; i++) {
			icons[i] = new ImageIcon("icons/" + i + ".png");
		}
		for (int i = iconArrayLength / 2, j = 0; i < iconArrayLength; i++, j++) {
			icons[i] = new ImageIcon("icons/" + j + ".png");
		}
	}

	public Icon[] getIcons() {
		return icons;
	}
}
