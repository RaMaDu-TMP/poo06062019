package br.edu.usf.poo.view.components.autocomplete.document;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface DocumentAdapter extends DocumentListener {

	@Override
	default void changedUpdate(DocumentEvent e) {
		textChanged(e);
	}

	@Override
	default void insertUpdate(DocumentEvent e) {
		textChanged(e);
	}

	@Override
	default void removeUpdate(DocumentEvent e) {
		textChanged(e);
	}

	void textChanged(DocumentEvent e);
}
