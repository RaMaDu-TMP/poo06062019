package br.edu.usf.poo.controller;

import br.edu.usf.poo.view.FrmSkate;

public class SkateController {
	private static SkateController instance;
	
	public static SkateController gi() {

		if (instance == null) {
			instance = new SkateController();
		}
		
		return instance;
	}
	
	private SkateController() {
		super();
	}
	
	public void openCreateSkateForm() {
		FrmSkate frmSkate = new FrmSkate();
		frmSkate.setVisible(true);
	}
}
