package br.edu.usf.poo.view.components.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.text.Element;
import javax.swing.text.PasswordView;
import javax.swing.text.View;

public class HintPasswordFieldUI extends HintTextFieldUI {

	public HintPasswordFieldUI(String hint) {
		super(hint);
	}
	
	public HintPasswordFieldUI(String hint, Color color) {
		super(hint, color);
	}
	
	public HintPasswordFieldUI(String hint, Color color, Font hintFont) {
		super(hint, color, hintFont);
	}

	/**
     * Creates a UI for a JPasswordField.
     *
     * @param c the JPasswordField
     * @return the UI
     */
    public static ComponentUI createUI(JComponent c) {
        return new BasicPasswordFieldUI();
    }

    /**
     * Fetches the name used as a key to look up properties through the
     * UIManager.  This is used as a prefix to all the standard
     * text properties.
     *
     * @return the name ("PasswordField")
     */
    protected String getPropertyPrefix() {
        return "PasswordField";
    }


    /**
     * Installs the necessary properties on the JPasswordField.
     * @since 1.6
     */
    protected void installDefaults() {
        super.installDefaults();
        String prefix = getPropertyPrefix();
        Character echoChar = (Character)UIManager.getDefaults().get(prefix + ".echoChar");
        if(echoChar != null) {
            LookAndFeel.installProperty(getComponent(), "echoChar", echoChar);
        }
    }

    /**
     * Creates a view (PasswordView) for an element.
     *
     * @param elem the element
     * @return the view
     */
    public View create(Element elem) {
        return new PasswordView(elem);
    }
    
}
